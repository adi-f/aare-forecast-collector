CREATE TABLE aare_guru_entry
(
    id                                                        BIGSERIAL PRIMARY KEY,
    location                                                  VARCHAR(20),
    timestamp                                                 TIMESTAMPTZ,
    current_water_temperature_celsius                         REAL,
    current_flow_cube_meters_per_second                       INTEGER,
    forecast2h_water_temperature_celsius                      REAL,
    timestamp_current_weather                                 TIMESTAMPTZ,
    current_air_temperature_celsius                           REAL,
    current_rainfall_mm_per10min                              REAL,

    weather_forecast_tomorrow_timestamp                       TIMESTAMPTZ,
    weather_forecast_tomorrow_symbol                          VARCHAR(20),
    weather_forecast_tomorrow_day_max_air_temperature_celsius INTEGER,
    weather_forecast_tomorrow_day_min_air_temperature_celsius INTEGER,
    weather_forecast_tomorrow_rainfall_mm_per10min            INTEGER,
    weather_forecast_tomorrow_rain_risk_percentage            INTEGER
);
CREATE UNIQUE INDEX agu_ts_loc ON aare_guru_entry (timestamp, location);
