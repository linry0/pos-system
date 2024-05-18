package computerLegacy;

import java.util.ArrayList;

// TODO make constructor accept regular array as parameter instead of ArrayList
// make really sure that no two different Categories have the same index
public class CategoryLegacy implements Inventory, Comparable<CategoryLegacy> {
    final private int index;
    final private String name;
    final private ArrayList<ItemLegacy> itemLegacies;

    public CategoryLegacy(int index, String name, ArrayList<ItemLegacy> itemLegacies) {
        this.index = index;
        this.name = name;
        this.itemLegacies = itemLegacies;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    public int size() {
        return itemLegacies.size();
    }

    public ItemLegacy getItem(int index) {
        return itemLegacies.get(index);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;

        CategoryLegacy categoryLegacy = (CategoryLegacy) object;
        return this.index == categoryLegacy.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(CategoryLegacy categoryLegacy) {
        return Integer.compare(this.index, categoryLegacy.index);
    }
}
