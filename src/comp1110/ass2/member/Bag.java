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
    private Tile[] tiles ;


    // added by Ruizheng Shen
    public Bag() {
        this.tiles = new Tile[100];
    }

    public String getCode() {
        return encode();
    }

    /**
     * The Bag is an 11-character string that represents the tiles left in the Bag.
     *
     *
     * 1st character is a "B".
     *
     * 2nd  and 3rd characters represent the number of a tiles in the string.
     *
     * 4th  and 5th characters represent the number of b tiles in the string.
     *
     * 6th  and 7th characters represent the number of c tiles in the string.
     *
     * 8th  and 9th characters represent the number of d tiles in the string.
     *
     * 10th and 11th characters represent the number of e tiles in the string.
     * @return String of the code
     */
    //implement by Yixin Ge
    public String encode(){
        //TODO
        StringBuilder code = new StringBuilder("B");
        String aCnt = amountChar( 'a');
        code.append(aCnt);
        String bCnt = amountChar('b');
        code.append(bCnt);
        String cCnt = amountChar( 'c');
        code.append(cCnt);
        String dCnt = amountChar( 'c');
        code.append(dCnt);
        String eCnt = amountChar('e');
        code.append(eCnt);
        return code.toString();
    }
    //find amount of a color in Tile[], return in String.
    public String amountChar(char type) {
        int cnt = 0;
        int len = tiles.length;
        Tile type1 = new Tile(type);
        for (int i = 0; i < len; i++) {
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

    public void decode(String bag) {
        int a = Integer.parseInt(bag.substring(1,3));
        int b = Integer.parseInt(bag.substring(3,5));
        int c = Integer.parseInt(bag.substring(5,7));
        int d = Integer.parseInt(bag.substring(7,9));
        int e = Integer.parseInt(bag.substring(9));
        Tile[] newTiles = new Tile[bag.length()];
        for (int i = 0; i < a;i++){
            newTiles[i] = new Tile('a');
        }
        for (int i = 0; i < b;i++){
            newTiles[i + a] = new Tile('b');
        }
        for (int i = 0; i < c;i++){
            newTiles[i + a + b] = new Tile('c');
        }
        for (int i = 0; i < d;i++){
            newTiles[i + a + b + c] = new Tile('d');
        }
        for (int i = 0; i < e;i++){
            newTiles[i + a + b + c +d] = new Tile('e');
        }
        placeTiles(newTiles);
    }

    public boolean placeTiles(Tile[] tiles) {
        int cnt = lengthTile();
        if (cnt + tiles.length <= 100) {
            System.arraycopy(tiles, 0, this.tiles, cnt, tiles.length);
            return true;
        }
        return false;
    }
    /**
     * find the number of tiles in bag
     * @return number of tiles in bag in current stage
     */
        public int lengthTile(){
            for (int i = 0; i < 100; i ++){
                if (tiles[i] == null){
                    return i;
                }
            }
            return 100;
        }
    public static void main(String[] args) {
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red),
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue)};
        Bag b = new Bag();
        System.out.println(b.getCode());

            }
        }

