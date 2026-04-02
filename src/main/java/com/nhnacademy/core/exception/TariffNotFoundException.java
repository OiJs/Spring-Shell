package com.nhnacademy.core.exception;

public class TariffNotFoundException extends RuntimeException {
    public TariffNotFoundException(String city, String sector) {
        super("요금 정보를 찾을 수 없습니다. city: " + city + ", sector: " + sector);
    }
}