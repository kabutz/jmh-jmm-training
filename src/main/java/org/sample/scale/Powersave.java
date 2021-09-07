package org.sample.scale;

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

@Warmup(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Powersave 
{
    private final int SIZE = 100_000_000;
    private final int[] SOURCE = new int[SIZE];
    
    double doCPUBoundStuff(int[] data) 
    {
        int numDataPoints = data.length;
        int i = 0;
        double avg = 0;
        double var = 0;

        data[0] = 0;
        data[1] = 1;
        
        for (i = 2; i < numDataPoints; i++) {
            data[i] = data[i-1] + data[i-2];
        }

        for (avg = 0, i = 0; i < numDataPoints; i++) {
            avg += (double)numDataPoints;
        }
        avg /= (double) numDataPoints;

        for (var = 0, i = 0; i < numDataPoints; i++) {
            double diff = (double) data[i] - avg;
            var += diff*diff;
        }
        var /= (double) (numDataPoints - 1);
        
        return avg;
    }
    
    @Setup
    public void setup()
    {
        for (int i = 0; i < SIZE; i++)
        {
            SOURCE[i] = i;
        }
    }
    
    @Benchmark
    @Threads(1)
    public double t1() 
    {
        return doCPUBoundStuff(SOURCE);
    }

    @Benchmark
    @Threads(2)
    public double t2() 
    {
        return doCPUBoundStuff(SOURCE);
    }

    @Benchmark
    @Threads(4)
    public double t4() 
    {
        return doCPUBoundStuff(SOURCE);
    }

    @Benchmark
    @Threads(6)
    public double t6() 
    {
        return doCPUBoundStuff(SOURCE);
    }
    
    @Benchmark
    @Threads(8)
    public double t8() 
    {
        return doCPUBoundStuff(SOURCE);
    }
}