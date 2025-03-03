package element;

import menu.Order;

public class EditableListOrders extends EditableList<Order> {
	public EditableListOrders() {
		super();
	}
	
	public void resetSelectedItem() {
		Order order = new Order();
		super.addItem(order);
	}
	
	@Override
	protected void addItem(Order order) {
		removeSelectedItem(); // the selected item is the one from which you loaded into the editor
		super.addItem(order); // essentially deleting the old item and saving the new item
	}
}
