package element;

import gui.Constants;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public abstract class EditableFields<T extends Enum<T>> {
	protected VBox containerFields;
	protected HashMap<Enum<T>, TextField> textFields;
	
	public EditableFields(Class<T> enumClass) {
		textFields = new HashMap<>();
			for (T key : enumClass.getEnumConstants()) {
				TextField textField = new TextField(key.toString());
				textFields.put(key, textField);
			}
		
		containerFields = new VBox();
		containerFields.setSpacing(Constants.VBOX_SPACING);
		containerFields.setPrefSize(Constants.VBOX_LIST_WIDTH*1.5, Constants.VBOX_LIST_HEIGHT);
			for (T key : enumClass.getEnumConstants()) {
					HBox hBox = new HBox();
					hBox.setSpacing(Constants.HBOX_SPACING);
						Label label = new Label(key.toString());
					hBox.getChildren().addAll(label, textFields.get(key));
				containerFields.getChildren().add(hBox);
			}
	}
	
	public VBox getContainer() {
		return containerFields;
	}
	
	public String get(Enum<T> tEnum) {
		return textFields.get(tEnum).getText();
	}
	
	public void set(Enum<T> tEnum, String string) {
		textFields.get(tEnum).setText(string);
	}
}

// class List
// class ListFields extends List
// class ListToggle extends List
// interface Editable
// class ListFieldsCustomer extends ListFields
// class ListToggleOrders extends ListToggle
// class ListToggleOrders extends ListToggle

// class ListFieldsCustomerEditable extends ListFields implements Editable