package org.sample;

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
@Warmup(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class ArraysAndHardwarePrefill
{
    final int SIZE = 100_000;
    final int[] src = new int[SIZE];

    @Setup
    public void setup()
    {
        // warm the cache
        for (int i = 0; i < SIZE; i++)
        {
            src[i] = 1;
        }
    }
    
    @Benchmark
    public int step1()
    {
        int sum = 0;
        for (int i = 0; i < SIZE; i++)
        {
            sum += src[i];
        }

        return sum;
    }

    @Benchmark
    public int step32()
    {
        int sum = 0;
        for (int i = 0; i < SIZE; i = i + 32)
        {
            sum += src[i];
        }

        return sum;
    }
    
    @Benchmark
    public int step16()
    {
        int sum = 0;
        for (int i = 0; i < SIZE; i = i + 16)
        {
            sum += src[i];
        }

        return sum;
    }
    
    @Benchmark
    public int step1Reverse()
    {
        int sum = 0;
        for (int i = SIZE - 1;  i >= 0; i--)
        {
            sum += src[i];
        }

        return sum;
    }
}
