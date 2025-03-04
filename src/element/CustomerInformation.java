package element;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import menu.Order;

public class CustomerInformation extends ContainerFields<CustomerInformationEnum> {
	private final Order orderWorking;
	
    public CustomerInformation(Order order) {
		super(CustomerInformationEnum.class);
		orderWorking = order;
		
		// paste preexisting text in order into fields
		for (CustomerInformationEnum customerInformationEnum : CustomerInformationEnum.values()) {
			String fieldText = orderWorking.getField(customerInformationEnum);
			setField(customerInformationEnum, fieldText);
		}
		
		// add listener to sync fields with orderWorking
		for (CustomerInformationEnum customerInformationEnum : CustomerInformationEnum.values()) {
			ChangeListener<String> changeListener = new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observableValue, String string1, String string2) {
					String fieldText = getField(customerInformationEnum);
					orderWorking.setField(customerInformationEnum, fieldText);
				}
			};
			textFields.get(customerInformationEnum).textProperty().addListener(changeListener);
		}
	}
}
