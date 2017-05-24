package com.negativevr.media_library.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MediaFile implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5898952383948456405L;
	private final long UUID;


	private List artistName;
	private String albumName;
	private int albumNumber;
	private String dateCreated;
	private String dateRecorded;
	private String songName;
	private double songTime;
	private String filePath;
	
	public MediaFile(List artistName, String albumName,int albumNumber, String dateCreated,
			String dateRecorded, String songName,double songTime, String filePath, long UUID)
	{
		this.artistName= artistName;
		this.albumName= albumName;
		this.albumNumber= albumNumber;
		this.dateCreated= dateCreated;
		this.dateRecorded= dateRecorded;
		this.songName= songName;
		this.songTime= songTime;
		this.filePath= filePath;
		this.UUID= UUID;
	}
	
	
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the artistName
	 */
	public List getArtistName() {
		return artistName;
	}
	/**
	 * @param set the artistName to artistName
	 */
	public void setArtistName(List artistName) {
		this.artistName = artistName;
	}
	/**
	 * @return the albumName
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * @param set the albumName to albumName
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	/**
	 * @return the albumNumber
	 */
	public int getAlbumNumber() {
		return albumNumber;
	}
	/**
	 * @param set albumNumber to albumNumber
	 */
	public void setAlbumNumber(int albumNumber) {
		this.albumNumber = albumNumber;
	}
	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}
	/**
	 * @param set dateCreated to dateCreated
	 */
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the dateRecorded
	 */
	public String getDateRecorded() {
		return dateRecorded;
	}
	/**
	 * @param set dateRecorded to dateRecorded 
	 */
	public void setDateRecorded(String dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
	/**
	 * @return the songName
	 */
	public String getSongName() {
		return songName;
	}
	/**
	 * @param set songName to songName 
	 */
	public void setSongName(String songName) {
		this.songName = songName;
	}
	/**
	 * @return the songTime
	 */
	public double getSongTime() {
		return songTime;
	}
	/**
	 * @param set songTime to songTime 
	 */
	public void setSongTime(double songTime) {
		this.songTime = songTime;
	}
	/**
	 * @return the uUID
	 */
	public long getUUID() {
		return UUID;
	}
	
	public void writeToDisk() throws IOException
	{
		FileOutputStream f_out= new FileOutputStream(UUID+""+".data");
		ObjectOutputStream obj_out= new ObjectOutputStream(f_out);
		obj_out.writeObject(this);
		obj_out.close();
		f_out.close();
	}
	
	@Override
	public String toString()
	{
		return "\nArtist Name(s): \n"+artistName+ "\n\nAlbum Name: \n"+ albumName + 
				"\nAlbum Number: \n"+albumNumber+"\n\nDate Created: \n" + dateCreated+
				"\n\nDate Recorded: \n"+dateRecorded+"\n\nSong Name: \n"+ songName
				+ "\nSong Time: \n"+ songTime+ "\n\nFile Path: \n"+filePath;
	}
		
	public static void main(String[] args)
	{
		List<String> name= new ArrayList<>();
		name.add("The Killers");
		MediaFile file = new MediaFile(name, "Kill\n", 4, new Date().toString(),
				 new Date().toString(), "When You Were Young\n", 3.14, "C:\\", 124L);
		//System.out.println(file);
		
		List<String> name1= new ArrayList<>();
		name1.add("One Republic");
		MediaFile file1 = new MediaFile(name1, "Hi\n", 6, new Date().toString(),
				 new Date().toString(), "All The Right Moves\n", 2.33, "C:\\", 3245L);
		
		List<String> name2= new ArrayList<>();
		name2.add("Stromae");
		MediaFile file2 = new MediaFile(name2, "Flower\n", 12, new Date().toString(),
				 new Date().toString(), "Formidable\n", 5.10, "C:\\", 1214L);
		
		 Map<Integer, MediaFile> map = new TreeMap<Integer, MediaFile>();  
		map. put(124, file);
		map. put(3245, file1);
		map. put(1214, file2);
		 
		System.out.println(map);
		 
	}
	
	
	
}