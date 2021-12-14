package main;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Utility {

    public static boolean rectangleOverlap(double[] rec1, double[] rec2) {
        double x1 = rec1[0], y1 = rec1[1], x2 = rec1[2], y2 = rec1[3];
        double x3 = rec2[0], y3 = rec2[1], x4 = rec2[2], y4 = rec2[3];

        return (x1 < x4) && (x3 < x2) && (y1 < y4) && (y3 < y2);
    }

    // Comparator with Generics
    public static <K, V extends Comparable<V>> Map<K, V> sort(final Map<K, V> map) {
        Comparator<K> c = (K k1, K k2) -> {
            int comp1 = map.get(k1).compareTo(map.get(k2));
            if (comp1 == 0)
                return 1;
            else
                return comp1;
        };

        SortedMap<K, V> sorted = new TreeMap<>(c);
        sorted.putAll(map);
        return sorted;
    }

}
