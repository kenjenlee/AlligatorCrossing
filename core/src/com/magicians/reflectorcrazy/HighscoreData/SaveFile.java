package com.magicians.reflectorcrazy.HighscoreData;

import com.magicians.reflectorcrazy.HighscoreData.ScoreData;
import com.magicians.reflectorcrazy.Reflector;

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
    public  static ScoreData sd;

    public SaveFile(Reflector app){
        this.sd = app.sd;
    }

    public static boolean fileExists(){
        File f = new File("C:/Users/Jen/Desktop/highScores.sav");
        return f.exists();
    }

    public static boolean load() {

        try{
            if(!fileExists()){
                init();
            }
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("C:/Users/Jen/Desktop/highScores.sav"));

            sd = (ScoreData)in.readObject();
            /**
            for(int i = 0; i<sd.MAXINDEX; i++){
                sd.highScores[i] = in.readInt();
            }**/

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

    public static  boolean save(){
        try{

            FileOutputStream fout = new FileOutputStream("C:/Users/Jen/Desktop/highScores.sav");
            ObjectOutputStream out = new ObjectOutputStream(
                    fout);

            out.writeObject(sd);
            /**
            for(int i =0; i<sd.MAXINDEX; i++){
                out.write(sd.highScores[i]);
                out.writeChar(' ');
            }**/

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void init(){
        sd = new ScoreData();
        sd.init();
        save();
    }



}
