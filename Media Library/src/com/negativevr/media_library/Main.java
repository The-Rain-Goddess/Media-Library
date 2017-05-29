package com.negativevr.media_library;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.negativevr.media_library.files.MediaFile;
import com.negativevr.media_library.gui.ApplicationWindow;

/**
 * 
 * @author Vaneh Boghosian, Ryan May
 *
 */

public class Main {
	private static final Map<Long, MediaFile> masterMediaData = Collections
			.synchronizedMap(new TreeMap<Long, MediaFile>());
	private static long UUID = 0;

	public Main() {

	}

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			createHomeDirectory();
			readFromFile();
		
			ApplicationWindow app = new ApplicationWindow();
			app.begin(args);
			
			writeToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// non-private accessors / mutators
	/**
	 * Returns the Master Media Data map; a synchronized TreeMap<Long,
	 * {@link com.negativevr.media_library.files.MediaFile;}>.
	 * 
	 * @return ccmasterMediaData
	 */
	public static Map<Long, MediaFile> getMasterData() {
		return masterMediaData;
	}
	
	/**
	 * Returns the Master Media Data map as a list; a synchronized List<{@link com.negativevr.media_library.files.MediaFile;}>.
	 * 
	 * @return ccmasterMediaData
	 */
	public static List<MediaFile> getMasterDataAsList() {
		List<MediaFile> list = new ArrayList<>();
		for (Map.Entry<Long, MediaFile> entry : masterMediaData.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	public static long getNextUUID() {
		return ++UUID;
	}

	// private accessors/ mutators
	private static void writeToDisk() throws IOException {
		for (Map.Entry<Long, MediaFile> entry : masterMediaData.entrySet()) {
			MediaFile fileToBeWritten = entry.getValue();
			File dir = new File("C:\\Music\\" + fileToBeWritten.getArtistName().toLowerCase() + "\\");
			dir.mkdirs();
			new File("C:\\Music\\" + fileToBeWritten.getArtistName().toLowerCase() + "\\"
					+ fileToBeWritten.getAlbumName().toLowerCase() + "\\").mkdirs();
			fileToBeWritten.writeToDisk();
		}
	}

	private static void readFromFile() throws IOException, ClassNotFoundException {
		List<File> files = getFilesFromMainDirectory();
		if(files!=null){
			for (File file : files) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				MediaFile media = (MediaFile) ois.readObject();
				media.setUUID(getNextUUID());
				System.out.println("New media read from disk: \n" + media);
				masterMediaData.put(media.getUUID(), media);
				ois.close();
			}
		}
	}

	public static List<File> getFilesFromMainDirectory() {
		File mainDirectory = new File("C:\\Music\\");
		File[] files = mainDirectory.listFiles();
		List<File> artistDir = Arrays.asList(files);
		List<File> allSongs = new ArrayList<>();
		for (int k = 0; k < artistDir.size(); k++) {
			if(artistDir.get(k).isDirectory()){
				List<File> albumDir = Arrays.asList(artistDir.get(k).listFiles());
				for(int i = 0; i< albumDir.size(); i++){
					if(albumDir.get(i).isDirectory()){
						List<File> songDir = Arrays.asList(albumDir.get(i).listFiles());
						for(int j = 0; j<songDir.size(); j++)
							allSongs.add(songDir.get(j));
						
					}
				}
			}
		} System.out.println("Files to be read into ram : \n" + allSongs); 
		return allSongs;
	}

	/*
	 * public static List<File> displayDirectoryContents(File directory) { try {
	 * File dir = new File("C:\\Music\\"); List<File> direct= new
	 * ArrayList<File>(); direct.add(dir); File[] directoryListing =
	 * dir.listFiles(); for (File file : directoryListing) { if
	 * (file.isDirectory()) { System.out.println("directory:" +
	 * dir.getCanonicalPath()); //displayDirectoryContents(dir); } else {
	 * System.out.println("     file:" + dir.getCanonicalPath()); } return
	 * direct; }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } return null; }
	 */

	/*
	 * private static List<File> find() { File dir = new File("C:\\Music\\");
	 * List<File> direct= new ArrayList<File>(); direct.add(dir); File[]
	 * directoryListing = dir.listFiles(); if (directoryListing != null) { for
	 * (File child : directoryListing) {
	 * 
	 * System.out.println("Directory: " + dir.getName()); } } return direct;
	 * 
	 * }
	 */

	/*
	 * File file = new File("C:\\Music\\"); List<File> direct= new
	 * ArrayList<File>(); direct.add(file);
	 * 
	 * for (File filesTest : direct) { if (filesTest.isDirectory()) {
	 * System.out.println("Directory: " + filesTest.getName());
	 * 
	 * } else { System.out.println("File: " + filesTest.getName()); } } return
	 * direct;
	 * 
	 * }
	 */

	/*
	 * File[] files = null; if (file.exists()) { files = file.listFiles(new
	 * FilenameFilter(){
	 * 
	 * @Override public boolean accept(File dir, String name) { return
	 * name.toLowerCase().endsWith(".data"); } }); } System.out.println(file); }
	 * return files; return null;
	 * 
	 * }
	 */

	private static void createHomeDirectory() throws IOException {
		File home = new File("C:\\Music\\");
		if (!home.exists())
			home.mkdirs();
	}
}
