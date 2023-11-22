package adif.aareforecast.collector.model.aareguru;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class AareGuruEntry {

  @Id
  Long id;
  Location location;
  OffsetDateTime timestamp;
  Float currentWaterTemperatureCelsius;
  Integer currentFlowCubeMetersPerSecond; // m³/s
  Float forecast2hWaterTemperatureCelsius;
  OffsetDateTime timestampCurrentWeather;
  Float currentAirTemperatureCelsius;
  Float currentRainfallMmPer10min; // mm/10min

  OffsetDateTime weatherForecastTomorrowTimestamp;
  WeatherSymbol weatherForecastTomorrowSymbol;
  Integer weatherForecastTomorrowDayMaxAirTemperatureCelsius;
  Integer weatherForecastTomorrowDayMinAirTemperatureCelsius;
  Integer weatherForecastTomorrowRainfallMmPer10min; // mm/10min
  Integer weatherForecastTomorrowRainRiskPercentage;

}
