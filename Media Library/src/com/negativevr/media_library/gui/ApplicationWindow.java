package com.negativevr.media_library.gui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        Scene scene = new Scene(componentLayout,500,500);
        
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
		GridPane componentLayout = new GridPane();
		componentLayout.setPadding(new Insets(10, 10, 10, 10));
		componentLayout.setVgap(5);
		componentLayout.setHgap(5);
		
		//Defining the Name text field
		final TextField name = new TextField();
		name.setPromptText("Enter your first name.");
		name.setPrefColumnCount(10);
		name.getText();
		GridPane.setConstraints(name, 0, 0);
		componentLayout.getChildren().add(name);
	
		//Defining the Last Name text field
		final TextField lastName = new TextField();
		lastName.setPromptText("Enter your last name.");
		GridPane.setConstraints(lastName, 0, 1);
		componentLayout.getChildren().add(lastName);
		//Defining the Comment text field
		final TextField comment = new TextField();
		comment.setPrefColumnCount(15);
		comment.setPromptText("Enter your comment.");
		GridPane.setConstraints(comment, 0, 2);
		componentLayout.getChildren().add(comment);
		//Defining the Submit button
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 1, 0);
		componentLayout.getChildren().add(submit);
		//Defining the Clear button
		Button clear = new Button("Clear");
		GridPane.setConstraints(clear, 1, 1);
		componentLayout.getChildren().add(clear);
		Scene scene = new Scene(componentLayout,400,400);
		addWindow.setScene(scene);
		addWindow.show();
	}
	
	private void showRemoveWindow(){
		
	}
	

}
