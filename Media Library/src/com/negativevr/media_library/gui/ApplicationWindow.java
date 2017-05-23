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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		componentLayout.setPadding(new Insets(20,0,20,20));
		
        //The button uses an inner class to handle the button click event
        Button tmpButton = new Button("Button");
        tmpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //do something
            }
        });
        
        //Add the BorderPane to the Scene
        Scene scene = new Scene(componentLayout,500,500);
        
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
	}
	
	private void showRemoveWindow(){
		
	}
	

}
