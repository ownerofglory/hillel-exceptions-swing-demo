package ua.ithillel.exception.util;

import ua.ithillel.exception.exception.ConversionException;

public class StringUtil {
    public static double convertDouble(String string) throws ConversionException {
        if (!string.matches("^-?\\d+(.\\d+)?$")) {
            // string is not a number
            throw new ConversionException("Unable to convert currency value");
        }

        return Double.parseDouble(string);
    }
}
