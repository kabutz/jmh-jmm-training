package org.sample;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.misc.FastRandom;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;


@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 4, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class LoopUnroll
{
    @Param({"10000"})
    int size = 10000;
    
    private FastRandom r = new FastRandom(7L);
    
    private int[] ints = null;
    
    @Setup
    @Before
    public void setup()
    {
    }
    
    /**
     * This is a random function call but with a fixed result
     * @param size
     * @return
     */
    private int next(final int size)
    {
        return r.nextInt(1) + size;
    }
    
    @Benchmark
    public int classic()
    {
        int s1 = 1;
        final int step = next(1);
        
        for (int i = 0; i < size; i = i + 1)
        {
            s1 += i;
        }
        
        return s1;
    }
    
    @Benchmark
    public int variable()
    {
        int s1 = 1;
        final int step = next(1);
        
        for (int i = 0; i < size; i = i + step)
        {
            s1 += i;
        }
        
        return s1;
    }
    
    @Benchmark
    public int manualUnroll()
    {
        int s1 = 1;
        final int step = next(1) + 9;
        
        for (int i = 0; i < size; i = i + step)
        {
            s1 += i;
            s1 += i + 1;
            s1 += i + 2;
            s1 += i + 3;
            s1 += i + 4;
            s1 += i + 5;
            s1 += i + 6;
            s1 += i + 7;
            s1 += i + 8;
            s1 += i + 9;
        }
        
        return s1;
    }
    
    @Test
    public void test()
    {
        Assert.assertEquals(classic(), variable());
        Assert.assertEquals(classic(), manualUnroll());
    }
}