package com.negativevr.media_library.gui;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import com.negativevr.media_library.Main;
import com.negativevr.media_library.files.MediaFile;
import com.negativevr.media_library.files.MediaFileAttribute;
import com.negativevr.media_library.storage.FilePathTreeItem;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ApplicationWindow extends Application{

	private final double WINDOW_MIN_WIDTH = 1200;
	private final double WINDOW_MIN_HEIGHT = 800;
	private TextField search;
	private TableView<MediaFile> dataTable;
	private MediaPlayer player;
	private Slider timeSlider, volumeSlider;
	private Duration duration;
	private Label time, artistLabel, songLabel;
	private Button fadeIn, fadeOut, play, reload, skip, next, previous;
	
	final Path rootPath = Paths.get("C:\\Music\\");
    final TreeItem<String> rootNode = new TreeItem<>(rootPath.toAbsolutePath().toString());
	private static final Duration FADE_DURATION = Duration.seconds(2.0);

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
		search = new TextField();
		
		//set main window size
		componentWindow.setMinHeight(WINDOW_MIN_HEIGHT);
		componentWindow.setMinWidth(WINDOW_MIN_WIDTH);
        
        //align dataTable
        tableDisplay.setRight(setupMediaDataTable());
        tableDisplay.setLeft(setupMediaFileBrowser());
        tableDisplay.setTop(setupMediaPlayer());;
        VBox.setMargin(tableDisplay, new Insets(10,10,10,10));
        componentLayout.getChildren().addAll(setupMenuBar(), tableDisplay);
        
        //add componentLayout to Window
		componentWindow.getChildren().addAll(componentLayout);
		
		//Create the scene and add the parent container to it
        Scene scene = new Scene(componentWindow, WINDOW_MIN_WIDTH, WINDOW_MIN_HEIGHT);
        
        //Add the Scene to the Stage
        rootStage.setScene(scene);
        rootStage.show();
	}
	
//private Media Player accessors / mutators
	private HBox setupMediaPlayer(){
		HBox mediaSlot = new HBox();
		VBox timeControls = new VBox();
		HBox timeBox = new HBox();
		HBox mediaControlBox = new HBox();
		HBox searchBox = new HBox();
		HBox fadeBox = new HBox(5);
		VBox volumeControls = new VBox(10);
		if(Main.getMasterDataAsList().size()!=0){
			Path path = Paths.get(Main.getMasterDataAsList().get(0).getFilePath());
			Media media = new Media(path.toFile().toURI().toString());
			player = new MediaPlayer(media);
		} else{
			player = new MediaPlayer(new Media(Paths.get("init.mp3").toFile().toURI().toString()));
		}
		
		//player.setAutoPlay(true);
		MediaView mediaView = new MediaView();
		mediaView.setMediaPlayer(player);
		Image PlayButtonImage = new Image("play.png");
		Image PauseButtonImage = new Image("pause.png");
		ImageView imageViewPlay = new ImageView(PlayButtonImage);
		ImageView imageViewPause = new ImageView(PauseButtonImage);
		
		play = new Button();
		play.setGraphic(imageViewPlay);
		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				updateValues();
				Status status = player.getStatus();
	
				if (status == Status.PAUSED
				|| status == Status.READY
				|| status == Status.UNKNOWN
				|| status == Status.STOPPED) {
					player.play();
					play.setGraphic(imageViewPause);
				} else {
					player.pause();
					play.setGraphic(imageViewPlay);
				}
			}
		});
		
		reload = new Button();
		reload.setGraphic(new ImageView(new Image("reload.png")));
		reload.setOnAction((ActionEvent e) -> {
			player.seek(player.getStartTime());
		});
		
		skip = new Button();
		skip.setGraphic((new ImageView(new Image("skip.png"))));
		
		previous = new Button();
		previous.setGraphic(new ImageView(new Image("previous.png")));
		
		next = new Button();
		next.setGraphic(new ImageView(new Image("next.png")));
		
		timeSlider = new Slider();
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		timeSlider.setMinSize(100, 50);
		
		timeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable ov) {
				if (timeSlider.isValueChanging()) {
					// multiply duration by percentage calculated by slider position
					Duration duration = player.getCurrentTime();
					if (duration != null) {
						player.seek(duration.multiply(timeSlider.getValue() / 100.0));
					}
					updateValues();
	
				}
			}
		});
		List<MediaFile> data = Main.getMasterDataAsList();
		if(data.size()!=0){
			artistLabel = new Label(data.get(0).getArtistName() + " - " +
									data.get(0).getAlbumName());
		
			songLabel = new Label(data.get(0).getSongName());
		
		} else{
			artistLabel = new Label();
			
			songLabel = new Label();
		}

		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
				updateValues();
			}
		});
		
		time = new Label();
		time.setTextFill(Color.BLACK);
		
		player.setOnReady(() -> {
			duration = player.getMedia().getDuration();
			updateValues();
		});
		
		//volume control slider
		volumeSlider = new Slider(0, 1, 0);
	    player.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
	    player.setVolume(0.5);

	    //fade in time line
	    final Timeline fadeInTimeline = new Timeline(
	      new KeyFrame(
	        FADE_DURATION,
	        new KeyValue(player.volumeProperty(), 1.0)
	      )
	    );
	    
	    //fade out timeline
	    final Timeline fadeOutTimeline = new Timeline(
	      new KeyFrame(
	        FADE_DURATION,
	        new KeyValue(player.volumeProperty(), 0.0)
	      )
	    );

	    //fade in button
	    fadeIn = new Button("Fade In");
	    fadeIn.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent t) {
	        fadeInTimeline.play();
	      }
	    });
	    fadeIn.setMaxWidth(Double.MAX_VALUE);
	    
	    //fade out button
	    fadeOut = new Button("Fade Out");
	    fadeOut.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent t) {
	        fadeOutTimeline.play();
	      }
	    });
	    fadeOut.setMaxWidth(Double.MAX_VALUE);
	    
	    //volume cotrol box
	    fadeBox.getChildren().addAll(fadeIn, fadeOut);
	    volumeControls.getChildren().setAll(new Label("Volume"), volumeSlider, fadeBox);
	    volumeControls.setAlignment(Pos.CENTER);

	    volumeControls.disableProperty().bind(
	      Bindings.or(
	        Bindings.equal(Timeline.Status.RUNNING, fadeInTimeline.statusProperty()),
	        Bindings.equal(Timeline.Status.RUNNING, fadeOutTimeline.statusProperty())
	      )
	    );
	    
	    timeControls.getChildren().addAll(songLabel, artistLabel, timeSlider);
	    timeControls.setAlignment(Pos.CENTER);
	    timeControls.setFillWidth(true);
	    timeControls.setMinWidth(300);
	    
	    timeBox.getChildren().addAll(time);
	    timeBox.setAlignment(Pos.CENTER);
	    
	    mediaControlBox.getChildren().addAll(previous,reload, play, skip, next);
	    mediaControlBox.setAlignment(Pos.CENTER);
	    
	    searchBox.getChildren().addAll(new Label("Search"), search);
	    searchBox.setAlignment(Pos.CENTER_RIGHT);
		
		mediaSlot.getChildren().addAll(mediaControlBox, timeBox, timeControls, volumeControls, mediaView, searchBox);
		mediaSlot.setSpacing(10);
		HBox.setHgrow(timeControls, Priority.ALWAYS);
		return mediaSlot;
	}
	
	private void updatePlayer(){
		timeSlider.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable ov) {
				if (timeSlider.isValueChanging()) {
					// multiply duration by percentage calculated by slider position
					Duration duration = player.getCurrentTime();
					if (duration != null) {
						player.seek(duration.multiply(timeSlider.getValue() / 100.0));
					}
					updateValues();
	
				}
			}
		});
		
		player.setOnReady(() -> {
			duration = player.getMedia().getDuration();
			updateValues();
		});
		
		player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> arg0, Duration arg1, Duration arg2) {
				updateValues();
			}
		});
		
		player.volumeProperty().bindBidirectional(volumeSlider.valueProperty());
		
		Image PlayButtonImage = new Image("play.png");
		Image PauseButtonImage = new Image("pause.png");
		ImageView imageViewPlay = new ImageView(PlayButtonImage);
		ImageView imageViewPause = new ImageView(PauseButtonImage);
		
		play.setGraphic(imageViewPause);
		play.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				updateValues();
				Status status = player.getStatus();
	
				if (status == Status.PAUSED
				|| status == Status.READY
				|| status == Status.UNKNOWN
				|| status == Status.STOPPED) {
					player.play();
					play.setGraphic(imageViewPause);
				} else {
					player.pause();
					play.setGraphic(imageViewPlay);
				}
			}
		});
		
		reload.setOnAction((ActionEvent e) -> {
			player.seek(player.getStartTime());
		});
		
		//fade in time line
	    final Timeline fadeInTimeline = new Timeline(
	      new KeyFrame(
	        FADE_DURATION,
	        new KeyValue(player.volumeProperty(), 1.0)
	      )
	    );
	    
	    //fade out timeline
	    final Timeline fadeOutTimeline = new Timeline(
	      new KeyFrame(
	        FADE_DURATION,
	        new KeyValue(player.volumeProperty(), 0.0)
	      )
	    );
		
		 //fade in button
	    fadeIn = new Button("Fade In");
	    fadeIn.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent t) {
	        fadeInTimeline.play();
	      }
	    });
	    fadeIn.setMaxWidth(Double.MAX_VALUE);
	    
	    //fade out button
	    fadeOut = new Button("Fade Out");
	    fadeOut.setOnAction(new EventHandler<ActionEvent>() {
	      @Override public void handle(ActionEvent t) {
	        fadeOutTimeline.play();
	      }
	    });
	    fadeOut.setMaxWidth(Double.MAX_VALUE);
	}
	
	private static String formatTime(Duration elapsed, Duration duration) {
		   int intElapsed = (int)Math.floor(elapsed.toSeconds());
		   int elapsedHours = intElapsed / (60 * 60);
		   if (elapsedHours > 0) {
		       intElapsed -= elapsedHours * 60 * 60;
		   }
		   int elapsedMinutes = intElapsed / 60;
		   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
		                           - elapsedMinutes * 60;
		 
		   if (duration.greaterThan(Duration.ZERO)) {
		      int intDuration = (int)Math.floor(duration.toSeconds());
		      int durationHours = intDuration / (60 * 60);
		      if (durationHours > 0) {
		         intDuration -= durationHours * 60 * 60;
		      }
		      int durationMinutes = intDuration / 60;
		      int durationSeconds = intDuration - durationHours * 60 * 60 - 
		          durationMinutes * 60;
		      if (durationHours > 0) {
		         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
		            elapsedHours, elapsedMinutes, elapsedSeconds,
		            durationHours, durationMinutes, durationSeconds);
		      } else {
		          return String.format("%02d:%02d/%02d:%02d",
		            elapsedMinutes, elapsedSeconds,durationMinutes, 
		                durationSeconds);
		      }
		      } else {
		          if (elapsedHours > 0) {
		             return String.format("%d:%02d:%02d", elapsedHours, 
		                    elapsedMinutes, elapsedSeconds);
		            } else {
		                return String.format("%02d:%02d",elapsedMinutes, 
		                    elapsedSeconds);
		            }
		        }
		    }
	
	private void updateValues() {
		if (time != null && timeSlider != null && duration != null) {
			Platform.runLater(new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					Duration currentTime = player.getCurrentTime();
					time.setText(formatTime(currentTime, duration));
					timeSlider.setDisable(duration.isUnknown());
					if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
						timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
					}
				}
			});
		}
	}
	
//private File Browser mutators / accessors
	private VBox setupMediaFileBrowser(){
		VBox treeBox = new VBox();
		treeBox.setMinHeight(650);
		treeBox.setMaxHeight(650);
	    treeBox.setPadding(new Insets(10,10,10,10));
	    treeBox.setSpacing(10);
	    
	    //setup the root directory for file browser
	    rootNode.setGraphic(new ImageView(new Image("remove.png")));
	    Iterable<Path> rootDirectories = getDirectories(rootPath);
	    
	    //populates the rootNode with Tree Items
	    for(Path name : rootDirectories){
	    	FilePathTreeItem treeNode = new FilePathTreeItem(name);
	    	rootNode.getChildren().add(treeNode);
	    	//treeNode.setExpanded(true);
	    } rootNode.setExpanded(true);
	    
	    //create the tree view
	    TreeView<String> treeView = new TreeView<>(rootNode);
	    treeView.setMinHeight(600);
	    
	    //add everything to the tree pane
	    treeBox.getChildren().addAll(new Label("File browser"), treeView);
	    
	    return treeBox;
	}
	
	@SuppressWarnings("unused")
	private void setupFileSystemWatcher(){
		Path myDir = Paths.get("C:\\Music");       

        try {
           WatchService watcher = myDir.getFileSystem().newWatchService();
           myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
           StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

           WatchKey watckKey = watcher.take();

           List<WatchEvent<?>> events = watckKey.pollEvents();
           for (WatchEvent<?> event : events) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("Created: " + event.context().toString());
                }
                if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("Delete: " + event.context().toString());
                }
                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("Modify: " + event.context().toString());
                }
            }
           
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
	}
	
	private void updateFileSystem(){ 
		System.out.println("Updating File System...");
		rootNode.getChildren().clear();
		Iterable<Path> rootDirectories = getDirectories(rootPath);
	    
	    for(Path name : rootDirectories){
	    	FilePathTreeItem treeNode = new FilePathTreeItem(name);
	    	rootNode.getChildren().add(treeNode);
	    	//treeNode.setExpanded(true);
	    } rootNode.setExpanded(true);
	}
	
	private List<Path> getDirectories(final Path dir) {
		final List<Path> dirlist = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (final Iterator<Path> it = stream.iterator(); it.hasNext();) {
				dirlist.add(dir.resolve(it.next()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} return dirlist;
	}
	
//private Data Table mutators / accessors
	private VBox setupMediaDataTable(){
		VBox container = new VBox();
		container.setMinHeight(650);
		dataTable = new TableView<MediaFile>();
		dataTable.setId("Data Table");
		dataTable.setMinHeight(650);
		dataTable.setMaxHeight(650);
		dataTable.setMinWidth(2*WINDOW_MIN_WIDTH/3);
		BorderPane.setMargin(dataTable, new Insets(10,10,10,10));
		
		//initialize the columns
		dataTable.getColumns().setAll(getDataTableColumns());
		
		//sort and filter the data
		SortedList<MediaFile> sortedData = getSortedData();
		
		// Bind the SortedList comparator to the TableView comparator
		sortedData.comparatorProperty().bind(dataTable.comparatorProperty());
		
		//set click event henadling
		setDataTableClickEvents();
		
		// Show the Data
		dataTable.setItems(sortedData);
		container.setMaxHeight(WINDOW_MIN_HEIGHT - 200);
		//VBox.setVgrow(dataTable, Priority.NEVER);
		container.getChildren().addAll(dataTable);
		return container;
	}
	
	private void setDataTableClickEvents(){
		dataTable.setRowFactory(tv -> {
			TableRow<MediaFile> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 1){
					System.out.println("Clicked");
					System.out.println(row.getItem());
				} else if(event.getClickCount() == 2){
					System.out.println("Playing: \n" + row.getItem());
					File mediaFile = new File(row.getItem().getFilePath());
					Media mediaToPlay = new Media(mediaFile.toURI().toString());
					player.stop();
					player = new MediaPlayer(mediaToPlay);
					player.setAutoPlay(true);
					updatePlayer();
				}
			});
			return row;
		});
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
		
		//set click events handling
		setDataTableClickEvents();
		
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
        menuBar.autosize();
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

//private add window mutators
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
                		&& !songPath.textProperty().getValue().equals("")
                		&& songPath.textProperty() != null){
                	MediaFile newFile = new MediaFile();
                	String number = (albumNumber.textProperty().getValue().equals("")) ? "0" : albumNumber.textProperty().getValue();
                	try{
                			newFile = new MediaFile(new MediaFileAttribute()
                					.setAlbum(albumName.textProperty())
                					.setName(songName.textProperty())
                					.setArtists(artistNames.textProperty())
                					.setGenre(genre.textProperty())
                					.setDateCreated(new Date().toString())
                					.setNumber(Integer.parseInt(number))
                					.setPath(songPath.textProperty().getValue())
                					.setPlays(0)
                					.setLength(AudioFileIO.read(new File(songPath.textProperty().getValue())).getAudioHeader().getTrackLength()),
                					Main.getNextUUID());
                			
                	} catch(IOException | NumberFormatException | CannotReadException | TagException | ReadOnlyFileException | InvalidAudioFrameException e){
                		e.printStackTrace();
                	}
                	System.out.println("Successfuly Added:");
                	System.out.println(newFile);
                	Main.getMasterData().put(newFile.getUUID(), newFile);
                	try {
						newFile.writeToDisk();
						Files.copy(Paths.get(songPath.textProperty().getValue()), Paths.get(newFile.getFileLocation() + songName.textProperty().getValue() + ".mp3") );
					} catch (IOException e) {
						e.printStackTrace();
					}
                	((Stage)((Button)t.getSource()).getScene().getWindow()).close();
                	updateDataTable();
                	updateFileSystem();
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
	                            path.appendText(file.getAbsolutePath().toString());
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
