package computer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Printer {
    public static Category chooseCategory(Scanner scanner, Category[] categories) {
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%s. %s\n", i + 1, categories[i].getName());
        }

        System.out.print("\n");
        System.out.print("Choose one category: ");

        int input = Integer.parseInt(scanner.nextLine());
        return categories[input - 1];
    }

    public static Item chooseItem(Scanner scanner, Item[] items) {
        for (int i = 0; i < items.length; i++) {
            System.out.printf("%s. %s\n", i + 1, items[i].getName());
        }

        System.out.print("\n");
        System.out.print("Choose one item: ");

        int input = Integer.parseInt(scanner.nextLine());
        return items[input - 1];
    }

    public static void main(String[] args) throws IOException {
        Menu menu = Reader.getMenu("src/menu.tsv");

        Scanner scanner = new Scanner(System.in);

        Category category = Printer.chooseCategory(scanner, menu.getCategories());
        Item item = Printer.chooseItem(scanner, category.getItems());

        scanner.close();

        Files.write(Paths.get("src/orders.tsv"), List.of(String.format("%s\t%s", item.getName(), item.getPrice())));
        java.lang.System.out.printf("\nYour order of %s was written to orders.tsv.\n", item.getName());
    }
}
