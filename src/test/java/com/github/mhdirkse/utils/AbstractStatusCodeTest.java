package com.github.mhdirkse.utils;

import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;

public class AbstractStatusCodeTest {
    private enum StatusCodeStub implements AbstractStatusCode {
        TEST_STATUS_ZERO_ARGS("Some status zero arguments."),
        TEST_STATUS_ONE_ARG("Some status about: {1}."),
        TEST_STATUS_TWO_ARGS("Some status about: {1} and {2}.");

        @Getter(onMethod = @__({@Override}))
        private String formatString;

        StatusCodeStub(final String formatString) {
            this.formatString = formatString;
        }
    }

    @Test
    public void whenStatusFormatsNoArgumentsAndNoArgumentsGivenThenNoFormattingDone() {
        Assert.assertEquals("Some status zero arguments.", StatusCodeStub.TEST_STATUS_ZERO_ARGS.format());
    }

    @Test
    public void whenStatusFormatsOneArgumentAndOneArgumentGivenThenFormatApplied() {
        Assert.assertEquals("Some status about: arg1.", StatusCodeStub.TEST_STATUS_ONE_ARG.format("arg1"));
    }

    @Test
    public void whenStatusFormatsTwoArgumentsAndTwoArgumentsGivenThenFormatApplied() {
        Assert.assertEquals("Some status about: one and two.",
                StatusCodeStub.TEST_STATUS_TWO_ARGS.format("one", "two"));
    }
}
