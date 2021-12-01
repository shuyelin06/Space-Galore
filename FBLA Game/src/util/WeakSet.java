package util;

import java.util.*;

public class WeakSet<E> extends HashSet<E> implements Set<E>, Cloneable, java.io.Serializable {

    private transient HashMap<E,Object> map;

    public WeakSet() {
        super();
    }

    public WeakSet(Collection<? extends E> c) {
        super(c);
    }

    public WeakSet(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public WeakSet(int initialCapacity) {
        super(initialCapacity);
    }

    WeakSet(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }

}
