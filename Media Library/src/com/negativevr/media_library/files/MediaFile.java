package com.negativevr.media_library.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
 
public class MediaFile implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5898952383948456405L;
	private long UUID;

	private String artistName;
	private String albumName;
	private int albumNumber;
	private String dateCreated;
	private String dateRecorded;
	private String songName;
	private double songTime;
	private String filePath;
	private String genre;

//constructors
	public MediaFile(StringProperty artistName, 
			StringProperty albumName,
			IntegerProperty albumNumber,
			StringProperty dateCreated,
			StringProperty dateRecorded, 
			StringProperty songName,
			DoubleProperty songTime,
			StringProperty filePath,
			StringProperty genre,
			long UUID)
	{
		this.artistName= artistName.get();
		this.albumName= albumName.get();
		this.albumNumber= albumNumber.get();
		this.dateCreated= dateCreated.get();
		this.dateRecorded= dateRecorded.get();
		this.songName= songName.get();
		this.songTime= songTime.get();
		this.filePath= filePath.get();
		this.genre = genre.get();
		this.UUID= UUID;
	}
	
	public MediaFile(String artistName, 
			String albumName,
			int albumNumber,
			String dateCreated,
			String dateRecorded, 
			String songName,
			Double songTime,
			String filePath, 
			String genre,
			long UUID)
	{
		this.artistName= artistName;
		this.albumName= albumName;
		this.albumNumber= albumNumber;
		this.dateCreated= dateCreated;
		this.dateRecorded= dateRecorded;
		this.songName= songName;
		this.songTime= songTime;
		this.filePath= filePath;
		this.genre = genre;
		this.UUID= UUID;
	}
	
	public MediaFile(MediaFileAttribute fileAttribute, long UUID){
		this.artistName = fileAttribute.getArtists().get();
		this.albumName = fileAttribute.getAlbum().get();
		this.albumNumber = fileAttribute.getNumber().get();
		this.dateCreated = fileAttribute.getDateCreated().get();
		this.dateRecorded = fileAttribute.getDateRecorded().get();
		this.songName = fileAttribute.getName().get();
		this.songTime = fileAttribute.getLength().get();
		this.filePath = fileAttribute.getPath().get();
		this.genre = fileAttribute.getGenre().get();
		this.UUID = UUID;
	}
	
	public MediaFile(){
		this.UUID = 999L;
	}
	
//private accessors/mutators
	private List<StringProperty> convertToProperty(List<String> l){
		List<StringProperty> list  = new ArrayList<>();
		for(String s : l){
			list.add(new SimpleStringProperty(s));
		} return list;
	}
	
//non-private accesors/ mutators	
	/**
	 * @return the media genre
	 */
	public StringProperty getGenreProperty(){
		return new SimpleStringProperty(genre);
	}
	/**
	 * @return the genre
	 */
	public String getGenre(){
		return genre;
	}
	/**
	 * @return the song length
	 */
	public DoubleProperty getSongLengthProperty(){
		return new SimpleDoubleProperty(songTime);
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
	public void setFilePath(StringProperty filePath) {
		this.filePath = filePath.get();
	}
	/**
	 * @return the artistName (List<String>)
	 */
	public String getArtistName() {
		return this.artistName;
	}
	/**
	 * @return the artistName
	 */
	public StringProperty getArtistNameProperty(){
		return new SimpleStringProperty(artistName);
	}
	/**
	 * @param set the artistName to artistName
	 */
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	/**
	 * @return the albumName (String)
	 */
	public String getAlbumName() {
		return albumName;
	}
	/**
	 * @return the albumName
	 */
	public StringProperty getAlbumNameProperty(){
		return new SimpleStringProperty(albumName);
	}
	/**
	 * @param set the albumName to albumName
	 */
	public void setAlbumName(StringProperty albumName) {
		this.albumName = albumName.get();
	}
	/**
	 * @return the albumNumber (int)
	 */
	public int getAlbumNumber() {
		return albumNumber;
	}
	/**
	 * @return the Album Number
	 */
	public IntegerProperty getAlbumNumberProperty(){
		return new SimpleIntegerProperty(albumNumber);
	}
	/**
	 * @param set albumNumber to albumNumber
	 */
	public void setAlbumNumber(IntegerProperty albumNumber) {
		this.albumNumber = albumNumber.get();
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
	public void setDateCreated(StringProperty dateCreated) {
		this.dateCreated = dateCreated.get();
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
	public void setDateRecorded(StringProperty dateRecorded) {
		this.dateRecorded = dateRecorded.get();
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
	public void setSongName(StringProperty songName) {
		this.songName = songName.get();
	}
	/**
	 * @return the songName (StringProperty)
	 */
	public StringProperty getSongNameProperty(){
		return new SimpleStringProperty(songName);
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
	public void setSongTime(DoubleProperty songTime) {
		this.songTime = songTime.get();
	}
	/**
	 * @return the uUID
	 */
	public long getUUID() {
		return UUID;
	}
	
	public void writeToDisk() throws IOException {
		File dir = new File("C:\\Music\\" + getArtistName().toLowerCase() + "\\" + getAlbumName().toLowerCase() + "\\");
		dir.mkdirs();
		FileOutputStream f_out = new FileOutputStream("C:\\Music\\"+ artistName.toLowerCase() + "\\" + albumName.toLowerCase() + "\\" + UUID + ".data");
		ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
		obj_out.writeObject(this);
		obj_out.close();
		f_out.close();		
	}
	
	@Override
	public String toString() {
		return "Artist Name(s): "+ artistName
				+ "\nAlbum Name: "+ albumName 
				+ "\nAlbum Number: "+ albumNumber
				+ "\nDate Created: " + dateCreated
				+ "\nDate Recorded: "+ dateRecorded
				+ "\nSong Name: "+ songName
				+ "\nSong Time: "+ songTime
				+ "\nFile Path: "+ filePath;
	}

	public void setUUID(long nextUUID) {
		this.UUID=nextUUID;
		
	}
}