package com.pbl4.monolingo.utility;

import java.util.Random;

public class ShuffleArray {

    public static void shuffle(Object[] arr) {
        Random rnd = new Random();
        for (int i = arr.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Object temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }
    }
}
