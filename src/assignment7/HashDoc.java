package assignment7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class HashDoc implements Runnable{
	ArrayList<String> fileNames;
	int start;
	int length;
	
	public HashDoc(ArrayList<String> fileNames,int start, int length) {
		this.fileNames = fileNames;
		this.start = start;
		this.length = length;
	}
	
	public void run() {
		for(int k = start; k < start + length; k++) {
			
			ArrayList<String> arr = new ArrayList<String>();
            try {
                BufferedReader br = new BufferedReader(new FileReader(Params.folder+"/" + fileNames.get(k)));

                String sCurrentLine;

                while ((sCurrentLine = br.readLine()) != null) {
                    if (!sCurrentLine.equals("")) {
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
                
                hashMap.put(phrase.toString(),true);
            }
            
            synchronized(Params.hList){
				Params.hList.add(k, hashMap);
			}
		}
	}
}
