package com.rahul.project.gateway.utility;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
public interface Patterns {

    String Validator_numberRegEx = "[0-9]+";
    String Validator_EmailRegEx = "^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$";
    String Validator_passwordRegEx = "^[0-9A-Za-z!@#$%^&*]+$";
    String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    String Validator_numAndCharRegEx = "^[a-zA-Z0-9_.-]*$";
    String Validator_minSize = "1";
    String Validator_CharRegEx = "^[a-zA-Z_ ]*$";
    String DECIMAL_PATTERN = "^[0-9]+$";
    String TIME_PATTERN = "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}";
    String Validator_TureOrFalse = "^([Tt][Rr][Uu][Ee]|[Ff][Aa][Ll][Ss][Ee])$";
    String Validator_numberDecimalRegEx = "^(\\d*\\.)?\\d+$";
}
