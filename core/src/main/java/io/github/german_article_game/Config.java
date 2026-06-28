package io.github.german_article_game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Config {

    Kryo kryo = new Kryo();

    static Boolean includeEnglishTranslation = true;

    Boolean debugMode = false;

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    Difficulty difficulty = Difficulty.EASY;

    public Config() {
        // try (Output output = new Output(new FileOutputStream("config.bin"))) {
        //     kryo.writeObject(output, includeEnglishTranslation);
        // } catch (FileNotFoundException e) {
        //     throw new RuntimeException(e);
        // }

        // try (Input input = new Input(new FileInputStream("config.bin"))) {
        //     includeEnglishTranslation = kryo.readObject(input, Boolean.class);
        //     System.out.println("is english on: " + includeEnglishTranslation.toString());
        // } catch (FileNotFoundException e) {
        //     throw new RuntimeException(e);
        // }
    }

    public void useDefaultValues() {

        includeEnglishTranslation = true;
        debugMode = false;

    }

    public void createNewConfigFile() {
        try (Output output = new Output(new FileOutputStream("config.bin"))) {
            System.out.println("Creating new Config file with default values");
            kryo.writeObject(output, includeEnglishTranslation);
            kryo.writeObject(output, debugMode);
            System.out.println("is debug on: " + debugMode.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }   
    }

    public void loadAllSettings() {

        try (Input input = new Input(new FileInputStream("config.bin"))) {
            includeEnglishTranslation = kryo.readObject(input, Boolean.class);
            System.out.println("is english on: " + includeEnglishTranslation.toString());

            debugMode = kryo.readObject(input, Boolean.class);
            System.out.println("is debug on: " + debugMode.toString());

        } catch (FileNotFoundException e) {
            System.out.println("Config file not found. Default values will be used.");
            useDefaultValues();
            System.out.println("Creating new Config file with default values");
            createNewConfigFile();
        } catch (KryoException e) {
            System.out.println("File Read Failed: " + e);
            useDefaultValues();
            createNewConfigFile();
        }

    }

}
