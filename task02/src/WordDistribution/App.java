package WordDistribution;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class App {

    public static void main(String[] args) throws IOException {
        
        // get directory name
        String fileZip = args[0];                   // location of zip file as passed in via command line argument
        // String fileZip = "Directory.zip";           // for testing
        File destDir = new File("texts");  // directory to unzip into

        // unzip the files
        byte[] buffer = new byte[1024];
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
                zipEntry = zis.getNextEntry();
            }
        }

        // grab individual text files into list of files "allFiles" variable
        File[] subFolder = destDir.listFiles();     // gives suess and frost directories
        for (File folder : subFolder) {
            File[] textFiles = folder.listFiles();  // gives the text files in Files[] form
            for (File file : textFiles) {
                System.out.println("\nProcessing file at " + file);
                // handle each text file (read, process, print)
                BufferedReader br = new BufferedReader(new FileReader(file));
                String text = "";
                
                try {
                    // append into one long line
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        text += temp + " ";
                    }
                } catch (Exception e) {
                    break;
                } finally {
                    br.close();
                }
                
                System.out.println(text);
                System.out.println(text.replaceAll("[^a-zA-Z ]", "").toLowerCase());
                // remove punctuations, make lowercase and split text
                String[] textSplit = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
        
                // create a list of firstword objects
                List<String> wordChecker = new ArrayList<>();
                List<Word> listOfWords = new ArrayList<>();
        
                // add second words for each firstword
                for (int i = 0; i < textSplit.length; i++) {
                    // try to prevent final word error (no next word to grab)
                    try {
                        // get first and second word
                        String firstWord = textSplit[i];
                        String secondWord = textSplit[i+1];
                        // get first word object and add second word to it
                        if (!wordChecker.contains(firstWord)) {
                            wordChecker.add(firstWord);
                            listOfWords.add(new Word(firstWord, secondWord));
                        } else {
                            // first word already exists, grab first word object and add second word count
                            int index = listOfWords.indexOf(new Word(firstWord));
                            listOfWords.get(index).addSecondWordCount(secondWord);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // final word, ignore
                    }
                }
        
                // print all word objects and their probability
                System.out.println("---------------------------");
                System.out.println("WORD PROBABILITY");
                System.out.println("---------------------------");
                for (Word word : listOfWords) {
                    word.printSecondWordProbability();
                }


            }
        }

    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
    
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
    
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
    
        return destFile;
    }

}
