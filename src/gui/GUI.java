package gui;

import element.ContainerMenu;
import element.CustomerInformation;
import element.ContainerEditableItems;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.stage.WindowEvent;
import util.Reader;
import menu.Menu;
import menu.Order;
import element.ContainerEditableOrders;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

// add to run configurations vm options (its hidden by default)
// --module-path /Users/linry/Documents/fortune-garden/javafx-sdk-22.0.1/lib --add-modules javafx.controls,javafx.fxml

//TODO add button to exit (pressing mac close does not shutdown programme (compare with previous version cause that didn't have shutdown problem))
//TODO add a printer to print order
//TODO add ability to save orders from main scene to hard disk

//TODO make Editable interface and rename ContainerEditableX to ContainerXEditable
//TODO make itemsOrdered not toggle when loaded from disk
//TODO should some public be protected?
//TODO organise constants

public class GUI extends Application implements Constants {
	public static HashMap<String, SceneBuilder> sceneReferralDatabase;
	public static ContainerEditableOrders containerEditableOrders;

	public static SceneBuilder sceneMain;
	public static SceneBuilder sceneOrder;
	
	static {
		sceneReferralDatabase = new HashMap<>();
		containerEditableOrders = new ContainerEditableOrders();

		sceneMain = stage -> {
			Scene scene = new Scene(new Group());
			scene.setUserData("sceneMain");
				HBox hBox = new HBox(Constants.HBOX_SPACING);
					ScrollPane scrollPaneSavedOrders = new ScrollPane();
					scrollPaneSavedOrders.setContent(containerEditableOrders.getContainer());
					
					VBox vBoxStageControls = new VBox(Constants.VBOX_SPACING);
						Button buttonNewOrder = buttonNewOrder(stage, "sceneOrder", "NEW ORDER"); //TODO
						Button buttonLoadOrder = buttonLoadScene(stage, "sceneOrder", "LOAD ORDER");
						Button buttonDeleteSelectedOrder = containerEditableOrders.buttonRemove("DELETE ORDER");
					vBoxStageControls.getChildren().addAll(buttonNewOrder, buttonLoadOrder, buttonDeleteSelectedOrder);
				hBox.getChildren().addAll(scrollPaneSavedOrders, vBoxStageControls);
			scene.setRoot(hBox);
			
			return scene;
		};
		
		sceneOrder = stage -> {
			Menu menu = Reader.getMenu("menu.tsv"); //TODO maybe put this somewhere else
			Order orderSelected = containerEditableOrders.getSelectedItem();
			Order orderWorking = orderSelected.clone(); // todo maybe rethink the placement of this initilisation
			
			Scene scene = new Scene(new Group());
			scene.setUserData("sceneOrder");
				HBox hBox = new HBox(Constants.HBOX_SPACING);
					ContainerEditableItems containerEditableItems = new ContainerEditableItems(orderWorking);
					ContainerMenu containerMenu = new ContainerMenu(menu, containerEditableItems);
					CustomerInformation customerInformation = new CustomerInformation(orderWorking);
			
					Pane vBoxCustomerInformation = customerInformation.getContainer();
					VBox vBoxCategories = (VBox) containerMenu.getContainer().getChildren().getFirst();
					VBox vBoxItems = (VBox) containerMenu.getContainer().getChildren().getLast();
					VBox vBoxItemsOrdered = (VBox) containerEditableItems.getContainer();
					
					VBox vBoxSceneControls = new VBox(Constants.HBOX_SPACING);
						Button buttonRemoveItem = containerEditableItems.buttonRemove("REMOVE ITEM");
						Button buttonSaveOrder = containerEditableOrders.buttonAdd(orderWorking, "SAVE ORDER");
						Button buttonClose = buttonLoadScene(stage, "sceneMain", "CLOSE");
					vBoxSceneControls.getChildren().addAll(buttonRemoveItem, buttonSaveOrder, buttonClose);
			
				hBox.getChildren().addAll(vBoxCustomerInformation, new ScrollPane(vBoxCategories), new ScrollPane(vBoxItems), new ScrollPane(vBoxItemsOrdered), vBoxSceneControls);
			scene.setRoot(hBox);
	        return scene;
	    };
		
		sceneReferralDatabase.put("sceneMain", sceneMain);
		sceneReferralDatabase.put("sceneOrder", sceneOrder);
	}
	
	static public void setScene(Stage stage, Scene scene) {
		Parent root = scene.getRoot();
		scene.setRoot(new Group());
		stage.getScene().setRoot(root);
	}
	
	static public Button buttonLoadScene(Stage stage, String sceneReferralKey, String label) {
		Button button = new Button(label);
			EventHandler<ActionEvent> eventHandler = actionEvent -> {
				ArrayList<String> sceneHistory = (ArrayList<String>) stage.getUserData(); // TODO maybe make (define) new stage that always has scene history as property
				sceneHistory.add(sceneReferralKey);
				
				Scene scene = sceneReferralDatabase.get(sceneReferralKey).build(stage);
				setScene(stage, scene);
			};
		button.setOnAction(eventHandler);
		
		return button;
	}

	static public Button buttonReloadScene(Stage stage, String label) {
		Button button = new Button(label);
		EventHandler<ActionEvent> eventHandler = actionEvent -> {
			ArrayList<String> sceneHistory = (ArrayList<String>) stage.getUserData();

			Scene scene = sceneReferralDatabase.get(sceneHistory.getLast()).build(stage);
			setScene(stage, scene);
		};
		button.setOnAction(eventHandler);

		return button;
	}
	
	static public Button buttonNewOrder(Stage stage, String sceneReferralKey, String label) {
		Button buttonLoadScene = buttonLoadScene(stage, sceneReferralKey, "LOAD");
		
		Button button = new Button(label);
		EventHandler<ActionEvent> eventHandler = actionEvent -> {
			containerEditableOrders.resetSelectedItem();
			buttonLoadScene.fire();
		};
		button.setOnAction(eventHandler);
		
		return button;
	}
	
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.setUserData(new ArrayList<String>()); // this contains keys for the scene referral database
        primaryStage.setTitle("screen0: fullscreen");
        primaryStage.setScene(sceneMain.build(primaryStage));
        primaryStage.setFullScreen(Constants.DEFAULT_FULLSCREEN);
		primaryStage.setMinWidth(1200);
        primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
    }
	
	//TODO recreate cookies for each scene so each scene's required prerequisite data is stored not in the link (in the case the data is stored with the user)
	//the data could also be stored with the webhost but everything is run locally here so just cookies is fine
	
//	public Button buttonExit(Stage stage) {
//		Button button = new Button("EXIT");
//		EventHandler<ActionEvent> eventHandler = actionEvent -> {
//			WindowEvent windowEvent = new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST);
//			stage.fireEvent(windowEvent);
//		};
//		button.setOnAction(eventHandler);
//
//		return button;
//	}
}

//stage.initModality(Modality.APPLICATION_MODAL);
//stage.initModality(Modality.WINDOW_MODAL);
//stage.initModality(Modality.NONE);
//stage.initStyle(StageStyle.DECORATED);
//stage.initStyle(StageStyle.UNDECORATED);
//stage.initStyle(StageStyle.TRANSPARENT);
//stage.initStyle(StageStyle.UNIFIED);
//stage.initStyle(StageStyle.UTILITY);
//stage.setOnCloseRequest(function);
//stage.setOnHiding(function);
//stage.setOnHidden(function);
//stage.setOnShown(function);
//stage.addEventHandler(KeyEvent.KEY_PRESSED, function); //read jenkov documentation for more
//scene.setCursor(Cursor.OPEN_HAND);
//scene.setCursor(Cursor.CLOSED_HAND);
//scene.setCursor(Cursor.CROSSHAIR);
//scene.setCursor(Cursor.DEFAULT);
//scene.setCursor(Cursor.HAND);
//scene.setCursor(Cursor.WAIT);
//scene.setCursor(Cursor.H_RESIZE);
//scene.setCursor(Cursor.V_RESIZE);
//scene.setCursor(Cursor.MOVE);
//scene.setCursor(Cursor.TEXT);

//node.setLayoutX(50); node.setLayoutY(50);
//node.setPrefWidth(400); node.setPrefHeight(200);
//node.setMinWidth(200); node.setMinHeight(100);
//node.setMaxWidth(600); node.setMaxHeight(300);
//node.setUserData(new MyObject("some data"));

//lock JavaFX Property like width to some ratio of height of pane
