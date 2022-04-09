package dev.pulsarfunction.airquality;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.apache.pulsar.functions.api.SerDe;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

/**
 *
 */
public class AQParserService {

    /**
     * parse raw NiFi message or Spring message
     * @param message
     * @return Observation
     */
    private Observation parseMessage(String message) {
        Observation msg = new Observation();
        if ( message == null) {
            return msg;
        }
        try {
            if ( message.trim().length() > 0) {
                if ( message.contains("apigw") ) {
                    // data comes from NiFi
                    GlobalObservation globalObservation = null;
                    ObjectMapper mapper = new ObjectMapper();
                    globalObservation = mapper.readValue(message, GlobalObservation.class);

                    if ( !globalObservation.getParameter().equalsIgnoreCase("PM10") &&
                            !globalObservation.getParameter().equalsIgnoreCase("PM25")) {
                        return null;
                    }

                    try {
                        msg.setAqi((int)(Double.parseDouble(globalObservation.getValue())*100) );
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    try {
                        msg.setLatitude(Double.parseDouble(globalObservation.getLatitude()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    msg.setDateObserved(globalObservation.getDate());

                    try {
                        msg.setLongitude(Double.parseDouble(globalObservation.getLongitude()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    // Do Not Have:  msg.setHourObserved( );
                    msg.setHourObserved(-1);

                    // pm10
                    // um025
                    // um010
                    // um100
                    // pm25

                    if ( globalObservation.getParameter().equalsIgnoreCase("PM10") ){
                        msg.setParameterName("PM10");
                    }

                    if ( globalObservation.getParameter().equalsIgnoreCase("PM25")) {
                        msg.setParameterName("PM2.5");
                    }

                    // Do Not Have:  msg.setLocalTimeZone( );
                    msg.setLocalTimeZone("EST");

                    msg.setReportingArea( globalObservation.getLocation() + ":" +
                                          globalObservation.getCity() + ":" +
                                          globalObservation.getCountry());

                    // TODO:  Map City + Country to State
                    //globalObservation.getCountry()
                    //
                    //msg.setStateCode( globalObservation.getCity() );
                }
                else {
                    ObjectMapper mapper = new ObjectMapper();
                    msg = mapper.readValue(message, Observation.class);
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        if (msg == null) {
            msg = new Observation();
        }
        return msg;
    }

    /**
     * return a clean observation
     * @param input   device as a raw string in bytes
     * @return Observation object
     */
    public Observation deserialize(byte[] input) {
        if (input == null) {
            return null;
        }
        String s = new String(input);
        Observation rawObs = parseMessage(s);
        if ( rawObs == null) {
            return null;
        }
//            String OS = System.getProperty("os.name").toLowerCase();
//            long timestamp = Instant.now().toEpochMilli();
//            UUID uuidKey = UUID.randomUUID();

        return rawObs;
    }
}
