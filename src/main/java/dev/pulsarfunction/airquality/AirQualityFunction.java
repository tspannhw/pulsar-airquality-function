package dev.pulsarfunction.airquality;

import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.apache.pulsar.functions.api.*;

import java.util.UUID;

/**
 * Air Quality Function
**/
public class AirQualityFunction implements Function<byte[], Void> {

    public static final String PM_25 = "PM2.5";
    public static final String JAVA = "Java";
    public static final String LANGUAGE = "language";
    public static final String MESSAGE_JSON = "Receive message JSON:";
    public static final String ERROR = "ERROR:";
    public static final String PERSISTENT_PUBLIC_DEFAULT_PM_25 = "persistent://public/default/aq-pm10";
    public static final String PERSISTENT_PUBLIC_DEFAULT_PM_10 = "persistent://public/default/aq-pm25";
    public static final String PERSISTENT_PUBLIC_DEFAULT_O3 = "persistent://public/default/aq-ozone";
    public static final String PM_10 = "PM10";

    /** PROCESS */
    @Override
    public Void process(byte[] input, Context context) {
        if ( input == null || context == null ) {
            return null;
        }
        System.out.println("Input topics:" + context.getInputTopics().toString() );

        if ( context.getLogger() != null && context.getLogger().isDebugEnabled() ) {
            context.getLogger().debug("LOG:" + input.toString());

            System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");

            context.getLogger().debug("Available processors (cores): " +
                    Runtime.getRuntime().availableProcessors());

            /* Total amount of free memory available to the JVM */
            context.getLogger().debug("Free memory (bytes): " +
                    Runtime.getRuntime().freeMemory());

            /* This will return Long.MAX_VALUE if there is no preset limit */
            long maxMemory = Runtime.getRuntime().maxMemory();

            /* Maximum amount of memory the JVM will attempt to use */
            context.getLogger().debug("Maximum memory (bytes): " +
                    (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

            /* Total memory currently available to the JVM */
            context.getLogger().debug("Total memory available to JVM (bytes): " +
                    Runtime.getRuntime().totalMemory());
        }

        try {
            AQParserService service = new AQParserService();
            Observation observation = service.deserialize(input);

            if ( observation != null ) {
                if ( context.getLogger() != null  && context.getLogger().isDebugEnabled() ) {
                    context.getLogger().debug(MESSAGE_JSON + observation.toString());
                }

                // Cleanup, routing and schema to 3 topics
                // TODO
                // Add enrichment, extra fields

                System.out.println(observation.toString());

                if ( observation.getParameterName().equalsIgnoreCase(PM_25)) {
                    context.newOutputMessage(PERSISTENT_PUBLIC_DEFAULT_PM_25, JSONSchema.of(Observation.class))
                            .key(UUID.randomUUID().toString())
                            .property(LANGUAGE, JAVA)
                            .value(observation)
                            .send();
                }
                else if (observation.getParameterName().equalsIgnoreCase(PM_10)) {
                    context.newOutputMessage(PERSISTENT_PUBLIC_DEFAULT_PM_10, JSONSchema.of(Observation.class))
                            .key(UUID.randomUUID().toString())
                            .property(LANGUAGE, JAVA)
                            .value(observation)
                            .send();
                }
                else {
                    context.newOutputMessage(PERSISTENT_PUBLIC_DEFAULT_O3, JSONSchema.of(Observation.class))
                            .key(UUID.randomUUID().toString())
                            .property(LANGUAGE, JAVA)
                            .value(observation)
                            .send();
                }
            }
        } catch (Throwable e) {
            if ( context.getLogger() != null) {
                context.getLogger().error(ERROR + e.getLocalizedMessage());
            }
        }
        return null;
    }
}
