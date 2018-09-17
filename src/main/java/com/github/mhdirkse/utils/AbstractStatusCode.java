package com.github.mhdirkse.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interface that supports formatting messages from enum values. To
 * use this class, derive an enum from this interface. The enum
 * will inherit the format method, allowing it to format a message
 * that is specific for the enum value. This is useful for error handling.
 * The system under test produces enum values, which are easier to check
 * than formatted error messages. Formatting the messages for the user
 * is done in the enum type.
 * <p>
 * Typically, the enum type has a constructor that takes the format string. The enum type then
 * constitutes a list of enum values with their format strings, giving a very clear
 * overview about the errors that are supported.
 * @author martijn
 *
 */
public interface AbstractStatusCode {
    /**
     * @param args String values to be substituted for placeholders {1}, {2}, ... .
     * @return The formatted message.
     */
    default String format(String... args) {
        String result = getFormatString();
        for(int i = 0; i < args.length; ++i) {
            result = result.replaceAll(
                    formatter(i+1),
                    args[i]);
        }
        if(isTestMode()) {
            Pattern p = Pattern.compile(anyFormatter());
            Matcher m = p.matcher(result);
            if(m.find()) {
                throw new IllegalArgumentException("There were missing format parameters");
            }
        }
        return result;
    }

    /**
     * @return The format string, to be supplied by a derived enum type.
     */
    String getFormatString();

    /**
     * @param i Formatter index.
     * @return Regular expression to be substituted.
     */
    static String formatter(final int i) {
        return String.format("\\Q{%d}\\E", i);
    }

    /**
     * @return Regular expression that can be used to check whether there are
     * unsubstituted formatters.
     */
    static String anyFormatter() {
        return "\\{\\d+\\}";
    }

    /**
     * Indicates whether calls to format should test the result. If true,
     * then an exception is thrown if the format string still contains
     * formatters after format parameter substitution. Default behaviour
     * is to return false.
     */
    default boolean isTestMode() {
        return false;
    }
}
