package adif.aareforcast.collector.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Location {
  BERN("bern");

  @Getter
  private final String city;
}
