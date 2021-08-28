package MainPackage.Levels;

public enum TileType {
    EMPTY(0), GRASS1(1), GRASS2(2), GRASS3(3), DIRT1(4), DIRT2(5), DIRT3(6),
    TREE(7), WALL(8);
    private int n;
    TileType(int n) {
        this.n = n;
    }

    public int getN(){
        return n;
    }

    public static TileType getTile(int n){
        switch(n){
            case 1:
                return GRASS1;
            case 2:
                return GRASS2;
            case 3:
                return GRASS3;
            case 4:
                return DIRT1;
            case 5:
                return DIRT2;
            case 6:
                return DIRT3;
            case 7:
                return TREE;
            case 8:
                return WALL;
            default:
                return EMPTY;
        }
    }
}
