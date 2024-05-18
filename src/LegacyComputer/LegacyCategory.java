package LegacyComputer;

import java.util.ArrayList;

// TODO make constructor accept regular array as parameter instead of ArrayList
// make really sure that no two different Categories have the same index
public class LegacyCategory implements LegacyInventory, Comparable<LegacyCategory> {
    final private int index;
    final private String name;
    final private ArrayList<LegacyItem> itemLegacies;

    public LegacyCategory(int index, String name, ArrayList<LegacyItem> itemLegacies) {
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

    public LegacyItem getItem(int index) {
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

        LegacyCategory legacyCategory = (LegacyCategory) object;
        return this.index == legacyCategory.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public int compareTo(LegacyCategory legacyCategory) {
        return Integer.compare(this.index, legacyCategory.index);
    }
}
