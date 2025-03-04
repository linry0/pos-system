package element;

import gui.Constants;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public abstract class ContainerFields<T extends Enum<T>> extends Container<VBox> {
	protected HashMap<Enum<T>, TextField> textFields;
	
	public ContainerFields(Class<T> enumClass) {
		super(new VBox());
		container.setSpacing(Constants.VBOX_SPACING);
		container.setPrefSize(Constants.VBOX_FIELDS_WIDTH, Constants.VBOX_FIELDS_HEIGHT);
		textFields = new HashMap<>();
		
		for (T key : enumClass.getEnumConstants()) {
				TextField textField = new TextField(key.toString());
			textFields.put(key, textField);
			
				HBox hBox = new HBox();
				hBox.setSpacing(Constants.HBOX_SPACING);
					Label label = new Label(key.toString());
				hBox.getChildren().addAll(label, textField);
			container.getChildren().add(hBox);
		}
	}
	
	public String getField(Enum<T> tEnum) {
		return textFields.get(tEnum).getText();
	}
	
	public void setField(Enum<T> tEnum, String string) {
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