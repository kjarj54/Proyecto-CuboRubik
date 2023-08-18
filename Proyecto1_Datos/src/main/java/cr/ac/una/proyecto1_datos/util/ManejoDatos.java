/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyecto1_datos.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author kevin
 */
public class ManejoDatos {

    private static final String TXT_PATH = "src/main/java/cr/ac/una/proyecto1_datos/model/datos.txt";
    private static final String TXT_PATH_RECORDS = "src/main/java/cr/ac/una/proyecto1_datos/model/records.txt";
    private Stack<Object[]> dataStack;
    

    public ManejoDatos() {
        dataStack = new Stack<>();
    }

    public void pushData(Object... attributes) {
        dataStack.push(attributes);
    }

    public void loadFromFile() {
        Path filePath = Paths.get(TXT_PATH);

        if (!Files.exists(filePath)) {
            System.out.println("El archivo no existe.");
            return;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] attributesStr = line.split(" ");
                Object[] attributes = new Object[attributesStr.length];
                for (int i = 0; i < attributesStr.length; i++) {
                    attributes[i] = attributesStr[i];
                }
                dataStack.push(attributes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {

        Path filePath = Paths.get(TXT_PATH);

        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            while (!dataStack.isEmpty()) {
                Object[] attributes = dataStack.pop();
                for (Object attribute : attributes) {
                    writer.write(attribute.toString());
                    writer.write(" ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
