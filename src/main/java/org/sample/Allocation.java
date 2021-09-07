package org.sample;

import java.util.concurrent.TimeUnit;

import org.misc.FastRandom;
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
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class Allocation
{
    final static FastRandom r = new FastRandom(7L);
    int a; int b;

    @Setup
    public void setup()  {
        a = r.nextInt(1000) + 128;
        b = r.nextInt(1000) + 128;
    }
    
    @Benchmark
    public int newInteger() {
        Integer A = new Integer(a);
        Integer B = new Integer(b);
        
        return A + B;
    }

    @Benchmark
    public int integerValueOf() {
        Integer A = Integer.valueOf(a);
        Integer B = Integer.valueOf(b);
        
        return A + B;
    }

    @Benchmark
    public int ints() {
        int A = a;
        int B = b;
        
        return A + B;
    }

    private int doIt(int a, int b)
    {
        int c = a;
        c++;
        
        int d = b;
        d += c;
        
        int x = c + d * 2;
        return x;
    }
    
    @Benchmark
    public int fCall()
    {
        return doIt(a, b);
    }

    
    
}
