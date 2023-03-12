CREATE TABLE IF NOT EXISTS car
(
    id                  BIGSERIAL PRIMARY KEY,
    brand               VARCHAR(128) NOT NULL,
    model               VARCHAR(128) NOT NULL,
    registration_number VARCHAR(15) UNIQUE
);

CREATE TABLE IF NOT EXISTS employee
(
    id                    BIGSERIAL PRIMARY KEY,
    type                  VARCHAR(31) NOT NULL,
    email                 VARCHAR(255) UNIQUE,
    firstname             VARCHAR(255) NOT NULL,
    lastname              VARCHAR(255) NOT NULL,
    password              VARCHAR(255) NOT NULL,
    role                  VARCHAR(255) NOT NULL,
    category              VARCHAR(255),
    driving_rights_number VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS trip
(
    id                 BIGSERIAL PRIMARY KEY,
    created_at         TIMESTAMP,
    created_by         VARCHAR(255),
    cost               NUMERIC(19,2) NOT NULL,
    destination        VARCHAR(255) NOT NULL,
    place_of_departure VARCHAR(255) NOT NULL,
    status             VARCHAR(255) NOT NULL,
    car_id             BIGINT REFERENCES car NOT NULL,
    dispatcher_id      BIGINT REFERENCES employee NOT NULL,
    driver_id          BIGINT REFERENCES employee NOT NULL
);