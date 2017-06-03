package com.negativevr.media_library.storage;

//import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Ryan May, Vaneh Boghosian
 *
 */

public class FilePathTreeItem extends TreeItem<String> {
	public static Image folderCollapseImage = new Image(ClassLoader.getSystemResourceAsStream("com/negativevr/media_library/res/folder.png"));
	public static Image folderExpandImage = new Image(ClassLoader.getSystemResourceAsStream("com/negativevr/media_library/res/open folder.png"));
	public static Image fileImage = new Image(ClassLoader.getSystemResourceAsStream("com/negativevr/media_library/res/file.png"));
	public static Image musicFileImage = new Image(ClassLoader.getSystemResourceAsStream("com/negativevr/media_library/res/music_file.png"));
	
	// this stores the full path to the file or directory
	private String fullPath;

	public String getFullPath() {
		return (this.fullPath);
	}

	private boolean isDirectory;

	public boolean isDirectory() {
		return (this.isDirectory);
	}

	public FilePathTreeItem(Path file) {
		super(file.toString());
		this.fullPath = file.toString();

		// test if this is a directory and set the icon
		if (Files.isDirectory(file)) {
			this.isDirectory = true;
			this.setGraphic(new ImageView(folderCollapseImage));
		} else if(this.fullPath.contains(".data")) {
			this.isDirectory = false;
			this.setGraphic(new ImageView(fileImage));
		} else{
			this.isDirectory = false;
			this.setGraphic(new ImageView(musicFileImage));
		}

		// set the value
		if (!fullPath.endsWith(File.separator)) {
			// set the value (which is what is displayed in the tree)
			String value = file.toString();
			int indexOf = value.lastIndexOf(File.separator);
			if (indexOf > 0) {
				this.setValue(value.substring(indexOf + 1));
			} else {
				this.setValue(value);
			}
		}
		/**
		 * 
		 */
		this.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<String>>() {
			@Override
			public void handle(TreeModificationEvent<String> e) {
				FilePathTreeItem source = (FilePathTreeItem) e.getSource();
				if (source.isDirectory() && source.isExpanded()) {
					ImageView iv = (ImageView) source.getGraphic();
					iv.setImage(folderExpandImage);
				} try {
					if (source.getChildren().isEmpty()) {
						//System.out.println(file.toAbsolutePath() + " isEmpty");
						Path path = Paths.get(source.getFullPath());
						BasicFileAttributes attribs = Files.readAttributes(path, BasicFileAttributes.class);
						if (attribs.isDirectory()) {
							DirectoryStream<Path> dir = Files.newDirectoryStream(path);
							for (Path file : dir) {
								FilePathTreeItem treeNode = new FilePathTreeItem(file);
								source.getChildren().add(treeNode);
							}
						}
					} else if(!source.getChildren().contains(this) && !isDirectory){
						//System.out.println(file.toAbsolutePath() +" isNotEmpty");
						
					}
				} catch (IOException x) {
					x.printStackTrace();
				}
			}
		});

		this.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<String>>() {
			@Override
			public void handle(TreeModificationEvent<String> e) {
				FilePathTreeItem source = (FilePathTreeItem) e.getSource();
				if (source.isDirectory() && !source.isExpanded()) {
					ImageView iv = (ImageView) source.getGraphic();
					iv.setImage(folderCollapseImage);
				}
			}
		});
	}
	
	public void addChangeListener(){
		
	}
	
	@Override
	public boolean isLeaf(){
		if(isDirectory)
			return false;
		else return true;
	}
}
