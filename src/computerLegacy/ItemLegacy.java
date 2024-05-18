package computerLegacy;

// make really sure that no two different Items have the same index
public class ItemLegacy implements Inventory, Comparable<ItemLegacy> {
    final private int index;
    final private String name;
    final private float price;

    public ItemLegacy(int index, String name, float price) {
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

    public float getPrice() {
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

        ItemLegacy itemLegacy = (ItemLegacy) object;
        return this.index == itemLegacy.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(ItemLegacy itemLegacy) {
        return Integer.compare(this.index, itemLegacy.index);
    }
}
