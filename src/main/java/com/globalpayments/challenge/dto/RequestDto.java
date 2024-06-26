package com.globalpayments.challenge.dto;

public class RequestDto {
    private String carName;
    private String carType;
    private Integer daysRented;
    private Integer daysRequested;
    private long customerId;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getDaysRented() {
        return daysRented;
    }

    public void setDaysRented(Integer daysRented) {
        this.daysRented = daysRented;
    }

    public Integer getDaysRequested() {
        return daysRequested;
    }

    public void setDaysRequested(Integer daysRequested) {
        this.daysRequested = daysRequested;
    }
}
