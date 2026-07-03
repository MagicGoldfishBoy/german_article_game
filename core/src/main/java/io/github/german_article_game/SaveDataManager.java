package io.github.german_article_game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.KryoBufferUnderflowException;
import com.esotericsoftware.kryo.io.Output;

public class SaveDataManager {

    public static List<String> nameList;

    public static Output output;

    public static void createSaveGameBin() {
        File file = new File("save_files/save.bin");
        if (!file.exists()) {
            System.out.println("creating save.bin");
            try (Output output = new Output(new FileOutputStream("save_files/save.bin"))) {
                
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            System.out.println("save.bin already exists");
        }
    }
    
    public static void appendSaveGameBin(String name) {
        File file = new File("save_files/save.bin");
        

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        Kryo kryo = new Kryo();

        kryo.register(ArrayList.class); 
        
        ArrayList<String> nameList = new ArrayList<>();


        if (file.exists() && file.length() > 0) {
            try (Input input = new Input(new FileInputStream(file))) {
        
                nameList = kryo.readObject(input, ArrayList.class);
            } catch (Exception e) {
                System.err.println("Error reading save file: " + e.getMessage());
            }
        }

        nameList.add(name);
        System.out.println("Updated List: " + nameList);


        try (Output output = new Output(new FileOutputStream(file))) {
            kryo.writeObject(output, nameList);
            output.flush();
        } catch (Exception e) {
            System.err.println("Error writing save file: " + e.getMessage());
        }
    }
    // public static void appendSaveGameBin(String name) {
    //     File file = new File("save_files/save.bin");
    //     Kryo kryo = new Kryo();
        
    //     if (file.exists() && file.canRead() && file.length() > 0) {

    //         try (Input input = new Input(new FileInputStream(file))) {
    //             String data = kryo.readObjectOrNull(input, String.class);
    //             nameList.add(data);
    //             nameList.add(name);
    //             System.out.println(nameList.toString());
    //             nameList.forEach(entry -> kryo.writeObject(output, entry));
    //             System.out.println(data + nameList.toString());
    //         } catch (Exception e) {
    //             System.out.println(e);
    //         }
    //     } else {
    //         try {
    //             output = new Output(new FileOutputStream(file));
    //             kryo.writeObject(output, name);
    //             System.out.println(output);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             System.out.println(e);
    //         }       
    //     }
    // }
    
}
