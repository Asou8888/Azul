package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */


/**
 * Modified by Ruizheng Shen. 2021.4.19
 * Add a constructor.
 */
public class Bag {
    private final Tile[] tiles;
    int aTiles = 0;
    int bTiles = 0;
    int cTiles = 0;
    int dTiles = 0;
    int eTiles = 0;



    // added by Ruizheng Shen
    public Bag() {
        this.tiles = new Tile[100];
    }

    public Bag(Tile[] tiles) {
        this.tiles = tiles;
    }


    public String getCode() {
        return encode();
    }

    /**
     * The Bag is an 11-character string that represents the tiles left in the Bag.
     * <p>
     * <p>
     * 1st character is a "B".
     * <p>
     * 2nd  and 3rd characters represent the number of a tiles in the string.
     * <p>
     * 4th  and 5th characters represent the number of b tiles in the string.
     * <p>
     * 6th  and 7th characters represent the number of c tiles in the string.
     * <p>
     * 8th  and 9th characters represent the number of d tiles in the string.
     * <p>
     * 10th and 11th characters represent the number of e tiles in the string.
     *
     * @return String of the code
     */
    /**
     * @author YiXin Ge
     * @return code
     */
    public String encode() {
        StringBuilder code = new StringBuilder("B");
        String aCnt = amountChar('a');
        code.append(aCnt);
        String bCnt = amountChar('b');
        code.append(bCnt);
        String cCnt = amountChar('c');
        code.append(cCnt);
        String dCnt = amountChar('c');
        code.append(dCnt);
        String eCnt = amountChar('e');
        code.append(eCnt);
        return code.toString();
    }

    /**
     * @author Xiao Xu
     * @param type color of the tile.
     * @return number of tiles of that color.
     */
    //find amount of a color in Tile[], return in String.
    public String amountChar(char type) {
        int cnt = 0;
        int len = tiles.length;
        Tile type1 = new Tile(type);
        for (int i = 0; i < len; i++) {
            if (tiles[i] == null) break;
            if (type1.getCode().equals(tiles[i].getCode())) {
                cnt++;
            }
        }
        String cntS;
        if (cnt < 10) {
            cntS = "0" + cnt;
        } else {
            cntS = String.valueOf(cnt);
        }
        return cntS;
    }

    /**
     * @author YiXin Ge
     * @param bag the bag state
     */
    public void decode(String bag) {
        int a = Integer.parseInt(bag.substring(1, 3));
        aTiles = a;
        int b = Integer.parseInt(bag.substring(3, 5));
        bTiles = b;
        int c = Integer.parseInt(bag.substring(5, 7));
        cTiles = c;
        int d = Integer.parseInt(bag.substring(7, 9));
        dTiles = d;
        int e = Integer.parseInt(bag.substring(9));
        eTiles = e;
        Tile[] newTiles = new Tile[a + b + c + d + e];
        for (int i = 0; i < a; i++) {
            newTiles[i] = new Tile('a');
        }
        for (int i = 0; i < b; i++) {
            newTiles[i + a] = new Tile('b');
        }
        for (int i = 0; i < c; i++) {
            newTiles[i + a + b] = new Tile('c');
        }
        for (int i = 0; i < d; i++) {
            newTiles[i + a + b + c] = new Tile('d');
        }
        for (int i = 0; i < e; i++) {
            newTiles[i + a + b + c + d] = new Tile('e');
        }
        placeTiles(newTiles);
    }

    /**
     * @author YiXin Ge
     * @param tiles the tiles to be placed in the bag.
     * @return whether the place is valid.
     */
    public boolean placeTiles(Tile[] tiles) {
        int cnt = lengthTile();
        if (cnt + tiles.length <= 100) {
            System.arraycopy(tiles, 0, this.tiles, cnt, tiles.length);
            return true;
        }
        return false;
    }

    /**
     * @author YiXin Ge
     * find the number of tiles in bag.
     * @return number of tiles in bag in current stage
     */
    public int lengthTile() {
        for (int i = 0; i < 100; i++) {
            if (tiles[i] == null) {
                return i;
            }
        }
        return 100;
    }

    public int getaTiles() {
        return aTiles;
    }

    public int getbTiles() {
        return bTiles;
    }

    public int getcTiles() {
        return cTiles;
    }

    public int getdTiles() {
        return dTiles;
    }

    public int geteTiles() {
        return eTiles;
    }

}

