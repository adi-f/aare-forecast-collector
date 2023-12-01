CREATE TABLE aare_guru_entry
(
    id                                                        BIGINT IDENTITY PRIMARY KEY,
    location                                                  VARCHAR(20),
    timestamp                                                 datetimeoffset,
    current_water_temperature_celsius                         FLOAT(24),
    current_flow_cube_meters_per_second                       INT,
    forecast2h_water_temperature_celsius                      FLOAT(24),
    timestamp_current_weather                                 datetimeoffset,
    current_air_temperature_celsius                           FLOAT(24),
    current_rainfall_mm_per10min                              FLOAT(24),

    weather_forecast_tomorrow_timestamp                       datetimeoffset,
    weather_forecast_tomorrow_symbol                          VARCHAR(20),
    weather_forecast_tomorrow_day_max_air_temperature_celsius INT,
    weather_forecast_tomorrow_day_min_air_temperature_celsius INT,
    weather_forecast_tomorrow_rainfall_mm_per10min            INT,
    weather_forecast_tomorrow_rain_risk_percentage            INT
);
CREATE UNIQUE INDEX agu_ts_loc ON aare_guru_entry (timestamp, location);
