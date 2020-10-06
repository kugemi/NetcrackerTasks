package com.netcracker.edu.kuzmin;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Holds a file that is an archive.
 * Stores information about name and checksum of the uncompressed entry data.
 *
 * @author Georgii Kuzmin
 */
public class ArchiveHolder {
    private HashMap<String, Long> files = new HashMap<>();
    private String path = null;

    public ArchiveHolder (String path) { this.path = path; }

    /**
     * Opens archive and put filenames and checksums in {@link #files}
     */
    public void readArchive () {
        if (path.endsWith(".zip") || path.endsWith(".jar"))
        {
            try (ZipInputStream zip = new ZipInputStream(new FileInputStream(path))) {
                ZipEntry entry;
                while ((entry = zip.getNextEntry())!= null) {
                    files.put(entry.getName(), entry.getCrc());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Invalid file extension");
        }
    }

    /**
     * Prints filenames and checksums.
     */
    public void printFiles () {
        for (Map.Entry<String, Long> entry : files.entrySet())
            System.out.println("File name: " + entry.getKey() + "   Chechsum: " + entry.getValue());
    }

    /**
     * @return name of the file.
     */
    public String getFileName () {
        return new File(path).getName();
    }

    /**
     * @return HashMap, that contains filenames and checksums.
     */
    public HashMap<String, Long> getFiles () {
        return files;
    }
}
