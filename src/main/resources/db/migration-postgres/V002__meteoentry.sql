CREATE TABLE meteo_entry
(
    id                                  BIGSERIAL PRIMARY KEY,
    station                             VARCHAR(20),
    timestamp                           TIMESTAMPTZ,
    air_temperature_celcius             REAL,
    rain_mm                             REAL,
    sun_intensity_watt_per_square_meter REAL,
    humidity_percent                    REAL,
    wind_direction360_degree            REAL,
    wind_speed_kmh                      REAL
);
CREATE UNIQUE INDEX met_ts_st ON meteo_entry (timestamp, station);
