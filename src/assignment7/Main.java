package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	// write your code here
        File file = new File(Params.folder);
        ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(file.list()));
        System.out.println(fileNames.size());
        ArrayList<HashMap<String,Boolean>> hashMaps = new ArrayList<>();

        for(String fileName: fileNames) {
            ArrayList<String> arr = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(Params.folder+"/" + fileName));

                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    if (!sCurrentLine.equals("")) {
                        //                    arr.add(sCurrentLine);
                        Collections.addAll(arr, sCurrentLine.split(" "));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            HashMap<String,Boolean> hashMap = new HashMap<>(arr.size() - Params.phraseLength + 1);

            for(int i = 0; i < arr.size() - Params.phraseLength + 1; i++) {
                StringBuilder phrase = new StringBuilder("");
                for (int j = i; j < i + Params.phraseLength; j++) {
                    String word = arr.get(j).toLowerCase().replaceAll("[^a-zA-Z0-9]","");
                    phrase.append(word);
                }
//                System.out.println(phrase.toString());
                hashMap.put(phrase.toString(),true);
            }

            hashMaps.add(hashMap);

        }

        HashMap<String[],Integer> comparisons = new HashMap<>();

        for(int A = 0; A < hashMaps.size()-1; A++) {
            Set<String> aKeys = hashMaps.get(A).keySet();
            for(int B = A + 1; B < hashMaps.size(); B++) {
                Integer hits = 0;
                HashMap<String,Boolean> bMap = hashMaps.get(B);

                for(String aPhrase: aKeys)  {
                    if(bMap.get(aPhrase) != null) {
                        hits++;
                    }
                }

                String[] filePair = {fileNames.get(A), fileNames.get(B)};
                comparisons.put(filePair,hits);
                if(hits >= Params.filter) {
                    System.out.println(filePair[0] + " " + filePair[1] + " Hits: " + hits);
                }
            }
        }


        System.out.println("Done");

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
