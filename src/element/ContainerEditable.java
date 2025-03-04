package element;

import gui.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


public class ContainerEditable<T> extends Container<VBox> {
	protected ToggleGroup toggleGroup;
	
	public ContainerEditable() {
		super(new VBox());
		container.setSpacing(Constants.VBOX_SPACING);
		
		this.toggleGroup = new ToggleGroup();
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
		RadioButton radioButton = new RadioButton();
		radioButton.setText(item.toString());
		radioButton.setUserData(item);
		
		container.getChildren().add(radioButton);
		radioButton.setToggleGroup(toggleGroup);
		radioButton.fire(); // toggles
	}
	
	public T getSelectedItem() {
		RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
		
		T item = (T) radioButton.getUserData();
		
		return item;
	}
	
	protected void removeSelectedItem() {
		RadioButton radioButton = (RadioButton) toggleGroup.getSelectedToggle();
		
		container.getChildren().remove(radioButton);
		radioButton.setToggleGroup(null);
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
