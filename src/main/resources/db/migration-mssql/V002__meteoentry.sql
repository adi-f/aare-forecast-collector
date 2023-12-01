CREATE TABLE meteo_entry
(
    id                                  BIGINT IDENTITY PRIMARY KEY,
    station                             VARCHAR(20),
    timestamp                           datetimeoffset,
    air_temperature_celcius             FLOAT(24),
    rain_mm                             FLOAT(24),
    sun_intensity_watt_per_square_meter FLOAT(24),
    humidity_percent                    FLOAT(24),
    wind_direction360_degree            FLOAT(24),
    wind_speed_kmh                      FLOAT(24)
);
CREATE UNIQUE INDEX met_ts_st ON meteo_entry (timestamp, station);
