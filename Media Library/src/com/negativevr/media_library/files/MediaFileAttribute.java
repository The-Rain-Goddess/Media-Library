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

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(StringProperty name) {
		this.name = name;
	}

	/**
	 * @param album
	 *            the album to set
	 */
	public void setAlbum(StringProperty album) {
		this.album = album;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(StringProperty dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @param dateRecorded
	 *            the dateRecorded to set
	 */
	public void setDateRecorded(StringProperty dateRecorded) {
		this.dateRecorded = dateRecorded;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(StringProperty path) {
		this.path = path;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(StringProperty genre) {
		this.genre = genre;
	}

	/**
	 * @param artists
	 *            the artists to set
	 */
	public void setArtists(List<StringProperty> artists) {
		this.artists = artists;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(IntegerProperty number) {
		this.number = number;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(DoubleProperty length) {
		this.length = length;
	}

	/**
	 * @param plays
	 *            the plays to set
	 */
	public void setPlays(IntegerProperty plays) {
		this.plays = plays;
	}
}
