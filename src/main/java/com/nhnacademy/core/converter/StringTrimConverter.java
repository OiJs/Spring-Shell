package com.nhnacademy.core.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class StringTrimConverter extends AbstractBeanField<String, String> {
    @Override
    protected String convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
        return value == null ? null : value.trim() ;
    }
}
