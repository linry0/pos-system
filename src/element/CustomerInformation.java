package element;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import menu.Order;

public class CustomerInformation extends EditableFields<CustomerInformationField> {
	private final Order orderWorking;
	
    public CustomerInformation(Order order) {
		super(CustomerInformationField.class);
		orderWorking = order;
		
		// paste preexisting text in order into fields
		for (CustomerInformationField customerInformationField : CustomerInformationField.values()) {
			textFields.get(customerInformationField).setText(orderWorking.getField(customerInformationField));
		}
		
		for (CustomerInformationField customerInformationField : CustomerInformationField.values()) {
			ChangeListener<String> changeListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observableValue, String string1, String string2) {
					orderWorking.setField(customerInformationField, string2);
				}
			};
			textFields.get(customerInformationField).textProperty().addListener(changeListener);
		}
	}
}
