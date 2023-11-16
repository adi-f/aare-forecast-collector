package adif.aareforcast.collector.dao;

import adif.aareforcast.collector.model.AareGuruEntry;
import adif.aareforcast.collector.model.Location;
import java.time.OffsetDateTime;
import org.springframework.data.repository.CrudRepository;

public interface AareGuruEntryRepository extends CrudRepository<AareGuruEntry, Long> {

  long countByTimestampAndLocation(OffsetDateTime timestamp, Location location);
}
