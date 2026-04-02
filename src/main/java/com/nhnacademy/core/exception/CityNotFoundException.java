package com.nhnacademy.core.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String city) {
        super("존재하지 않는 지자체입니다: " + city);
    }
}