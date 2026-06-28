package io.github.german_article_game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class Config {

    Kryo kryo = new Kryo();

    Boolean includeEnglishTranslation = true;

    Boolean debugMode = false;

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    Difficulty difficulty = Difficulty.EASY;

    public Config() {
        try (Output output = new Output(new FileOutputStream("config.bin"))) {
            kryo.writeObject(output, includeEnglishTranslation);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Input input = new Input(new FileInputStream("config.bin"))) {
            includeEnglishTranslation = kryo.readObject(input, Boolean.class);
            System.out.println("is english on: " + includeEnglishTranslation.toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
