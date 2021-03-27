package comp1110.ass2.member;

public class Tile {
    /* The maximum length of any valid combination of tiles is 100 */
    public static final int MAX_NUM = 100;
    char colorChar;
}

class BlueTile extends Tile{
         public BlueTile(char colorChar) {
             colorChar = 'a';
             super.colorChar = colorChar;
         }
}
class GreenTile extends Tile{
    public GreenTile(char colorChar) {
        colorChar = 'b';
        super.colorChar = colorChar;
    }
}
class OrangeTile extends Tile{
    public OrangeTile(char colorChar) {
        colorChar = 'c';
        super.colorChar = colorChar;
    }
}
class PurpleTile extends Tile{
    public PurpleTile(char colorChar) {
        colorChar = 'd';
        super.colorChar = colorChar;
    }
}
class RedTile extends Tile{
    public RedTile(char colorChar) {
        colorChar = 'e';
        super.colorChar = colorChar;
    }
}

    }