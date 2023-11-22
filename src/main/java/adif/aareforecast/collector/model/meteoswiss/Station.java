package adif.aareforecast.collector.model.meteoswiss;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Station {
// https://data.geo.admin.ch/ch.meteoschweiz.messnetz-automatisch/ch.meteoschweiz.messnetz-automatisch_de.csv

  MEIRINGEN("MER"),
  BRIENZ("BRZ"),
  INTERLAKEN("INT"),
  FRUTIGEN("FRU"),
  THUN("THU"),
  BANTIGER("BAN"),
  BERN("BER"),
  MUEHLEBERG("MUB"),
  CRESSIER("CRM"),
  GRENCHEN("GRE");

  private final static Map<String, Station> ID_ENUM_MAPPER = new HashMap<>();
  static {
    for(Station station : Station.values()) {
      ID_ENUM_MAPPER.put(station.getId(), station);
    }
  }

  @Getter
  private final String id;

  public static Station getFromId(String id) {
    return ID_ENUM_MAPPER.get(id);
  }
}
