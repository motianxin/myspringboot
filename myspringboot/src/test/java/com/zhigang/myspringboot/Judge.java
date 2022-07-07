package com.zhigang.myspringboot;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * <判断语句链式表达，knowledgeSummary>
 *
 * @Author admin
 * @Since 2020/12/5 0:39
 */
public final class Judge {
    private BooleanSupplier test;

    private Judge(BooleanSupplier test) {
        this.test = test;
    }

    public static Judge ifTrue(BooleanSupplier booleanSupplier) {
        return new Judge(booleanSupplier);
    }

    public Judge or(BooleanSupplier booleanSupplier) {
        test = () -> test.getAsBoolean() || booleanSupplier.getAsBoolean();
        return this;
    }

    public Judge and(BooleanSupplier booleanSupplier) {
        test = () -> test.getAsBoolean() && booleanSupplier.getAsBoolean();
        return this;
    }

    public void then(Runnable runnable) {
        if (test.getAsBoolean()) {
            runnable.run();
        }
    }

    public void thenOrElse(Runnable trueRun, Runnable falseRun) {
        if (test.getAsBoolean()) {
            trueRun.run();
        } else {
            falseRun.run();
        }
    }

    public void orElse(Runnable falseRun) {
        if (test.getAsBoolean()) {
            return;
        }
        falseRun.run();
    }

    public <T> T thenGet(Supplier<T> supplier, T elseValue) {
        return test.getAsBoolean() ? supplier.get() : elseValue;
    }

    public <T> T thenGet(Supplier<T> supplier, Supplier<T> elseSupplier) {
        return test.getAsBoolean() ? supplier.get() : elseSupplier.get();
    }

    public boolean get() {
        return test.getAsBoolean();
    }
}
