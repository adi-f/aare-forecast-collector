package adif.aareforcast.collector.dao;

import adif.aareforcast.collector.model.aareguru.AareGuruEntry;
import adif.aareforcast.collector.model.aareguru.AareGuruEntry.AareGuruEntryBuilder;
import adif.aareforcast.collector.model.aareguru.Location;
import adif.aareforcast.collector.model.aareguru.WeatherSymbol;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AareGuruIntegrationService implements InitializingBean {

  private static final int NUMBER_OF_FORCAST_DAYS = 1; // max=3 (4 days)

  private static final ZoneId AARE_LOCAL_TIME_ZONE = ZoneId.of("Europe/Zurich");
  private static final List<Field<?>> FIELDS_TO_READ;
  private static final String FIELDS_QUERY;

  private static final Pattern NEW_LINE = Pattern.compile("(\r|\n)+");

  static {
    // Documentation of fields: https://aareguru.existenz.ch/
    // Swagger: https://aareguru.existenz.ch/openapi/#/
    List<Field<?>> fields = new ArrayList<>();
    fields.add(field("aare.timestamp", P::parseTimestamp, AareGuruEntryBuilder::timestamp)); // timestamp UTC in seconds
    fields.add(field("aare.temperature_prec", P::parseFloat, AareGuruEntryBuilder::currentWaterTemperatureCelsius)); // current water temperature °C; floating-point
    fields.add(field("aare.flow", P::parseInteger, AareGuruEntryBuilder::currentFlowCubeMetersPerSecond)); // current flow, m³/s, integer
    fields.add(field("aare.forecast2h", P::parseFloat, AareGuruEntryBuilder::forecast2hWaterTemperatureCelsius)); // forecast+2h water temperature in °C; floating-point
    fields.add(field("weather.current.timestamp", P::parseTimestamp, AareGuruEntryBuilder::timestampCurrentWeather)); // timestamp UTC in seconds
    fields.add(field("weather.current.tt", P::parseFloat, AareGuruEntryBuilder::currentAirTemperatureCelsius)); // current air temperature °C; floating-point
    fields.add(field("weather.current.rrreal", P::parseFloat, AareGuruEntryBuilder::currentRainfallMmPer10min)); // current rainfall, mm/10min; floating-point

    for(int i = 0; i < NUMBER_OF_FORCAST_DAYS; i++) { // 0 = tomorrow
      if(i != 0) throw new RuntimeException("please refactor the DTO-setters below, they are not made yet for looping");
      fields.add(field("weather.forecast." + i + ".timestamp", P::parseTimestamp, AareGuruEntryBuilder::weatherForecastTomorrowTimestamp)); // timestamp UTC in seconds
      fields.add(field("weather.forecast." + i + ".symt", P::parseWeatherSymbol, AareGuruEntryBuilder::weatherForecastTomorrowSymbol)); // weather symbol; integer https://meteotest.ch/en/weather-api/wetter-api-dokumentation/weather-symbols
      fields.add(field("weather.forecast." + i + ".tx", P::parseInteger, AareGuruEntryBuilder::weatherForecastTomorrowDayMaxAirTemperatureCelsius)); // max. air temperature of day °C; integer
      fields.add(field("weather.forecast." + i + ".tn", P::parseInteger, AareGuruEntryBuilder::weatherForecastTomorrowDayMinAirTemperatureCelsius)); // min. air temperature of day °C; integer
      fields.add(field("weather.forecast." + i + ".rr", P::parseInteger, AareGuruEntryBuilder::weatherForecastTomorrowRainfallMmPer10min)); // rainfall, mm/10min; integer
      fields.add(field("weather.forecast." + i + ".rrisk", P::parseInteger, AareGuruEntryBuilder::weatherForecastTomorrowRainRiskPercentage)); // probability of rain; percentage; integer
    }
    FIELDS_TO_READ = Collections.unmodifiableList(fields);
    FIELDS_QUERY = FIELDS_TO_READ.stream().map(Field::getFielName).collect(Collectors.joining(","));
  }

  @Value("${aareForcasrCollector.aareGuruBaseUrl}")
  private String baseUrl;

  @Autowired
  private RestTemplateBuilder restTemplateBuilder;

  private RestTemplate aareGuruRestTemplate;

  public AareGuruEntry readCurrent(Location location) {
    String response = readV2018Current(location.getCity());
    return parse(response).location(location).build();
  }

  private String readV2018Current(String city) {
    String path = UriComponentsBuilder.fromPath("/v2018/current")
        .queryParam("city", city)
        .queryParam("app", "aare-forcast-ML-trainer")
        .queryParam("version", "1")
        .queryParam("values", FIELDS_QUERY)
        .toUriString();
    return aareGuruRestTemplate.getForObject(path, String.class);
  }

  private AareGuruEntryBuilder parse(String response) {
    String[] lines = NEW_LINE.split(response);
    if(lines.length != FIELDS_TO_READ.size()) {
      throw new RuntimeException("Exception at parsing: expected " + FIELDS_TO_READ.size() + " lines, got " + lines.length);
    }
    AareGuruEntryBuilder builder = AareGuruEntry.builder();
    for(int i = 0; i < lines.length; i++) {
      Field<Object> field = (Field<Object>) FIELDS_TO_READ.get(i);
      Object value = field.getParser().apply(lines[i]);
      field.getSetter().accept(builder, value);
    }
    return builder;
  }


  @Override
  public void afterPropertiesSet() {
    aareGuruRestTemplate = restTemplateBuilder
        .rootUri(baseUrl)
        .build();
  }


  private static <T> Field<T> field(String fielName, Function<String, T> parser, BiConsumer<AareGuruEntryBuilder, T> setter) {
    return new Field<>(fielName, parser, setter);
  }
  @lombok.Value
  private static class Field<T> {
    String fielName;
    Function<String, T> parser;
    BiConsumer<AareGuruEntryBuilder, T> setter;
  }

  private static class P {
    private static OffsetDateTime parseTimestamp(String unixTimestamp) {
      return OffsetDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(unixTimestamp)), AARE_LOCAL_TIME_ZONE);
    }

    private static float parseFloat(String number) {
      return Float.parseFloat(number);
    }

    private static int parseInteger(String number) {
      return Integer.parseInt(number);
    }

    private static WeatherSymbol parseWeatherSymbol(String symbolNumberCode) {
      return WeatherSymbol.fromInteger(Integer.parseInt(symbolNumberCode));
    }
  }
}
