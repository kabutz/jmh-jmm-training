package org.sample;

public class AllocationTrap
{
    private final static int OUTER = 4000;
    private final static int TRAP = 2000;
    private static Object o = null;
    private final static long[] timers = new long[OUTER];
    
    public static void main(String[] args)
    {
        Object trap = null;
        Object o = null;
        
        for (int i = 0; i < OUTER; i++)
        {
            final Timer t = Timer.startTimer();
            
            for (int j = 0; j < 1000; j++)
            {
                // burn time and train that null is normal
                o = new Object();
                
                if (trap != null)
                {
                    System.out.println("Got you.");
                    trap = null;
                }
            }
            
            // Give me a Non-Null, Vasily. 
            // One Non-Null only, please.  
            if (i == TRAP)
            {
                trap = new Object();
            }

            timers[i] = t.stop().runtimeNanos();
        }        

        for (int i = 0; i < OUTER; i = i + 1 )
        {
            System.out.printf("%4d\t%4d\n", i, timers[i]);
        }
        
        System.out.println(o);
        
    }

}
