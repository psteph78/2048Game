package model;

/**
 * class to define tiles of gameboard
 */
public class Tile {
    private boolean merged;
    private int value;

    public Tile(int val) {
        value = val;
    }

    public int getValue() {
        return value;
    }

    public void setMerged(boolean merge) {
        merged = merge;
    }

    public boolean canMergeWith(Tile other) {
        return !merged && other != null && !other.merged && value == other.getValue();
    }

    public int mergeWith(Tile other) {
        if (canMergeWith(other)) {
            value *= 2;
            merged = true;
            return value;
        }
        return -1;
    }
}