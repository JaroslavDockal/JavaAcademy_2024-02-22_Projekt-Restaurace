package com.engeto.ja.restaurant;

public class Settings {

    private static final String DEFAULT_IMAGE = "blank";
    private static final String DATE_FORMAT = "d.M. H:m";
    private static final String DELIMITER = "\t";
    private static final int MAX_TABLE_NUMBER = 19;

    public static String getDefaultImage() {
        return DEFAULT_IMAGE;
    }

    public static String getDelimiter() {
        return DELIMITER;
    }

    public static String getDateFormat() {
        return DATE_FORMAT;
    }

    public static int getMaxTableNumber() {
        return MAX_TABLE_NUMBER;
    }

}