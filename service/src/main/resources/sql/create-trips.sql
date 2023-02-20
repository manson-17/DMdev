CREATE SEQUENCE IF NOT EXISTS atp.hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE atp.trips
(
    id            BIGINT NOT NULL,
    creation_date TIMESTAMP WITHOUT TIME ZONE,
    start_date    TIMESTAMP WITHOUT TIME ZONE,
    end_date      TIMESTAMP WITHOUT TIME ZONE,
    status        VARCHAR(255),
    "from"        VARCHAR(255),
    "to"          VARCHAR(255),
    dispatcher_id BIGINT,
    driver_id     BIGINT,
    CONSTRAINT pk_trips PRIMARY KEY (id)
);

ALTER TABLE atp.trips
    ADD CONSTRAINT FK_TRIPS_ON_DISPATCHER FOREIGN KEY (dispatcher_id) REFERENCES atp.dispatcher (id);

ALTER TABLE atp.trips
    ADD CONSTRAINT FK_TRIPS_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES atp.driver (id);