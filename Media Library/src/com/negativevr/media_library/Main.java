package com.negativevr.media_library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.negativevr.media_library.files.MediaFile;

/**
 * 
 * @author Vaneh Boghosian, Ryan May
 *
 */

public class Main {
	private static final Map<Long, MediaFile> masterMediaData = Collections.synchronizedMap(new TreeMap<Long, MediaFile>());
	
	public Main() {
		
	}

	public static void main(String[] args) {
		List<String> name= new ArrayList<>();
		name.add("The Killers");
		MediaFile file = new MediaFile(name, "Kill\n", 4, new Date().toString(),
				 new Date().toString(), "When You Were Young\n", 210.0, "C:\\", 124L);
		
		List<String> name1= new ArrayList<>();
		name1.add("One Republic");
		MediaFile file1 = new MediaFile(name1, "Hi\n", 6, new Date().toString(),
				 new Date().toString(), "All The Right Moves\n", 193.0, "C:\\", 3245L);
		
		List<String> name2= new ArrayList<>();
		name2.add("Stromae");
		MediaFile file2 = new MediaFile(name2, "Flower\n", 12, new Date().toString(),
				 new Date().toString(), "Formidable\n", 510.0, "C:\\", 1214L);
		
		masterMediaData.put(124L, file);
		masterMediaData.put(3245L, file1);
		masterMediaData.put(1214L, file2);
		 
		System.out.println(masterMediaData);
	}
	
//non-private accessors / mutators	
	/**
	 * Returns the Master Media Data map; a synchronized TreeMap<Long, {@link com.negativevr.media_library.files.MediaFile;}>.
	 * @return ccmasterMediaData
	 */
	public static Map<Long, MediaFile> getMasterData(){ return masterMediaData; }
	
	public static List<MediaFile> getMasterDataAsList(){
		List<MediaFile> list = new ArrayList<>();
		for( Map.Entry<Long, MediaFile> entry : masterMediaData.entrySet()){
			list.add(entry.getValue());
		} return list;
	}
	
// private accessors/ mutators
	
	
	
}
