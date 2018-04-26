package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
	
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

    }
}
