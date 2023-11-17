package adif.aareforcast.collector.controller;

import adif.aareforcast.collector.dao.AareGuruIntegrationService;
import adif.aareforcast.collector.model.aareguru.AareGuruEntry;
import adif.aareforcast.collector.model.aareguru.Location;
import adif.aareforcast.collector.model.aareguru.Metadata;
import adif.aareforcast.collector.service.AareGuruEntryService;
import adif.aareforcast.collector.service.AareGuruEntryService.PollingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("aare-guru/{location}")
  public AareGuruEntry readAareguru(@PathVariable Location location) {
    return aareGuruIntegrationService.readCurrent(location);
  }

  @GetMapping("aare-guru/{location}/poll")
  public PollingStatus poll(@PathVariable Location location) {
    return aareGuruEntryService.poll(location);
  }

  @GetMapping("metadata")
  public Metadata readMetadata() {
    return aareGuruEntryService.readMetadata();
  }

}
