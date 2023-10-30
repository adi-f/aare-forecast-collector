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

  Instant timestamp;
}
