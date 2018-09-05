package com.haulmont.testtask.ui.converters;

import com.vaadin.data.util.converter.Converter;
import java.util.Locale;

public class StringToDoubleConverter implements Converter<String, Double> {

        @Override
        public Double convertToModel(String value, Class<? extends Double> targetType, Locale locale)
                    throws com.vaadin.data.util.converter.Converter.ConversionException {
            if (value == null)
                return null;
            if (value.isEmpty()) {
                // Or set some kind of default value at start.
                return null;
            }
            return Double.parseDouble(value);
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