package adif.aareforcast.collector.model.aareguru;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Metadata {
  long numberOfAareGuruEntries;
}
