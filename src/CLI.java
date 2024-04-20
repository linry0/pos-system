import computer.Category;
import computer.Item;
import computer.Menu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// TODO update directory links to just "src/items.txt"
public class CLI {
    private static Menu reader() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/menu/items.txt"));
        ArrayList<Category> categories = new ArrayList<Category>();
        ArrayList<Item> items = new ArrayList<Item>();
        String name = "";

        for (String line : lines) {
            if (Objects.equals(line, "")) {
                categories.add(new Category(name, items));
                items = new ArrayList<Item>();
                name = "";
            } else if (line.startsWith("|")) {
                String[] splitItem = line.split("\\|");
                name = splitItem[1];
            } else {
                String[] splitItem = line.split("\\|");
                items.add(new Item(splitItem[0], Float.parseFloat(splitItem[1])));
            }
        }

        return new Menu("Menu", categories);
    }

    public static void main(String[] args) throws IOException {
        Menu menu = reader();
        Scanner scanner = new Scanner(java.lang.System.in);

        // first menu screen of picking a category
        for (int i = 0; i < menu.size(); i++) {
            java.lang.System.out.printf("%s. %s\n", i, menu.getCategory(i));
        }
        java.lang.System.out.print("\nPick one category: ");
        int option1 = Integer.parseInt(scanner.nextLine());

        // second menu screen of picking an item
        java.lang.System.out.println();
        for (int i = 0; i < menu.getCategory(option1).size(); i++) {
            Item item = menu.getCategory(option1).getItem(i);
            java.lang.System.out.printf("%s. %s | £%s\n", i, item, item.getPrice());
        }
        java.lang.System.out.print("\nPick one item: ");
        int option2 = Integer.parseInt(scanner.nextLine());

        // array of orders to be written to orders.txt
        ArrayList<String> lines = new ArrayList<>();
        Item item = menu.getCategory(option1).getItem(option2);
        lines.add(String.format("%s | £%s", item, item.getPrice()));

        // writing the array to orders.txt
        Path path = Paths.get("src/menu/orders.txt");
        if (!Files.exists(path)) Files.createFile(path);
        Files.write(path, lines);
        java.lang.System.out.printf("\nYour order of %s was written to orders.txt.\n", menu.getCategory(option1).getItem(option2));
    }
}
