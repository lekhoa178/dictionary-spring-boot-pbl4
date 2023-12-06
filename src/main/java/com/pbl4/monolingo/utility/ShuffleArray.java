package com.pbl4.monolingo.utility;

import com.pbl4.monolingo.entity.Notebook;

import java.util.List;
import java.util.Random;

public class ShuffleArray {

    public static void shuffle(List<Notebook> arr) {
        Random rnd = new Random();
        for (int i = arr.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Notebook temp = arr.get(index);
            arr.set(index, arr.get(i));
            arr.set(i, temp);
        }

    }
}
