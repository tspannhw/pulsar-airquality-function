package dev.pulsarfunction.airquality;

import dev.pulsarfunction.airquality.AirQualityFunction;
import org.apache.pulsar.common.functions.ConsumerConfig;
import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.common.io.SourceConfig;
import org.apache.pulsar.common.schema.SchemaType;
import org.apache.pulsar.functions.LocalRunner;
import org.junit.Assert;
import org.junit.Test;
import org.apache.pulsar.functions.api.Context;

import java.util.*;

import static org.mockito.Mockito.mock;

public class AirQualityFunctionTest {

    protected Context ctx;

    protected void init(Context ctx) {
        this.ctx = ctx;
    }

    /**
     *
     * persistent://public/default/airquality
     * ----- got message -----
     * key:[49a3dbcd-ec7b-41ff-b5c7-9989c5ada1cc], properties:[], content:{"dateObserved":"2022-04-08 ","hourObserved":16,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"O3","aqi":43,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
     * ----- got message -----
     * key:[2cd71353-1a7b-4c06-bc71-de32a206dbbf], properties:[], content:{"dateObserved":"2022-04-08 ","hourObserved":16,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"PM2.5","aqi":43,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
     * ----- got message -----
     * key:[de38b19b-82b5-48fe-b23c-0d5a79f6495a], properties:[], content:{"dateObserved":"2022-04-08 ","hourObserved":16,"localTimeZone":"EST","reportingArea":"Atlanta","stateCode":"GA","latitude":33.65,"longitude":-84.43,"parameterName":"PM10","aqi":19,"category":{"number":1,"name":"Good","additionalProperties":{}},"additionalProperties":{}}
     * ^Croot@pulsar1:/opt/demo/apache-pulsar-2.9.1#
     *
     */
    // PM10 is any particulate matter in the air with a diameter of 10 micrometers or less, including smoke, dust, soot, salts, acids, and metals.
    public static String JSON_STRING = "{\"dateObserved\":\"2022-04-08 \",\"hourObserved\":8,\"localTimeZone\":\"EST\",\"reportingArea\":\"Atlanta\",\"stateCode\":\"GA\",\"latitude\":33.65,\"longitude\":-84.43,\"parameterName\":\"PM10\",\"aqi\":14,\"category\":{\"number\":1,\"name\":\"Good\",\"additionalProperties\":{}},\"additionalProperties\":{}}";

    // PM2.5  2.5 microns or less in diameter (PM2. 5).
    public static String JSON_STRING_PM25= "{\"dateObserved\":\"2022-04-08 \",\"hourObserved\":8,\"localTimeZone\":\"EST\",\"reportingArea\":\"Atlanta\",\"stateCode\":\"GA\",\"latitude\":33.65,\"longitude\":-84.43,\"parameterName\":\"PM2.5\",\"aqi\":31,\"category\":{\"number\":1,\"name\":\"Good\",\"additionalProperties\":{}},\"additionalProperties\":{}}";

    // Ozone
    public static String JSON_STRING_O3 = "{\"dateObserved\":\"2022-04-08 \",\"hourObserved\":8,\"localTimeZone\":\"EST\",\"reportingArea\":\"Atlanta\",\"stateCode\":\"GA\",\"latitude\":33.65,\"longitude\":-84.43,\"parameterName\":\"O3\",\"aqi\":32,\"category\":{\"number\":1,\"name\":\"Good\",\"additionalProperties\":{}},\"additionalProperties\":{}}";

    // airqualityglobal
    public static String JSON_GLOBAL_FAIL = "{\"apigw-requestid\":\"QN4Ori9eoAMEJZA=\",\"date\":\"Thu, 07 Apr 2022 15:45:46 GMT\",\"country\":\"US\",\"lastUpdated\":\"2022-04-07T05:05:19+00:00\",\"unit\":\"particles/cm³\",\"city\":\"\",\"latitude\":\"33.719944\",\"parameter\":\"um010\",\"location\":\"SCTV_09\",\"server-timing\":\"total;dur=208.18\",\"value\":\"0.11\",\"longitude\":\"-117.42265\"}";

    // airqualityglobal
    public static String JSON_GLOBAL = "{\"apigw-requestid\":\"QN4Ori9eoAMEJZA=\",\"date\":\"Thu, 07 Apr 2022 15:45:46 GMT\",\"country\":\"US\",\"lastUpdated\":\"2022-04-07T05:05:19+00:00\",\"unit\":\"particles/cm³\",\"city\":\"\",\"latitude\":\"33.719944\",\"parameter\":\"pm10\",\"location\":\"SCTV_09\",\"server-timing\":\"total;dur=208.18\",\"value\":\"0.11\",\"longitude\":\"-117.42265\"}";

    //{"apigw-requestid":"QN4Ori9eoAMEJZA=","date":"Thu, 07 Apr 2022 15:45:46 GMT","country":"US","lastUpdated":"2022-04-07T05:05:19+00:00","unit":"particles/cm³","city":"","latitude":"33.719944","parameter":"um010","location":"SCTV_09","server-timing":"total;dur=208.18","value":"0.11","longitude":"-117.42265"}

    /**
     *
     * @param msg
     */
    protected void log(String msg) {
        if (ctx != null && ctx.getLogger() != null) {
            ctx.getLogger().info(String.format("Function: [%s, id: %s, instanceId: %d of %d] %s",
                    ctx.getFunctionName(), ctx.getFunctionId(), ctx.getInstanceId(), ctx.getNumInstances(), msg));
        }
    }

    @Test
    public void testAirQualityFunction() {
        AirQualityFunction func = new AirQualityFunction();
      //  func.process(JSON_STRING.getBytes(), mock(Context.class));

        func.process(JSON_STRING_PM25.getBytes(), mock(Context.class));

  //      func.process(JSON_STRING_O3.getBytes(), mock(Context.class));

        func.process(JSON_GLOBAL.getBytes(), mock(Context.class));

//        func.process(JSON_GLOBAL_FAIL.getBytes(), mock(Context.class));
    }

    /**
     * @param args   string arguments
     * @throws Exception
     *  .inputs(Collections.singleton("persistent://public/default/airqualityglobal"))
     */
    public static void main(String[] args) throws Exception {

        // multiple topics
        Collection<String> inputTopics = new ArrayList<String>();
        inputTopics.add("persistent://public/default/airqualityglobal");
        inputTopics.add("persistent://public/default/airquality");

        FunctionConfig functionConfig = FunctionConfig.builder()
                .className(AirQualityFunction.class.getName())
                .inputs(inputTopics)
                .name("AirQualityTest")
                .runtime(FunctionConfig.Runtime.JAVA)
                .autoAck(true)
                .build();

        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl("pulsar://pulsar1:6650")
                .functionConfig(functionConfig)
                .build();

        localRunner.start(false);

        Thread.sleep(30000);
        localRunner.stop();
        System.exit(0);
    }
}
