package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {

    private Scanner file;

    public FileReader (String file) {
        try {
            this.file = new Scanner(new File(file));
        } catch (Exception e) {
            System.out.println("Nao foi possivel achar o arquivo!");
        }
    }

    public boolean fileIsOpen () {
        return file.hasNext();
    }

    public ArrayList<String> readWholeFile () {
        ArrayList<String> frases = new ArrayList<String>();
        while (file.hasNextLine()) {
            frases.add(file.nextLine());
            System.out.println(frases.get(frases.size()-1));
        }
        return frases;
    }

    public String readFileLine () {
        if (file.hasNext()) {
            return file.nextLine();
        }
        return null;
    }

    public String readFileWord () {
        if (file.hasNext()) {
            return file.next();
        }
        return null;
    }
}
