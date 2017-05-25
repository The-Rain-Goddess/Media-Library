package com.negativevr.media_library.storage;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.shape.Path;

public class FilePathTreeItem extends TreeItem<String> {
	public static Image folderCollapseImage=new Image(ClassLoader.getSystemResourceAsStream("com/huguesjohnson/javafxfilebrowsedemo/folder.png"));
    public static Image folderExpandImage=new Image(ClassLoader.getSystemResourceAsStream("com/huguesjohnson/javafxfilebrowsedemo/folder-open.png"));
    public static Image fileImage=new Image(ClassLoader.getSystemResourceAsStream("com/huguesjohnson/javafxfilebrowsedemo/text-x-generic.png"));
  
    //this stores the full path to the file or directory
    private String fullPath;
    public String getFullPath(){return(this.fullPath);}
  
    private boolean isDirectory;
    public boolean isDirectory(){return(this.isDirectory);}
    
    public FilePathTreeItem(Path file){
    	super(file.toString());
    	this.fullPath=file.toString();
    }    
}
