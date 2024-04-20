package computer;

// make really sure that no two different Items have the same index
public class Item implements Inventory, Comparable<Item> {
    final private int index;
    final private String name;
    final private float price;

    protected Item(int index, String name, float price) {
        this.index = index;
        this.name = name;
        this.price = price;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    protected float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        //TODO if (!(object instanceof Item)) return false;
        if (this.getClass() != object.getClass()) return false;

        Item item = (Item) object;
        return this.index == item.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(Item item) {
        return Integer.compare(this.index, item.index);
    }
}
