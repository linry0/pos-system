package element;

import gui.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import menu.Category;
import menu.Item;
import menu.Menu;

import java.util.List;

public class ContainerMenu extends Container<HBox> {
	protected final VBox containerCategories;
	protected final VBox containerItems;
	protected final ToggleGroup toggleGroupCategories;
	protected final ContainerEditableItems containerEditableItemsWorking;
	
	public ContainerMenu(Menu menu, ContainerEditableItems containerEditableItems) {
		super(new HBox());
		container.setSpacing(Constants.HBOX_SPACING);
		toggleGroupCategories = new ToggleGroup();
		containerEditableItemsWorking = containerEditableItems;
		
			containerCategories = new VBox();
			containerCategories.setSpacing(Constants.VBOX_SPACING);
			containerCategories.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);
				List<Category> categories = List.of(menu.getCategories());
				for (Category category : categories) {
						ToggleButton toggleButton = buttonCategoryLoad(category);
						toggleButton.setToggleGroup(toggleGroupCategories);
					containerCategories.getChildren().add(toggleButton);
				}
			containerItems = new VBox();
			containerItems.setSpacing(Constants.VBOX_SPACING);
			containerItems.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);
		container.getChildren().addAll(containerCategories, containerItems);
	}
	
	protected ToggleButton buttonCategoryLoad(Category category) { // TODO togglegroup
		List<Item> items = List.of(category.getItems()); // TODO maybe make category.getItems() return List<Item> instead of Item[]
		
		ToggleButton toggleButton = new ToggleButton();
		toggleButton.setText(category.getName());
			EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					if (toggleButton.isSelected()) {
						itemsClear();
						itemsLoad(items);
					} else {
						itemsClear();
					}
					
				}
			};
		toggleButton.setOnAction(eventHandler);
		
		return toggleButton;
	}
	
	protected Button buttonItemOrder(Item item) {
		Button button = new Button();
		button.setText(item.getName());
			EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					containerEditableItemsWorking.addItem(item);
				}
			};
		button.setOnAction(eventHandler);
		
		return button;
	}
	
	protected void itemsLoad(List<Item> items) {
		for (Item item : items) {
			Button button = buttonItemOrder(item);
			containerItems.getChildren().add(button);
		}
	}
	
	protected void itemsClear() {
		containerItems.getChildren().clear();
	}
}
