package comp1110.ass2.member;

/**
 * The Floor class implements the floor of a player, which has a lostPoint array to record the lost points and a tiles array,
 * which are the tiles in the floor.
 *
 * @author Ruizheng Shen
 * @version 1.0
 * @since 2021.3.27
 */
public class Floor {
    private Tile[] tiles;
    private static final int FLOOR_WIDTH = 7;
    public static final int[] lostPoint = {-1, -1, -2, -2, -2, -3, -3};

    public Floor() {
        this.tiles = new Tile[FLOOR_WIDTH];
    }

    /**
     * return the code of the current state of floor.
     *
     * @return the String code of the current floor.
     */
    public String getCode() {
        // TODO: Think about should we do anything else in this method.
        return encode();
    }

    /**
     * encode the current state of floor.
     * <p>
     * [Floor encoding]
     * The Floor String is composed of 0 or more tiles sorted alphabetically. Note that you may choose to display the tiles in your
     * GUI unsorted.
     * * first character is `"F"`
     * * the following 0 or more characters are `a` to `f` where `f` represents the first player token. Note that there is only
     * one first player token.
     * <p>
     * For example:
     * `"Faabbe"` means two `a` tiles, two `b` tiles and one `e` tile have been dropped on the floor.
     * An empty Floor string would look like this: `"F"`
     * <p>
     * [Score encoding]
     * The Score is a string of up to three digits representing the player's score.
     * <p>
     * For example "120" means the player has a score of 120. A score of 0 is represented by "0".
     *
     * @return the code
     */
    private String encode() {
        // TODO: implements the encode method.
        StringBuilder code = new StringBuilder();
        code.append('F');
        for (Tile t : tiles) {
            code.append(t);
        }
        return code.toString();
    }

    /**
     * Calculate the current score in the floor.(This will be calculate at the end of each round.)
     *
     * @return the current score in the floor
     */
    public int score() {
        // TODO: implements the score method.
        int index = 0;
        for (Tile x : tiles){
            index += 1;
        }
        int scr = 0;
        for(int i = 0;i < index; i++){
            scr =+ lostPoint[i];
        }
        return scr;
    }

    public static void main(String[] args) {
        Tile[] tiles;
        tiles = new Tile[]{
                new Tile(TileType.Red),
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                null,
                null,
    };


    }
}
