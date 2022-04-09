bin/pulsar-client consume "persistent://public/default/aq-pm25" -s aqrpm25-reader -n 0
bin/pulsar-client consume "persistent://public/default/aq-pm10" -s aqrpm10-reader -n 0
bin/pulsar-client consume "persistent://public/default/aq-ozone" -s aqozone-reader -n 0
