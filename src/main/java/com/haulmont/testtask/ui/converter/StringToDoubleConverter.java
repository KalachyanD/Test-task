package com.haulmont.testtask.ui.converter;

import java.text.NumberFormat;
import java.util.Locale;

public class StringToDoubleConverter extends com.vaadin.data.util.converter.StringToDoubleConverter {

    @Override
    public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException,NumberFormatException {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try{
            return Double.parseDouble(value);
        }catch (NumberFormatException e){
            return 0.0;
        }
    }

    @Override
    public String convertToPresentation(Double value, Class<? extends String> targetType, Locale locale)
            throws com.vaadin.data.util.converter.Converter.ConversionException {
        if (value == null)
            return null;
        return String.valueOf(value);
    }

    @Override
    public Class<Double> getModelType() {
        return Double.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}