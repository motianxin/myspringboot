package com.zhigang.myspringboot;

import javax.annotation.Nonnull;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * <判断语句链式表达>
 *
 * @Author admin
 * @Since 2020/12/5 0:39
 */
public final class Judge {
    private BooleanSupplier test;

    private Judge(BooleanSupplier test) {
        this.test = test;
    }

    public static Judge ifTrue(@Nonnull BooleanSupplier booleanSupplier) {
        return new Judge(booleanSupplier);
    }

    public Judge or(@Nonnull BooleanSupplier booleanSupplier) {
        test = () -> test.getAsBoolean() || booleanSupplier.getAsBoolean();
        return this;
    }

    public Judge and(@Nonnull BooleanSupplier booleanSupplier) {
        test = () -> test.getAsBoolean() && booleanSupplier.getAsBoolean();
        return this;
    }

    public void then(@Nonnull Runnable runnable) {
        if (test.getAsBoolean()) {
            runnable.run();
        }
    }

    public void thenOrElse(@Nonnull Runnable trueRun, @Nonnull Runnable falseRun) {
        if (test.getAsBoolean()) {
            trueRun.run();
        } else {
            falseRun.run();
        }
    }

    public void orElse(@Nonnull Runnable falseRun) {
        if (test.getAsBoolean()) {
            return;
        }
        falseRun.run();
    }

    public <T> T thenGet(@Nonnull Supplier<T> supplier, T elseValue) {
        return test.getAsBoolean() ? supplier.get() : elseValue;
    }

    public <T> T thenGet(@Nonnull Supplier<T> supplier, @Nonnull Supplier<T> elseSupplier) {
        return test.getAsBoolean() ? supplier.get() : elseSupplier.get();
    }

    public boolean get() {
        return test.getAsBoolean();
    }
}
