package adif.aareforcast.collector.controller;

import adif.aareforcast.collector.dao.AareGuruIntegrationService;
import adif.aareforcast.collector.model.AareGuruEntry;
import adif.aareforcast.collector.model.Location;
import adif.aareforcast.collector.model.Metadata;
import adif.aareforcast.collector.service.AareGuruEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CollectorController {

  @Autowired
  private AareGuruEntryService aareGuruEntryService;

  @Autowired
  private AareGuruIntegrationService aareGuruIntegrationService;

  @GetMapping("ping")
  public String ping() {
    return "pong";
  }

  @GetMapping("aare-guru")
  public AareGuruEntry readAareguru(Location location) {
    return aareGuruIntegrationService.readCurrent(location);
  }

  @GetMapping("metadata")
  public Metadata readMetadata() {
    return aareGuruEntryService.readMetadata();
  }

}
