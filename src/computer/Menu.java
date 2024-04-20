package computer;

import java.util.ArrayList;

// TODO make constructor accept regular array as parameter instead of ArrayList
// make really sure that no two different Menus have the same index
public class Menu implements Inventory, Comparable<Menu> {
    final private int index;
    final private String name;
    final private ArrayList<Category> categories;

    protected Menu(int index, String name, ArrayList<Category> categories) {
        this.index = index;
        this.name = name;
        this.categories = categories;
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
        return categories.size();
    }

    protected Category getCategory(int index) {
        return categories.get(index);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;

        Menu menu = (Menu) object;
        return this.index == menu.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(Menu menu) {
        return Integer.compare(this.index, menu.index);
    }
}
