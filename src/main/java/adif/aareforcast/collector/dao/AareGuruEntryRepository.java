package adif.aareforcast.collector.dao;

import adif.aareforcast.collector.model.aareguru.AareGuruEntry;
import adif.aareforcast.collector.model.aareguru.Location;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface AareGuruEntryRepository extends CrudRepository<AareGuruEntry, Long> {

  long countByTimestampAndLocation(OffsetDateTime timestamp, Location location);

  Optional<AareGuruEntry> findTopByOrderByTimestampDesc();
}
