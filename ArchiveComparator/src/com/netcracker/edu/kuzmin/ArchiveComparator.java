package com.netcracker.edu.kuzmin;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compares two archives with each other.
 * Finds and displays comparison results.
 *
 * @author Georgii Kuzmin
 */
public class ArchiveComparator {
    private HashMap<String, Long> firstArchiveFiles = new HashMap<>();
    private HashMap<String, Long> secondArchiveFiles = new HashMap<>();
    private List<String> result = new ArrayList<>();
    private String firstArchiveName;
    private String secondArchiveName;

    public ArchiveComparator (ArchiveHolder first, ArchiveHolder second) {
        this.firstArchiveFiles = first.getFiles();
        this.secondArchiveFiles = second.getFiles();
        this.firstArchiveName = first.getFileName();
        this.secondArchiveName = second.getFileName();
    }

    /**
     * Compares the contents of two archives.
     */
    public void compare () {
        for (Map.Entry<String, Long> entry : firstArchiveFiles.entrySet()){
            if ((!secondArchiveFiles.containsKey(entry.getKey())) && (!secondArchiveFiles.containsValue(entry.getValue()))) //file was deleted
                result.add("- " + entry.getKey());
        }
        for (Map.Entry<String, Long> entry : secondArchiveFiles.entrySet()){
            if ((!firstArchiveFiles.containsKey(entry.getKey())) && (!firstArchiveFiles.containsValue(entry.getValue())))  //file was added
                result.add("+ " + entry.getKey());
        }
        for (Map.Entry<String, Long> entryFirst : firstArchiveFiles.entrySet()){
            for (Map.Entry<String, Long> entrySecond : secondArchiveFiles.entrySet()){
                if (entryFirst.getKey().equals(entrySecond.getKey()) && !entryFirst.getValue().equals(entrySecond.getValue()))  //file was updated
                    result.add("* " + entryFirst.getKey());
            }
        }
        for (Map.Entry<String, Long> entryFirst : firstArchiveFiles.entrySet()){
            for (Map.Entry<String, Long> entrySecond : secondArchiveFiles.entrySet()){
                if (!entryFirst.getKey().equals(entrySecond.getKey()) && entryFirst.getValue().equals(entrySecond.getValue()))  //file was presumably renamed
                    result.add("? " + entryFirst.getKey() + " - ? " + entrySecond.getKey());
            }
        }
    }

    /**
     * Creates a text document with the comparison result.
     */
    public void printResult ()
    {
        try(FileWriter fileWriter = new FileWriter("results.txt", false)) {
            fileWriter.write("Comparison of " + firstArchiveName + " and " + secondArchiveName + "\n");
            for (String res : result)
                fileWriter.write(res + "\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
