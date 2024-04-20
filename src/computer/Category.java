package computer;

import java.util.ArrayList;

// TODO make constructor accept regular array as parameter instead of ArrayList
// make really sure that no two different Categories have the same index
public class Category implements Inventory, Comparable<Category> {
    final private int index;
    final private String name;
    final private ArrayList<Item> items;

    protected Category(int index, String name, ArrayList<Item> items) {
        this.index = index;
        this.name = name;
        this.items = items;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    protected int size() {
        return items.size();
    }

    protected Item getItem(int index) {
        return items.get(index);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;

        Category category = (Category) object;
        return this.index == category.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(Category category) {
        return Integer.compare(this.index, category.index);
    }
}
