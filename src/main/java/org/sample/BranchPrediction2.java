package org.sample;

import java.util.Arrays;
import java.util.Random;
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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
public class BranchPrediction2
{
    private static final int COUNT = 10_000;

    private int[] sorted = new int[COUNT];
    private int[] unsorted = new int[COUNT];

    @Setup
    public void setup()
    {
        final Random r = new Random(13241L);
        
        for (int i = 0; i < COUNT; i++)
        {
        	final int d = r.nextInt();
        	
            sorted[i] = d;
            unsorted[i] = d;
        }        
        Arrays.sort(sorted);
    }

    public int doIt(int[] array)
    {
    	int sum = 0;
        for (int v : array)
        {
            if (v > 0)
            {
                sum = sum + 2;
            }
            else
            {
                sum = sum + 1;
            }
        }
        return sum;
    }
    
    
    @Benchmark
    public int sorted()
    {
    	return doIt(sorted);
    }

    @Benchmark
    public int unsorted()
    {
    	return doIt(unsorted);
    }
}
