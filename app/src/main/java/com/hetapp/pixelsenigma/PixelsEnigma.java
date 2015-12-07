package com.hetapp.pixelsenigma;

import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.IllegalFormatWidthException;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

/**
 * Created by ruseck on 03.12.15.
 */
public class PixelsEnigma implements Serializable {

    private int time = 80;
    private int attemptsSize = 8;
    private int score = 0;
    private int numPositions = 4;
    private int numVariants = 6;
    private ArrayList<Integer> enigma;
    private ArrayList<ArrayList<Integer>> history;
    ArrayList<Integer> convert;

    private void makeConvert() {
        convert = new ArrayList<>();
        convert.add(R.drawable.red_pixel);
        convert.add(R.drawable.orange_pixel);
        convert.add(R.drawable.yellow_pixel);
        convert.add(R.drawable.green_pixel);
        convert.add(R.drawable.blue_pixel);
        convert.add(R.drawable.purple_pixel);

    }

    public int getTime() {
        return time;
    }

    public int getAttemptsSize() {
        return attemptsSize;
    }

    public PixelsEnigma(int numPositions, int attemptsSize, int time, int numVariants) {
        this.numPositions = numPositions;
        this.attemptsSize = attemptsSize;
        this.time = time;
        this.numVariants = numVariants;
        this.history = new ArrayList<ArrayList<Integer>>();
        makeConvert();
        setEnigma();
    }

    public void setEnigma() {
        enigma = new ArrayList<>(numPositions);
        for (int i = 0; i < numPositions; i++) {
            enigma.add(convert.get((int) (Math.random() * numVariants)));
        }
    }

    public int[] check(ArrayList<Integer> enigma) throws IllegalFormatWidthException {
        if (this.enigma.size() != enigma.size())
            throw new IllegalFormatWidthException(this.enigma.size() - enigma.size());
        int countB = 0;
        int countW = 0;
        ArrayList<Integer> copyCode = new ArrayList<Integer>();
        ArrayList<Integer> copyAttempt = new ArrayList<Integer>();
        copyCode.addAll(this.enigma);
        copyAttempt.addAll(enigma);
        for (int i = 0; i < copyCode.size(); ) {
            if (copyCode.get(i).equals(copyAttempt.get(i))) {
                copyCode.remove(i);
                copyAttempt.remove(i);
                countB++;
            } else {
                i++;
            }
        }
        for (int i = 0; i < copyCode.size(); i++) {
            for (int k = 0; i < copyCode.size() && k < copyCode.size(); ) {
                if (copyCode.get(i).equals(copyAttempt.get(k))) {
                    copyCode.remove(i);
                    copyAttempt.remove(k);
                    countW++;
                    k = 0;
                } else {
                    k++;
                }
            }
        }
        int ar[] = {countB, countW};
        return ar;
    }

    public void putAttempt(ArrayList<Integer> attempt) {
        history.add(attempt);
        attemptsSize--;
        int[] check = check(attempt);
        score += check[1] * 2;
        score += check[0] * 5;
    }

    public void upParam() {
        time += 40;
        attemptsSize += 4;
    }

    public void setTime() {
        time--;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Integer> getEnigma() {
        return enigma;
    }
}
