package util;

import menu.Category;
import menu.Item;
import menu.Menu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Reader {
	private static String[] readFile(String filePath) {
	    try (InputStream inputStream = Reader.class.getResourceAsStream("/data/menu.tsv");
	         BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
	        
	        return reader.lines().toArray(String[]::new);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new String[]{"Starters\tSoup\t100"};
	    }
	}


    private static String[][] formatFile(String[] lines) { // TODO maybe eliminate magic number 3 and rename triples to values to be more modular in case i add more values per line
        ArrayList<String[]> pairs = new ArrayList<String[]>();

        for (String line : lines) {
            String[] triple = line.split("\t+"); // TODO rename triple to entry. entry consists of category, item, and price

            if (triple.length == 3) pairs.add(triple);
        }

        return pairs.toArray(new String[][]{});
    }

    private static Menu parseMenu(String[][] triples) { // TODO optimise this shit
        LinkedHashMap<String, ArrayList<Item>> temp = new LinkedHashMap<String, ArrayList<Item>>();

        for (String[] triple : triples) {
            String name = triple[0];
            Item item = new Item(triple[1], Integer.parseInt(triple[2]));

            if (temp.containsKey(name)) {
                temp.get(name).add(item);
            } else {
                temp.put(name, new ArrayList<Item>(List.of(item)));
            }
        }

        ArrayList<Category> categories = new ArrayList<Category>();
        for (String string : temp.keySet()) {
            Category category = new Category(string, temp.get(string).toArray(new Item[]{}));
            categories.add(category);
        }

        return new Menu("test", categories.toArray(new Category[]{}));
    }

    public static Menu getMenu(String filePath) {
        return parseMenu(formatFile(readFile(filePath)));
    }

    public static void main(String[] args) {
        Menu menu = Reader.getMenu("main/resources/menu.tsv");
        Category[] categories = menu.getCategories();
        Item[] items;

        for (Category category : categories) {
            System.out.printf("Category: %s\n", category.getName());
            items = category.getItems();
            for (Item item : items) System.out.printf("Item: %s\n", item.getName());
            System.out.println();
        }
    }
}
