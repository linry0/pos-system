package menu;

public class Item implements Comparable<Item>, Cloneable {
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
	
	@Override
	public Item clone() {
		try {
			Item item = (Item) super.clone();
			return item;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Cloning failed", e);
		}
	}
	
	@Override
	public String toString() {
		return name;
	}
}
