package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> x;
    public MaxArrayDeque(Comparator<T> c) {
        super();
        x = c;
    }

    public T max() {
        if (size() > 0 && x != null) {
            T biggest = get(0);
            for (int i = 0; i < size(); i++) {
                if (x.compare(get(i), biggest) >= 0) {
                    biggest = get(i);
                }
            }
            return biggest;
        }
        return null;
    }

    public T max(Comparator<T> c) {
        if (size() > 0) {
            T biggest = get(0);
            for (int i = 0; i < size(); i++) {
                if (c.compare(get(i), biggest) >= 0) {
                    biggest = get(i);
                }
            }
            return biggest;
        }
        return null;
    }
}

