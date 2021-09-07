package org.jol;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SimpleArrayList<T> implements List<T>
{
    Object[] data;
    int size;

    SimpleArrayList(final SimpleArrayList<T> list)
    {
        data = list.data;
        size = list.size;
    }

    public SimpleArrayList(final List<T> list)
    {
        this();
        addAll(list);
    }

    public SimpleArrayList()
    {
        data = new Object[10];
    }

    public SimpleArrayList(int capacity)
    {
        data = new Object[capacity];
    }

    private void grow()
    {
        final int length = data.length;
        final Object[] newData = new Object[length << 1];
        System.arraycopy(data, 0, newData, 0, length);
        data = newData;
    }
    
    public boolean add(T o)
    {
        final int length = data.length;
        if (size == length)
        {
            grow();
        }

        data[size] = o;
        size++;

        return true;
    }

    public boolean addLast(T o)
    {
        return add(o);
    }

    public boolean addFirst(T o)
    {
        add(0, o);
        return true;
    }

    public boolean push(T o)
    {
        return addFirst(o);
    }

    public T pop()
    {
        return remove(0);
    }

    @SuppressWarnings("unchecked")
    public T get(int index)
    {
        return (T) data[index];
    }

    public T getFirst()
    {
        return get(0);
    }

    public T peek()
    {
        return getFirst();
    }

    @SuppressWarnings("unchecked")
    public T getLast()
    {
        return (T) data[size - 1];
    }

    public int size()
    {
        return size;
    }

    @SuppressWarnings("unchecked")
    public T[] toArray() 
    {
        return (T[]) Arrays.copyOf(data, size);
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        throw new IllegalArgumentException();
    }

    @Override
    public java.util.Iterator<T> iterator()
    {
        return new Iterator<T>(this);
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return (T[]) data;
    }

    @Override
    public boolean remove(Object o)
    {
        throw new IllegalArgumentException();
    }

    public T removeLast()
    {
        final int pos = size - 1;
        final T old = (T) data[pos];

        // set position empty to permit GC to kick in
        data[pos] = null;

        // make it smaller
        size--;

        return old;
    }

    public T removeFirst()
    {
        return remove(0);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        c.forEach(i -> add(i));

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public void clear()
    {
        Arrays.fill(data, null);
        size = 0;
    }

    @Override
    public T set(int index, T element)
    {
        final T old = (T) data[index];
        data[index] = element;
        
        return old;
    }

    @Override
    public void add(int index, T element)
    {
        // adding at the end
        if (index == size)
        {
            add(element);
            return;
        }
        
        // still room?
        if (size == data.length)
        {
            grow();
        }

        // don't waste a temp array for copying.... go oldschool with a loop
        for (int i = data.length - 1; i > index; i--)
        {
            data[i] = data[i - 1];
        }

        data[index] = element;
        size++;
    }

    @Override
    public T remove(int index)
    {
        final T tmp = (T) data[index];

        // if we remove the last, we keep it simple
        if (index == size - 1)
        {
            data[index] = null; // make gc happy
            size--;

            return tmp;
        }

        // ok, for everything else, we have to compact the array and because we don't want to 
        // waste memory, we have to loop in a very classic manner
        for (int i = index; i < size - 1; i++)
        {
            data[i] = data[i + 1];
        }

        // one less now
        size--;

        // return old
        return tmp;
    }

    @Override
    public int indexOf(Object o)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public int lastIndexOf(Object o)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public ListIterator<T> listIterator(int index)
    {
        throw new IllegalArgumentException();

    }

    @Override
    public List<T> subList(int fromIndex, int toIndex)
    {
        throw new IllegalArgumentException();

    }

    class ListIterator<T> implements java.util.ListIterator<T>
    {
        private int cursor = -1;
        private int last = -1;
        private final SimpleArrayList<T> list;
        private int direction = 0;

        public ListIterator(final SimpleArrayList<T> list)
        {
            this.list = list;
        }

        @Override
        public boolean hasNext()
        {
            return cursor < list.size - 1;
        }

        @Override
        public T next()
        {
            cursor++;
            last = cursor;
            direction = +1;
            return list.get(last);
        }

        @Override
        public boolean hasPrevious()
        {
            return cursor >= 0;
        }

        @Override
        public T previous()
        {
            last = cursor;
            cursor--;
            direction = -1;
            return list.get(last);
        }

        @Override
        public int nextIndex()
        {
            return cursor + 1;
        }

        @Override
        public int previousIndex()
        {
            return cursor;
        }

        @Override
        public void remove()
        {
            list.remove(last);    
            if (direction == +1)
            {
                cursor--;
            }
        }

        @Override
        public void set(T e)
        {
            list.set(last, e);
        }

        @Override
        public void add(T e)
        {
            if (list.size == 0)
            {
                list.add(e);
            }
            else
            {
                list.add(cursor + 1, e);
            }
            cursor++;
        }

    }

    static class Iterator<T> implements java.util.Iterator<T>
    {
        private final SimpleArrayList<T> list;
        private int pos = -1;

        public Iterator(final SimpleArrayList<T> list)
        {
            this.list = list;
        }

        @Override
        public boolean hasNext()
        {
            return pos < list.size - 1;
        }

        @Override
        public T next()
        {
            pos++;
            T next = list.get(pos);
            return next;
        }
    }

    @Override
    public java.util.ListIterator<T> listIterator()
    {
        return new ListIterator<T>(this);
    }
}
