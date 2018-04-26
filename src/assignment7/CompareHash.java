package assignment7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class CompareHash implements Runnable{

	int start;
	int length;
	ArrayList<String> fileNames;
	ArrayList<HashMap<String,Boolean>> hashMaps;
	HashMap<String[],Integer> comparisons;
	
	public CompareHash(ArrayList<String> fileNames, int start, int length, ArrayList<HashMap<String,Boolean>> hashMaps, HashMap<String[],Integer> comparisons) {
		this.start = start;
		this.length = length;
		this.fileNames = fileNames;
		this.hashMaps = hashMaps;
		this.comparisons = comparisons;
	}
	
	
	public void run() {
		for(int A = start; A < start + length; A++) {
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
                synchronized(comparisons) {
                	comparisons.put(filePair,hits);
                }
                
                if(hits >= Params.filter) {
                    System.out.println(filePair[0] + " " + filePair[1] + " Hits: " + hits);
                }
                
            }
        }
	}
	
}
