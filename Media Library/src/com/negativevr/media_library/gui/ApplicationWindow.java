package com.negativevr.media_library.gui;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.negativevr.media_library.Main;
import com.negativevr.media_library.files.MediaFile;
import com.negativevr.media_library.files.MediaFileAttribute;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private final double WINDOW_MIN_WIDTH = 900;
	private final double WINDOW_MIN_HEIGHT = 800;
	private TextField search;
	private TableView<MediaFile> dataTable;

//constructor	
	public ApplicationWindow() {
		
	}
	
//class start point	
	public void begin(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage rootStage) throws Exception { 
		AnchorPane componentWindow = new AnchorPane();
		VBox componentLayout = new VBox();
		BorderPane tableDisplay = new BorderPane();
		
		//sets up searchbox
		GridPane searchBox = new GridPane();
		searchBox.setAlignment(Pos.TOP_RIGHT);
		search = new TextField();
		GridPane.setConstraints(search, 0,1);
		GridPane.setMargin(search, new Insets(5,5,5,5));
		Label dataSearchLabel = new Label("Search: ");
		GridPane.setConstraints(dataSearchLabel, 0,0);
		
		//set main window size
		componentWindow.setMinHeight(WINDOW_MIN_HEIGHT);
		componentWindow.setMinWidth(WINDOW_MIN_WIDTH);
		     
        //Create the scene and add the parent container to it
        Scene scene = new Scene(componentWindow, WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT);
        
        //align Search box
        searchBox.getChildren().addAll(dataSearchLabel, search);
        BorderPane.setAlignment(searchBox, Pos.TOP_RIGHT);
        tableDisplay.setTop(searchBox);
        
        //align dataTable
        tableDisplay.setRight(setupMediaDataTable());
        componentLayout.getChildren().addAll(setupMenuBar(), tableDisplay);
        
        //add componentLayout to Window
		componentWindow.getChildren().addAll(componentLayout);
        
        //Add the Scene to the Stage
        rootStage.setScene(scene);
        rootStage.show();
	}
	
//private Data Table mutators / accessors
	private TableView<MediaFile> setupMediaDataTable(){
		dataTable = new TableView<MediaFile>();
		dataTable.setId("Data Table");
		dataTable.setMinHeight(WINDOW_MIN_HEIGHT);
		dataTable.setMinWidth(2*WINDOW_MIN_WIDTH/3);
		
		//initialize the columns
		dataTable.getColumns().setAll(getDataTableColumns());
		
		//sort and filter the data
		SortedList<MediaFile> sortedData = getSortedData();
		
		// Bind the SortedList comparator to the TableView comparator
		sortedData.comparatorProperty().bind(dataTable.comparatorProperty());
		
		// Show the Data
		dataTable.setItems(sortedData);
		
		return dataTable;
	}
	
	private SortedList<MediaFile> getSortedData(){
		//get the data
		List<MediaFile> data = Main.getMasterDataAsList();
				
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
		
		return sortedData;
	}
	
	private void updateDataTable(){
		//sort and filter the data
		SortedList<MediaFile> sortedData = getSortedData();
		
		// Bind the SortedList comparator to the TableView comparator
		sortedData.comparatorProperty().bind(dataTable.comparatorProperty());
		
		// Show the Data
		dataTable.setItems(sortedData);
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
		//create new Stage
		Stage addWindow = new Stage();
		addWindow.setTitle("ADD Media");
		GridPane componentLayout = getAddWindowLayout();
		
		//add all window components to componentLayout
		componentLayout.getChildren().addAll(getWindowComponents(addWindow));
		
		//create Scene
		Scene scene = new Scene(componentLayout,550,300);
		
		//set Screen to show Scene
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
		songPath.setPromptText("C:\\... *");
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
		albumNumber.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	                albumNumber.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	    });
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
		
		//required label
		final Label requiredLabel = new Label("* Required");
		GridPane.setConstraints(requiredLabel, 1,7);
		
		//Defining the Submit button
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 2, 3);
		submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
			public void handle(ActionEvent t) {
                if(songName.textProperty()!=null 
                		&& albumName.textProperty() != null 
                		&& artistNames.textProperty()!=null
                		&& !songName.textProperty().getValue().equals("")
                		&& !albumName.textProperty().getValue().equals("")
                		&& !artistNames.textProperty().getValue().equals("")
                		/* && !songPath.textProperty().getValue().equals("") */){
                	MediaFile newFile = new MediaFile(new MediaFileAttribute()
                					.setAlbum(albumName.textProperty())
                					.setName(songName.textProperty())
                					.setArtistStrings(Arrays.asList(artistNames.textProperty().getValue().split("; ")))
                					.setGenre(genre.textProperty())
                					.setDateCreated(new Date().toString())
                					.setNumber(Integer.parseInt(albumNumber.textProperty().getValue().replace("", "0")))
                					.setPlays(0)
                					.setLength(0.0), //TODO: figure out how to get length from File Path
                					Main.getNextUUID());
                	System.out.println("Success!");
                	Main.getMasterData().put(newFile.getUUID(), newFile);
                	System.out.println(Main.getMasterDataAsList());
                	((Stage)((Button)t.getSource()).getScene().getWindow()).close();
                	updateDataTable();
                }
            }
        });
		
		//Defining the Clear button
		Button clear = new Button("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
			public void handle(ActionEvent t) {
            	List<TextField> fields = Arrays.asList(songPath, songName, artistNames, albumName, albumNumber, genre);
            	for(TextField s : fields)
            		s.textProperty().set("");
            }
        });
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
		
		return Arrays.asList(	songPathLabel, songNameLabel, songArtistLabel, songAlbumLabel, songNumberLabel, songGenreLabel, requiredLabel,
								songPath, songName, artistNames, albumName, albumNumber, genre,
								openFile, submit, clear);
	}
	
	private void showRemoveWindow(){
		
	}
	

}
