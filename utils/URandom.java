package utils;

import java.util.Random;

public class URandom {
    private int min = 0;
    private int max = 9;
    private int result;

    public URandom() {
        setRandom();
    }

    public URandom(int max) {
        this.max = max;
        setRandom();
    }

    public URandom(int max, int min) {
        this.min = min;
        this.max = max;

        if (max > min) {
            setRandom();
        }
    }

    private void setRandom() {
        Random rand = new Random();
        result = rand.nextInt(max - min) + min;
    }

    public int get() {
        return result;
    }
}