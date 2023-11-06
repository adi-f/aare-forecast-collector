package adif.aareforcast.collector.dao;

import adif.aareforcast.collector.model.AareGuruEntry;
import java.time.Instant;
import org.springframework.data.repository.CrudRepository;

public interface AareGuruEntryRepository extends CrudRepository<AareGuruEntry, Long> {

  long countByTimestamp(Instant timestamp);
}
