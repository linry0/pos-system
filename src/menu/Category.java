package menu;

import java.util.Arrays;

public class Category implements Comparable<Category> {
    private final String name;
    private Item[] items;

    public Category(String name, Item[] items) {
        Arrays.sort(items);

        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }
    public Item[] getItems() {
        return items;
    }

    @Override
    public int compareTo(Category category) {
        return this.name.compareTo(category.name);
    }
}
