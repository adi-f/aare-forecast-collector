package adif.aareforcast.collector.service;

import adif.aareforcast.collector.dao.AareGuruEntryRepository;
import adif.aareforcast.collector.dao.AareGuruIntegrationService;
import adif.aareforcast.collector.model.PollingStatus;
import adif.aareforcast.collector.model.aareguru.AareGuruEntry;
import adif.aareforcast.collector.model.aareguru.Location;
import adif.aareforcast.collector.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AareGuruEntryService {

  @Autowired
  private AareGuruIntegrationService aareGuruIntegrationService;

  @Autowired
  private AareGuruEntryRepository repository;
  public Metadata readMetadata() {
    return Metadata.builder()
        .numberOfAareGuruEntries(repository.count())
        .build();
  }

  @Transactional
  public PollingStatus poll(Location location) {
    AareGuruEntry newEntry = aareGuruIntegrationService.readCurrent(location);
    boolean alreadyPresent = repository.countByTimestampAndLocation(newEntry.getTimestamp(), newEntry.getLocation()) > 0L;
    if(alreadyPresent) {
      return PollingStatus.ALREADY_PRESENT;
    } else {
      repository.save(newEntry);
      return PollingStatus.NEW;
    }

  }
}
