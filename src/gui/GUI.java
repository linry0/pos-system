package gui;

import util.Reader;
import menu.Category;
import menu.Item;
import menu.Menu;
import menu.Order;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

// add to run configurations vm options (its hidden by default)
// --module-path /Users/linry/Documents/fortune-garden/javafx-sdk-22.0.1/lib --add-modules javafx.controls,javafx.fxml

//TODO add ability to save orders from main scene to hard disk
//TODO add a printer to print order

public class GUI extends Application implements Constants {
	public static HashMap<String, SceneBuilder> sceneReferralDatabase;
	public static SavedOrders savedOrders;

	public static SceneBuilder sceneMain;
	public static SceneBuilder sceneOrder;
	
	static {
		sceneReferralDatabase = new HashMap<>();
		savedOrders = new SavedOrders();

		sceneMain = stage -> {
			Scene scene = new Scene(new Group());
			scene.setUserData("sceneMain");
				HBox hBox = new HBox(Constants.HBOX_SPACING);
					ScrollPane scrollPaneSavedOrders = new ScrollPane();
					scrollPaneSavedOrders.setContent(savedOrders.getVBox());
					
					VBox vBoxStageControls = new VBox(Constants.VBOX_SPACING);
						Button buttonNewOrder = buttonNewScene(stage, "sceneOrder", "NEW ORDER"); //TODO
						Button buttonLoadOrder = buttonLoadScene(stage, "sceneOrder", "LOAD ORDER");
						Button buttonDeleteSelectedOrder = buttonDeleteSelectedOrder(stage, "DELETE SELECTED");
					vBoxStageControls.getChildren().addAll(buttonNewOrder, buttonLoadOrder, buttonDeleteSelectedOrder);
				hBox.getChildren().addAll(scrollPaneSavedOrders, vBoxStageControls);
			scene.setRoot(hBox);
			
			return scene;
		};
		
		sceneOrder = stage -> {
			Order orderSelected = savedOrders.getSelected();
			Menu menu = Reader.getMenu("menu.tsv"); //TODO maybe put this somewhere else
			
			Scene scene = new Scene(new Group());
			scene.setUserData("sceneOrder");
				HBox hBox = new HBox(Constants.HBOX_SPACING);
					VBox vBoxItems = new VBox(Constants.VBOX_SPACING);
					vBoxItems.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);

						OrderedItems orderedItems = new OrderedItems();
						orderedItems.addAll(orderSelected.getItems());
					VBox vBoxOrderedItems = orderedItems.getVBox();

						CustomerInformation customerInformation = new CustomerInformation();
						customerInformation.setName(orderSelected.getName());
						customerInformation.setTelephone(orderSelected.getTelephone());
						customerInformation.setPostcode(orderSelected.getPostcode());
						customerInformation.setAddress1(orderSelected.getAddress1());
						customerInformation.setAddress2(orderSelected.getAddress2());
						customerInformation.setNotes(orderSelected.getNotes());
					VBox vBoxCustomerInformation = customerInformation.getVBox();

					VBox vBoxSceneControls = new VBox(Constants.HBOX_SPACING);
						Button buttonRemoveItem = orderedItems.buttonItemRemove("REMOVE ITEM");
						Button buttonSaveOrder = buttonSaveOrder(customerInformation, orderedItems, "SAVE ORDER");
						Button buttonClose = buttonLoadScene(stage, "sceneMain", "CLOSE");
					vBoxSceneControls.getChildren().addAll(buttonRemoveItem, buttonSaveOrder, buttonClose);

					ScrollPane scrollPaneCategories = scrollPaneCategories(menu, vBoxItems, orderedItems);
					ScrollPane scrollPaneItems = new ScrollPane(vBoxItems);
					ScrollPane scrollPaneOrders = new ScrollPane(vBoxOrderedItems);
				hBox.getChildren().addAll(vBoxCustomerInformation, scrollPaneCategories, scrollPaneItems, scrollPaneOrders, vBoxSceneControls);
			scene.setRoot(hBox);
	        return scene;
	    };
		
		sceneReferralDatabase.put("sceneMain", sceneMain);
		sceneReferralDatabase.put("sceneOrder", sceneOrder);
	}
	
	static public Button buttonLoadScene(Stage stage, String sceneReferralKey, String label) {
		Button button = new Button(label);
			EventHandler<ActionEvent> eventHandler = actionEvent -> {
				ArrayList<String> sceneHistory = (ArrayList<String>) stage.getUserData(); // TODO maybe make new stage that always has scene history as property
				sceneHistory.add(sceneReferralKey);
				
				Scene scene = sceneReferralDatabase.get(sceneReferralKey).build(stage);
				stage.setScene(scene);
			};
		button.setOnAction(eventHandler);
		
		return button;
	}

	static public Button buttonNewScene(Stage stage, String sceneReferralKey, String label) {
		Button buttonLoadScene = buttonLoadScene(stage, sceneReferralKey, "LOAD");

		Button button = new Button(label);
		EventHandler<ActionEvent> eventHandler = actionEvent -> {
			savedOrders.resetSelected();
			buttonLoadScene.fire();
		};
		button.setOnAction(eventHandler);

		return button;
	}

	static public Button buttonReloadScene(Stage stage, String label) {
		Button button = new Button(label);
		EventHandler<ActionEvent> eventHandler = actionEvent -> {
			ArrayList<String> sceneHistory = (ArrayList<String>) stage.getUserData();

			Scene scene = sceneReferralDatabase.get(sceneHistory.getLast()).build(stage);
			stage.setScene(scene);
		};
		button.setOnAction(eventHandler);

		return button;
	}

	static public Button buttonDeleteSelectedOrder(Stage stage, String label) {
		Button buttonReloadScene = buttonReloadScene(stage, "RELOAD");

		Button button = new Button(label);
			EventHandler<ActionEvent> eventHandler = actionEvent -> {
				savedOrders.removeSelected();
				buttonReloadScene.fire();
			};
		button.setOnAction(eventHandler);

		return button;
	}

	static public Button buttonSaveOrder(CustomerInformation customerInformation, OrderedItems orderedItems, String label) {
		Button button = new Button(label);
			EventHandler<ActionEvent> eventHandler = actionEvent -> {
					String name = customerInformation.getName();
					String telephone = customerInformation.getTelephone();
					String postcode = customerInformation.getPostcode();
					String address1 = customerInformation.getAddress1();
					String address2 = customerInformation.getAddress2();
					String notes = customerInformation.getNotes();
					ArrayList<Item> items = orderedItems.getAll();
				Order order = new Order(name, telephone, postcode, address1, address2, notes, items);

				savedOrders.add(order);
			};
		button.setOnAction(eventHandler);

		return button;
	}
	
    static public ToggleButton buttonCategoryChoose(Category category, Pane paneItems, OrderedItems orderedItems) {
        ToggleButton toggleButton = new ToggleButton(category.getName());
            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
					ScrollPane scrollPaneItems = scrollPaneItems(category, orderedItems);
					
					if (toggleButton.isSelected()) {
						paneItems.getChildren().clear();
						paneItems.getChildren().addAll(((Pane) scrollPaneItems.getContent()).getChildren());
					} else {
						paneItems.getChildren().clear();
					}
                }
            };
        toggleButton.setOnAction(eventHandler);
        
		return toggleButton;
    }
	
	static public ScrollPane scrollPaneCategories(Menu menu, Pane paneItems, OrderedItems orderedItems) {
        ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox(10);
            vBox.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);
	            ToggleGroup toggleGroup = new ToggleGroup();
                ArrayList<ToggleButton> toggleButtonsChooseCategory = new ArrayList<>();
				
                for (Category category : menu.getCategories()) {
                    ToggleButton toggleButtonCategoryChoose = buttonCategoryChoose(category, paneItems, orderedItems);
					toggleButtonCategoryChoose.setToggleGroup(toggleGroup);
                    toggleButtonsChooseCategory.add(toggleButtonCategoryChoose);
                }
            vBox.getChildren().addAll(toggleButtonsChooseCategory);
        scrollPane.setContent(vBox);

        return scrollPane;
    }
	
	static public ScrollPane scrollPaneItems(Category category, OrderedItems orderedItems) {
        ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox(10);
            vBox.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);
				ArrayList<Button> buttonsItemAdd = new ArrayList<>();
				for (Item item : category.getItems()) {
					buttonsItemAdd.add(orderedItems.buttonItemAdd(item));
				}
            vBox.getChildren().addAll(buttonsItemAdd);
        scrollPane.setContent(vBox);

        return scrollPane;
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
        primaryStage.show();
//        primaryStage.setOnCloseRequest(event -> {
//            stageClose(primaryStage).showAndWait();
//        });
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
