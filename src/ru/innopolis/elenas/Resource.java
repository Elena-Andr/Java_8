package ru.innopolis.elenas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class for parsing file resources
 */
public class Resource {

    private String filePath;

    public Resource(String path){
        this.filePath = path;
    }

    /**
     * Method which performs integer parsing
     * @return List of retrieved numbers
     */
    public List<Integer> getNumbers(){
        List<Integer> numbers = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String line = bufferedReader.readLine();

            while (line != null && !Main.isCancelled) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }

            String[] numbersArrayAsString = stringBuilder.toString().split(" ");
            numbers = populateNumberList(numbersArrayAsString);

        } catch (IOException ex) {
            Main.isCancelled = true;
            System.out.println(ex.getMessage());
        }
        return numbers;
    }

    private List<Integer> populateNumberList(String[] numbersArrayAsString) {
        List<Integer> numbers = new ArrayList<>();
        try {
            for (int i = 0; i < numbersArrayAsString.length; i++) {
                numbers.add(Integer.parseInt(numbersArrayAsString[i]));

                if(Main.isCancelled){
                    return Collections.emptyList();
                }
            }
        }catch (NumberFormatException ex){
            Main.isCancelled = true;
            System.out.println("Input data is incorrect: " + ex.getMessage());
        }
        return numbers;
    }
}
