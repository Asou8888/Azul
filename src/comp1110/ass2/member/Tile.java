package comp1110.ass2.member;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */
public class Tile {
    /* The maximum length of any valid combination of tiles is 100 */
    /* The firstPlayer is a special tile */
    public static final int MAX_NUM = 100;
    public static final int MAX_First = 1;

    char colorChar;
    String colorName;
    /* Given a char from tile and returns in string form*/
    public String getCode() {
        return String.valueOf(colorChar);
    }
}

class BlueTile extends Tile{
         public BlueTile() {
             super.colorChar = 'a';
             super.colorName = "blue";
         }
}
class GreenTile extends Tile{
    public GreenTile() {
        super.colorChar = 'b';
        super.colorName = "green";
    }
}
class OrangeTile extends Tile{
    public OrangeTile(char colorChar) {
        super.colorChar = 'c';
        super.colorName = "orange";
    }
}
class PurpleTile extends Tile{
    public PurpleTile() {
        super.colorChar = 'd';
        super.colorName = "purple";
    }
}
class RedTile extends Tile{
    public RedTile() {
        super.colorChar = 'e';
        super.colorName = "red";
    }
}
class FirstPlayer extends Tile{
    public FirstPlayer() {
        super.colorChar = 'f';
        super.colorName = "firstPlayer";
    }
}
