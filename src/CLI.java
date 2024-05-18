import computerLegacy.CategoryLegacy;
import computerLegacy.ItemLegacy;
import computerLegacy.MenuLegacy;

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
    private static MenuLegacy reader() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/items.txt"));
        ArrayList<CategoryLegacy> categories = new ArrayList<CategoryLegacy>();
        ArrayList<ItemLegacy> itemLegacies = new ArrayList<ItemLegacy>();
        String name = "";

        for (String line : lines) {
            if (Objects.equals(line, "")) {
                categories.add(new CategoryLegacy(0, name, itemLegacies));
                itemLegacies = new ArrayList<ItemLegacy>();
                name = "";
            } else if (line.startsWith("|")) {
                String[] splitItem = line.split("\\|");
                name = splitItem[1];
            } else {
                String[] splitItem = line.split("\\|");
                itemLegacies.add(new ItemLegacy(0, splitItem[0], Float.parseFloat(splitItem[1])));
            }
        }

        return new MenuLegacy(0,"Menu", categories);
    }

    public static void main(String[] args) throws IOException {
        MenuLegacy menuLegacy = reader();
        Scanner scanner = new Scanner(java.lang.System.in);

        // first menu screen of picking a category
        for (int i = 0; i < menuLegacy.size(); i++) {
            java.lang.System.out.printf("%s. %s\n", i, menuLegacy.getCategory(i));
        }
        java.lang.System.out.print("\nPick one category: ");
        int option1 = Integer.parseInt(scanner.nextLine());

        // second menu screen of picking an item
        java.lang.System.out.println();
        for (int i = 0; i < menuLegacy.getCategory(option1).size(); i++) {
            ItemLegacy itemLegacy = menuLegacy.getCategory(option1).getItem(i);
            java.lang.System.out.printf("%s. %s | £%s\n", i, itemLegacy, itemLegacy.getPrice());
        }
        java.lang.System.out.print("\nPick one item: ");
        int option2 = Integer.parseInt(scanner.nextLine());

        // array of orders to be written to orders.txt
        ArrayList<String> lines = new ArrayList<>();
        ItemLegacy itemLegacy = menuLegacy.getCategory(option1).getItem(option2);
        lines.add(String.format("%s | £%s", itemLegacy, itemLegacy.getPrice()));

        // writing the array to orders.txt
        Path path = Paths.get("src/orders.txt");
        if (!Files.exists(path)) Files.createFile(path);
        Files.write(path, lines);
        java.lang.System.out.printf("\nYour order of %s was written to orders.txt.\n", menuLegacy.getCategory(option1).getItem(option2));
    }
}
