package assignment7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class BigHash implements Runnable{
	
	List<String> fileNames;
	int start;
	int length;
	
	public BigHash(List<String> fileNames,int start, int length) {
		this.fileNames = fileNames;
		this.start = start;
		this.length = length;
	}
	
	public void run() {
		//loop that iterates over length # of files
		for(int k = start; k < start + length; k++) {
			
			//gets appropriate file path 
			List<String> arr = new ArrayList<String>();
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

            //hashes the phrases and puts them in a large hashmap
            for(int i = 0; i < arr.size() - Params.phraseLength + 1; i++) {

            	//forms phrases
            	StringBuilder phrase = new StringBuilder("");
                for (int j = i; j < i + Params.phraseLength; j++) {
                    String word = arr.get(j).toLowerCase().replaceAll("[^a-zA-Z0-9]","");
                    phrase.append(word);
                }

                //appends phrases to list via synchronization
                synchronized(Params.bigList) {
	                int key = phrase.toString().hashCode();
	                if(Params.bigList.containsKey(key)) {
	                	Params.bigList.get(key).add(k);
	                }else {
	                	TreeSet<Integer> fileData = new TreeSet<>();
	                	fileData.add(k);
	                	Params.bigList.put(key, fileData);
	                }
                }
                
            }
            
		}
	}
}
