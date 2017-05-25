package com.negativevr.media_library.files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MediaFileAttribute {
	private StringProperty name, album, dateCreated, dateRecorded, path, genre;
	private List<StringProperty> artists;
	private IntegerProperty number;
	private DoubleProperty length;
	private IntegerProperty plays;
	
//contructor
	public MediaFileAttribute(){
		this.name = new SimpleStringProperty("");
		this.album = new SimpleStringProperty("");
		this.dateCreated = new SimpleStringProperty(new Date().toString());
		this.dateRecorded = new SimpleStringProperty("");
		this.path = new SimpleStringProperty("C:\\");
		this.genre = new SimpleStringProperty("");
		this.artists = Arrays.asList(new SimpleStringProperty(""));
		this.number = new SimpleIntegerProperty(0);
		this.plays = new SimpleIntegerProperty(0);
		this.length = new SimpleDoubleProperty(0.0);
	}

//getters	
	/**
	 * @return the name
	 */
	public StringProperty getName() {
		return name;
	}

	/**
	 * @return the album
	 */
	public StringProperty getAlbum() {
		return album;
	}

	/**
	 * @return the dateCreated
	 */
	public StringProperty getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return the dateRecorded
	 */
	public StringProperty getDateRecorded() {
		return dateRecorded;
	}

	/**
	 * @return the path
	 */
	public StringProperty getPath() {
		return path;
	}

	/**
	 * @return the genre
	 */
	public StringProperty getGenre() {
		return genre;
	}

	/**
	 * @return the artists
	 */
	public List<StringProperty> getArtists() {
		return artists;
	}

	/**
	 * @return the number
	 */
	public IntegerProperty getNumber() {
		return number;
	}

	/**
	 * @return the length
	 */
	public DoubleProperty getLength() {
		return length;
	}

	/**
	 * @return the plays
	 */
	public IntegerProperty getPlays() {
		return plays;
	}

//setters
	/**
	 * @param name
	 *            the name to set
	 */
	public MediaFileAttribute setName(StringProperty name) {
		this.name = name;
		return this;
	}
	
	/**
	 * @param name
	 *            the name to set
	 */
	public MediaFileAttribute setName(String name) {
		this.name = new SimpleStringProperty(name);
		return this;
	}

	/**
	 * @param album
	 *            the album to set
	 */
	public MediaFileAttribute setAlbum(StringProperty album) {
		this.album = album;
		return this;
	}
	
	/**
	 * @param album
	 *            the album to set
	 */
	public MediaFileAttribute setAlbum(String album) {
		this.album = new SimpleStringProperty(album);
		return this;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public MediaFileAttribute setDateCreated(StringProperty dateCreated) {
		this.dateCreated = dateCreated;
		return this;
	}
	
	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public MediaFileAttribute setDateCreated(String dateCreated) {
		this.dateCreated = new SimpleStringProperty(dateCreated);
		return this;
	}

	/**
	 * @param dateRecorded
	 *            the dateRecorded to set
	 */
	public MediaFileAttribute setDateRecorded(StringProperty dateRecorded) {
		this.dateRecorded = dateRecorded;
		return this;
	}
	
	/**
	 * @param dateRecorded
	 *            the dateRecorded to set
	 */
	public MediaFileAttribute setDateRecorded(String dateRecorded) {
		this.dateRecorded = new SimpleStringProperty(dateRecorded);
		return this;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public MediaFileAttribute setPath(StringProperty path) {
		this.path = path;
		return this;
	}
	
	/**
	 * @param path
	 *            the path to set
	 */
	public MediaFileAttribute setPath(String path) {
		this.path = new SimpleStringProperty(path);
		return this;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public MediaFileAttribute setGenre(StringProperty genre) {
		this.genre = genre;
		return this;
	}
	
	/**
	 * @param genre
	 *            the genre to set
	 */
	public MediaFileAttribute setGenre(String genre) {
		this.genre = new SimpleStringProperty(genre);
		return this;
	}

	/**
	 * @param artists
	 *            the artists to set
	 */
	public MediaFileAttribute setArtists(List<StringProperty> artists) {
		this.artists = artists;
		return this;
	}
	
	/**
	 * @param artists
	 *            the artists to set
	 */
	public MediaFileAttribute setArtistStrings(List<String> artists) {
		List<StringProperty> list = new ArrayList<>();
		for(String s : artists){
			list.add(new SimpleStringProperty(s));
		} this.artists = list;
		return this;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public MediaFileAttribute setNumber(IntegerProperty number) {
		this.number = number;
		return this;
	}
	
	/**
	 * @param number
	 *            the number to set
	 */
	public MediaFileAttribute setNumber(int number) {
		this.number = new SimpleIntegerProperty(number);
		return this;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public MediaFileAttribute setLength(DoubleProperty length) {
		this.length = length;
		return this;
	}
	
	/**
	 * @param length
	 *            the length to set
	 */
	public MediaFileAttribute setLength(double length) {
		this.length = new SimpleDoubleProperty(length);
		return this;
	}

	/**
	 * @param plays
	 *            the plays to set
	 */
	public MediaFileAttribute setPlays(IntegerProperty plays) {
		this.plays = plays;
		return this;
	}
	
	/**
	 * @param plays
	 *            the plays to set
	 */
	public MediaFileAttribute setPlays(int plays) {
		this.plays = new SimpleIntegerProperty(plays);
		return this;
	}
}
