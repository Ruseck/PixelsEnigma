package com.hetapp.pixelsenigma;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatWidthException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ruseck on 10.09.15.
 */
abstract public class Game implements Serializable {

    private int numPositions;
    private int numVariants;

    private int numAttempts;
    private int time;

    private int bonusTime;
    private int bonusAttempts;

    private int score;
    private int scoreForWhite;
    private int scoreForBlack;
    private int scoreForWin;

    public Game(int numPositions,
                int numVariants,
                int numAttempts,
                int time,
                int bonusTime,
                int bonusAttempts,
                int scoreForWhite,
                int scoreForBlack,
                int scoreForWin) {
        this.numPositions = numPositions;
        this.numVariants = numVariants;
        this.numAttempts = numAttempts;
        this.time = time;
        this.bonusTime = bonusTime;
        this.bonusAttempts = bonusAttempts;
        this.scoreForWhite = scoreForWhite;
        this.scoreForBlack = scoreForBlack;
        this.scoreForWin = scoreForWin;
        history = new ArrayList<>();
        setEnigma();
    }

    private ArrayList<Integer> enigma;
    private ArrayList<ArrayList<Integer>> history;

    public void setEnigma() {
        enigma = new ArrayList<Integer>(numPositions);
        for (int i = 0; i < numPositions; i++) {
            enigma.add((int) (Math.random() * numVariants));
        }
    }

    public int[] checkAttempt(ArrayList<Integer> attempt) throws IllegalFormatWidthException {
        if (this.enigma.size() != attempt.size())
            throw new IllegalFormatWidthException(this.enigma.size() - attempt.size());
        int countB = 0;
        int countW = 0;
        ArrayList<Integer> copyCode = new ArrayList<Integer>();
        ArrayList<Integer> copyAttempt = new ArrayList<Integer>();
        copyCode.addAll(this.enigma);
        copyAttempt.addAll(attempt);
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
        numAttempts--;
        int[]check=checkAttempt(attempt);
        score+=check[0]*scoreForBlack;
        score+=check[1]*scoreForWhite;
        startTimer();
        putAttemptGUI();
        if (check[0] == numPositions){
            winAction();
        }
        else if (isLose()) loseAction();
    }

    public boolean isWin(ArrayList<Integer> attempt) {
        if (checkAttempt(attempt)[0] == numPositions) return true;
        return false;
    }

    public boolean isLose() {
        if (time <= 1 || numAttempts < 1) return true;
        return false;
    }

    abstract void putAttemptGUI();

    public void winAction() {
        time += bonusTime;
        numAttempts += bonusAttempts;
        score += scoreForWin;
        setEnigma();
        stopTimer();
        winActionGUI();
    }

    public abstract void winActionGUI();

    public void loseAction() {
        loseActionGUI();
    }

    public abstract void loseActionGUI();

    private Timer timer;
    private TimerTask task;

    public abstract void timerGUI();

    private boolean isTimerWork = false;

    public void startTimer() {
        if (isTimerWork) return;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if(isLose())stopTimer();
                time--;
                timerGUI();
            }
        };
        timer.schedule(task, 1000, 1000);
        isTimerWork = true;
    }

    public void stopTimer() {
        if (!isTimerWork) return;
        timer.cancel();
        timer.purge();
        isTimerWork=false;
    }

    public int getNumAttempts() {
        return numAttempts;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    public ArrayList<Integer> getEnigma() {
        return enigma;
    }
}
