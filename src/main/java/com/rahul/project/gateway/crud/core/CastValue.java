package com.rahul.project.gateway.crud.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Rahul Malhotra
 * <p>
 * Standard that defines casting operations
 */
public interface CastValue {

    String BOOLEAN = "java.lang.Boolean";
    String NUMBER = "java.lang.Number";
    String INTEGER = "java.lang.Integer";// java.lang.Integer
    String BIGINTEGER = "java.math.BigInteger";// java.math.BigInteger
    String BIGDECIMAL = "java.math.BigDecimal";// java.math.BigDecimal
    String BYTE = "java.lang.Byte";// java.lang.Byte
    String LONG = "java.lang.Long";// java.lang.Long
    String FLOAT = "java.lang.Float";// java.lang.Float
    String DOUBLE = "java.lang.Double";// java.lang.Double
    String CHAR = "java.lang.Character";// java.lang.Character
    String STRING = "java.lang.String";// java.lang.String
    String SQLDATE = "java.sql.Date";// java.sql.Date
    String UTLDATE = "java.util.Date";// java.util.Date
    String TIMESTAMP = "java.sql.Timestamp";// java.sql.Timestamp
    String INTG_DATE_FORMAT = "mm/dd/YYYY";
    String INTG_TIME_FORMAT = "mm/dd/YYYY HH:MM:SS";

    default <T> T safeCastValue(String value, Class<T> clz, String defaultValue) {
        T returnVal = null;
        try {
            returnVal = castValue(value, clz, defaultValue);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    default <T> T castValue(String value, Class<T> clz, String defaultValue) {
        if (value == null || value.isEmpty()) {
            if (defaultValue == null || defaultValue.isEmpty()) {
                throw new IllegalArgumentException("One of either <value> or <defaultValue> should not be null.");
            }
            value = defaultValue;
        }
        return this.castObject(value, clz);
    }

    /**
     * if (className.equals(INTEGER)) { returnValue = Integer.parseInt(value); } if
     * (className.equals(BIGINTEGER)) { returnValue = new BigInteger(value); } if
     * (className.equals(BIGDECIMAL)) { returnValue = new BigInteger(value); } if
     * (className.equals(BYTE)) { returnValue = Byte.parseByte(value); } if
     * (className.equals(LONG)) { returnValue = Long.parseLong(value); } if
     * (className.equals(FLOAT)) { returnValue = Float.parseFloat(value); } if
     * (className.equals(DOUBLE)) { returnValue = Double.parseDouble(value); } if
     * (className.equals(BOOLEAN)) { returnValue = Boolean.parseBoolean(value); }
     */
    @SuppressWarnings("unchecked")
    default <T> T castString(String value, Class<T> clz) {
        Object returnValue = null;
        String className = clz.getTypeName();
        switch (className) {
            case INTEGER:
                returnValue = Integer.parseInt(value);
                break;
            case BIGINTEGER:
                returnValue = new BigInteger(value);
                break;
            case BIGDECIMAL:
                returnValue = new BigInteger(value);
                break;
            case BYTE:
                returnValue = Byte.parseByte(value);
                break;
            case LONG:
                returnValue = Long.parseLong(value);
                break;
            case FLOAT:
                returnValue = Float.parseFloat(value);
                break;
            case DOUBLE:
                returnValue = Double.parseDouble(value);
                break;
            case BOOLEAN:
                returnValue = Boolean.parseBoolean(value);
                break;
            default:
                throw new IllegalArgumentException("Casting facility not available for provided class " + className);
        }
        return (T) returnValue;
    }

    @SuppressWarnings("unchecked")
    default <T> T castObject(Object value, Class<T> clz) throws ParseException {
        T returnValue = null;
        String className = clz.getTypeName();
        SimpleDateFormat sdf;
        if (className.equals(STRING) && value instanceof String) {
            return (T) value;
        }
        if (clz.getSuperclass().getTypeName().equals(NUMBER) || clz.getTypeName().equals(BOOLEAN)) {
            return value instanceof String ? castString(value.toString(), clz) : (T) value;
        } else if (className.equals(SQLDATE)) {
            sdf = new SimpleDateFormat(INTG_DATE_FORMAT);
            return (T) sdf.parse(value.toString());
        } else if (className.equals(UTLDATE)) {
            sdf = new SimpleDateFormat(INTG_TIME_FORMAT);
            return (T) sdf.parse(value.toString());
        } else if (className.equals(TIMESTAMP)) {
            sdf = new SimpleDateFormat(INTG_TIME_FORMAT);
            return (T) sdf.parse(value.toString());
        }
        return returnValue;
    }

    default <T> T safeCastObject(Object value, Class<T> clz) {
        T t = null;
        try {
            t = castObject(value, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    default Integer intValue(Object value) {
        return this.safeCastObject(value, Integer.class);
    }

    default Long longValue(Object value) {
        return this.safeCastObject(value, Long.class);
    }

    default String stringValue(Object value) {
        return this.safeCastObject(value, String.class);
    }

    default Date sqlDateValue(Object value) {
        return this.safeCastObject(value, Date.class);
    }

    default java.util.Date utilDateValue(Object value) {
        return this.safeCastObject(value, java.util.Date.class);
    }

    default Timestamp timestampValue(Object value) {
        return this.safeCastObject(value, Timestamp.class);
    }

    default boolean boolValue(Object value) {
        return this.safeCastObject(value, Boolean.class);
    }

    default BigDecimal bigDecimalValue(Object value) {
        return this.safeCastObject(value, BigDecimal.class);
    }

    /**
     * Below methods are to be used in custom map and CRUDRequest User can see
     * castObject(String key, Class<T> clz) implementation in both these classes
     */
    default <T> T castObject(String key, Class<T> clz) {
        return null;// Impl needs to be provided according to requirement
    }

    default Integer intValue(String key) {
        return this.castObject(key, Integer.class);
    }

    default Long longValue(String key) {
        return this.castObject(key, Long.class);
    }

    default String stringValue(String key) {
        return this.castObject(key, String.class);
    }

    default Date sqlDateValue(String key) {
        return this.castObject(key, Date.class);
    }

    default java.util.Date utilDateValue(String key) {
        return this.castObject(key, java.util.Date.class);
    }

    default Timestamp timestampValue(String key) {
        return this.castObject(key, Timestamp.class);
    }

    default boolean boolValue(String key) {
        return this.castObject(key, Boolean.class);
    }

    default BigDecimal bigDecimalValue(String key) {
        return this.castObject(key, BigDecimal.class);
    }

}

/**
 * "filter":{ "OR":{ "AND":{ "name":"Arun", "isActive":true }, "AND":{
 * "name":"Ajay", "isActive":true }, "OR":{ "name_LIKE":"Ankur",
 * "isActive":true, "age_GT":25 } } }
 *
 * @author Rahul Malhotra
 * @Date : 07-Jun-2019 at 5:32:30 pm
 */
class Filter {
    String qualKey;
    // ApplicationMap<String, Object>

}
