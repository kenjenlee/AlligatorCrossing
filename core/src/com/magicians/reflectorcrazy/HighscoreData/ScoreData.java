package com.magicians.reflectorcrazy.HighscoreData;

import com.magicians.reflectorcrazy.Reflector;

import java.io.Serializable;

/**
 * Created by Jen on 2017-01-04.
 */

public class ScoreData implements Serializable {

    private  int[] highScores;
    public static int MAXINDEX = 10;
    private SaveFile saveFile;
    public int tentativeScore;

    public ScoreData(){
        highScores = new int[MAXINDEX];
    }

    public void init(){
        for(int i=0; i<MAXINDEX; i++){
            highScores[i] = 0;
        }
    }

    public int[] getHighScores(){
        return highScores;
    }

    public int getTentativeScore(){
        return tentativeScore;
    }

    public void setTentativeScore(int i){
        tentativeScore = i;
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
