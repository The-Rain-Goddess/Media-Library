package com.negativevr.media_library.files;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class MediaFileAttribute {
	private StringProperty name, album, dateCreated, dateRecorded, path, genre;
	private List<StringProperty> artists;
	private IntegerProperty number;
	private DoubleProperty length;
	private IntegerProperty plays;

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
	 * @param album
	 *            the album to set
	 */
	public MediaFileAttribute setAlbum(StringProperty album) {
		this.album = album;
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
	 * @param dateRecorded
	 *            the dateRecorded to set
	 */
	public MediaFileAttribute setDateRecorded(StringProperty dateRecorded) {
		this.dateRecorded = dateRecorded;
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
	 * @param genre
	 *            the genre to set
	 */
	public MediaFileAttribute setGenre(StringProperty genre) {
		this.genre = genre;
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
	 * @param number
	 *            the number to set
	 */
	public MediaFileAttribute setNumber(IntegerProperty number) {
		this.number = number;
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
	 * @param plays
	 *            the plays to set
	 */
	public MediaFileAttribute setPlays(IntegerProperty plays) {
		this.plays = plays;
		return this;
	}
}
