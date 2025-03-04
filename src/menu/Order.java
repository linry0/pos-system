package menu;

import element.CustomerInformationEnum;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order implements Cloneable{
	private final ZonedDateTime zonedDateTime;
	private HashMap<CustomerInformationEnum, String> fields;
	private List<Item> items;
	
	public Order() {
		this.zonedDateTime = ZonedDateTime.now();
		this.fields = new HashMap<>();
		for (CustomerInformationEnum customerInformationEnum : CustomerInformationEnum.values()) {
			fields.put(customerInformationEnum, "");
		}
		this.items = new ArrayList<>();
	}
	
	public Order(HashMap<CustomerInformationEnum, String> fields, List<Item> items) {
		this();
		
		for (CustomerInformationEnum customerInformationEnum : fields.keySet()) {
			this.fields.put(customerInformationEnum, fields.get(customerInformationEnum));
		}
		
		this.items = items;
	}
	
	public String getField(CustomerInformationEnum customerInformationEnum) { //TODO improve safety by returning copy instead of original
		return fields.get(customerInformationEnum);
	}
	public List<Item> getItems() {
		return items;
	}
	
	public void setField(CustomerInformationEnum customerInformationEnum, String string) {
		fields.put(customerInformationEnum, string);
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	@Override
	public Order clone() {
		try {
			Order order = (Order) super.clone();
			// keep the zonedDateTime constant throughout clones
			
			// copy over customer fields
			for (CustomerInformationEnum customerInformationEnum : CustomerInformationEnum.values()) {
				order.setField(customerInformationEnum, getField(customerInformationEnum));
			}
			
			// copy over items
			order.items = new ArrayList<>();
			for (Item item : items) {
				order.items.add(item.clone());
			}
			
			return order;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Cloning failed", e);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond());
	}
}
