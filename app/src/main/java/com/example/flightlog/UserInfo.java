package com.example.flightlog;

public class UserInfo {
    private String Commander;
    private String CoPilot;
    private String Source;

    public String getFlightNumber() {
        return FlightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        FlightNumber = flightNumber;
    }

    public String getAircraftType() {
        return AircraftType;
    }

    public void setAircraftType(String aircraftType) {
        AircraftType = aircraftType;
    }

    private String Destination;
    private Integer Year;
    private Integer Month;
    private Integer Day;
    private Integer StartHr;
    private Integer StartMin;
    private Integer StopHr;
    private Integer StopMin;

    public String getTime1() {
        return Time1;
    }

    public void setTime1(String time1) {
        Time1 = time1;
    }

    public String getTime2() {
        return Time2;
    }

    public void setTime2(String time2) {
        Time2 = time2;
    }

    public String getTime3() {
        return Time3;
    }

    public void setTime3(String time3) {
        Time3 = time3;
    }

    private Integer DayTimeHr;
    private Integer DayTimeMin;
    private String FlightNumber;
    private String AircraftType;
    private String Time1;
    private String Time2;
    private String Time3;

    public Integer getDayTimeHr() {
        return DayTimeHr;
    }

    public void setDayTimeHr(Integer dayTimeHr) {
        DayTimeHr = dayTimeHr;
    }

    public Integer getDayTimeMin() {
        return DayTimeMin;
    }

    public void setDayTimeMin(Integer dayTimeMin) {
        DayTimeMin = dayTimeMin;
    }

    public Integer getNightTimeHr() {
        return NightTimeHr;
    }

    public void setNightTimeHr(Integer nightTimeHr) {
        NightTimeHr = nightTimeHr;
    }

    public Integer getNightTimeMin() {
        return NightTimeMin;
    }

    public void setNightTimeMin(Integer nightTimeMin) {
        NightTimeMin = nightTimeMin;
    }

    private Integer NightTimeHr;
    private Integer NightTimeMin;

    public Integer getStartHr() {
        return StartHr;
    }

    public void setStartHr(Integer startHr) {
        StartHr = startHr;
    }

    public Integer getStartMin() {
        return StartMin;
    }

    public void setStartMin(Integer startMin) {
        StartMin = startMin;
    }

    public Integer getStopHr() {
        return StopHr;
    }

    public void setStopHr(Integer stopHr) {
        StopHr = stopHr;
    }

    public Integer getStopMin() {
        return StopMin;
    }

    public void setStopMin(Integer stopMin) {
        StopMin = stopMin;
    }

    public UserInfo(String commander, String coPilot, String source, String destination,
                    Integer year, Integer month, Integer day, Integer startHr, Integer startMin,
                    Integer stopHr, Integer stopMin, String aircraftType) {
        Commander = commander;
        CoPilot = coPilot;
        Source = source;
        Destination = destination;
        Year = year;
        Month = month;
        Day = day;
        StartHr = startHr;
        StartMin = startMin;
        StopHr = stopHr;
        StopMin = stopMin;
        AircraftType = aircraftType;
    }




    public Integer getYear() {
        return Year;
    }

    public void setYear(Integer year) {
        Year = year;
    }

    public Integer getMonth() {
        return Month;
    }

    public void setMonth(Integer month) {
        Month = month;
    }

    public Integer getDay() {
        return Day;
    }

    public void setDay(Integer day) {
        Day = day;
    }

    public UserInfo(String commander, String coPilot, String source, String destination) {
        Commander = commander;
        CoPilot = coPilot;
        Source = source;
        Destination = destination;
    }

    public UserInfo() {

    }

    public String getCommander() {
        return Commander;
    }

    public void setCommander(String commander) {
        Commander = commander;
    }

    public String getCoPilot() {
        return CoPilot;
    }

    public void setCoPilot(String coPilot) {
        CoPilot = coPilot;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public static Integer getMonth(String smonth) {
        Integer month = -1;
        switch(smonth) {
            case "January": {
                month = 1;
                break;
            }

            case "February" : {
                month = 2;
                break;
            }

            case "March" : {
                month = 3;
                break;
            }

            case "April" : {
                month = 4;
                break;
            }

            case "May" : {
                month = 5;
                break;
            }

            case "June" : {
                month = 6;
                break;
            }

            case "July" : {
                month = 7;
                break;
            }

            case "August" : {
                month = 8;
                break;
            }

            case "September" : {
                month = 9;
                break;
            }

            case "October" : {
                month = 10;
                break;
            }

            case "November" : {
                month = 11;
                break;
            }

            case "December" : {
                month = 12;
                break;
            }


        }
        return  month;
    }
};
