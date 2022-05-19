bin/pulsar-admin functions stop --name AirQuality --namespace default --tenant public
bin/pulsar-admin functions delete --name AirQuality --namespace default --tenant public
bin/pulsar-admin functions create --auto-ack true --jar /opt/demo/pulsar-airquality-function/airquality-1.0.jar --classname "dev.pulsarfunction.airquality.AirQualityFunction" --dead-letter-topic "persistent://public/default/aqdead" --inputs "persistent://public/default/airqualityglobal,persistent://public/default/airquality" --log-topic "persistent://public/default/aqlog" --name AirQuality --namespace default --tenant public --max-message-retries 5
