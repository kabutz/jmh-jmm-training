package org.sample;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 30, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(3)
public class StringsMixed {
    private static final String PREFIX = "a";
    private static final String SEPARATOR = ";";
    private static final String FORMAT =
            PREFIX + "%d" + SEPARATOR + "%s" + SEPARATOR + "%b";
    private long time;
    private double nanoTime;
    private int LENGTH;
    private boolean oddTime;

    @Setup
    public void setup() {
        time = System.currentTimeMillis();
        nanoTime = System.nanoTime();
        oddTime = time % 2 == 1;
        String expected = plain();
        LENGTH = expected.length();
        for (Method method : StringsMixed.class.getDeclaredMethods()) {
            if (method.getReturnType() == String.class
                    && method.getParameterCount() == 0) {
                try {
                    String result = (String) method.invoke(this);
                    if (!result.equals(expected)) {
                        throw new AssertionError(String.format(
                                "Expected \"%s\", but got \"%s\" from method %s()",
                                expected, result, method.getName()));
                    }
                } catch (ReflectiveOperationException e) {
                    throw new AssertionError(e);
                }
            }
        }
    }

    @Benchmark
    public String plain() {
        return PREFIX + time + SEPARATOR + nanoTime + SEPARATOR + oddTime;
    }

    @Benchmark
    public String concat() {
        return PREFIX.concat("" + time).concat(SEPARATOR).concat("" + nanoTime)
                .concat(SEPARATOR).concat("" + oddTime);
    }

    @Benchmark
    public String builder() {
        return new StringBuilder().append(PREFIX)
                .append(time)
                .append(SEPARATOR)
                .append(nanoTime)
                .append(SEPARATOR)
                .append(oddTime)
                .toString();
    }

    @Benchmark
    public String builderSized() {
        return new StringBuilder(LENGTH).append(PREFIX)
                .append(time)
                .append(SEPARATOR)
                .append(nanoTime)
                .append(SEPARATOR)
                .append(oddTime)
                .toString();
    }

    @Benchmark
    public String builderNonFluid() {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX);
        sb.append(time);
        sb.append(SEPARATOR);
        sb.append(nanoTime);
        sb.append(SEPARATOR);
        sb.append(oddTime);
        return sb.toString();
    }

    @Benchmark
    public String builderNonFluidSized() {
        StringBuilder sb = new StringBuilder(LENGTH);
        sb.append(PREFIX);
        sb.append(time);
        sb.append(SEPARATOR);
        sb.append(nanoTime);
        sb.append(SEPARATOR);
        sb.append(oddTime);
        return sb.toString();
    }

    @Benchmark
    public String format() {
        return String.format(FORMAT, time, nanoTime, oddTime);
    }
}