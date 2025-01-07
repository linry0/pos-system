package menu;

public class Item implements Comparable<Item> {
    private final String name;
    private final Integer price;

    public Item(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public Integer getPrice() {
        return price;
    }

    @Override
    public int compareTo(Item item) {
        return this.name.compareTo(item.name);
    }
}
