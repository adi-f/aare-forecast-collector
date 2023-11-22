package adif.aareforcast.collector.service;

import adif.aareforcast.collector.dao.MeteoEntryRepository;
import adif.aareforcast.collector.dao.MeteoSwissIntegrationService;
import adif.aareforcast.collector.model.Metadata;
import adif.aareforcast.collector.model.PollingStatus;
import adif.aareforcast.collector.model.meteoswiss.MeteoEntry;
import adif.aareforcast.collector.model.meteoswiss.Station;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeteoEntryService {

  @Autowired
  private MeteoSwissIntegrationService meteoSwissIntegrationService;

  @Autowired
  private MeteoEntryRepository repository;

  public Metadata readMetadata() {
    return Metadata.builder()
        .numberOfMeteoEntries(repository.count())
        .latestMeteoSwissEntriy(repository.findTopByOrderByTimestampDesc().map(MeteoEntry::getTimestamp).orElse(null))
        .build();
  }

  @Transactional
  public PollingStatus poll(List<Station> stations) {
    List<MeteoEntry> newEntries = meteoSwissIntegrationService.readMeteo(stations);
    boolean hasAlreadyPresent = false;
    boolean hasNew = false;
    for(MeteoEntry newEntry : newEntries) {
      boolean alreadyPresent = repository.countByTimestampAndStation(newEntry.getTimestamp(), newEntry.getStation()) > 0L;
      hasAlreadyPresent |= alreadyPresent;
      hasNew |= !alreadyPresent;

      if(!alreadyPresent) {
        repository.save(newEntry);
      }
    }

    if(hasNew && !hasAlreadyPresent) {
      return PollingStatus.NEW;
    } else if(!hasNew && hasAlreadyPresent) {
      return PollingStatus.ALREADY_PRESENT;
    } else {
      return PollingStatus.MIXED;
    }
  }
}
