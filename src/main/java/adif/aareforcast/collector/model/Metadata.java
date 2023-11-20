package adif.aareforcast.collector.model;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Metadata {
  long numberOfAareGuruEntries;
  long numberOfMeteoEntries;
  OffsetDateTime latestAareGuruEntriy;
  OffsetDateTime latestMeteoSwissEntriy;
}
