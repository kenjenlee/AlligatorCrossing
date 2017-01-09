package com.magicians.reflectorcrazy.HighscoreData;

import java.io.Serializable;

/**
 * Created by Jen on 2017-01-04.
 */

public class ScoreData implements Serializable {

    public int[] highScores;
    public static int MAXINDEX = 10;
    private SaveFile saveFile;
    public int score;

    public ScoreData(){
        highScores = new int[MAXINDEX];

        for(int i=0; i<MAXINDEX; i++){
            highScores[i] = 0;
        }
        saveFile = new SaveFile();
        saveFile.load();


    }

    public int[] getHighScores(){
        return highScores;
    }

    public boolean isHighscore(int score){
        return score>highScores[MAXINDEX-1];
    }

    public void updateHighscore(int score){

        if(isHighscore(score)){
            highScores[MAXINDEX-1] = score;
        }

        //sort the highScore list
        for(int i=MAXINDEX-1; i>=0; i--){
            for(int j=i-1; j>=0; j--){
                if(highScores[i]>highScores[j]){
                    int temp = highScores[i];
                    highScores[i] = highScores[j];
                    highScores[j] = temp;
                }
            }
        }

    }
}
