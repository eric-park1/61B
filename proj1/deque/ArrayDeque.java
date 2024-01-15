package deque;
import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int CUTOFF = 16;
    private static final int RESIZER = 4;
    private static final int INITIAL_SIZE = 8;
    private T[] arrayDeque;

    public ArrayDeque() {
        arrayDeque = (T[]) new Object[INITIAL_SIZE];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int index;
        public ArrayDequeIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return get(index) != null;
        }

        public T next() {
            T item = get(index);
            index += 1;
            return item;
        }
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int x = nextFirst + 1;
        if (x >= arrayDeque.length) {
            x = 0;
        }
        nextFirst = capacity - (arrayDeque.length - nextFirst);
        if (nextFirst >= nextLast) {
            System.arraycopy(arrayDeque, x, a, nextFirst + 1, capacity - nextFirst - 1);
            System.arraycopy(arrayDeque, 0, a, 0, nextLast);
        } else {
            nextLast = nextFirst + size() + 1;
            System.arraycopy(arrayDeque, x, a, 1, nextLast - nextFirst - 1);
            nextFirst = 0;
            nextLast = size() + 1;
        }
        arrayDeque = a;
    }

    @Override
    public void addFirst(T item) {
        if (nextFirst == -1) {
            nextFirst = arrayDeque.length - 1;
        }
        arrayDeque[nextFirst] = item;
        size += 1;
        nextFirst -= 1;
        if (size() == arrayDeque.length) {
            resize(arrayDeque.length * 2);
        }
    }

    @Override
    public void addLast(T item) {
        if (nextLast == arrayDeque.length) {
            nextLast = 0;
        }
        arrayDeque[nextLast] = item;
        size += 1;
        nextLast += 1;
        if (size() == arrayDeque.length) {
            resize(arrayDeque.length * 2);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        String s = "";
        for (int i = 0; i < size(); i++) {
            s = s + arrayDeque[i] + " ";
        }
        System.out.println(s);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (arrayDeque.length > CUTOFF && ((size() - 1) * RESIZER) < arrayDeque.length) {
            resize(arrayDeque.length / 2);
        }
        if (nextFirst == arrayDeque.length - 1) {
            T item = arrayDeque[0];
            arrayDeque[0] = null;
            nextFirst = 0;
            size -= 1;
            return item;
        } else {
            T item = arrayDeque[nextFirst + 1];
            arrayDeque[nextFirst + 1] = null;
            nextFirst += 1;
            size -= 1;
            return item;
        }
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (arrayDeque.length > CUTOFF && ((size() - 1) * RESIZER) < arrayDeque.length) {
            resize(arrayDeque.length / 2);
        }
        if (nextLast == 0) {
            T item = arrayDeque[arrayDeque.length - 1];
            arrayDeque[arrayDeque.length - 1] = null;
            nextLast = arrayDeque.length - 1;
            size -= 1;
            return item;
        } else {
            T item = arrayDeque[nextLast - 1];
            arrayDeque[nextLast - 1] = null;
            nextLast -= 1;
            size -= 1;
            return item;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size()) {
            return null;
        }
        if (nextFirst + index + 1 >= arrayDeque.length) {
            T item = arrayDeque[nextFirst + index - arrayDeque.length + 1];
            return item;
        } else {
            T item = arrayDeque[nextFirst + index + 1];
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Deque) {
            if (((Deque) o).size() != size()) {
                return false;
            }
            for (int i = 0; i < size(); i++) {
                if (((Deque) o).get(i).equals(get(i))) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
}
