package element;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import gui.Constants;

import java.util.ArrayList;
import java.util.List;


public class EditableList<T> {
	protected VBox container; //TODO generalise this maybe have type be `? extends Box` or something
	protected ToggleGroup toggleGroup;
	
	public EditableList() {
		this.container = new VBox();
		this.container.setSpacing(Constants.VBOX_SPACING);
		this.container.setPrefSize(Constants.VBOX_LIST_WIDTH, Constants.VBOX_LIST_HEIGHT);

		this.toggleGroup = new ToggleGroup();
	}
	
	public Pane getContainer() {
		return container;
	}
	
	public T getSelectedItem() {
		RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
		
		T item = (T) radioButton.getUserData();
		
		return item;
	}
	
	public List<T> getItems() {
		List<Toggle> toggles = toggleGroup.getToggles();
		List<T> items = new ArrayList<>();
		
		for (Toggle toggle : toggles) {
			if (toggle instanceof RadioButton radioButton) {
				T item = (T) radioButton.getUserData();
				
				items.add(item);
			}
		}
		
		return items;
	}
	
	protected void addItem(T item) {
		RadioButton radioButton = radioButton(item);
		radioButton.setUserData(item);
		
		container.getChildren().add(radioButton);
		radioButton.setToggleGroup(toggleGroup);
		radioButton.fire(); // toggles
	}
	
	protected void removeSelectedItem() {
		RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
		
		container.getChildren().remove(radioButton);
		radioButton.setToggleGroup(null);
	}
	
	protected RadioButton radioButton(Object item) { //TODO generalise this to any Toggle
		RadioButton radioButton = new RadioButton();
		radioButton.setText(item.toString());
		
		return radioButton;
	}
	
	public Button buttonAdd(T item, String string) {
		Button button = new Button();
		button.setText(string);
			EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					addItem(item);
				}
			};
		button.setOnAction(eventHandler);
		
		return button;
	}
	
	public Button buttonRemove(String string) {
		Button button = new Button();
		button.setText(string);
			EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					removeSelectedItem();
				}
			};
		button.setOnAction(eventHandler);
		
		return button;
	}
}
