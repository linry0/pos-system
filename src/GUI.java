import computer.*;
import computer.Menu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
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

//TODO add functionality to remove items from orders
//TODO add ability to save orders from main scene to hard disk
//TODO move Printer from computer package to root directory and rename CLI
//TODO formulate plan to integrate classes from computer package into GUI (Menu, Category, Item, Reader) (you can set userdata on a JavaFX node using the setUserData() method which takes any Object of your choosing)

//TODO implement back button feature like internet browser each scene is a webpage. stage.userdata will be a list of scene addresses and each scene.userdata will be the stage



public class GUI extends Application implements Constants {
	public static HashMap<String, SceneBuilder> sceneReferralDatabase;
	public static ToggleGroup toggleGroupSavedOrders;
	
	public static SceneBuilder sceneMain;
	public static SceneBuilder sceneOrder;
	
	static {
		sceneReferralDatabase = new HashMap<>();
		toggleGroupSavedOrders = new ToggleGroup();

		sceneMain = stage -> {
			Scene scene = new Scene(new Group());
			scene.setUserData("sceneMain");
				HBox hBox = new HBox(HBOX_SPACING);
					ScrollPane scrollPaneSavedOrders = new ScrollPane();
						VBox vBoxSavedOrders = new VBox(VBOX_SPACING);
						vBoxSavedOrders.setPrefSize(VBOX_LIST_WIDTH, VBOX_LIST_HEIGHT);
						vBoxSavedOrders.setUserData(toggleGroupSavedOrders);
						
						for (Toggle toggle : toggleGroupSavedOrders.getToggles()) {
							if (toggle instanceof ToggleButton toggleButton) {
								vBoxSavedOrders.getChildren().add(toggleButton);
							}
						}
					scrollPaneSavedOrders.setContent(vBoxSavedOrders);
					
					VBox vBoxStageControls = new VBox(VBOX_SPACING);
//						Button buttonNewOrder; //TODO
						Button buttonLoadOrder = buttonLoadScene(stage, "sceneOrder", "LOAD ORDER");
					vBoxStageControls.getChildren().addAll(buttonLoadOrder);
				hBox.getChildren().addAll(scrollPaneSavedOrders, vBoxStageControls);
			scene.setRoot(hBox);
			
			return scene;
		};
		
		sceneOrder = stage -> {
			ToggleGroup toggleGroupSavedOrders = GUI.toggleGroupSavedOrders;
			Order orderSelected = toggleGroupSavedOrders.getSelectedToggle() != null ? (Order) toggleGroupSavedOrders.getSelectedToggle().getUserData() : new Order();
			Menu menu = Reader.getMenu("src/menu.tsv"); //TODO maybe put this somewhere else
			
			Scene scene = new Scene(new Group());
			scene.setUserData("sceneOrder");
				VBox vBox = new VBox(VBOX_SPACING);
		            Label label = new Label("Welcome to the fortune-garden computer!");
		
		            HBox hBox = new HBox(HBOX_SPACING);
						VBox vBoxItems = new VBox(VBOX_SPACING);
						vBoxItems.setPrefSize(VBOX_LIST_WIDTH, VBOX_LIST_HEIGHT);
		                VBox vBoxOrders = new VBox(VBOX_SPACING);
						vBoxOrders.setPrefSize(VBOX_LIST_WIDTH, VBOX_LIST_HEIGHT);
							for (Item item : orderSelected.getItems()) {
									HBox hBoxOrder = new HBox(HBOX_SPACING);
										Label labelItemName = new Label(item.getName());
										Label labelItemPrice = new Label(item.getPrice().toString());
									hBoxOrder.getChildren().add(labelItemName);
									hBoxOrder.getChildren().add(labelItemPrice);
								vBoxOrders.getChildren().add(hBoxOrder);
							}
		
						VBox vBoxCustomerInformation = vBoxCustomerInformation();
			                ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(0)).getChildren().getLast()).setText(orderSelected.getName());
			                ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(1)).getChildren().getLast()).setText(orderSelected.getTelephone());
			                ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(2)).getChildren().getLast()).setText(orderSelected.getPostcode());
			                ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(3)).getChildren().getLast()).setText(orderSelected.getAddress1());
			                ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(4)).getChildren().getLast()).setText(orderSelected.getAddress2());
			                ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(5)).getChildren().getLast()).setText(orderSelected.getNotes());
		                ScrollPane scrollPaneCategories = scrollPaneCategories(menu, vBoxItems, vBoxOrders);
						ScrollPane scrollPaneItems = new ScrollPane(vBoxItems);
		                ScrollPane scrollPaneOrders = new ScrollPane(vBoxOrders);
					hBox.getChildren().addAll(vBoxCustomerInformation, scrollPaneCategories, scrollPaneItems, scrollPaneOrders);
		
					HBox hBoxSceneControls = new HBox(HBOX_SPACING);
						Button buttonSaveOrder = buttonSaveOrder(vBoxCustomerInformation, vBoxOrders);
						Button buttonClose = buttonLoadScene(stage, "sceneMain", "CLOSE");
					hBoxSceneControls.getChildren().addAll(buttonSaveOrder, buttonClose);
		
		        vBox.getChildren().addAll(label, hBox, hBoxSceneControls);
				
			scene.setRoot(vBox);
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
	
	public Button buttonReloadScene(Stage stage, String sceneReferralKey, String label) {
		Button button = new Button(label);
			EventHandler<ActionEvent> eventHandler = actionEvent -> {
				ArrayList<String> sceneHistory = (ArrayList<String>) stage.getUserData();
				
				Scene scene = sceneReferralDatabase.get(sceneHistory.getLast()).build(stage);
				stage.setScene(scene);
			};
		button.setOnAction(eventHandler);
		
		return button;
	}
	
	static public Button buttonSaveOrder(VBox vBoxCustomerInformation, VBox vBoxOrders) {
		Button button = new Button("SAVE ORDER");
			EventHandler<ActionEvent> eventHandler = actionEvent -> {
				ToggleGroup toggleGroupSavedOrders = GUI.toggleGroupSavedOrders;
				
					String name = ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(0)).getChildren().getLast()).getText();
					String telephone = ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(1)).getChildren().getLast()).getText();
					String postcode = ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(2)).getChildren().getLast()).getText();
					String address1 = ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(3)).getChildren().getLast()).getText();
					String address2 = ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(4)).getChildren().getLast()).getText();
					String notes = ((TextInputControl) ((HBox) vBoxCustomerInformation.getChildren().get(5)).getChildren().getLast()).getText();
					ArrayList<Item> items = new ArrayList<>();
						for (Node node : vBoxOrders.getChildren()) {
							if (node instanceof HBox hBox) {
										String itemName = ((Label) hBox.getChildren().getFirst()).getText();
										Integer itemPrice = Integer.parseInt(((Label) hBox.getChildren().getLast()).getText());
									Item item = new Item(itemName, itemPrice);
								items.add(item);
							}
						}
				Order order = new Order(name, telephone, postcode, address1, address2, notes, items);
				
				ToggleButton toggleButton = new ToggleButton(order.getZonedDateTime().toString());
				toggleButton.setToggleGroup(toggleGroupSavedOrders);
				toggleButton.setUserData(order); // may cause issues this overwrites the togglegroup selected might change inbetween loading the scene and saving the scene so...
			};
		button.setOnAction(eventHandler);
		
		return button;
	}
	
    static public ToggleButton buttonChooseCategory(Category category, Pane paneItems, Pane paneOrders) {
        ToggleButton toggleButton = new ToggleButton(category.getName());
            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
					ScrollPane scrollPaneItems = scrollPaneItems(category, paneOrders);
					
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
	
	static public Button buttonAddItem(Item item, Pane paneOrders) {
		Button button = new Button(item.getName());
			Label label = new Label(item.getName()); //deprecated in favour of adding a new label instance each button press since adding duplicate items to panes is not allowed
			EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
						HBox hBox = new HBox(HBOX_SPACING);
							
							Label label1 = new Label(item.getName());
							Label label2 = new Label(Integer.toString(item.getPrice()));
						hBox.getChildren().addAll(label1, label2);
					paneOrders.getChildren().add(hBox);
				}
			};
		button.setOnAction(eventHandler);

		return button;
	}
	
	static public VBox vBoxCustomerInformation() {
		VBox vBox = new VBox(VBOX_SPACING);
		vBox.setPrefSize(VBOX_FIELDS_WIDTH, VBOX_FIELDS_HEIGHT);
			String[] fields = {"Name", "Telephone", "Postcode", "House No.", "Address", "Notes"};
			for (String field : fields) {
					HBox hBox = new HBox(HBOX_SPACING);
						Label label = new Label(field);
						TextField textField = new TextField();
					hBox.getChildren().addAll(label, textField);
				vBox.getChildren().add(hBox);
			}

		return vBox;
	}
	
	static public ScrollPane scrollPaneCategories(Menu menu, Pane paneItems, Pane paneOrders) {
        ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox(10);
            vBox.setPrefSize(VBOX_LIST_WIDTH, VBOX_LIST_HEIGHT);
	            ToggleGroup toggleGroup = new ToggleGroup();
                ArrayList<ToggleButton> toggleButtonsChooseCategory = new ArrayList<>();
				
                for (Category category : menu.getCategories()) {
                    ToggleButton toggleButtonChooseCategory = buttonChooseCategory(category, paneItems, paneOrders);
					toggleButtonChooseCategory.setToggleGroup(toggleGroup);
                    toggleButtonsChooseCategory.add(toggleButtonChooseCategory);
                }
            vBox.getChildren().addAll(toggleButtonsChooseCategory);
        scrollPane.setContent(vBox);

        return scrollPane;
    }
	
	static public ScrollPane scrollPaneItems(Category category, Pane paneOrders) {
        ScrollPane scrollPane = new ScrollPane();
            VBox vBox = new VBox(10);
            vBox.setPrefSize(VBOX_LIST_WIDTH, VBOX_LIST_HEIGHT);
				ArrayList<Button> buttonsAddItem = new ArrayList<>();
				for (Item item : category.getItems()) {
					buttonsAddItem.add(buttonAddItem(item, paneOrders));
				}
            vBox.getChildren().addAll(buttonsAddItem);
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
        primaryStage.setFullScreen(DEFAULT_FULLSCREEN);
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
