package LegacyComputer;

import java.util.ArrayList;

// TODO make constructor accept regular array as parameter instead of ArrayList
// make really sure that no two different Menus have the same index
public class LegacyMenu implements LegacyInventory, Comparable<LegacyMenu> {
    final private int index;
    final private String name;
    final private ArrayList<LegacyCategory> categories;

    public LegacyMenu(int index, String name, ArrayList<LegacyCategory> categories) {
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

    public LegacyCategory getCategory(int index) {
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

        LegacyMenu legacyMenu = (LegacyMenu) object;
        return this.index == legacyMenu.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(LegacyMenu legacyMenu) {
        return Integer.compare(this.index, legacyMenu.index);
    }
}
