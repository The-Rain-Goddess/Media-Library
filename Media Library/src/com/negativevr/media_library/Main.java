package com.negativevr.media_library;

import java.util.ArrayList;
import java.util.Collections;
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
		
	}
	
//non-private accessors / mutators	
	/**
	 * Returns the Master Media Data map; a synchronized TreeMap<Long, {@link com.negativevr.media_library.files.MediaFile;}>.
	 * @return masterMediaData
	 */
	public static Map<Long, MediaFile> getMasterData(){ return masterMediaData; }
	
	public static List<MediaFile> getMasterDataAsList(){
		List<MediaFile> list = new ArrayList<>();
		for( Map.Entry<Long, MediaFile> entry : masterMediaData.entrySet()){
			list.add(entry.getValue());
		} return list;
	}
}
