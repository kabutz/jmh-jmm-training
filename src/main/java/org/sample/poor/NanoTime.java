package org.sample.poor;

import java.util.Arrays;

import org.sample.Timer;

public class NanoTime
{   
	public static void main (String[] args)
	{
		int SIZE = Integer.valueOf(args[0]);
		final short[] result = new short[SIZE];

		long last = System.nanoTime();
		for (int i = 0; i < SIZE; i++)
		{
			long l = System.nanoTime();
			result[i] = (short) (l - last);
			last = l;
		}

		int a = String.valueOf(SIZE).length() + 1;
		int m = 6;

		if (args.length == 1)
		{
			System.out.printf("# Size: %,d%n", SIZE);
			for (int i = 0; i < SIZE; i++)
			{
				int c = i + 1;
				String s = String.format("%," + a + "d, %,"+m+"d\n", i + 1, result[i]);

				if (c <= 100)
				{
					System.out.print(s);
				}
//				else if (c <= 100 && c % 10 == 0)
//				{
//					System.out.print(s);
//				}
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

	}
}
