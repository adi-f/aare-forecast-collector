package adif.aareforcast.collector.controller;

import adif.aareforcast.collector.model.Metadata;
import adif.aareforcast.collector.service.AareGuruEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ExportController {

  @Autowired
  private AareGuruEntryService aareGuruEntryService;

  @GetMapping("ping")
  public String ping() {
    return "pong";
  }

  @GetMapping("metadata")
  public Metadata readMetadata() {
    return aareGuruEntryService.readMetadata();
  }

}
