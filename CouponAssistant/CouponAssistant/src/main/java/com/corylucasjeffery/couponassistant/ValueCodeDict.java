package com.corylucasjeffery.couponassistant;


import android.util.Log;

import java.util.HashMap;

public class ValueCodeDict {
    private final String TAG = "VALUE";

    private static HashMap<String, String> values = new HashMap<String, String>();
    private static HashMap<String, String> special = new HashMap<String, String>();
    static {
        values.put("00", "Cashier Input");
        values.put("01", "Free Item");
        values.put("02", "B4G1");
        values.put("03", "$1.10");
        values.put("04", "$1.35");
        values.put("05", "$1.40");
        values.put("06", "$1.60");
        values.put("07", "B3 $1.50");
        values.put("08", "B2 $3.00");
        values.put("09", "B3 $2.00");
        values.put("10", "$0.10");
        values.put("11", "$1.85");
        values.put("12", "$0.12");
        values.put("13", "B4 $1.00");
        values.put("14", "B1G1");
        values.put("15", "$0.15");
        values.put("16", "B2G1");
        values.put("18", "$2.60");
        values.put("19", "B3G1");
        values.put("20", "$0.20");
        values.put("21", "B2 $0.35");
        values.put("22", "B2 $0.40");
        values.put("23", "B2 $0.45");
        values.put("24", "B2 $0.50");
        values.put("25", "$0.25");
        values.put("26", "$2.85");
        values.put("28", "B2 $0.55");
        values.put("29", "$0.29");
        values.put("30", "$0.30");
        values.put("31", "B2 $0.60");
        values.put("32", "B2 $0.75");
        values.put("33", "B2 $1.00");
        values.put("34", "B2 $1.25");
        values.put("35", "$0.35");
        values.put("36", "B2 $1.50");
        values.put("37", "B3 $0.25");
        values.put("38", "B3 $0.30");
        values.put("39", "$0.39");
        values.put("40", "$0.40");
        values.put("41", "B3 $0.50");
        values.put("42", "B3 $1.00");
        values.put("43", "B2 $1.10");
        values.put("44", "B2 $1.35");
        values.put("45", "$0.45");
        values.put("46", "B2 $1.60");
        values.put("47", "B2 $1.75");
        values.put("48", "B2 $1.85");
        values.put("49", "$0.49");
        values.put("50", "$0.50");
        values.put("51", "B2 $2.00");
        values.put("52", "B3 $0.55");
        values.put("53", "B2 $0.10");
        values.put("54", "B2 $0.15");
        values.put("55", "$0.55");
        values.put("56", "B2 $0.20");
        values.put("57", "B2 $0.25");
        values.put("58", "B2 $0.30");
        values.put("59", "$0.59");
        values.put("60", "$0.60");
        values.put("61", "$10.00");
        values.put("62", "$9.50");
        values.put("63", "$9.00");
        values.put("64", "$8.50");
        values.put("65", "$0.65");
        values.put("66", "$8.00");
        values.put("67", "$7.50");
        values.put("68", "$7.00");
        values.put("69", "$0.69");
        values.put("70", "$0.70");
        values.put("71", "$6.50");
        values.put("72", "$6.00");
        values.put("73", "$5.50");
        values.put("74", "$5.00");
        values.put("75", "$0.75");
        values.put("76", "$1.00");
        values.put("77", "$1.25");
        values.put("78", "$1.50");
        values.put("79", "$0.79");
        values.put("80", "$0.80");
        values.put("81", "$1.75");
        values.put("82", "$2.00");
        values.put("83", "$2.25");
        values.put("84", "$2.50");
        values.put("85", "$0.85");
        values.put("86", "$2.75");
        values.put("87", "$3.00");
        values.put("88", "$3.25");
        values.put("89", "$0.89");
        values.put("90", "$0.90");
        values.put("91", "$3.50");
        values.put("92", "$3.75");
        values.put("93", "$4.00");
        values.put("95", "$0.95");
        values.put("96", "$4.50");
        values.put("98", "B2 $0.65");
        values.put("99", "$0.99");
        special.put("B2", "Buy two or more");
        special.put("B3", "Buy three or more");
        special.put("B4", "Buy four or more");
        special.put("B1G1", "Buy one get one free");
        special.put("B2G1", "Buy two get one free");
        special.put("B3G1", "Buy three get one free");
        special.put("B4G1", "Buy four get one free");
    }
        /*
        BxGy = Buy x or more, Get y free (same product)
        Bx $z = Buy x or more, Get $z off
        Codes that are reserved for future use are not listed
        */

    String getValue(String code) {
        if (values.containsKey(code)) {
            return values.get(code);
        }
        else {
            return "value code not found";
        }
    }

    String extractDiscount(String value) {
        String discount = "";
        String startOfValue = value.substring(0,1);
        if(startOfValue.equals("$")) {
           discount = value;
        }
        else {
            String[] temp = value.split(" ");
            if (temp.length >= 1) {
                discount = "--";
            }
            else if (temp.length == 2) {
                discount = temp[1].trim();
            }
        }
        return discount;
    }

    String extractLimitations(String value) {
        String limitation = "";
        Log.v(TAG, "start extract");
        String startOfValue = value.substring(0, 1);
        Log.v(TAG, "start of value: "+startOfValue);
        if (!startOfValue.equals("$")) {
            String[] split = value.split(" ");
            String temp = split[0].trim();
            Log.v(TAG, "key: "+temp);
            if (special.containsKey(temp)) {
                limitation = special.get(temp);
            }
        }
        return limitation;
    }
}
