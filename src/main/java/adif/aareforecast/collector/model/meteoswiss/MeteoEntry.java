package adif.aareforecast.collector.model.meteoswiss;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class MeteoEntry {

  @Id
  Long id;
  Station station;
  OffsetDateTime timestamp;
  Float airTemperatureCelcius;
  Float rainMm;
  Float sunIntensityWattPerSquareMeter; // seems to be an integer
  Float humidityPercent;
  Float windDirection360Degree; // seems to be an integer
  Float windSpeedKmh;
}
