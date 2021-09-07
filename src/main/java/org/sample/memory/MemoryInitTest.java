package org.sample.memory;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import sun.misc.Unsafe;

@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class MemoryInitTest
{
    static Unsafe U;
    int[] a;
    int[] b;

    static
    {
        try
        {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(null);
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    @Param({ "1", "100", "1000", "10000", "1000000"})
    int size;
    long bytes;
    long address;

    @Setup(Level.Invocation)
    public void setup()
    {
        bytes = 4 * size + 4 + 12;
        address = 0;
    }

    @TearDown(Level.Invocation)
    public void tearDown()
    {
        if (address != 0)
        {
            U.freeMemory(address);
            address = 0;
        }
    }
    
    @Benchmark
    public long unsafe()
    {
        address = U.allocateMemory(bytes);
        return address;
    }

    @Benchmark
    public long unsafeInitialized()
    {
        address = U.allocateMemory(bytes);
        U.setMemory(address, bytes, (byte) 0);
        
        return address;
    }    
    
    @Benchmark
    public long safe()
    {
        a = new int[size];
        address = 0;
        
        return address;
    }
    
}