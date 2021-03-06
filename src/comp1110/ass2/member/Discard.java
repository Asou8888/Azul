package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

/**
 * Modified by Ruizheng Shen, 2021.4.19
 * Add a constructor.
 */

public class Discard {
    private Tile[] tiles;
    int aTiles;
    int bTiles;
    int cTiles;
    int dTiles;
    int eTiles;

    public Discard() {
        this.tiles = new Tile[100];
    }
    public Discard(Tile[] tiles){
        this.tiles = tiles;
    }

    public String getCode() {
        return encode();
    }

    /**
     * @author Ruizheng Shen
     * The Discard is an 11-character string that represents the tiles in the Discard area.
     * <p>
     * first character is a "D".
     * The following characters are formulated in the same way as the above Bag string.
     * For example, "D0005101500" means the Discard contains:
     * zero a tiles, 5 b tiles, ten c tiles, fifteen d tiles and zero e tiles.
     *
     * @return String of the code
     */
    public String encode() {
        StringBuilder code = new StringBuilder("D");
        String aCnt = amountChar( 'a');
        code.append(aCnt);
        String bCnt = amountChar('b');
        code.append(bCnt);
        String cCnt = amountChar( 'c');
        code.append(cCnt);
        String dCnt = amountChar( 'd');
        code.append(dCnt);
        String eCnt = amountChar('e');
        code.append(eCnt);
        return code.toString();
    }

    /**
     * find amount of a color in Tile[], return in String.
     * @author Xiao Xu
     * @param type the coloreChar
     * @return the number of tiles
     */
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
        if (cnt < 10){
            cntS = "0"+ cnt;
        }else{
             cntS = String.valueOf(cnt);
        }
        return cntS;
    }

    /**
     * @author YiXin Ge
     * Given the String Start with "D" and decode it into list of tiles.
     * @param discard the discard state
     */
    public void decode(String discard) {
        int a = Integer.parseInt(discard.substring(1,3));
        aTiles = a;
        int b = Integer.parseInt(discard.substring(3,5));
        bTiles = b;
        int c = Integer.parseInt(discard.substring(5,7));
        cTiles = c;
        int d = Integer.parseInt(discard.substring(7,9));
        dTiles = d;
        int e = Integer.parseInt(discard.substring(9));
        eTiles = e;
        Tile[] newTiles = new Tile[a+b+c+d+e];
        for (int i = 0; i < a;i++){
            newTiles[i] = new Tile('a');
        }
        for (int i = 0; i < b;i++){
            newTiles[i + a] = new Tile('b');
        }
        for (int i = 0; i < c;i++){
            newTiles[i + a + b ] = new Tile('c');
        }
        for (int i = 0; i < d;i++){
            newTiles[i + a + b + c] = new Tile('d');
        }
        for (int i = 0; i < e;i++){
            newTiles[i + a + b + c +d ] = new Tile('e');
        }
        placeTiles(newTiles);
    }

    /**
     * @author YiXin Ge
     * @param color the color of the tiles
     * @param num the number of the tiles
     */
    public void placeTiles(char color,int num){
        int numOfTiles = 0;
        if(num != 0) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i] == null) {
                    tiles[i] = new Tile(color);
                    numOfTiles += 1;
                    if (numOfTiles == num) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * @author Xiao Xu
     * replace a tile in last valid position in the discard.
     * @param color the colorChar
     */
    public void replaceTile(char color){
        int num = 0;
        for(int i = 0;i<tiles.length;i++){
            if(tiles[i] != null){
                num += 1;
            }else {
                break;
            }
        }
        tiles[num-1] = new Tile(color);
    }

    /**
     * @author Ruizheng Shen
     * Determine if the amount of tiles can be fitted in discard.
     * @param tiles the tiles to be placed in the discard.
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
     * @author Ruizheng Shen
     * find the number of tiles in current stage
     * @return the number of tiles
     */
    public int lengthTile(){
        for (int i = 0; i < 100; i ++){
            if (this.tiles[i] == null){
                return i;
            }
        }
        return 100;
    }
    /**
     * @author YiXin Ge
     * Determine if discard is empty
     * @return true if is empty and not otherwise
     */
    public boolean isEmpty() {
        return this.tiles[0] == null;
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
