CREATE TABLE IF NOT EXISTS dm_dev.public.trip
(
    id                  BIGSERIAL PRIMARY KEY ,
    brand               VARCHAR(128),
    model               VARCHAR(128),
    registration_number VARCHAR(15) UNIQUE
);