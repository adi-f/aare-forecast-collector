package adif.aareforecast.collector.dao;

import static adif.aareforecast.collector.Helper.readRessource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import adif.aareforecast.collector.model.meteoswiss.MeteoEntry;
import adif.aareforecast.collector.model.meteoswiss.Station;
import java.time.OffsetDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class MeteoSwissIntegrationServiceTest {

  @Mock
  private RestTemplate meteoSwissRestTemplate;

  @InjectMocks
  private MeteoSwissIntegrationService meteoSwissIntegrationService;

  @BeforeEach
  void setUp() throws IllegalAccessException {
    when(meteoSwissRestTemplate.getForObject((String)any(), eq(String.class)))
        .thenReturn(readRessource("data/VQHA80_202311172120.csv"));
  }

  @Test
  void testReadMeteo() {
    List<MeteoEntry> expected = List.of(
        MeteoEntry.builder()
            .station(Station.BANTIGER)
            .timestamp(OffsetDateTime.parse("2023-11-17T21:20+01:00"))
            .airTemperatureCelcius(1.7f)
            .sunIntensityWattPerSquareMeter(1.0f)
            .humidityPercent(92.2f)
            .windDirection360Degree(246f)
            .windSpeedKmh(26.3f)
            .build(),
        MeteoEntry.builder()
            .station(Station.THUN)
            .timestamp(OffsetDateTime.parse("2023-11-17T21:20+01:00"))
            .airTemperatureCelcius(4.6f)
            .rainMm(0f)
            .sunIntensityWattPerSquareMeter(0f)
            .humidityPercent(99.2f)
            .windDirection360Degree(87f)
            .windSpeedKmh(3.6f)
            .build()
    );
    List<MeteoEntry> result = meteoSwissIntegrationService.readMeteo(List.of(Station.THUN, Station.BANTIGER));
    assertEquals(expected, result);
  }
}