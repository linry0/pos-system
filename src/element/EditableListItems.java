package element;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import menu.Item;
import menu.Order;

public class EditableListItems extends EditableList<Item> {
	protected VBox containerParent;
	protected HBox containerTotal;
	private Order orderWorking;
	
	public EditableListItems(Order order) {
		super();
		orderWorking = order;
		
		// append a new container
		containerParent = new VBox();
		containerParent.setSpacing(container.getSpacing());
			container.setPrefHeight(container.getPrefHeight() - 200);
			
			containerTotal = new HBox();
			containerTotal.setSpacing(container.getSpacing());
			containerTotal.setPrefSize(container.getPrefWidth(), 200);
				Label label1 = new Label("Total: ");
				Label label2 = new Label("0");
			containerTotal.getChildren().addAll(label1, label2);
		containerParent.getChildren().addAll(container, containerTotal);
		
		// paste preexisting items from orderWorking into EditableListItems
		for (Item item : orderWorking.getItems()) {
			super.addItem(item);
			refreshTotal();
		}
	}
	
	@Override
	public Pane getContainer() {
		return containerParent;
	}
	
	protected void refreshTotal() {
		Integer total = 0;
		
		for (Toggle toggle : toggleGroup.getToggles()) {
			if (toggle instanceof RadioButton radioButton) {
				Item item = (Item) radioButton.getUserData();
				total += item.getPrice();
			}
		}
		
		Label label = (Label) containerTotal.getChildren().getLast();
		label.setText(total.toString());
	}
	
	@Override
	public void addItem(Item item) {
		orderWorking.addItem(item);
		
		super.addItem(item);
		refreshTotal();
	}
	
	@Override
	protected void removeSelectedItem() {
		orderWorking.removeItem(getSelectedItem());
		
		super.removeSelectedItem();
		refreshTotal();
	}
}
