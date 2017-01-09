package com.magicians.reflectorcrazy.HighscoreData;

import com.magicians.reflectorcrazy.HighscoreData.ScoreData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Jen on 2017-01-05.
 */

public class SaveFile {
    public static ScoreData sd;

    public SaveFile(){

    }

    public static boolean fileExists(){
        File f = new File("highScores.sav");
        return f.exists();
    }

    public static boolean load() {

        try{
            if(!fileExists()){
                return false;
            }
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("highScores.sav"));
            for(int i = 0; i<sd.MAXINDEX; i++){
                sd.highScores[i] = in.readInt();
            }

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean save(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("highScores.sav")
            );
            for(int i =0; i<sd.MAXINDEX; i++){
                out.write(sd.highScores[i]);
                out.writeChar(' ');
            }

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



}
