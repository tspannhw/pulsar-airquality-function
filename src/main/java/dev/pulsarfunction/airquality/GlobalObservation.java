package dev.pulsarfunction.airquality;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalObservation implements Serializable {
    private static final long serialVersionUID = 2L;

    /**
     *     //{"
     *     apigw-requestid":"QN4Ori9eoAMEJZA=",
     *     "date":"Thu, 07 Apr 2022 15:45:46 GMT",
     *     "country":"US",
     *     "lastUpdated":"2022-04-07T05:05:19+00:00",
     *     "unit":"particles/cm³",
     *     "city":"",
     *     "latitude":"33.719944",
     *     "parameter":"um010",
     *     "location":"SCTV_09",
     *     "server-timing":"total;dur=208.18",
     *     "value":"0.11",
     *     "longitude":"-117.42265"}
     */

    private String apigwRequestid;
    private String date;
    private String country;
    private String lastUpdated;
    private String unit;
    private String city;
    private String latitude;
    private String parameter;
    private String location;
    private String serverTiming;
    private String value;
    private String longitude;

    /**
     * No args constructor for use in serialization
     *
     */
    public GlobalObservation() {
        super();
    }

    public GlobalObservation(String apigwRequestid, String date, String country, String lastUpdated, String unit, String city, String latitude, String parameter, String location, String serverTiming, String value, String longitude) {
        super();
        this.apigwRequestid = apigwRequestid;
        this.date = date;
        this.country = country;
        this.lastUpdated = lastUpdated;
        this.unit = unit;
        this.city = city;
        this.latitude = latitude;
        this.parameter = parameter;
        this.location = location;
        this.serverTiming = serverTiming;
        this.value = value;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GlobalObservation.class.getSimpleName() + "[", "]")
                .add("apigwRequestid='" + apigwRequestid + "'")
                .add("date='" + date + "'")
                .add("country='" + country + "'")
                .add("lastUpdated='" + lastUpdated + "'")
                .add("unit='" + unit + "'")
                .add("city='" + city + "'")
                .add("latitude='" + latitude + "'")
                .add("parameter='" + parameter + "'")
                .add("location='" + location + "'")
                .add("serverTiming='" + serverTiming + "'")
                .add("value='" + value + "'")
                .add("longitude='" + longitude + "'")
                .toString();
    }

    public String getApigwRequestid() {
        return apigwRequestid;
    }

    public void setApigwRequestid(String apigwRequestid) {
        this.apigwRequestid = apigwRequestid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getServerTiming() {
        return serverTiming;
    }

    public void setServerTiming(String serverTiming) {
        this.serverTiming = serverTiming;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
