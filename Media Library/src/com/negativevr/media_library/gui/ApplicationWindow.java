package com.negativevr.media_library.gui;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationWindow extends Application{

	public ApplicationWindow() {
		
	}
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage rootStage) throws Exception { 
		VBox componentLayout = new VBox();
		componentLayout.setPadding(new Insets(0,0,20,0));
		
        //The button uses an inner class to handle the button click event
        Button tmpButton = new Button("Button");
        tmpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //do something
            }
        });
        
        //Add the VBox to the Scene
        Scene scene = new Scene(componentLayout,600,600);
        
        //Adds MenuBar to all children Views
        ((VBox) scene.getRoot()).getChildren().addAll(setupMenuBar());
        
        //Add the Scene to the Stage
        rootStage.setScene(scene);
        rootStage.show();
	}
	
//private menu mutators / accessors	
	private MenuBar setupMenuBar(){
		MenuBar menuBar = new MenuBar();
        
        // --- Menu File
        Menu menuFile = getMenuFileOption();
        
        // --- Menu Edit
        Menu menuEdit = getMenuEditOption();
 
        // --- Menu View
        Menu menuView = getMenuViewOption();
 
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        return menuBar;
	}
	
	private Menu getMenuFileOption(){
		Menu menu = new Menu("File");
		MenuItem add = getMenuItem("Add", new MenuAction(){
			@Override
			public void execute() {
				showAddWindow();
			}
		});
		
		MenuItem remove = getMenuItem("Remove", new MenuAction(){
			@Override
			public void execute() {
				showRemoveWindow();
			}
		});
	    menu.getItems().addAll(add, remove);
	    return menu;
	}
	
	private MenuItem getMenuItem(String name, MenuAction action){
		MenuItem item = new MenuItem(name,
            new ImageView(new Image(name.toLowerCase()+".png")));
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
			public void handle(ActionEvent t) {
                action.execute();
            }
        });
        return item;
	}
	
	private Menu getMenuEditOption(){
		Menu menu = new Menu("Edit");
		return menu;
	}
	
	private Menu getMenuViewOption(){
		Menu menu = new Menu("View");
		return menu;
	}

//private button mutators
	private void showAddWindow(){
		System.out.println("HI");
		Stage addWindow = new Stage();
		addWindow.setTitle("ADD Media");
		GridPane componentLayout = getAddWindowLayout();
		
		componentLayout.getChildren().addAll(getWindowComponents(addWindow));
		
		Scene scene = new Scene(componentLayout,550,300);
		addWindow.setScene(scene);
		addWindow.show();
	}
	
	private GridPane getAddWindowLayout(){
		GridPane componentLayout = new GridPane();
		componentLayout.setPadding(new Insets(10, 10, 10, 10));
		componentLayout.setVgap(5);
		componentLayout.setHgap(5);
		return componentLayout;
	}
	
	private List<Control> getWindowComponents(Stage parent){
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Media Selection.");
		
		//Defining path text field
		final TextField songPath = new TextField();
		songPath.setPromptText("C:\\");
		songPath.setPrefColumnCount(30);
		songPath.setEditable(false);
		songPath.setFocusTraversable(false);
		GridPane.setConstraints(songPath, 1, 0);
		final Label songPathLabel = new Label("Path: ");
		GridPane.setConstraints(songPathLabel, 0, 0);
				
		//Defining the Name text field
		final TextField songName = new TextField();
		songName.setPromptText("Enter the song's name. *");
		songName.setPrefColumnCount(20);
		GridPane.setConstraints(songName, 1, 2);
		final Label songNameLabel = new Label("Name: ");
		GridPane.setConstraints(songNameLabel, 0, 2);

		//Defining the Last Name text field
		final TextField artistNames = new TextField();
		artistNames.setPromptText("Enter the artist name(s). *");
		GridPane.setConstraints(artistNames, 1, 3);
		final Label songArtistLabel = new Label("Artist(s): ");
		GridPane.setConstraints(songArtistLabel, 0, 3);
		
		//Defining the Comment text field
		final TextField albumName = new TextField();
		albumName.setPrefColumnCount(15);
		albumName.setPromptText("Enter the album name. *");
		GridPane.setConstraints(albumName, 1, 4);
		final Label songAlbumLabel = new Label("Album: ");
		GridPane.setConstraints(songAlbumLabel, 0, 4);
		
		//album number
		final TextField albumNumber = new TextField();
		albumNumber.setPrefColumnCount(20);
		albumNumber.setPromptText("Enter the song's album number.");
		GridPane.setConstraints(albumNumber, 1, 5);
		final Label songNumberLabel = new Label("Number: ");
		GridPane.setConstraints(songNumberLabel, 0, 5);
		
		//genre
		final TextField genre = new TextField();
		genre.setPrefColumnCount(20);
		genre.setPromptText("Enter the song's genre.");
		GridPane.setConstraints(genre, 1, 6);
		final Label songGenreLabel = new Label("Genre: ");
		GridPane.setConstraints(songGenreLabel, 0, 6);
		
		//Defining the Submit button
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 2, 3);
		
		//Defining the Clear button
		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 2, 4);
		
		//Open file button
		final Button openFile = new Button("Add media files...");
		openFile.setOnAction(
	            new EventHandler<ActionEvent>() {
	            	final TextField path = songPath;
	                @Override
	                public void handle(final ActionEvent e) {
	                    List<File> list =
	                        fileChooser.showOpenMultipleDialog(parent);
	                    if (list != null) {
	                        for (File file : list) {
	                            path.appendText(file.getAbsolutePath().toString() + "; ");
	                        }
	                    }
	                }
	            });
		GridPane.setConstraints(openFile, 2, 0);
		
		return Arrays.asList(	songPathLabel, songNameLabel, songArtistLabel, songAlbumLabel, songNumberLabel, songGenreLabel,
								songPath, songName, artistNames, albumName, albumNumber, genre,
								openFile, submit, clear);
	}
	
	private void showRemoveWindow(){
		
	}
	

}
