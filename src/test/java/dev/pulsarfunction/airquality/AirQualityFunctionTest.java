package dev.pulsarfunction.airquality;

import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;
import org.apache.pulsar.functions.api.Context;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.mock;

/**
 * testing
 */
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

        func.process(JSON_STRING.getBytes(), mock(Context.class));

        func.process(JSON_STRING_PM25.getBytes(), mock(Context.class));

        func.process(JSON_STRING_O3.getBytes(), mock(Context.class));

        func.process(JSON_GLOBAL.getBytes(), mock(Context.class));

        func.process(JSON_GLOBAL_FAIL.getBytes(), mock(Context.class));
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

        /**
         *
         *
         *  .clientAuthParams("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5rRXdSVVU1TUVOQlJrWTJNalEzTVRZek9FVkZRVVUyT0RNME5qUkRRVEU1T1VNMU16STVPUSJ9.eyJodHRwczovL3N0cmVhbW5hdGl2ZS5pby91c2VybmFtZSI6InRzcGFubkBzbmRlbW8uYXV0aC5zdHJlYW1uYXRpdmUuY2xvdWQiLCJpc3MiOiJodHRwczovL2F1dGguc3RyZWFtbmF0aXZlLmNsb3VkLyIsInN1YiI6IkNJRzFGTlNmRnNiOXMyc2lSRHNoY0xCZE1zNWtEMUxzQGNsaWVudHMiLCJhdWQiOiJ1cm46c246cHVsc2FyOnNuZGVtbzpkZW1vLWNsdXN0ZXIiLCJpYXQiOjE2NDk5MzQ4MjksImV4cCI6MTY1MDUzOTYyOSwiYXpwIjoiQ0lHMUZOU2ZGc2I5czJzaVJEc2hjTEJkTXM1a0QxTHMiLCJzY29wZSI6ImFkbWluIGFjY2VzcyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyIsInBlcm1pc3Npb25zIjpbImFkbWluIiwiYWNjZXNzIl19.hXmqplmhxVD-hFrtEJSJSOgNDq0dwxFntfz4PXNsKrDBQZe2gxPfztR13cdpeDJzlza_rCOLfFCqCqzcnV1DJFnoSpsVNjygBrHkiTXKxufDuyvvReLYDujtMuDUl5mr-y5IlKpeiUEIbk3RaW_AVVP6XCzxxqlXNQBq8OLJ3IpYFowM1TRjgEIvHw-K9f-j_BxNDaFTzi1VE40_zUZ1mDA2u9QFre-zhJ84j_Wjj-qdXWygd4BI2lj4MEtK6FODVQVab_cStHYNtx7mA7wk2KsPkXoJ1qWLrL8NjlS7YjLd69vJQTyAkGqzTgr3K5R3TIOinA6KG6ZzJqQmdZhBKQ")
         */
        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl("pulsar+ssl://demo.sndemo.snio.cloud:6651")
                .clientAuthPlugin("org.apache.pulsar.client.impl.auth.AuthenticationToken")
                .clientAuthParams("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5rRXdSVVU1TUVOQlJrWTJNalEzTVRZek9FVkZRVVUyT0RNME5qUkRRVEU1T1VNMU16STVPUSJ9.eyJodHRwczovL3N0cmVhbW5hdGl2ZS5pby91c2VybmFtZSI6InRzcGFubkBzbmRlbW8uYXV0aC5zdHJlYW1uYXRpdmUuY2xvdWQiLCJpc3MiOiJodHRwczovL2F1dGguc3RyZWFtbmF0aXZlLmNsb3VkLyIsInN1YiI6IkNJRzFGTlNmRnNiOXMyc2lSRHNoY0xCZE1zNWtEMUxzQGNsaWVudHMiLCJhdWQiOiJ1cm46c246cHVsc2FyOnNuZGVtbzpkZW1vLWNsdXN0ZXIiLCJpYXQiOjE2NDk5MzQ4MjksImV4cCI6MTY1MDUzOTYyOSwiYXpwIjoiQ0lHMUZOU2ZGc2I5czJzaVJEc2hjTEJkTXM1a0QxTHMiLCJzY29wZSI6ImFkbWluIGFjY2VzcyIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyIsInBlcm1pc3Npb25zIjpbImFkbWluIiwiYWNjZXNzIl19.hXmqplmhxVD-hFrtEJSJSOgNDq0dwxFntfz4PXNsKrDBQZe2gxPfztR13cdpeDJzlza_rCOLfFCqCqzcnV1DJFnoSpsVNjygBrHkiTXKxufDuyvvReLYDujtMuDUl5mr-y5IlKpeiUEIbk3RaW_AVVP6XCzxxqlXNQBq8OLJ3IpYFowM1TRjgEIvHw-K9f-j_BxNDaFTzi1VE40_zUZ1mDA2u9QFre-zhJ84j_Wjj-qdXWygd4BI2lj4MEtK6FODVQVab_cStHYNtx7mA7wk2KsPkXoJ1qWLrL8NjlS7YjLd69vJQTyAkGqzTgr3K5R3TIOinA6KG6ZzJqQmdZhBKQ")
                .functionConfig(functionConfig)
                .build();

        localRunner.start(false);

        Thread.sleep(30000);
        localRunner.stop();
        System.exit(0);
    }
}