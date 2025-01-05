package computer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reader {
    private static String[] readFile(String filePath) {
        Path path = Paths.get(filePath);
        String[] lines = new String[]{};

        try {
            return Files.readAllLines(path).toArray(lines);
        } catch (Exception e) {
            return lines;
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
        HashMap<String, ArrayList<Item>> temp = new HashMap<String, ArrayList<Item>>();

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
        Menu menu = Reader.getMenu("src/menu.tsv");
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
