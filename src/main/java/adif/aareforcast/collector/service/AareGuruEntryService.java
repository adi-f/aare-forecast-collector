package adif.aareforcast.collector.service;

import adif.aareforcast.collector.dao.AareGuruEntryRepository;
import adif.aareforcast.collector.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AareGuruEntryService {

  @Autowired
  private AareGuruEntryRepository repository;
  public Metadata readMetadata() {
    return Metadata.builder()
        .numberOfAareGuruEntries(repository.count())
        .build();
  }

}
