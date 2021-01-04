package com.rahul.project.gateway.service;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidString(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isValidList(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isValidSet(Set set) {
        return set != null && !set.isEmpty();
    }

    public static boolean isValidPhoneNumberLength(String phoneNo) {
        return phoneNo.length() == 10;
    }

    public static boolean isValidPanCard(String s) {
        String regexPattern = "^[\\w]{3}(p|P|c|C|h|H|f|F|a|A|t|T|b|B|l|L|j|J|g|G)[\\w][\\d]{4}[\\w]$";
        Pattern sPattern
                = Pattern.compile(regexPattern);
        return sPattern.matcher(s).matches();
    }

    public static boolean isValidIfscCode(String s) {
        String regexPattern = "^[a-zA-Z0-9]*$";//"[A-Z|a-z]{4}[0][\\d]{6}$";
        Pattern sPattern
                = Pattern.compile(regexPattern);
        return sPattern.matcher(s).matches();
    }

}
