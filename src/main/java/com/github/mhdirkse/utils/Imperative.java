package com.github.mhdirkse.utils;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/**
 * Imperative utils that support functional programming. These
 * are workarounds in Java 8 for the function Stream.takeWhile
 * introduced in Java 9.
 * @author martijn
 *
 */
public final class Imperative {
    private Imperative() {
    }

    /**
     * @param failables Boolean suppliers. They are executed until one of the
     * suppliers returns false.
     * @return true if all failables returned true, otherwise false.
     */
    public static boolean runWhileTrue(final List<Supplier<Boolean>> failables) {
        boolean success = true;
        Iterator<Supplier<Boolean>> it = failables.iterator();
        while(success && it.hasNext()) {
            success = it.next().get();
        }
        return success;
    }

    /**
     * @param failables Boolean suppliers. They are executed until one of the
     * suppliers returns false.
     * @param last Runnable that is executed if all failables have returned true. 
     */
    public static void runWhileTrue(
            final List<Supplier<Boolean>> failables,
            final Runnable last) {
        boolean success = runWhileTrue(failables);
        if(success) {
            last.run();
        }
    }

    /**
     * @param errorMakers Boolean suppliers. They are executed until one returns true.
     * @return false if all errorMakers returned false, otherwise true. 
     */
    public static boolean runWhileFalse(final List<Supplier<Boolean>> errorMakers) {
        boolean hasErrors = false;
        Iterator<Supplier<Boolean>> it = errorMakers.iterator();
        while((!hasErrors) && it.hasNext()) {
            hasErrors = it.next().get();
        }
        return hasErrors;
    }

    /**
     * @param errorMakers Boolean suppliers. They are executed until one returns true.
     * @param last  Runnable that is executed if all errorMakers have returned false.
     */
    public static void runWhileFalse(
            final List<Supplier<Boolean>> errorMakers,
            final Runnable last) {
        boolean hasErrors = runWhileFalse(errorMakers);
        if(!hasErrors) {
            last.run();
        }
    }
}
