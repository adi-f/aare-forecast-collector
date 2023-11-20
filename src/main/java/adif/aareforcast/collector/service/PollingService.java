package adif.aareforcast.collector.service;

import adif.aareforcast.collector.model.PollingStatus;
import adif.aareforcast.collector.model.aareguru.Location;
import adif.aareforcast.collector.model.meteoswiss.Station;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PollingService {

  Logger log = LoggerFactory.getLogger(PollingService.class);

  @Value("${aareForcastCollector.locations}")
  List<Location> locations;

  @Value("${aareForcastCollector.stations}")
  List<Station> stations;

  @Autowired
  private AareGuruEntryService aareGuruEntryService;

  @Autowired
  private MeteoEntryService meteoEntryService;

  @Scheduled(cron = "${aareForcastCollector.scheduler}")
  public void schedule() {
    pollAareGuruEntries();
    pollMeteoEntries();
  }

  private void pollMeteoEntries() {
    try {
      PollingStatus pollingStatus = meteoEntryService.poll(stations);
      log.info("Polled Swiss Meteo with status {}", pollingStatus);
    } catch (Exception e) {
      log.warn("Polled Swiss Meteo with error", e);
    }
  }

  private void pollAareGuruEntries() {
    PollingStatus pollingStatus = null;
    for (Location location : locations) {
      try {
        PollingStatus result = aareGuruEntryService.poll(location);
        log.debug("Polled Aare Guru with status {}", pollingStatus);
        if(result != pollingStatus) {
          pollingStatus = pollingStatus == null ? result : PollingStatus.MIXED;
        }
      } catch (Exception e) {
        log.warn("Polled Aare Guru with error", e);
      }
    }
    log.info("Polled Aare Guru with status {}", pollingStatus);
  }
}
