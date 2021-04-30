package comp1110.ass2.member;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

/**
 * Modified by Ruizheng Shen in 2021.4.18
 *     1. Removing inherited classes.
 *     2. Removing (String)'colorName' member.
 *     3. Add a Enum class TileType, for the tile's color.
 *     4. Add a constructor.
 *     5. Add 'private' keyword to tileType and colorChar.
 */

public class Tile {
    /* The maximum length of any valid combination of tiles is 100 */
    /* The firstPlayer is a special tile */
    public static final int MAX_NUM = 100;
    public static final int MAX_First = 1;

    private final TileType tileType; // the color of this tile.

    private char colorChar; // the code for this tile.(considering using a map from color name to code.)

    // Constructor
    public Tile(TileType color) {
        this.tileType = color;
        switch (color) {
            case Blue -> this.colorChar = 'a';
            case Green -> this.colorChar = 'b';
            case Orange -> this.colorChar = 'c';
            case Purple -> this.colorChar = 'd';
            case Red -> this.colorChar = 'e';
            case FirstPlayer -> this.colorChar = 'f';
        }
    }

    /**
     * Constructor: initialize the Tile with colorChar
     * Author: Ruizheng Shen, Date: 2021.4.27
     * @param colorChar
     */
    public Tile(char colorChar) {
        this.colorChar = colorChar;
        switch (colorChar) {
            case 'a' -> this.tileType = TileType.Blue;
            case 'b' -> this.tileType = TileType.Green;
            case 'c' -> this.tileType = TileType.Orange;
            case 'd' -> this.tileType = TileType.Purple;
            case 'e' -> this.tileType = TileType.Red;
            default -> this.tileType = TileType.FirstPlayer;
        }
    }

    /* Given a char from tile and returns in string form*/
    public String getCode() {
        return String.valueOf(colorChar);
    }

    /* the visitor to tileType, returning a enum type */
    public TileType getTileType() {
        return this.tileType;
    }


}
