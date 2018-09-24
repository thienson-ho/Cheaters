package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
	public static void main(String[] args) {
        //Gets input parameters
	Scanner scanner = new Scanner(System.in);
        System.out.println("Enter File Path");
        Params.folder = scanner.nextLine();
        System.out.println("Enter number of words in phrase");
        Params.phraseLength = scanner.nextInt();
        System.out.println("Enter threshold hit number");
        Params.filter = scanner.nextInt();

        //begin tracking processing time
        long start = System.nanoTime();
    	
    	File file = new File(Params.folder);
    	List<String> fileNames = Arrays.asList(file.list());
        
    	//Hashmap that contains phrases and associated set of documents
        Params.bigList = new HashMap();
        //Array of threads 
        Thread[] tList = new Thread[Params.numThreads];
        //variable that represents the number of files each thread handles
        int split = fileNames.size()/Params.numThreads;
        
        //Creates each thread and adds it to the List
        for(int i = 0; i < Params.numThreads; i++) {
        	//All threads run at least "split" files
        	if(i < Params.numThreads - 1) {
        		BigHash func = new BigHash(fileNames, split * i, split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}else { //the last thread runs all the remaining files
        		BigHash func = new BigHash(fileNames, split * i, fileNames.size()%Params.numThreads + split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}
        }
        //joins all the threads
        for(Thread aThread : tList) {
        	try {
				aThread.join();
			} catch (InterruptedException e) {
				System.out.println("Could not join Hashing thread");
			}
        }
        
        
        //A hashmap that tracks File pairs and matches
        HashMap<String,Integer> comparisons = new HashMap<>();
        //The number of pairs each thread handles 
        split = Params.bigList.size() / Params.numThreads;
        
        //Compares Files and stores the result
        Collection<TreeSet<Integer>> matchList = Params.bigList.values();
        for(TreeSet<Integer> theSet: matchList) {
        	if(theSet.size() >= 2) {
        		Integer[] theList = theSet.toArray(new Integer[theSet.size()]);
        		for(int i = 0; i < theList.length - 1; i++){
	        		for(int j = i + 1 ; j < theList.length; j++) {
	        			
	        			String keyStr = fileNames.get(theList[i]) + " " + fileNames.get(theList[j]);
	        			
	        			if(comparisons.containsKey(keyStr)) {
	        				int val = comparisons.get(keyStr);
	        				comparisons.put(keyStr, val + 1);
	        			}else {
	        				comparisons.put(keyStr, 1);
	        			}
	        		}
	        	}
        	}
        }
        HashMap<String,Integer> results = new HashMap<>();
        
        //Goes through the matches and prints out the ones above a certain parameter
        Set<String> fileCompare = comparisons.keySet();
        for(String comboName : fileCompare) {
        	if(comparisons.get(comboName) >= Params.filter) {
//        		System.out.println(comboName + " " + comparisons.get(comboName));
        		results.put(comboName, comparisons.get(comboName));
        	}
        }

        Map<String,Integer> sortedResults = Main.sortByValue(results);
        for(String key: sortedResults.keySet()) {
            System.out.println(sortedResults.get(key) + " " + key);
        }
        
        //prints out run time
        long end = System.nanoTime();
//        System.out.println("\n\n" + "BigList size: " + Params.bigList.size());
        System.out.println("Done");
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1000000000.0;
        System.out.println(seconds + " seconds elapsed");
        
    }

	//sorts the map via TreeMap data structure
    public static Map sortByValue(Map unsortedMap) {
        Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }
}

class ValueComparator implements Comparator {
    Map map;

    public ValueComparator(Map map) {
        this.map = map;
    }

    public int compare(Object keyA, Object keyB) {
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        return valueB.compareTo(valueA);
    }
}
