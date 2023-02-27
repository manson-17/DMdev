CREATE TABLE IF NOT EXISTS dm_dev.public.trip
(
    id                 BIGSERIAL PRIMARY KEY ,
    created_at         TIMESTAMP,
    created_by         VARCHAR(255),
    destination        VARCHAR(255),
    place_of_departure VARCHAR(255),
    status             VARCHAR(255),
    car_id             BIGINT REFERENCES car NOT NULL,
    dispatcher_id      BIGINT REFERENCES employee NOT NULL,
    driver_id          BIGINT REFERENCES employee NOT NULL
);