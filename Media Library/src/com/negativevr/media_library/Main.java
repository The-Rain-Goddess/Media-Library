package com.negativevr.media_library;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.negativevr.media_library.files.MediaFile;
import com.negativevr.media_library.files.MediaFileAttribute;
import com.negativevr.media_library.gui.ApplicationWindow;

/**
 * 
 * @author Vaneh Boghosian, Ryan May
 *
 */

public class Main {
	private static final Map<Long, MediaFile> masterMediaData = Collections.synchronizedMap(new TreeMap<Long, MediaFile>());
	private static long UUID = 0;
	
	public Main() {
		
	}

	public static void main(String[] args) {
		try{
			createHomeDirectory();
			readFromFile();
		} catch(IOException e){
			e.printStackTrace();
		}
		List<String> name= new ArrayList<>();
		name.add("The Killers");
		MediaFile file = new MediaFile(name, "Kill", 4, new Date().toString(),
				 new Date().toString(), "When You Were Young", 210.0, "C:\\","Alternative", getNextUUID());
		
		List<String> name1= new ArrayList<>();
		name1.add("One Republic");
		MediaFile file1 = new MediaFile(name1, "Hi", 6, new Date().toString(),
				 new Date().toString(), "All The Right Moves", 193.0, "C:\\", "Rock", getNextUUID());
		
		List<String> name2= new ArrayList<>();
		name2.add("Stromae");
		MediaFile file2 = new MediaFile(name2, "Flower", 12, new Date().toString(),
				 new Date().toString(), "Formidable", 510.0, "C:\\","Pop", getNextUUID());
		
		MediaFile file3 = new MediaFile(new MediaFileAttribute().setArtistStrings(Arrays.asList("The Killers"))
										.setAlbum("Sam's Town")
										.setName("Jenny Was A Friend Of Mine")
										.setGenre("Indie Rock")
										.setDateCreated(new Date().toString())
										.setDateRecorded("9/5/2006")
										.setLength(192)
										.setNumber(1)
										.setPath("C:\\")
										.setPlays(0)
										, getNextUUID());
		
		masterMediaData.put(file .getUUID(), file);
		masterMediaData.put(file1.getUUID(), file1);
		masterMediaData.put(file2.getUUID(), file2);
		masterMediaData.put(file3.getUUID(), file3);
		 
		//System.out.println(masterMediaData);
		
		ApplicationWindow app = new ApplicationWindow();
		app.begin(args);
		try {
			readToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public static long getNextUUID(){
		return ++UUID;
	}
	
// private accessors/ mutators
	private static void readToDisk() throws IOException{
		for(Map.Entry<Long, MediaFile> entry: masterMediaData.entrySet()){
			MediaFile file=entry.getValue();
			File dir = new File("C:\\Music\\"+ file.getArtistName().get(0).toLowerCase()+ "\\");
		    dir.mkdirs();
		    new File("C:\\Music\\" + file.getArtistName().get(0).toLowerCase() + "\\"
		    			+ file.getAlbumName().toLowerCase() + "\\").mkdirs();
		    file.writeToDisk();
		}
	}
	
	private static void readFromFile() throws IOException{
		
	}
	
	private static void createHomeDirectory() throws IOException{
		File home = new File("C:\\Music\\");
		if(!home.exists())
			home.mkdirs();
	}
}
