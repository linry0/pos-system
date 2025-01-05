package computer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Writer {
	//TODO in the future make the second param List<Order> its just that now Order's items is a List<String> rather than List<Item> so temporary fix for now
//	public void writeOrder(String filePath, Order order) {
//		Path path = Paths.get(filePath);
//		List<String> fields = Arrays.asList(new String[]{order.getName(), order.getTelephone(), order.getPostcode(), order.getAddress1(), order.getAddress2(), order.getNotes()});
//		List<String> items = order.getItems();
//		List<String> strings = new ArrayList<>();
//		strings.addAll(fields);
//		strings.addAll(items);
//
//		try {
//			Files.write(path, strings);
//		} catch (IOException e) {
//			System.err.println("Error creating items file: " + e.getMessage());
//		}
//	}
	
//	public Order readOrder(String filePath) {
//		Path path = Paths.get(filePath);
//		Order order = null;
//
//		try {
//			List<String> lines = Files.readAllLines(path);
//			if (lines.size() >= 6) {
//				String name = lines.get(0);
//				String telephone = lines.get(1);
//				String postcode = lines.get(2);
//				String address1 = lines.get(3);
//				String address2 = lines.get(4);
//				String notes = lines.get(5);
//				List<String> items = lines.subList(6, lines.size());
//
//				order = new Order(name, telephone, postcode, address1, address2, notes, items);
//			}
//		} catch (IOException e) {
//			System.err.println("Error reading order file: " + e.getMessage());
//		}
//
//		return order;
//	}
	
	
}
