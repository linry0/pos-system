package computerLegacy;

import java.util.ArrayList;

// TODO make constructor accept regular array as parameter instead of ArrayList
// make really sure that no two different Menus have the same index
public class MenuLegacy implements Inventory, Comparable<MenuLegacy> {
    final private int index;
    final private String name;
    final private ArrayList<CategoryLegacy> categories;

    public MenuLegacy(int index, String name, ArrayList<CategoryLegacy> categories) {
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

    public int size() {
        return categories.size();
    }

    public CategoryLegacy getCategory(int index) {
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

        MenuLegacy menuLegacy = (MenuLegacy) object;
        return this.index == menuLegacy.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(MenuLegacy menuLegacy) {
        return Integer.compare(this.index, menuLegacy.index);
    }
}
