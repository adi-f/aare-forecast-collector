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
        .build();
  }

  @Transactional
  public PollingStatus poll(Station station) {
    MeteoEntry newEntry = meteoSwissIntegrationService.readMeteo(List.of(station)).get(0);
    boolean alreadyPresent = repository.countByTimestampAndStation(newEntry.getTimestamp(), newEntry.getStation()) > 0L;
    if(alreadyPresent) {
      return PollingStatus.ALREADY_PRESENT;
    } else {
      repository.save(newEntry);
      return PollingStatus.NEW;
    }
  }
}
