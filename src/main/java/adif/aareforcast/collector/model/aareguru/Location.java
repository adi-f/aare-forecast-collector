package adif.aareforcast.collector.model.aareguru;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Location {
  BRIENZ("brienz"),
  INTERLAKEN("interlaken"),
  THUN("thun"),
  BERN("bern"),
  HAGNECK("hagneck"),
  BIEL("biel"),
  OLTEN("olten"),
  BRUGG("brugg");

  @Getter
  private final String city;
}
