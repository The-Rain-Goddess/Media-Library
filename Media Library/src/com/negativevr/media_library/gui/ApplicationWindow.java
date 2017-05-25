package com.negativevr.media_library.gui;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.negativevr.media_library.Main;
import com.negativevr.media_library.files.MediaFile;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ApplicationWindow extends Application{

	private final double WINDOW_MIN_WIDTH = 600;
	private final double WINDOW_MIN_HEIGHT = 600;
	
	public ApplicationWindow() {
		
	}
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage rootStage) throws Exception { 
		AnchorPane componentWindow = new AnchorPane();
		VBox componentLayout = new VBox();
		BorderPane tableDisplay = new BorderPane();
		GridPane searchBox = new GridPane();
		searchBox.setAlignment(Pos.TOP_RIGHT);
		final TextField dataSearch = new TextField();
		GridPane.setConstraints(dataSearch, 0,1);
		GridPane.setMargin(dataSearch, new Insets(5,5,5,5));
		Label dataSearchLabel = new Label("Search: ");
		GridPane.setConstraints(dataSearchLabel, 0,0);
		
		componentWindow.setMinHeight(WINDOW_MIN_HEIGHT);
		componentWindow.setMinWidth(WINDOW_MIN_WIDTH);
		     
        //Add the VBox to the Scene
        Scene scene = new Scene(componentWindow,WINDOW_MIN_WIDTH,WINDOW_MIN_HEIGHT);
        searchBox.getChildren().addAll(dataSearchLabel, dataSearch);
        
        BorderPane.setAlignment(searchBox, Pos.TOP_RIGHT);
        tableDisplay.setTop(searchBox);
        
        tableDisplay.setRight(setupMediaDataTable(dataSearch));
        componentLayout.getChildren().addAll(setupMenuBar(), tableDisplay);
		componentWindow.getChildren().addAll(componentLayout);
        //Adds MenuBar to all children Views
        //((AnchorPane) scene.getRoot()).getChildren().addAll(setupMenuBar());
        
        //Add the Scene to the Stage
        rootStage.setScene(scene);
        rootStage.show();
	}
	
//private Data Table mutators / accessors
	private TableView<MediaFile> setupMediaDataTable(final TextField search){
		TableView<MediaFile> dataTable = new TableView<MediaFile>();
		dataTable.setMinHeight(WINDOW_MIN_HEIGHT);
		List<MediaFile> data = Main.getMasterDataAsList();
		
		
		//initialize the columns
		dataTable.getColumns().setAll(getDataTableColumns());
		
		//wrap the ObservableList in a FilteredList
		FilteredList<MediaFile> filteredData = new FilteredList<MediaFile>(FXCollections.observableList(data), p -> true);
		
		//set the filter Predicate whenever the filter changes
		search.textProperty().addListener((observable, oldValue, newValue)-> {
			filteredData.setPredicate(media -> {
				//If filter is empty, display all
				if(newValue == null || newValue.isEmpty()){
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase();
				if(media.getSongName().toLowerCase().contains(lowerCaseFilter))
					return true;
				else if(media.getArtistName().toString().toLowerCase().contains(lowerCaseFilter))
					return true;
				else if(media.getAlbumName().toLowerCase().contains(lowerCaseFilter))
					return true;
				else if(media.getGenre().toLowerCase().contains(lowerCaseFilter))
					return true;
				else return false;
			});
		});
		
		//wrap the filtered list in a sorted list
		SortedList<MediaFile> sortedData = new SortedList<MediaFile>(filteredData);
		
		// Bind the SortedList comparator to the TableView comparator
		sortedData.comparatorProperty().bind(dataTable.comparatorProperty());
		
		// Show the Data
		dataTable.setItems(sortedData);
		
		return dataTable;
	}
	
	private List<TableColumn<MediaFile, ?>> getDataTableColumns(){
		//Column 1: Name
		TableColumn<MediaFile, String> mediaNameCol = new TableColumn<MediaFile, String>("Name");
		mediaNameCol.setCellValueFactory(cellData -> cellData.getValue().getSongNameProperty());
		
		//Column 2: Artist
		TableColumn<MediaFile, String> mediaArtistCol = new TableColumn<MediaFile, String>("Artist(s)");
		mediaArtistCol.setCellValueFactory(cellData -> cellData.getValue().getArtistNameProperty());
		
		//Column 3: Album
		TableColumn<MediaFile, String> mediaAlbumCol = new TableColumn<MediaFile, String>("Album");
		mediaAlbumCol.setCellValueFactory(cellData -> cellData.getValue().getAlbumNameProperty());
		
		//Column 4: Album Number
		TableColumn<MediaFile, Number> mediaNumberCol = new TableColumn<MediaFile, Number>("Number");
		mediaNumberCol.setCellValueFactory(cellData -> cellData.getValue().getAlbumNumberProperty());
		
		//Column 5: Genre
		TableColumn<MediaFile, String> mediaGenreCol = new TableColumn<MediaFile, String>("Genre");
		mediaGenreCol.setCellValueFactory(cellData -> cellData.getValue().getGenreProperty());
		
		//Column 6: Song Length
		TableColumn<MediaFile, Number> mediaLengthCol = new TableColumn<MediaFile, Number>("Length");
		mediaLengthCol.setCellValueFactory(cellData -> cellData.getValue().getSongLengthProperty());
		return Arrays.asList(mediaNameCol, mediaArtistCol, mediaAlbumCol, mediaNumberCol,mediaGenreCol, mediaLengthCol);		
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
        menuBar.setMinWidth(WINDOW_MIN_WIDTH);
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
