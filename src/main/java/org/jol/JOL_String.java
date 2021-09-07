package org.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

public class JOL_String
{
    static Object o = new A();
    
    public static void main(String[] args) throws Exception 
    {
        System.out.println(VM.current().details());
        
        System.out.println("==== Class ===============================");
        System.out.println(ClassLayout.parseClass(o.getClass()).toPrintable());

        
        System.out.println("=== Instance Layout ====================================");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        System.out.println("==== Instance Graphed Layout=========================");
        System.out.println(GraphLayout.parseInstance(o).toFootprint());
        
    }
}
class A 
{
    String s;
    boolean b1;
    int a2;
}
class B extends A
{
    String a;
    char c;
    int i;
}