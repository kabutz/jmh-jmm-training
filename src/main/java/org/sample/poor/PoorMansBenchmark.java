package org.sample.poor;

import java.util.Arrays;

import org.sample.Timer;

public class PoorMansBenchmark
{   
	private static String append(int i)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		sb.append("Foo");
		sb.append(i);
		sb.append('c');
		sb.append("lkJAHJ AHSDJHF KJASHF HSAKJFD");

		final String s = sb.toString();
		final char[] ch = s.toCharArray();
		Arrays.sort(ch);

		return new String(ch);
	}

	public static void main (String[] args)
	{
		int SIZE = Integer.valueOf(args[0]);
		int WARM = 10_000;
		final long[] result = new long[Math.max(WARM, SIZE)];

		// compile our utilities first
		for (int i = 0; i < WARM; i++)
		{
			final org.sample.Timer t1 = Timer.startTimer().resetAndStart();
			result[i] = t1.stop().runtimeNanos();
		}

		int sum = 0;
		for (int i = 0; i < SIZE; i++)
		{
			Timer t = Timer.startTimer();       

			sum += append(i).length();

			result[i] = t.stop().runtimeNanos();
		}  

		int a = String.valueOf(SIZE).length() + 1;
		int m = String.valueOf(Arrays.stream(result).max().getAsLong()).length() + 2;

		if (args.length == 1)
		{
			System.out.printf("# Size: %,d%n", SIZE);
			for (int i = 0; i < SIZE; i++)
			{
				int c = i + 1;
				String s = String.format("%," + a + "d, %,"+m+"d\n", i + 1, result[i]);

				if (c <= 10)
				{
					System.out.print(s);
				}
				else if (c <= 100 && c % 10 == 0)
				{
					System.out.print(s);
				}
				else if (c <= 1000 && c % 100 == 0)
				{
					System.out.print(s);
				}
				else if (c <= 10000 && c % 1000 == 0)
				{
					System.out.print(s);
				}
				else if (c <= 100000 && c % 10000 == 0)
				{
					System.out.print(s);
				}               
				else if (c <= 1000000 && c % 100000 == 0)
				{
					System.out.print(s);
				}  
			} 
		}
		else
		{
	        for (int i = 0; i < SIZE; i++)
	        {
	            int c = i + 1;
	            String s = String.format("%d\t%d\n", i + 1, result[i]);
	    
	            if (c <= 10000)
	            {
	                System.out.print(s);
	            }
	            else if (c <= 100_000 && c % 100 == 0)
	            {
	                System.out.print(s);
	            }
	        } 
		}

		// pretend you need it but you don't need it
		// just to avoid optimizations
		if (sum < 0)
		{
			System.out.println(sum);
		}
	}
}
