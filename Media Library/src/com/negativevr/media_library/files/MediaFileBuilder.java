package com.negativevr.media_library.files;

public class MediaFileBuilder {
	private MediaFile file;
	
	public MediaFileBuilder(){
		setFile(new MediaFile());
	}

	/**
	 * @return the file
	 */
	public MediaFile getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(MediaFile file) {
		this.file = file;
	}
}
