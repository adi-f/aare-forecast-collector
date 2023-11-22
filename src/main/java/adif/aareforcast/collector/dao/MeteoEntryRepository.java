package adif.aareforcast.collector.dao;

import adif.aareforcast.collector.model.meteoswiss.MeteoEntry;
import adif.aareforcast.collector.model.meteoswiss.Station;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface MeteoEntryRepository extends CrudRepository<MeteoEntry, Long> {

  long countByTimestampAndStation(OffsetDateTime timestamp, Station station);

  Optional<MeteoEntry> findTopByOrderByTimestampDesc();

}
