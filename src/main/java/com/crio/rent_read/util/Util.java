package com.crio.rent_read.util;

public class Util {
    
    public static String mask(String stringValue) {
        if (stringValue == null || stringValue.length() < 4) {
            return stringValue;
        }

        return stringValue.replaceAll("(?<=.{3}).", "*");
    }
}
