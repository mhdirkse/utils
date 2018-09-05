package com.github.mhdirkse.utils;

public interface AbstractStatusCode {
    default String format(String... args) {
        String result = getFormatString();
        for(int i = 0; i < args.length; ++i) {
            String toReplace = String.format("\\Q{%d}\\E", i+1);
            result = result.replaceAll(
                    toReplace,
                    args[i]);
        }
        return result;
    }

    String getFormatString();
}
