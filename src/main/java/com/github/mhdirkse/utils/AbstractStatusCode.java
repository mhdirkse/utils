package com.github.mhdirkse.utils;

public interface AbstractStatusCode {
    default String format(String... args) {
        String result = getFormatString();
        for(int i = 0; i < args.length; ++i) {
            result = result.replaceAll(
                    formatter(i+1),
                    args[i]);
        }
        return result;
    }

    String getFormatString();

    static String formatter(final int i) {
        return String.format("\\Q{%d}\\E", i);
    }

    static String anyFormatter() {
        return "\\{\\d+\\}";
    }
}
