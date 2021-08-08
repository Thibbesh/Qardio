# Temperature


###### Motivation

This project is an assignment. Implemented code would be a nice example of real-time events and how platform APIs capture and present data. In this assignment, device refers to temperature sensor.


### Features

Temperature APIs provides

- Collect device data using HTTP endpoints.

- Saving data point in InfluxDB.

- Using Apache Kafka to create event stream.

- Real-time data processing.



### How to run


###### Prerequisite
- JDK 1.11 (Tested with Oracle JDK)
- Maven 3.6.x+
- Docker (18.09.2), Docker Compose (1.23.2)

###### Run Apache Kafka, ZooKeeper and InfluxDB in Docker
```
$ docker-compose up
```

###### Build
```
$ mvn clean compile install
```

###### Run
```
$ mvn spring-boot:run
```

###### Quick test

Temperature sensor send data
```
curl -X POST localhost:8080/temperatures \
-H 'Content-Type: application/json' \
-d '{ "unixTimestamp": 1563142700, "temperatureInFahrenheit": 20 }'
```
example response
```
{"success":true}
```

Temperature sensor send data in batch
```
curl -X POST localhost:8080/batch/temperatures \
-H 'Content-Type: application/json' \
-d '[{ "unixTimestamp": 1563142701, "temperatureInFahrenheit": 21 }, { "unixTimestamp": 1563142702, "temperatureInFahrenheit": 22 }]'
```
example response
```
[{"success":true},{"success":true}]
```

Get aggregated temperature data report
```
curl -X GET http://localhost:8080/temperatures?startTime=1563142700&endTime=1563142800 \
-H 'Content-Type: application/json'
```
example response
```
{
   "deviceId":"e01a7bc8-ee40-48ba-80ee-f8acbaba5f14",
   "data":[
      {
         "unixTimestamp":1563142700,
         "temperatureInFahrenheit":20.0
      },
      {
         "unixTimestamp":1563142701,
         "temperatureInFahrenheit":21.0
      },
      {
         "unixTimestamp":1563142702,
         "temperatureInFahrenheit":22.0
      },
      {
         "unixTimestamp":1563142795,
         "temperatureInFahrenheit":24.0
      }
   ]
}

```


### Design Decisions

- Authentication and authorization is not taking into consideration. LoggedInUserService.java provides mocked data.

- When it comes to making software design decisions for IoT devices, it’s important to fit your functional requirements within the capabilities of resource-constrained devices.

  * Communication through two protocols: MQTT, HTTP and Apache Kafka
  * For assignment purpose, I picked HTTP.
  * Good to read:
    https://cloud.google.com/blog/products/iot-devices/http-vs-mqtt-a-tale-of-two-iot-protocols[article 1]
    https://medium.com/mqtt-buddy/mqtt-vs-http-which-one-is-the-best-for-iot-c868169b3105[article 2]
    https://stackoverflow.com/questions/37391827/what-is-the-difference-between-mqtt-broker-and-apache-kafka[article 3]

- Data stream from IoT devices collected by REST API.

  * Eventually put to Apache Kafka.
  * Persisted in storage.

- Application has both Apache Kafka event producer and consumer. Separate microservice could make it more scalable.

  * Collector REST API endpoint produce event.
  * Reporting/Metrics collector services consume events.

- InfluxDB is chosen because it well suited for time series data. Fast time range query makes it good choose. Caching on top of service layer could bring more speed to the APIs.


### Development
##### How to run tests

###### How to run unit tests
To run the unit tests, execute the following commands
```
mvn clean test-compile test
```

### Improvements to make
- Use MQTT as default protocol, put data events directly to Apache Kafka.
- Build docker image (plugin already added in the pom).
- Generate and check OWASP report.
- Improve code coverage.
  * Apache Kafka topic listener code is not covered with tests.

