package adif.aareforcast.collector.service;

import adif.aareforcast.collector.dao.AareGuruEntryRepository;
import adif.aareforcast.collector.dao.AareGuruIntegrationService;
import adif.aareforcast.collector.model.AareGuruEntry;
import adif.aareforcast.collector.model.Location;
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

}
