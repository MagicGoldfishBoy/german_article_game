package io.github.german_article_game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.function.Function;

import com.badlogic.gdx.Input.Keys;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import io.github.german_article_game.Bullet.Bullet;
import io.github.german_article_game.Bullet.PlayerBullet;
import io.github.german_article_game.Bullet.PlayerSpreadingBullet;

public class Config {

    Kryo kryo = new Kryo();

    static Boolean includeEnglishTranslation = true;

    static Boolean isDebugMode = false;

    enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }

    static Difficulty difficulty = Difficulty.EASY;

    static float volume = 0.5f;

    static float buttonFontScale = 0.35f;

    static int upKey = Keys.W;
    static int leftKey = Keys.A;
    static int downKey = Keys.S;
    static int rightKey = Keys.D;
    static int fireKey = Keys.SPACE;
    static int pauseKey = Keys.P;


    public static Function<Main, Bullet> currentPlayerBullet = PlayerSpreadingBullet::new;

    public Config() {
        kryo.register(Enum.class);
        kryo.register(Difficulty.class);
        kryo.register(Float.class);
    }

    public void useDefaultValues() {

        includeEnglishTranslation = true;
        isDebugMode = false;
        difficulty = Difficulty.EASY;
        volume = 0.5f;

    }

    public void createNewConfigFile() {
        try (Output output = new Output(new FileOutputStream("config.bin"))) {

            System.out.println("Creating new Config file with default values");

            kryo.writeObject(output, includeEnglishTranslation);
            System.out.println("is english on: " + includeEnglishTranslation.toString());

            kryo.writeObject(output, isDebugMode);
            System.out.println("is debug on: " + isDebugMode.toString());

            kryo.writeObject(output, difficulty);
            System.out.println("difficulty: " + difficulty);

            kryo.writeObject(output, volume);
            System.out.println("volume: " + volume);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }   
    }

    public void loadAllSettings() {

        try (Input input = new Input(new FileInputStream("config.bin"))) {

            includeEnglishTranslation = kryo.readObject(input, Boolean.class);
            System.out.println("is english on: " + includeEnglishTranslation.toString());

            isDebugMode = kryo.readObject(input, Boolean.class);
            System.out.println("is debug on: " + isDebugMode.toString());

            difficulty = kryo.readObject(input, Difficulty.class);
            System.out.println("difficulty: " + difficulty);

            volume = kryo.readObject(input, Float.class);
            System.out.println("volume: " + volume);

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
