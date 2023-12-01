package adif.aareforecast.collector.controller;

import adif.aareforecast.collector.dao.AareGuruIntegrationService;
import adif.aareforecast.collector.dao.MeteoSwissIntegrationService;
import adif.aareforecast.collector.model.Metadata;
import adif.aareforecast.collector.model.PollingStatus;
import adif.aareforecast.collector.model.aareguru.AareGuruEntry;
import adif.aareforecast.collector.model.aareguru.Location;
import adif.aareforecast.collector.model.meteoswiss.MeteoEntry;
import adif.aareforecast.collector.model.meteoswiss.Station;
import adif.aareforecast.collector.service.AareGuruEntryService;
import adif.aareforecast.collector.service.ExportService;
import adif.aareforecast.collector.service.MeteoEntryService;
import adif.aareforecast.collector.service.PollingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Tag(name = "Collecting Data")
// @PreAuthorize("hasAuthority('aare-foracast-role')")
public class CollectorController {

  @Autowired
  private AareGuruEntryService aareGuruEntryService;

  @Autowired
  private MeteoEntryService meteoEntryService;

  @Autowired
  private AareGuruIntegrationService aareGuruIntegrationService;

  @Autowired
  private MeteoSwissIntegrationService meteoSwissIntegrationService;

  @Autowired
  private PollingService pollingService;

  @Autowired
  private ExportService exportService;

  @GetMapping(value = "ping", produces = MediaType.TEXT_PLAIN_VALUE)
  @Operation(
      summary = "Ping",
      description = "Simple Ping that returns \"pong\""
  )
  public String ping() {
    return "pong";
  }

  @PostMapping("poll")
  @Operation(
      summary = "Poll all",
      description = "Read all data and persist them. Always returns NO CONTENT since exceptions are handled internally."
  )
  public void poll() {
    pollingService.schedule();
  }

  @GetMapping("aare-guru/{location}")
  @Operation(
      summary = "Read from AareGuru",
      description = "Read the AareGuru data from one location (no persisting)"
  )
  public AareGuruEntry readAareguru(@PathVariable Location location) {
    return aareGuruIntegrationService.readCurrent(location);
  }

  @GetMapping("meteo-swiss/{station}")
  @Operation(
      summary = "Read from Meteo Swiss",
      description = "Read the Meteo Swiss data from one station (no persisting)"
  )
  public MeteoEntry readMeteoSwiss(@PathVariable Station station) {
    return meteoSwissIntegrationService.readMeteo(List.of(station)).get(0);
  }

  @PostMapping("aare-guru/{location}/poll")
  @Operation(
      summary = "Poll from AareGuru",
      description = "Poll for one location (read and persist if new data)"
  )
  public PollingStatus poll(@PathVariable Location location) {
    return aareGuruEntryService.poll(location);
  }

  @PostMapping("meteo-swiss/{station}/poll")
  @Operation(
      summary = "Poll from Meteo Swiss",
      description = "Poll for one station (read and persist if new data)"
  )
  public PollingStatus poll(@PathVariable Station station) {
    return meteoEntryService.poll(List.of(station));
  }

  @GetMapping("metadata")
  @Operation(
      summary = "Read metadata",
      description = "Read metadata about persisted data"
  )
  public Metadata readMetadata() {
    return exportService.readMetadata();
  }
}
