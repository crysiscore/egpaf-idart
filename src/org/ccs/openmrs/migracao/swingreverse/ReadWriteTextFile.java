/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author asamuel
 */
public class ReadWriteTextFile {

    final static Charset ENCODING = StandardCharsets.UTF_8;

    //For smaller files
    /**
     * Note: the javadoc of Files.readAllLines says it's intended for small
     * files. But its implementation uses buffering, so it's likely good even
     * for fairly large files.
     */
    public List<String> readSmallTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        return Files.readAllLines(path, ENCODING);
    }

    public void writeSmallTextFile(List<String> aLines, String aFileName) {
        try {
            Path path = Paths.get(aFileName);
            Files.write(path, aLines, ENCODING, StandardOpenOption.APPEND);
        } catch (IOException io) {
            System.out.println("Error writing to file: " + io.getCause().toString());
        }

    }

    //For larger files
    // Assumimos que nao teremos muitos "exception" durante a uniao de nids entao nao precisamos deste metodo
    public void readLargerTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        try {
            Scanner scanner = new Scanner(path, ENCODING.name());
            while (scanner.hasNextLine()) {
                //process each line in some way
                log(scanner.nextLine());
            }
        } catch (Exception e) {
        }
    }

    //For larger files
    // Assumimos que nao teremos muitos "exception" durante a uniao de nids entao nao precisamos deste metodo
    public void readLargerTextFileAlternate(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);
        try {
            BufferedReader reader = Files.newBufferedReader(path, ENCODING);
            String line = null;
            while ((line = reader.readLine()) != null) {
                //process each line in some way
                log(line);
            }
        } catch (Exception e) {
        }
    }

    //For larger files
    // Assumimos que nao teremos muitos "exception" durante a uniao de nids entao nao precisamos deste metodo
    public void writeLargerTextFile(String aFileName, List<String> aLines) throws IOException {
        Path path = Paths.get(aFileName);
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, ENCODING);
            for (String line : aLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (Exception e) {
        };
    }

    private static void log(Object aMsg) {
        System.out.println(String.valueOf(aMsg));
    }

} 

/*
 public static void main(String... aArgs) throws IOException{
    ReadWriteTextFileJDK7 text = new ReadWriteTextFileJDK7();
    
    //treat as a small file
    List<String> lines = text.readSmallTextFile(FILE_NAME);
    log(lines);
    lines.add("This is a line added in code.");
    text.writeSmallTextFile(lines, FILE_NAME);
    
    //treat as a large file - use some buffering
    text.readLargerTextFile(FILE_NAME);
    lines = Arrays.asList("Down to the Waterline", "Water of Love");
    text.writeLargerTextFile(OUTPUT_FILE_NAME, lines);   
  }
*/
