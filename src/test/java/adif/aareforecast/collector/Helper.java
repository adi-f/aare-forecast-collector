package adif.aareforecast.collector;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class Helper {

  private Helper() {}

  public static String readRessource(String path) {
    try (InputStream is = Helper.class.getClassLoader().getResourceAsStream(path)){
      return IOUtils.toString(is, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
