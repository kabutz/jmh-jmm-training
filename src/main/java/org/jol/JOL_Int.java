package org.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;
import org.openjdk.jol.vm.VM;

public class JOL_Int
{
    static Object o = new Diff(Operation.DELETE, XltCharBuffer.EMPTY);

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

    public class Operation {
        public static final byte DELETE = 1;
        public static final byte INSERT = 2;
        public static final byte EQUAL = 3;
      }

    /**
     * Class representing one diff operation.
     */
    public static class Diff {
        /**
         * One of: INSERT, DELETE or EQUAL.
         */
        public int operation;

        /**
         * The text associated with this diff operation.
         */
        public XltCharBuffer text;

        /**
         * Constructor.  Initializes the diff with the provided values.
         * @param operation One of INSERT, DELETE or EQUAL.
         * @param text The text being applied.
         */
        public Diff(int operation, final String text) {
            this(operation, XltCharBuffer.valueOf(text));
        }

        public Diff(int operation, final XltCharBuffer text) {
            // Construct a diff with the specified operation and text.
            this.operation = operation;
            this.text = text;
        }

        /**
         * Display a human-readable version of this Diff.
         * @return text version.
         */
        public String toString() {
            String prettyText = this.text.toString().replace('\n', '\u00b6');
            return "Diff(" + this.operation + ",\"" + prettyText + "\")";
        }

        /**
         * Create a numeric hash value for a Diff.
         * This function is not used by DMP.
         * @return Hash value.
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = operation;
            result += prime * ((text == null) ? 0 : text.hashCode());
            return result;
        }

        /**
         * Is this Diff equivalent to another Diff?
         * @param obj Another Diff to compare against.
         * @return true or false.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Diff other = (Diff) obj;
            if (operation != other.operation) {
                return false;
            }
            if (text == null) {
                if (other.text != null) {
                    return false;
                }
            } else if (!text.equals(other.text)) {
                return false;
            }
            return true;
        }
    }

}