package adif.aareforecast.collector.service;

import adif.aareforecast.collector.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

  @Autowired
  private AareGuruEntryService aareGuruEntryService;

  @Autowired
  private MeteoEntryService meteoEntryService;
  public Metadata readMetadata() {
    Metadata aareGuru = aareGuruEntryService.readMetadata();
    Metadata meteoSwiss = meteoEntryService.readMetadata();
    return Metadata.builder()
        .numberOfAareGuruEntries(aareGuru.getNumberOfAareGuruEntries())
        .latestAareGuruEntriy(aareGuru.getLatestAareGuruEntriy())
        .numberOfMeteoEntries(meteoSwiss.getNumberOfMeteoEntries())
        .latestMeteoSwissEntriy(meteoSwiss.getLatestMeteoSwissEntriy())
        .build();
  }
}
