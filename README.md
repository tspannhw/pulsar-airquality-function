## pulsar-airquality-function

## Developer Workspace

* Using JDK 8. 1.8.0_292.  OPEN JDK 64-bit Server
* Using IntelliJ IDEA CE 2021.2

## Developer Deployment Server

* Ubuntu Ubuntu 18.04.6 LTS
* JDK 1.8.0_312
* 70G RAM
* 24 Virtual Cores
* HP ProLiant DL360 G7 1U RackMount 64-bit Server with 2×Six-Core X5677 Xeon 3.46GHz CPUs 
*    72GB PC3-10600R RAM 
*    4×900GB 10K SAS SFF HDD, P410i RAID, 4×GigaBit NIC

## setup

````
bin/pulsar-admin functions get --name AirQuality --namespace default --tenant public

bin/pulsar-admin functions status --name AirQuality --namespace default --tenant public

bin/pulsar-client consume "persistent://public/default/aq-pm25" -s "fnpm25reader" -n 5
bin/pulsar-client consume "persistent://public/default/aq-pm10" -s "fnpm10reader" -n 5
bin/pulsar-client consume "persistent://public/default/aq-ozone" -s "fnpm10reader" -n 5

PM2.5
PM10


````

## References

* https://github.com/tspannhw/flip-java-energy
* https://github.com/tspannhw/pulsar-energy-function 

# Overview

Using Java Function to clean up and split air quality readings sent from NiFi


# Prerequisites

- Java 1.8 or higher version
- Java Client: 2.9.1

# Details

## Create a Standaone Apache Pulsar 2.9.1 Cluster or Use StreamNative Cloud

## Create Pulsar Topics

## setup

````

bin/pulsar-admin topics create persistent://public/default/aqdead
bin/pulsar-admin topics create persistent://public/default/aqlog
bin/pulsar-admin topics create persistent://public/default/airqualityglobal
bin/pulsar-admin topics create persistent://public/default/airquality
bin/pulsar-admin topics create persistent://public/default/aq-pm25
bin/pulsar-admin topics create persistent://public/default/aq-pm10
bin/pulsar-admin topics create persistent://public/default/aq-ozone
````

## Test Consume Messages

````

bin/pulsar-client consume "persistent://public/default/aq-pm25" -s "aqpm25xr" -n 0
bin/pulsar-client consume "persistent://public/default/aq-pm10" -s "aqpm10xr" -n 0
bin/pulsar-client consume "persistent://public/default/aq-ozone" -s "aqozonexr" -n 0

{
  "numInstances" : 1,
  "numRunning" : 1,
  "instances" : [ {
    "instanceId" : 0,
    "status" : {
      "running" : true,
      "error" : "",
      "numRestarts" : 0,
      "numReceived" : 2157,
      "numSuccessfullyProcessed" : 2157,
      "numUserExceptions" : 0,
      "latestUserExceptions" : [ ],
      "numSystemExceptions" : 0,
      "latestSystemExceptions" : [ ],
      "averageLatency" : 3.7605293514140055,
      "lastInvocationTime" : 1649508904507,
      "workerId" : "c-standalone-fw-127.0.0.1-8080"
    }
  } ]
}
bin/pulsar-admin functions status --name AirQuality
````
