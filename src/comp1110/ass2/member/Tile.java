package comp1110.ass2.member;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.Text;

import java.beans.EventHandler;

/**
 * Modified by Ruizheng Shen in 2021.4.18
 *     1. Removing inherited classes.
 *     2. Removing (String)'colorName' member.
 *     3. Add a Enum class TileType, for the tile's color.
 *     4. Add a constructor.
 *     5. Add 'private' keyword to tileType and colorChar.
 */

/**
 * Modified by Ruizheng Shen in 2021.5.1
 *     1. extends Rectangle(javafx)
 *     2.
 */

public class Tile extends Rectangle {
    /* The maximum length of any valid combination of tiles is 100 */
    /* The firstPlayer is a special tile */
    public static final int MAX_NUM = 100;
    public static final int MAX_First = 1;

    private final TileType tileType; // the color of this tile.
    private char colorChar; // the code for this tile.(considering using a map from color name to code.)

    // TODO:For javafx
    public static final int TILE_WIDTH = 20; // set the size of tile in the play board.
    int xIndex;
    int yIndex;

    // Constructor
    public Tile(TileType color) {
        this.tileType = color;
        setAppearance();
        switch (color) {
            case Blue -> {
                this.colorChar = 'a';
                setFill(Color.BLUE);
            }
            case Green -> {
                this.colorChar = 'b';
                setFill(Color.GREEN);
            }
            case Orange -> {
                this.colorChar = 'c';
                setFill(Color.ORANGE);
            }
            case Purple -> {
                this.colorChar = 'd';
                setFill(Color.PURPLE);
            }
            case Red -> {
                this.colorChar = 'e';
                setFill(Color.RED);
            }
            case FirstPlayer -> {
                this.colorChar = 'f';
                setFill(Color.GREY);
            }
            default -> {
                this.colorChar = ' ';
                setFill(Color.WHITE);
            }
        }
    }

    /**
     * Constructor: initialize the Tile with colorChar
     * Author: Ruizheng Shen, Date: 2021.4.27
     * @param colorChar
     */
    public Tile(char colorChar) {
        this.colorChar = colorChar;
        setAppearance();
        switch (colorChar) {
            case 'a' -> {
                this.tileType = TileType.Blue;
                setFill(Color.BLUE);
            }
            case 'b' -> {
                this.tileType = TileType.Green;
                setFill(Color.GREEN);
            }
            case 'c' -> {
                this.tileType = TileType.Orange;
                setFill(Color.ORANGE);
            }
            case 'd' -> {
                this.tileType = TileType.Purple;
                setFill(Color.PURPLE);
            }
            case 'e' -> {
                this.tileType = TileType.Red;
                setFill(Color.RED);
            }
            case 'f' -> {
                this.tileType = TileType.FirstPlayer;
                setFill(Color.GREY);

            }
            default -> {
                setFill(Color.LIGHTGRAY);
                this.tileType = null;
                this.colorChar = ' '; // colorChar of null tile is empty
            }
        }
    }

    /**
     * Constructor for javafx
     * @param colorChar the colorChar of this tile
     * @param x the xIndex
     * @param y the yIndex
     */
    public Tile(char colorChar, int x, int y) {
        setAppearance();
        setLocation(x, y);
        this.colorChar = colorChar;
        // TODO: create border for tiles.
        switch (colorChar) {
            case 'a' -> {
                this.tileType = TileType.Blue;
                setFill(Color.BLUE);
            }
            case 'b' -> {
                this.tileType = TileType.Green;
                setFill(Color.GREEN);
            }
            case 'c' -> {
                this.tileType = TileType.Orange;
                setFill(Color.ORANGE);
            }
            case 'd' -> {
                this.tileType = TileType.Purple;
                setFill(Color.PURPLE);
            }
            case 'e' -> {
                this.tileType = TileType.Red;
                setFill(Color.RED);
            }
            case 'f' -> {
                this.tileType = TileType.FirstPlayer;
                setFill(Color.GREY);

            }
            default -> {
                setFill(Color.WHITE);
                this.tileType = null;
                this.colorChar = ' '; // colorChar of null tile is empty
            }
        }
    }

    /**
     * Constructor for javafx
     * @param color the color of this tile
     * @param x the xIndex
     * @param y the yIndex
     */
    public Tile(TileType color, int x, int y) {
        setAppearance();
        setLocation(x, y);
        this.tileType = color;
        // TODO: create border for tiles, also consider the consequence of empty tiles in previous codes.
        switch (color) {
            case Blue -> {
                this.colorChar = 'a';
                setFill(Color.BLUE);
            }
            case Green -> {
                this.colorChar = 'b';
                setFill(Color.GREEN);
            }
            case Orange -> {
                this.colorChar = 'c';
                setFill(Color.ORANGE);
            }
            case Purple -> {
                this.colorChar = 'd';
                setFill(Color.PURPLE);
            }
            case Red -> {
                this.colorChar = 'e';
                setFill(Color.RED);
            }
            case FirstPlayer -> {
                this.colorChar = 'f';
                setFill(Color.GREY);
            }
            default -> {
                this.colorChar = ' ';
                setFill(Color.WHITE);
            }
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

    /**
     * check whether this is an empty tile(if it is empty, then this tile will be white in the board.)
     * @return whether this tile is empty.
     */
    public boolean isEmpty() {
        return this.tileType == null;
    }

    public void setLocation(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.setLayoutX(xIndex);
        this.setLayoutY(yIndex);
    }

    /**
     * xIndex visitor
     * @return xIndex
     */
    public int getxIndex() {
        return xIndex;
    }

    /**
     * yIndex visitor
     * @return yIndex
     */
    public int getyIndex() {
        return yIndex;
    }

    public void setAppearance() {
        this.setHeight(TILE_WIDTH);
        this.setWidth(TILE_WIDTH);
        this.setScaleX((double)95/100);
        this.setScaleY((double)95/100);
        this.setArcHeight(12);
        this.setArcWidth(12);
        this.setOnMouseEntered(e -> {
            this.setOpacity(0.6);
        });
        this.setOnMouseExited(e -> {
            this.setOpacity(1.0);
        });
        this.setOnMouseClicked(e -> {
            this.setOpacity(0.6);
            // TODO: other action while clicking mouse.
        });
    }

    @Override
    public String toString() {
        return this.getCode() + ": " + this.getTileType();
    }


}
