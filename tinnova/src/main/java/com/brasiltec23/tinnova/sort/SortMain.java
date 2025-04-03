package com.brasiltec23.tinnova.sort;

import java.util.Arrays;

public class SortMain {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sortWithBubbleSort(new int[]{5, 3, 2, 4, 7, 1, 0, 6})));
    }

    public static int[] sortWithBubbleSort(int[] values) {

        int orderedIndex = values.length;
        boolean changed;
        for (int outside = 0; outside < values.length; outside++) {
            changed = false;
            for (int inside = 0; inside < (orderedIndex - 1); inside++) {
                if (values[inside] > values[inside + 1]) {
                    int replacement = values[inside + 1];
                    values[inside + 1] = values[inside];
                    values[inside] = replacement;
                    changed = true;
                }
            }
            if (!changed) break;
            orderedIndex--;
        }
        return values;
    }
}
