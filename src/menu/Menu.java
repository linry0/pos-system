package menu;

public class Menu {
    private final String name;
    private final Category[] categories;

    public Menu(String name, Category[] categories) {
        // Arrays.sort(categories);

        this.name = name;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }
    public Category[] getCategories() {
        return categories;
    }
}
