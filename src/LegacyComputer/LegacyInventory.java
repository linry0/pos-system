package LegacyComputer;

// TODO determine whether getName or toString should be used to get the name in main code for Menu, Category, and Item
// TODO test equals, hashCode, and CompareTo methods for Menu, Category, and Item
// TODO have inheritance and abstract inheritance for remembrance
// TODO create git repository for this and OG Week 7 Lab files
public interface LegacyInventory {
    int getIndex();
    String getName();

    String toString();
    boolean equals(Object object);
    int hashCode();
}
