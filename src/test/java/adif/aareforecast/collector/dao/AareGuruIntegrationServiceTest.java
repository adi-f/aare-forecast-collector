package adif.aareforecast.collector.dao;

import static adif.aareforecast.collector.Helper.readRessource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import adif.aareforecast.collector.model.aareguru.AareGuruEntry;
import adif.aareforecast.collector.model.aareguru.Location;
import adif.aareforecast.collector.model.aareguru.WeatherSymbol;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class AareGuruIntegrationServiceTest {

  @Mock
  private RestTemplate aareGuruRestTemplate;

  @InjectMocks
  private AareGuruIntegrationService aareGuruIntegrationService;

  @BeforeEach
  void setUp() {
    when(aareGuruRestTemplate.getForObject(anyString(), eq(String.class)))
        .thenReturn(readRessource("data/aareguru_1700303402.txt"));
  }

  @Test
  void testReadCurrent() {
    AareGuruEntry expected = AareGuruEntry.builder()
        .location(Location.BERN)
        .timestamp(OffsetDateTime.parse("2023-11-18T11:30:02+01:00"))
        .currentWaterTemperatureCelsius(10.11f)
        .currentFlowCubeMetersPerSecond(285)
        .forecast2hWaterTemperatureCelsius(10.2f)
        .timestampCurrentWeather(OffsetDateTime.parse("2023-11-18T11:30+01:00"))
        .currentAirTemperatureCelsius(5.2f)
        .currentRainfallMmPer10min(0f)
        .weatherForecastTomorrowTimestamp(OffsetDateTime.parse("2023-11-19T11:50+01:00"))
        .weatherForecastTomorrowSymbol(WeatherSymbol.RAIN_SUN)
        .weatherForecastTomorrowDayMaxAirTemperatureCelsius(13)
        .weatherForecastTomorrowDayMinAirTemperatureCelsius(5)
        .weatherForecastTomorrowRainfallMmPer10min(5)
        .weatherForecastTomorrowRainRiskPercentage(90)
        .build();
    AareGuruEntry result = aareGuruIntegrationService.readCurrent(Location.BERN);
    assertEquals(expected, result);
  }
}