package adif.aareforcast.collector.controller;

import adif.aareforcast.collector.dao.AareGuruIntegrationService;
import adif.aareforcast.collector.dao.MeteoSwissIntegrationService;
import adif.aareforcast.collector.model.PollingStatus;
import adif.aareforcast.collector.model.aareguru.AareGuruEntry;
import adif.aareforcast.collector.model.aareguru.Location;
import adif.aareforcast.collector.model.Metadata;
import adif.aareforcast.collector.model.meteoswiss.MeteoEntry;
import adif.aareforcast.collector.model.meteoswiss.Station;
import adif.aareforcast.collector.service.AareGuruEntryService;
import adif.aareforcast.collector.service.MeteoEntryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CollectorController {

  @Autowired
  private AareGuruEntryService aareGuruEntryService;

  @Autowired
  private MeteoEntryService meteoEntryService;

  @Autowired
  private AareGuruIntegrationService aareGuruIntegrationService;

  @Autowired
  private MeteoSwissIntegrationService meteoSwissIntegrationService;

  @GetMapping("ping")
  public String ping() {
    return "pong";
  }

  @GetMapping("aare-guru/{location}")
  public AareGuruEntry readAareguru(@PathVariable Location location) {
    return aareGuruIntegrationService.readCurrent(location);
  }

  @GetMapping("meteo-swiss/{station}")
  public MeteoEntry readMeteoSwiss(@PathVariable Station station) {
    return meteoSwissIntegrationService.readMeteo(List.of(station)).get(0);
  }

  @PostMapping("aare-guru/{location}/poll")
  public PollingStatus poll(@PathVariable Location location) {
    return aareGuruEntryService.poll(location);
  }

  @PostMapping("meteo-swiss/{station}/poll")
  public PollingStatus poll(@PathVariable Station station) {
    return meteoEntryService.poll(station);
  }

  @GetMapping("metadata")
  public Metadata readMetadata() {
    return aareGuruEntryService.readMetadata();
  }
}
