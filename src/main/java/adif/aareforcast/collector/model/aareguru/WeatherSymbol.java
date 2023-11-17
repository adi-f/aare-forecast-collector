package adif.aareforcast.collector.model.aareguru;

// https://meteotest.ch/en/weather-api/wetter-api-dokumentation/weather-symbols
public enum WeatherSymbol {

  SUNNY,
  MOSTLY_SUNNY,
  CLOUDY,
  HEAVY_CLOUDY,
  THUNDERSTORM_HEAT,
  HEAVY_RAIN,
  SNOWFALL,
  FOG,
  SLEET,
  RAIN_SUN, // sleet 2?
  LIGHT_RAIN,
  SNOW_SHOWER,
  THUNDERSTORM,
  LOW_STRATUS,
  SLEET_SHOWER;

  public static WeatherSymbol fromInteger(int code) {
    return WeatherSymbol.values()[code-1];
  }

}
