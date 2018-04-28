package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	
    	/*
        System.out.println("Start");
        long start = System.nanoTime();
    	
    	File file = new File(Params.folder);
        ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(file.list()));
        
        Params.hList = new HashMapList(fileNames.size());
        ArrayList<HashMap<String,Boolean>> hashMaps;
        Thread[] tList = new Thread[Params.numThreads];
        int split = fileNames.size()/Params.numThreads;

        for(int i = 0; i < Params.numThreads; i++) {
        	if(i < Params.numThreads - 1) {
        		HashDoc func = new HashDoc(fileNames, split * i, split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}else {
        		HashDoc func = new HashDoc(fileNames, split * i, fileNames.size()%Params.numThreads + split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[Params.numThreads - 1] = aThread;
        	}
        }
        
        for(Thread aThread : tList) {
        	try {
				aThread.join();
			} catch (InterruptedException e) {
				System.out.println("Could not join Hashing thread");
			}
        }
        
        hashMaps = Params.hList.hashMaps;
        HashMap<String[],Integer> comparisons = new HashMap<>();
        tList = new Thread[Params.numThreads];
        split = hashMaps.size() / Params.numThreads;
        
        for(int i = 0; i < Params.numThreads; i++) {
        	if(i < Params.numThreads - 1) {
        		CompareHash func = new CompareHash(fileNames, split * i, split, hashMaps, comparisons);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}else {
        		CompareHash func = new CompareHash(fileNames, split * i, hashMaps.size()%Params.numThreads + split, hashMaps, comparisons);//Params.hashMaps.size()%Params.numThreads + split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}
        }
        
        for(Thread aThread : tList) {
        	try {
				aThread.join();
			} catch (InterruptedException e) {
				System.out.println("Could not join Hashing thread");
			}
        }

        System.out.println("# Hash Map: " + hashMaps.size());
        long end = System.nanoTime();
        System.out.println("Done");
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1000000000.0;
        System.out.println(seconds + " seconds elapsed");
    	*/
    	
    	//********************************************************************************************************************
    	
    	/*
    	System.out.println("Start");
        long start = System.nanoTime();
    	
    	File file = new File(Params.folder);
        //ArrayList<String> fileNames = new ArrayList<String>(Arrays.asList(file.list()));
    	List<String> fileNames = Arrays.asList(file.list());
        
        HashMap<Integer,HashSet<Integer>> bigList = new HashMap();
        //Thread[] tList = new Thread[Params.numThreads];
        //int split = fileNames.size()/Params.numThreads;
        
        for(int k = 0; k < fileNames.size(); k++) {
        //for(String name : fileNames) {
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

            
            for(int i = 0; i < arr.size() - Params.phraseLength + 1; i++) {

            	StringBuilder phrase = new StringBuilder("");
                for (int j = i; j < i + Params.phraseLength; j++) {
                    String word = arr.get(j).toLowerCase().replaceAll("[^a-zA-Z0-9]","");
                    phrase.append(word);
                }
                
                int key = phrase.toString().hashCode();
                if(bigList.containsKey(key)) {
                	bigList.get(key).add(k);
                }else {
                	HashSet<Integer> fileData = new HashSet<>();
                	fileData.add(k);
                	bigList.put(phrase.toString().hashCode(), fileData);
                }
                
            }
        }
        
        long end = System.nanoTime();
        System.out.println(bigList.size());
        System.out.println("Done");
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1000000000.0;
        System.out.println(seconds + " seconds elapsed");
        
        */
    	
    	//********************************************************************************************************************
    	
    	System.out.println("Start");
        long start = System.nanoTime();
    	
    	File file = new File(Params.folder);
    	List<String> fileNames = Arrays.asList(file.list());
        
        Params.bigList = new HashMap();
        Thread[] tList = new Thread[Params.numThreads];
        int split = fileNames.size()/Params.numThreads;
        
        for(int i = 0; i < Params.numThreads; i++) {
        	if(i < Params.numThreads - 1) {
        		BigHash func = new BigHash(fileNames, split * i, split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}else {
        		BigHash func = new BigHash(fileNames, split * i, fileNames.size()%Params.numThreads + split);
        		Thread aThread = new Thread(func);
        		aThread.start();
        		tList[i] = aThread;
        	}
        }
        
        for(Thread aThread : tList) {
        	try {
				aThread.join();
			} catch (InterruptedException e) {
				System.out.println("Could not join Hashing thread");
			}
        }
        
        HashMap<String,Integer> comparisons = new HashMap<>();
        tList = new Thread[Params.numThreads];
        split = Params.bigList.size() / Params.numThreads;
        
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
        
        Set<String> fileCompare = comparisons.keySet();
        for(String comboName : fileCompare) {
        	if(comparisons.get(comboName) >= 200) {
        		System.out.println(comboName + " " + comparisons.get(comboName));
        	}
        }
        
        long end = System.nanoTime();
        System.out.println("\n\n" + "BigList size: " + Params.bigList.size());
        System.out.println("Done");
        long elapsedTime = end - start;
        double seconds = (double)elapsedTime / 1000000000.0;
        System.out.println(seconds + " seconds elapsed");
        
    }
}
