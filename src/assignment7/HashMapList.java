package assignment7;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapList {
	
	ArrayList<HashMap<String,Boolean>> hashMaps;
	
	public HashMapList(int size) {
		hashMaps = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			hashMaps.add(null);
		}
	}
	
	public void add(int index, HashMap<String,Boolean> theMap) {
		hashMaps.set(index, theMap);
	}
	
	public ArrayList<HashMap<String,Boolean>> returnMap(){
		return hashMaps;
	}
}

