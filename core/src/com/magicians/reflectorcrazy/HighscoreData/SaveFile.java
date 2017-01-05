package com.magicians.reflectorcrazy.HighscoreData;

import com.magicians.reflectorcrazy.ScoreData;

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

    public SaveFile(ScoreData sd){
        this.sd = sd;
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
            sd = (ScoreData)in.readObject();
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean save(){
        try{
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("highScores.sav")
            );
            out.writeObject(sd);
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



}
