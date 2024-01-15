package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private LinkedDeque sentinel;
    private LinkedDeque last;
    private int size;

    public LinkedListDeque() {
        sentinel = new LinkedDeque(null, sentinel, sentinel);
        last = sentinel;
        size = 0;
    }
    private class LinkedDeque {
        private T item;
        private LinkedDeque next;
        private LinkedDeque previous;
        public LinkedDeque(T i, LinkedDeque p, LinkedDeque n) {
            item = i;
            next = n;
            previous = p;
        }
    }
    private class LinkedListDequeIterator implements Iterator<T> {
        private int index;
        public LinkedListDequeIterator() {
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

    @Override
    public void addFirst(T item) {
        size += 1;
        sentinel.next = new LinkedDeque(item, sentinel, sentinel.next);
        if (size == 1) {
            last = sentinel.next;
        } else {
            sentinel.next.next.previous = sentinel.next;
        }
        sentinel.previous = last;
    }

    @Override
    public void addLast(T item) {
        size += 1;
        sentinel.previous = new LinkedDeque(item, last, sentinel);
        last.next = sentinel.previous;
        last = sentinel.previous;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        String s = "";
        LinkedDeque p = sentinel.next;
        while (p.item != null) {
            s = s + p.item + " ";
            p = p.next;
        }
        System.out.println(s);
    }

    @Override
    public T removeFirst() {
        if (!isEmpty()) {
            T x = sentinel.next.item;
            size -= 1;
            if (last == sentinel.next) {
                last = sentinel;
                sentinel.next = sentinel;
                sentinel.previous = sentinel;
            } else {
                sentinel.next = sentinel.next.next;
                sentinel.next.previous.previous = null;
                sentinel.next.previous.next = null;
                sentinel.next.previous = sentinel;
            }
            return x;
        }
        return null;
    }

    @Override
    public T removeLast() {
        if (!isEmpty()) {
            T x = last.item;
            size -= 1;
            sentinel.previous = sentinel.previous.previous;
            last.next = null;
            last = sentinel.previous;
            last.next.previous = null;
            last.next = sentinel;
            return x;
        }
        return null;
    }

    @Override
    public T get(int index) {
        if (index < size()) {
            int counter = 0;
            LinkedDeque p = sentinel.next;
            while (counter < index) {
                p = p.next;
                counter += 1;
            }
            return p.item;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Deque) {
            if (((Deque) o).size() != this.size()) {
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

    private T getRecursivehelper(int index, LinkedDeque x) {
        if (x == null) {
            return null;
        } else if (index == 0) {
            return x.item;
        } else {
            return getRecursivehelper(index - 1, x.next);
        }
    }
    public T getRecursive(int index) {
        return getRecursivehelper(index, sentinel.next);
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
}
