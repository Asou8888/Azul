package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

/**
 * Modified by Ruizheng Shen, 2021.4.19
 * Add a constructor
 */

public class Center {
    private Tile[] tiles;

    // added by Ruizheng Shen.
    public Center(Tile[] tiles) {
        this.tiles = tiles;
    }


    public String getCode() {

        return encode();
    }

    /**
     * The encoding for the Centre placement string is very similar for factories, but is not limited to 4 tiles.
     *
     * The first character is a "C".
     * The following characters are any number of a to e tiles and up to one f (first player) tile, in alphabetical order.
     * for example:
     *
     * "C" means the Centre is empty.
     *
     * "Caaaabbcf" means the Centre contains four a tiles, two b tiles, one c tile and one f tile.
     * @return String of the code
     */

    public String encode(){
        //TODO
        return "";
    }


    /**
     *get the number of tiles in current stage
     * @return the number of tiles in center
     */
    public int getCurrentNum(){
        //TODO:return the number of tiles in current stage
        return 1;
    }

    /**
     * Determine whether there is a firstPlayer tile in center or not
     * @return true if there is and false if there is not
     */
    public boolean hasFirstPlayer(){
        //TODO:return if there is a firstplayer in center
        return true;
    }


    /**
     * Determine if the center is empty or not
     * @return true if center is empty and false if not
     */
    public boolean isEmpty(){
        //TODO:return if the center is empty or not
        return true;
    }

}
