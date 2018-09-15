package com.github.mhdirkse.utils;

/**
 * Interface that supports formatting messages from enum values. To
 * use this class, derive an enum from this interface. The enum
 * will inherit the format method, allowing it to format a message
 * that is specific for the enum value. This is useful for error handling.
 * The system under test produces enum values, which are easier to check
 * then formatted error messages. Formatting the messages for the user
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
}
