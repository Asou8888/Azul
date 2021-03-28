package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

public class Discard {
    private Tile[] tile = new Tile[100];

    public String getCode() {
        return encode();
    }

    /**
     * The Discard is an 11-character string that represents the tiles in the Discard area.
     *
     * first character is a "D".
     * The following characters are formulated in the same way as the above Bag string.
     * For example, "D0005101500" means the Discard contains:
     * zero a tiles, 5 b tiles, ten c tiles, fifteen d tiles and zero e tiles.
     * @return String of the code
     */

    public String encode(){
        //TODO
        return "";
    }

    /**
     * Determine if diacard is empty
     * @return true if is empty and not otherwise
     */
    public boolean isEmpty(){
        //TODO
        return true;
    }


    /**
     * find the number of tiles in current stage
     * @return the number of tiles
     */
    public  int CurNum(){
        //TODO
        return 1;
    }
}
