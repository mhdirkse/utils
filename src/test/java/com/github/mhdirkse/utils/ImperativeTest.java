package com.github.mhdirkse.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImperativeTest {
    private List<Integer> outputs;

    private int[] getOutputArray() {
        return ArrayUtils.toPrimitive(outputs.toArray(new Integer[]{}), 0);
    }

    @Before
    public void setUp() {
        outputs = new ArrayList<>();
    }

    private boolean addAndReturn(final int toAdd, final boolean toReturn) {
        outputs.add(toAdd);
        return toReturn;
    }

    private void add(final int toAdd) {
        outputs.add(toAdd);
    }

    @Test
    public void whenNoFailablesThenLastExecuted() {
        Imperative.runWhileTrue(new ArrayList<Supplier<Boolean>>(), () -> this.add(1));
        Assert.assertArrayEquals(new int[] {1}, getOutputArray());
    }

    @Test
    public void whenOneFailableReturnsTrueThenLastExecuted() {
        List<Supplier<Boolean>> failables = new ArrayList<>();
        failables.add(() -> addAndReturn(1, true));
        Runnable last = () -> add(2);
        Imperative.runWhileTrue(failables, last);
        Assert.assertArrayEquals(new int[] {1, 2}, getOutputArray());
    }

    @Test
    public void whenOneFailableReturnsFalseThenLastNotExecuted() {
        List<Supplier<Boolean>> failables = new ArrayList<>();
        failables.add(() -> addAndReturn(1, false));
        Runnable last = () -> add(2);
        Imperative.runWhileTrue(failables, last);
        Assert.assertArrayEquals(new int[] {1}, getOutputArray());
    }

    @Test
    public void whenTwoFailableOfTwoReturnsTrueThenAllExecuted() {
        List<Supplier<Boolean>> failables = new ArrayList<>();
        failables.add(() -> addAndReturn(1, true));
        failables.add(() -> addAndReturn(2, true));
        Assert.assertTrue(Imperative.runWhileTrue(failables));
        Assert.assertArrayEquals(new int[] {1, 2}, getOutputArray());
    }

    @Test
    public void whenOneFailableOfTwoReturnsFalseThenSecondNotExecuted() {
        List<Supplier<Boolean>> failables = new ArrayList<>();
        failables.add(() -> addAndReturn(1, false));
        failables.add(() -> addAndReturn(2, true));
        Assert.assertFalse(Imperative.runWhileTrue(failables));
        Assert.assertArrayEquals(new int[] {1}, getOutputArray());
    }

    @Test
    public void whenNoErrorMakersThenLastExecuted() {
        Imperative.runWhileFalse(new ArrayList<Supplier<Boolean>>(), () -> this.add(1));
        Assert.assertArrayEquals(new int[] {1}, getOutputArray());
    }

    @Test
    public void whenOneErrorMakerReturnsFalseThenLastExecuted() {
        List<Supplier<Boolean>> errorMakers = new ArrayList<>();
        errorMakers.add(() -> addAndReturn(1, false));
        Runnable last = () -> add(2);
        Imperative.runWhileFalse(errorMakers, last);
        Assert.assertArrayEquals(new int[] {1, 2}, getOutputArray());
    }

    @Test
    public void whenOneErrorMakerReturnsTrueThenLastNotExecuted() {
        List<Supplier<Boolean>> errorMakers = new ArrayList<>();
        errorMakers.add(() -> addAndReturn(1, true));
        Runnable last = () -> add(2);
        Imperative.runWhileFalse(errorMakers, last);
        Assert.assertArrayEquals(new int[] {1}, getOutputArray());
    }

    @Test
    public void whenTwoErrorMakersOfTwoReturnFalseThenAllExecuted() {
        List<Supplier<Boolean>> errorMakers = new ArrayList<>();
        errorMakers.add(() -> addAndReturn(1, false));
        errorMakers.add(() -> addAndReturn(2, false));
        Assert.assertFalse(Imperative.runWhileFalse(errorMakers));
        Assert.assertArrayEquals(new int[] {1, 2}, getOutputArray());
    }

    @Test
    public void whenOneErrorMakerOfTwoReturnsTrueThenSecondNotExecuted() {
        List<Supplier<Boolean>> errorMakers = new ArrayList<>();
        errorMakers.add(() -> addAndReturn(1, true));
        errorMakers.add(() -> addAndReturn(2, false));
        Assert.assertTrue(Imperative.runWhileFalse(errorMakers));
        Assert.assertArrayEquals(new int[] {1}, getOutputArray());
    }
}
