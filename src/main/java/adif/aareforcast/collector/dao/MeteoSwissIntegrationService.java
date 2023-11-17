package adif.aareforcast.collector.dao;

import adif.aareforcast.collector.model.meteoswiss.MeteoEntry;
import adif.aareforcast.collector.model.meteoswiss.Station;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

public class MeteoSwissIntegrationService {

  // General: https://opendata.swiss/de/dataset/automatische-wetterstationen-aktuelle-messwerte
  // Description (ZIP): https://data.geo.admin.ch/ch.meteoschweiz.messwerte-aktuell/data.zip
  // CSV Column description: https://data.geo.admin.ch/ch.meteoschweiz.messwerte-aktuell/info/VQHA80_de.txt
  // Stations description: https://data.geo.admin.ch/ch.meteoschweiz.messnetz-automatisch/ch.meteoschweiz.messnetz-automatisch_de.csv
  // Nice UI: https://www.meteoschweiz.admin.ch/service-und-publikationen/applikationen/messwerte-und-messnetze.html#param=messnetz-automatisch&lang=de&station=KIS&chart=hour&table=false
  private static final String URL = "https://data.geo.admin.ch/ch.meteoschweiz.messwerte-aktuell/VQHA80.csv";

  private static final String NO_VALUE = "-";
  private static final String CSV_SEPARATOR = ";";
  private static final DateTimeFormatter METEO_SWISS_FORMAT = DateTimeFormatter.ofPattern("uuuuMMddHHmmss");
  private static final ZoneId METEO_SWISS_TIME_ZONE = ZoneId.of("Europe/Zurich");



  public List<MeteoEntry> readMeteo(List<Station> filter) {
    String rawCsv = readMeteoRawCsv();
    Scanner csvLines = new Scanner(rawCsv);
    Set<String> wantedStations = filter.stream().map(Station::getId).collect(Collectors.toUnmodifiableSet());
    ColumnReader columnReader = new ColumnReader(splitLine(csvLines.nextLine()));
    List<MeteoEntry> result = new ArrayList<>();

    while (csvLines.hasNextLine()) {
      List<String> csvLine = splitLine(csvLines.nextLine());
      Optional<String> station = columnReader.readString(Column.STATION, csvLine);
      if(!station.map(wantedStations::contains).orElse(false)) {
        continue;
      }
      result.add(columnReader.parseMeteoEntry(csvLine));
    }
    return Collections.unmodifiableList(result);
  }
  private String readMeteoRawCsv() {
    return ""; // TODO
  }

  private List<String> splitLine(String line) {
    return List.of(line.split(CSV_SEPARATOR));
  }
  static class ColumnReader {
    private final Map<String, Integer> columnNameIndexMapping;

    ColumnReader(List<String> headerRow) {
      Map<String, Integer> nameIndexMapping = new HashMap<>(headerRow.size());
      for(int i = 0; i < headerRow.size(); i++) {
        nameIndexMapping.put(headerRow.get(i), i);
      }
      this.columnNameIndexMapping = Collections.unmodifiableMap(nameIndexMapping);
    }

    Optional<String> readString(Column column, List<String> row) {
      Integer index = columnNameIndexMapping.get(column.id);
      if(index == null) {
        throw new IllegalArgumentException("unknown column " + column.id);
      }
      if(index >= row.size()) {
        return Optional.empty();
      }
      return Optional
          .ofNullable(StringUtils.trimToNull(row.get(index)))
          .map(str -> NO_VALUE.equals(str) ? null : str);
    }

    Optional<Station> readStation(Column column, List<String> row) {
      return readString(column, row).map(Station::getFromId);
    }
    Optional<OffsetDateTime> readTimestamp(Column column, List<String> row) {
      return readString(column, row)
          .map(str -> LocalDateTime.parse(str, METEO_SWISS_FORMAT))
          .map(timestamp -> timestamp.atZone(METEO_SWISS_TIME_ZONE).toOffsetDateTime());
    }

    Optional<Float> readFloat(Column column, List<String> row) {
      return readString(column, row).map(Float::parseFloat);
    }
    Optional<Integer> readInteger(Column column, List<String> row) {
      return readString(column, row).map(Integer::parseInt);
    }

    <T> T readFirstPresent(Column columnPreferred, Column columnFallback, BiFunction<Column, List<String>, Optional<T>> readMapper, List<String> row) {
      return readMapper.apply(columnPreferred, row).orElseGet(() -> readMapper.apply(columnFallback, row).orElse(null));
    }


    public MeteoEntry parseMeteoEntry(List<String> csvLine) {
      return MeteoEntry.builder()
          .station(readStation(Column.STATION, csvLine).orElse(null))
          .timestamp(readTimestamp(Column.TIMESTAMP, csvLine).orElse(null))
          .airTemperatureCelcius(readFirstPresent(Column.AIR_TEMPERATURE1, Column.AIR_TEMPERATURE2, this::readFloat, csvLine))
          .rainMm(readFloat(Column.RAIN, csvLine).orElse(null))
          .sunIntensityWattPerQuareMeter(readFloat(Column.SUN, csvLine).orElse(null))
          .humidityPercent(readFirstPresent(Column.HUMIDITY1, Column.HUMIDITY2, this::readFloat, csvLine))
          .windDirection360Degree(readFirstPresent(Column.WIND_DIRECTION1, Column.WIND_DIRECTION2, this::readInteger, csvLine))
          .windSpeendKmh(readFirstPresent(Column.WIND_SPEED1, Column.WIND_SPEED2, this::readFloat, csvLine))
          .build();
    }
  }
  @AllArgsConstructor
  enum Column {
    STATION("Station/Location"),
    TIMESTAMP("Date"),
    AIR_TEMPERATURE1("tre200s0"),
    AIR_TEMPERATURE2("ta1tows0"),
    RAIN("rre150z0"),
    SUN("gre000z0"),
    HUMIDITY1("ure200s0"),
    HUMIDITY2("uretows0"),
    WIND_DIRECTION1("dkl010z0"),
    WIND_DIRECTION2("dv1towz0"),
    WIND_SPEED1("fu3010z0"),
    WIND_SPEED2("fu3towz0");

    private final String id;
  }
}
