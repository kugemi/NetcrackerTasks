package com.netcracker.edu.kuzmin;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        String firstPath = null;
        String secondPath = null;
        if (args.length == 0) {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION)
                firstPath = fileChooser.getSelectedFile().getAbsolutePath();
            ret = -1;
            ret = fileChooser.showDialog(null, "Open file");
            if(ret == JFileChooser.APPROVE_OPTION)
                secondPath = fileChooser.getSelectedFile().getAbsolutePath();
        } else if (args.length == 2) {
            firstPath = args[0];
            secondPath = args[1];
        } else {
            throw new IllegalArgumentException("The wrong number of arguments were sent");
        }

        if (firstPath != null && secondPath != null) {
            ArchiveHolder firstArchive = new ArchiveHolder(firstPath);
            firstArchive.readArchive();
            ArchiveHolder secondArchive = new ArchiveHolder(secondPath);
            secondArchive.readArchive();
            ArchiveComparator comparator = new ArchiveComparator(firstArchive, secondArchive);
            comparator.compare();
            comparator.printResult();
        }
        else {
            throw new IllegalArgumentException("Incorrect definition of the file path");
        }
    }
}
