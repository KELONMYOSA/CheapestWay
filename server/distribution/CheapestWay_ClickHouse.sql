/* Таблицы COUNTRIES */
CREATE TABLE countries_kafka
(
    code     String,
    name     String,
    currency String,
    en       String
)
    ENGINE = Kafka
        SETTINGS kafka_broker_list = 'kafka:29092',
            kafka_topic_list = 'to-clickhouse-countries',
            kafka_group_name = 'to-clickhouse-countries',
            kafka_format = 'JSONEachRow';

CREATE TABLE countries_output
(
    code     String,
    name     String,
    currency String,
    en       String
)
    ENGINE = ReplacingMergeTree()
        ORDER BY name;

CREATE MATERIALIZED VIEW countries_kafka_mv TO countries_output AS
SELECT *
FROM countries_kafka;


/* Таблицы CITIES */
CREATE TABLE cities_kafka
(
    code         String,
    name         String,
    time_zone    String,
    country_code String,
    en           String,
    lon          Float,
    lat          Float
)
    ENGINE = Kafka
        SETTINGS kafka_broker_list = 'kafka:29092',
            kafka_topic_list = 'to-clickhouse-cities',
            kafka_group_name = 'to-clickhouse-cities',
            kafka_format = 'JSONEachRow';

CREATE TABLE cities_output
(
    code         String,
    name         String,
    time_zone    String,
    country_code String,
    en           String,
    lon          Float,
    lat          Float
)
    ENGINE = ReplacingMergeTree()
        ORDER BY name;

CREATE MATERIALIZED VIEW cities_kafka_mv TO cities_output AS
SELECT *
FROM cities_kafka;


/* Таблицы AIRPORTS */
CREATE TABLE airports_kafka
(
    code         String,
    name         String,
    time_zone    String,
    country_code String,
    city_code    String,
    en           String,
    iata_type    String,
    flightable   Boolean,
    lon          Float,
    lat          Float
)
    ENGINE = Kafka
        SETTINGS kafka_broker_list = 'kafka:29092',
            kafka_topic_list = 'to-clickhouse-airports',
            kafka_group_name = 'to-clickhouse-airports',
            kafka_format = 'JSONEachRow';

CREATE TABLE airports_output
(
    code         String,
    name         String,
    time_zone    String,
    country_code String,
    city_code    String,
    en           String,
    iata_type    String,
    flightable   Boolean,
    lon          Float,
    lat          Float
)
    ENGINE = ReplacingMergeTree()
        ORDER BY name;

CREATE MATERIALIZED VIEW airports_kafka_mv TO airports_output AS
SELECT *
FROM airports_kafka;


/* Таблицы AIRLINES */
CREATE TABLE airlines_kafka
(
    code       String,
    name       String,
    en         String,
    is_lowcost Boolean
)
    ENGINE = Kafka
        SETTINGS kafka_broker_list = 'kafka:29092',
            kafka_topic_list = 'to-clickhouse-airlines',
            kafka_group_name = 'to-clickhouse-airlines',
            kafka_format = 'JSONEachRow';

CREATE TABLE airlines_output
(
    code       String,
    name       String,
    en         String,
    is_lowcost Boolean
)
    ENGINE = ReplacingMergeTree()
        ORDER BY name;

CREATE MATERIALIZED VIEW airlines_kafka_mv TO airlines_output AS
SELECT *
FROM airlines_kafka;


/* Таблицы ROUTES */
CREATE TABLE routes_kafka
(
    airline_iata           String,
    departure_airport_iata String,
    arrival_airport_iata   String
)
    ENGINE = Kafka
        SETTINGS kafka_broker_list = 'kafka:29092',
            kafka_topic_list = 'to-clickhouse-routes',
            kafka_group_name = 'to-clickhouse-routes',
            kafka_format = 'JSONEachRow';

CREATE TABLE routes_output
(
    airline_iata           String,
    departure_airport_iata String,
    arrival_airport_iata   String
)
    ENGINE = MergeTree()
        ORDER BY departure_airport_iata;

CREATE MATERIALIZED VIEW routes_kafka_mv TO routes_output AS
SELECT *
FROM routes_kafka;


/* Таблицы TICKETS */
CREATE TABLE tickets_kafka
(
    timestamp           DateTime,
    origin              String,
    destination         String,
    origin_airport      String,
    destination_airport String,
    price               UInt32,
    airline             String,
    flight_number       String,
    departure_at        String,
    duration            UInt16,
    link                String
)
    ENGINE = Kafka
        SETTINGS kafka_broker_list = 'kafka:29092',
            kafka_topic_list = 'to-clickhouse-tickets',
            kafka_group_name = 'to-clickhouse-tickets',
            kafka_format = 'JSONEachRow';

CREATE TABLE tickets_output
(
    timestamp           DateTime,
    origin              String,
    destination         String,
    origin_airport      String,
    destination_airport String,
    price               UInt32,
    airline             String,
    flight_number       String,
    departure_at        String,
    duration            UInt16,
    link                String
)
    ENGINE = MergeTree()
        ORDER BY timestamp;

CREATE MATERIALIZED VIEW tickets_kafka_mv TO tickets_output AS
SELECT *
FROM tickets_kafka;


/* Вес таблиц */
SELECT table, round(sum(bytes) / 1024 / 1024 / 1024, 2) as size_gb
FROM system.parts
WHERE active
GROUP BY table
ORDER BY size_gb DESC