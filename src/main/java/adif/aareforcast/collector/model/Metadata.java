package adif.aareforcast.collector.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Metadata {
  long numberOfAareGuruEntries;
  long numberOfMeteoEntries;
}
