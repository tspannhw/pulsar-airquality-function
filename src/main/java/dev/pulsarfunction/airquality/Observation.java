package dev.pulsarfunction.airquality;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Observation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
       {"dateObserved":"2022-04-07 ",
        "hourObserved":13,
        "localTimeZone":"EST",
        "reportingArea":"Atlanta",
        "stateCode":"GA",
        "latitude":33.65,
        "longitude":-84.43,
        "parameterName":"O3",
        "aqi":40,
        "category":{"number":1,"name":"Good",
        "additionalProperties":{}},
        "additionalProperties":{}}
     */

    private String dateObserved;
    private Integer hourObserved;
    private String localTimeZone;
    private String reportingArea;
    private String stateCode;
    private String parameterName;
    private Double latitude;
    private Double longitude;
    private Integer aqi;

    /**
     * No args constructor for use in serialization
     *
     */
    public Observation() {
        super();
    }

    public Observation(String dateObserved, Integer hourObserved, String localTimeZone, String reportingArea, String stateCode, String parameterName, Double latitude, Double longitude, Integer aqi) {
        super();
        this.dateObserved = dateObserved;
        this.hourObserved = hourObserved;
        this.localTimeZone = localTimeZone;
        this.reportingArea = reportingArea;
        this.stateCode = stateCode;
        this.parameterName = parameterName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Observation.class.getSimpleName() + "[", "]")
                .add("dateObserved='" + dateObserved + "'")
                .add("hourObserved=" + hourObserved)
                .add("localTimeZone='" + localTimeZone + "'")
                .add("reportingArea='" + reportingArea + "'")
                .add("stateCode='" + stateCode + "'")
                .add("parameterName='" + parameterName + "'")
                .add("latitude=" + latitude)
                .add("longitude=" + longitude)
                .add("aqi=" + aqi)
                .toString();
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getDateObserved() {
        return dateObserved;
    }

    public void setDateObserved(String dateObserved) {
        this.dateObserved = dateObserved;
    }

    public Integer getHourObserved() {
        return hourObserved;
    }

    public void setHourObserved(Integer hourObserved) {
        this.hourObserved = hourObserved;
    }

    public String getLocalTimeZone() {
        return localTimeZone;
    }

    public void setLocalTimeZone(String localTimeZone) {
        this.localTimeZone = localTimeZone;
    }

    public String getReportingArea() {
        return reportingArea;
    }

    public void setReportingArea(String reportingArea) {
        this.reportingArea = reportingArea;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }
}