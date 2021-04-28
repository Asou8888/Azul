package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

import gittest.C;
import org.hamcrest.Condition;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modified by Ruizheng Shen, 2021.4.19
 * Add a constructor
 */

public class Center {
    private ArrayList<Tile> tiles;

    // added by Ruizheng Shen.
    public Center(Tile[] tiles) {
        this.tiles = new ArrayList<Tile>(Arrays.asList(tiles));
    }

    public Center(){
        this.tiles = new ArrayList<Tile>();
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
        /**
         * Written by Xiao Xu 4/29
         */
        String center = "C";
        for(int i =0;i<tiles.size();i++){
            center += tiles.get(i).getCode();
        }
        return center;
    }

    public void decode(String center){
        int num = center.length();
        for(int i = 1; i<num;i++){
            for(int n = 1; n<center.length();n++){
                Tile a = new Tile(center.toCharArray()[n]);
                 this.tiles.add(a);
            }

        }
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

    public static void main(String[] args) {
        /**
         * Test written by Xiao Xu
         */
        String a = "Caaaabcf";
        Center c = new Center();
        c.decode(a);
        for(int i = 0; i < a.length();i++){
            System.out.print(c.tiles.get(i).getCode());
        }


    }

}
