package adif.aareforcast.collector.model;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class AareGuruEntry {

  @Id
  Long id;
  Location location;
  Instant timestamp;
  Float currentWaterTemperatureCelsius;
  Integer currentFlowCubeMetersPerSecond; // m³/s
  Integer forecast2hFlowCubeMetersPerSecond; // m³/s
  Instant timestampCurrentWeather;
  Float currentAirTemperatureCelsius;
  Float currentRainfallMmPer10nin; // mm/10min

  Instant weatherForecastTomorrowTimestamp;
  WeatherSymbol weatherForecastTomorrowSymbol;
  Integer weatherForecastTomorrowDayMaxAirTemperatureCelsius;
  Integer weatherForecastTomorrowDayMinAirTemperatureCelsius;
  Integer weatherForecastTomorrowRainfallMmPer10nin; // mm/10min
  Integer weatherForecastTomorrowRainRiskPercentage;

}
