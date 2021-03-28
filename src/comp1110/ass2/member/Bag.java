package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

public class Bag {
    private Tile[] tiles = new Tile[100];

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

    public String encode(){
        //TODO
        return "";
    }

    /**
     * find the number of tiles in bag
     * @return number of tiles in bag in current stage
     */
    public int curNum(){
        //TODO
        return 1;
    }
}
