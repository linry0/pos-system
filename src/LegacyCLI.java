import LegacyComputer.LegacyCategory;
import LegacyComputer.LegacyItem;
import LegacyComputer.LegacyMenu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// TODO update directory links to just "src/LegacyItems.txt"
public class LegacyCLI {
    private static LegacyMenu reader() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/LegacyItems.txt"));
        ArrayList<LegacyCategory> categories = new ArrayList<LegacyCategory>();
        ArrayList<LegacyItem> itemLegacies = new ArrayList<LegacyItem>();
        String name = "";

        for (String line : lines) {
            if (Objects.equals(line, "")) {
                categories.add(new LegacyCategory(0, name, itemLegacies));
                itemLegacies = new ArrayList<LegacyItem>();
                name = "";
            } else if (line.startsWith("|")) {
                String[] splitItem = line.split("\\|");
                name = splitItem[1];
            } else {
                String[] splitItem = line.split("\\|");
                itemLegacies.add(new LegacyItem(0, splitItem[0], Float.parseFloat(splitItem[1])));
            }
        }

        return new LegacyMenu(0,"Menu", categories);
    }

    public static void main(String[] args) throws IOException {
        LegacyMenu legacyMenu = reader();
        Scanner scanner = new Scanner(java.lang.System.in);

        // first menu screen of picking a category
        for (int i = 0; i < legacyMenu.size(); i++) {
            java.lang.System.out.printf("%s. %s\n", i, legacyMenu.getCategory(i));
        }
        java.lang.System.out.print("\nPick one category: ");
        int option1 = Integer.parseInt(scanner.nextLine());

        // second menu screen of picking an item
        java.lang.System.out.println();
        for (int i = 0; i < legacyMenu.getCategory(option1).size(); i++) {
            LegacyItem legacyItem = legacyMenu.getCategory(option1).getItem(i);
            java.lang.System.out.printf("%s. %s | £%s\n", i, legacyItem, legacyItem.getPrice());
        }
        java.lang.System.out.print("\nPick one item: ");
        int option2 = Integer.parseInt(scanner.nextLine());

        // array of orders to be written to LegacyOrders.txt
        ArrayList<String> lines = new ArrayList<>();
        LegacyItem legacyItem = legacyMenu.getCategory(option1).getItem(option2);
        lines.add(String.format("%s | £%s", legacyItem, legacyItem.getPrice()));

        // writing the array to LegacyOrders.txt
        Path path = Paths.get("src/LegacyOrders.txt");
        if (!Files.exists(path)) Files.createFile(path);
        Files.write(path, lines);
        java.lang.System.out.printf("\nYour order of %s was written to LegacyOrders.txt.\n", legacyMenu.getCategory(option1).getItem(option2));
    }
}
