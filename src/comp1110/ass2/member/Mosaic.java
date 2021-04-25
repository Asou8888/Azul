package comp1110.ass2.member;

/**
 * The Mosaic class implements the mosaic of a player, which has a tile array(5*5)
 * @author Ruizheng Shen
 * @version 1.0
 * @since 2021.3.27
 */

public class Mosaic {

    private Tile[] tiles;
    private static final int MOSAIC_WIDTH = 25;
    private Player player; //add by Xiao Xu

    public Mosaic() {
        this.tiles = new Tile[MOSAIC_WIDTH];
    }

    public Mosaic(Tile[] tiles) {
        this.tiles = tiles; //add by Xiao Xu
    }

    /**
     * Return the code of the current state in mosaic.
     * @return the String code of mosaic.
     */
    public String getCode() {
        return encode();
    }

    /**
     * Encode the current state in mosaic.
     *
     * [Mosaic encoding]
     * The Mosaic is a string prefixed by `"M"` and composed of 3-character substrings representing the tiles on the mosaic.
     * The mosaic is a 5x5 grid indexed by (row, column).
     * * The first character is `"M"`
     * * The following characters are in groups of three in the pattern: {tile}{row}{column}
     *     * tile `a` - `e`.
     *     * row number `0` - `4`.
     *     * column number `0` - `4`.
     *
     * For example `"Mb00a02a13e42"` means there is one `b` tile located at `row 0`, `column 0` `(0,0)` two `a` tiles located at `(0,2)` and `(1,3)`, and one `e` tile located at `(4,2)`. Note that the Mosaic string is ordered by `row` then `column` ie: a01 comes before a10.
     *
     * An empty mosaic string would look like this: `"M"
     *
     * [Score encoding]
     * The Score is a string of up to three digits representing the player's score.
     *
     * For example "120" means the player has a score of 120. A score of 0 is represented by "0".
     *
     * @return the code
     */
    private String encode() {
        /**
         * Written by Xiao Xu 4/25/2021
         */
        String mosaic = "M";
        for(int i = 0; i < 25;) {
            try {
                if (tiles[i].getTileType() == TileType.Blue) {
                    mosaic += "a";
                    mosaic += i / 5;
                    mosaic += i % 5;
                }
            if (tiles[i].getTileType() == TileType.Green) {
                mosaic += "b";
                mosaic += i / 5;
                mosaic += i % 5;
            }
            if (tiles[i].getTileType() == TileType.Orange) {
                mosaic += "c";
                mosaic += i / 5;
                mosaic += i % 5;
            }
            if (tiles[i].getTileType() == TileType.Purple) {
                mosaic += "d";
                mosaic += i / 5;
                mosaic += i % 5;
            }
            if (tiles[i].getTileType() == TileType.Red) {
                    mosaic += "e";
                    mosaic += i / 5;
                    mosaic += i % 5;
            }
           } catch (Exception e){
        }
            i = i + 1;
        }
        return mosaic;
    }

    /**
     * Calculate the current score in the mosaic.(This will be calculated at the end of each round)
     * @return the current score in the mosaic
     */
    public int score() {
        return -1;
    }

    /**
     * Calculate the final score in the mosaic.(This will be calculated at the end of the game)
     * From README.md(Bonus score):
     * Each player gains additional bonus points if they satisfy the following conditions:
     *
     * * Gain 2 points for each complete row of your mosaic (5 consecutive horizontal tiles).
     * * Gain 7 points for each complete column of your mosaic (5 consecutive vertical tiles).
     * * Gain 10 points for each colour of tile for which you have placed all 5 tiles on your mosaic.
     * @return the bonus score in the mosaic
     */
    public int getBonusScore() {
        return -1;
    }

    public boolean isRowFull() {
        /**
         * Written by Xiao Xu 4/25/2021
         */
        String mosaic = getCode();
        String number = "";
        if(mosaic != null && !"".equals(mosaic)) {
            for (int i = 0; i < mosaic.length(); i++) {
                if (mosaic.charAt(i) >= 48 && mosaic.charAt(i) <= 57) {
                    number += mosaic.charAt(i);
                }
            }
        }
        if(number.contains("0001020304")  || number.contains("1011121314") || number.contains("2021222324") || number.contains("3031323334") || number.contains("4041424344")||number.contains("5051525354")){
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red), null, null, null, null,
                null, null, new Tile(TileType.Green), null, new Tile(TileType.Blue),
                new Tile(TileType.Blue),null, null, null, null,null,
                null,new Tile(TileType.Blue),null,null,new Tile(TileType.Purple),
                new Tile(TileType.Purple), null,new Tile(TileType.Orange),new Tile(TileType.Orange),null
        };
        Mosaic m = new Mosaic(tiles);
        System.out.println(m.isRowFull());
        System.out.println(m.getCode());

    }
}
