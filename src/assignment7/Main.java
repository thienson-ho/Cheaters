package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
	// write your code here
        File file = new File("src/sm_doc_set");
        ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(file.list()));

        ArrayList<String> arr = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/sm_doc_set/" + fileNames.get(2)));

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                if(!sCurrentLine.equals("")) {
//                    arr.add(sCurrentLine);
                    Collections.addAll(arr, sCurrentLine.split(" "));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(arr.size());

//        System.out.println(arr.get(0));
//        ArrayList<String> arr1 = new ArrayList<String>();
//        String[] firstLine = arr.get(0).split(" ");
//        Collections.addAll(arr1, arr.get(0).split(" "));
//        System.out.println((int) firstLine[26].charAt(6));
//        System.out.println(arr1);
//        System.out.println((arr.get(arr.size() - 1)));
//        System.out.println((arr.get(arr.size() - 2)));
//        System.out.println((arr.get(arr.size() - 3)));

    }
}
