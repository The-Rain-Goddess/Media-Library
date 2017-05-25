package com.negativevr.media_library.files;

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
	private final long UUID;

	private List<StringProperty> artistName;
	private StringProperty albumName;
	private IntegerProperty albumNumber;
	private StringProperty dateCreated;
	private StringProperty dateRecorded;
	private StringProperty songName;
	private DoubleProperty songTime;
	private StringProperty filePath;
	private StringProperty genre;

//constructors
	public MediaFile(List<StringProperty> artistName, 
			StringProperty albumName,
			IntegerProperty albumNumber,
			StringProperty dateCreated,
			StringProperty dateRecorded, 
			StringProperty songName,
			DoubleProperty songTime,
			StringProperty filePath, 
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
		this.UUID= UUID;
	}
	
	public MediaFile(List<String> artistName, 
			String albumName,
			int albumNumber,
			String dateCreated,
			String dateRecorded, 
			String songName,
			Double songTime,
			String filePath, 
			long UUID)
	{
		this.artistName= convertToProperty(artistName);
		this.albumName= new SimpleStringProperty(albumName);
		this.albumNumber= new SimpleIntegerProperty(albumNumber);
		this.dateCreated= new SimpleStringProperty(dateCreated);
		this.dateRecorded= new SimpleStringProperty(dateRecorded);
		this.songName= new SimpleStringProperty(songName);
		this.songTime= new SimpleDoubleProperty(songTime);
		this.filePath= new SimpleStringProperty(filePath);
		this.UUID= UUID;
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
		return genre;
	}
	/**
	 * @return the genre
	 */
	public String getGenre(){
		return genre.get();
	}
	/**
	 * @return the song length
	 */
	public DoubleProperty getSongLengthProperty(){
		return songTime;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath.get();
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(StringProperty filePath) {
		this.filePath = filePath;
	}
	/**
	 * @return the artistName (List<String>)
	 */
	public List<String> getArtistName() {
		List<String> list = new ArrayList<>();
		for(StringProperty s : this.artistName)
			list.add(s.get());
		return list;
	}
	/**
	 * @return the artistName
	 */
	public StringProperty getArtistNameProperty(){
		String out = "";
		for(StringProperty t : artistName){
			out += (out.equals("")) ? out + t.get() : out + " " + t.get();
		}
		StringProperty s = new SimpleStringProperty(out); 
		return s;
	}
	/**
	 * @param set the artistName to artistName
	 */
	public void setArtistName(List<StringProperty> artistName) {
		this.artistName = artistName;
	}
	/**
	 * @return the albumName (String)
	 */
	public String getAlbumName() {
		return albumName.get();
	}
	/**
	 * @return the albumName
	 */
	public StringProperty getAlbumNameProperty(){
		return albumName;
	}
	/**
	 * @param set the albumName to albumName
	 */
	public void setAlbumName(StringProperty albumName) {
		this.albumName = albumName;
	}
	/**
	 * @return the albumNumber (int)
	 */
	public int getAlbumNumber() {
		return albumNumber.get();
	}
	/**
	 * @return the Album Number
	 */
	public IntegerProperty getAlbumNumberProperty(){
		return albumNumber;
	}
	/**
	 * @param set albumNumber to albumNumber
	 */
	public void setAlbumNumber(IntegerProperty albumNumber) {
		this.albumNumber = albumNumber;
	}
	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated.get();
	}
	/**
	 * @param set dateCreated to dateCreated
	 */
	public void setDateCreated(StringProperty dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @return the dateRecorded
	 */
	public String getDateRecorded() {
		return dateRecorded.get();
	}
	/**
	 * @param set dateRecorded to dateRecorded 
	 */
	public void setDateRecorded(StringProperty dateRecorded) {
		this.dateRecorded = dateRecorded;
	}
	/**
	 * @return the songName
	 */
	public String getSongName() {
		return songName.get();
	}
	/**
	 * @param set songName to songName 
	 */
	public void setSongName(StringProperty songName) {
		this.songName = songName;
	}
	/**
	 * @return the songName (StringProperty)
	 */
	public StringProperty getSongNameProperty(){
		return songName;
	}
	/**
	 * @return the songTime
	 */
	public double getSongTime() {
		return songTime.get();
	}
	/**
	 * @param set songTime to songTime 
	 */
	public void setSongTime(DoubleProperty songTime) {
		this.songTime = songTime;
	}
	/**
	 * @return the uUID
	 */
	public long getUUID() {
		return UUID;
	}
	
	public void writeToDisk() throws IOException {
		FileOutputStream f_out= new FileOutputStream("C:\\Music\\"+ artistName.get(0) + "\\" + albumName.get()+ "\\" + UUID + ".data");
		ObjectOutputStream obj_out= new ObjectOutputStream(f_out);
		obj_out.writeObject(this);
		obj_out.close();
		f_out.close();
	}
	
	@Override
	public String toString() {
		return "\nArtist Name(s): \n"+artistName.get(0).get()+ "\n\nAlbum Name: \n"+ albumName.get() + 
				"\nAlbum Number: \n"+albumNumber.get()+"\n\nDate Created: \n" + dateCreated.get()+
				"\n\nDate Recorded: \n"+dateRecorded.get()+"\n\nSong Name: \n"+ songName.get()
				+ "\nSong Time: \n"+ songTime.get()+ "\n\nFile Path: \n"+filePath.get();
	}
}