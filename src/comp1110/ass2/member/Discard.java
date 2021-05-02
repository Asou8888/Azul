package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Modified by Ruizheng Shen, 2021.4.19
 * Add a constructor.
 */

public class Discard {
    private Tile[] tiles;
    int aTiles;
    int bTiles;
    int cTiles;
    int dTiles;
    int eTiles;

    /* Members of javafx  */
    private Label discardLabel = new Label("Discard: ");
    private final Label[] tileLabel = new Label[]{
            new Label("Blue Tiles: "),
            new Label("Green Tiles: "),
            new Label("Orange Tiles: "),
            new Label("Purple Tiles: "),
            new Label("Red Tiles: ")
    };
    private final TextField[] discardRemainTiles = new TextField[5];
    private HBox[] items = new HBox[5];
    private final VBox discardView = new VBox();
    int xIndex;
    int yIndex;

    public Discard() {
        createView();
        this.tiles = new Tile[100];
    }
    public Discard(Tile[] tiles){
        createView();
        this.tiles = tiles;
    }

    private void createView() {
        discardView.getChildren().add(discardLabel);
        for (int i = 0; i < 5; i++) {
            tileLabel[i].setPrefWidth(100);
            discardRemainTiles[i] = new TextField();
            discardRemainTiles[i].setDisable(true);
            discardRemainTiles[i].setPrefWidth(50);
            items[i] = new HBox();
            items[i].getChildren().addAll(tileLabel[i], discardRemainTiles[i]);
            discardView.getChildren().add(items[i]);
        }
    }

    public void updateView() {
        int[] tilesNum = new int[]{aTiles, bTiles, cTiles, dTiles, eTiles};
        for (int i = 0; i < 5; i++) {
            this.discardRemainTiles[i].setText(String.valueOf(tilesNum[i]));
        }
    }

    public VBox getDiscardView() {
        updateView();
        return discardView;
    }
    public void setLocation(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.discardView.setLayoutX(this.xIndex);
        this.discardView.setLayoutY(this.yIndex);
    }
    public int getxIndex() {
        return xIndex;
    }
    public int getyIndex() {
        return yIndex;
    }

    public String getCode() {
        return encode();
    }

    /**
     * The Discard is an 11-character string that represents the tiles in the Discard area.
     * <p>
     * first character is a "D".
     * The following characters are formulated in the same way as the above Bag string.
     * For example, "D0005101500" means the Discard contains:
     * zero a tiles, 5 b tiles, ten c tiles, fifteen d tiles and zero e tiles.
     *
     * @return String of the code
     */
    public String encode() {
        //TODO
        StringBuilder code = new StringBuilder("D");
        String aCnt = amountChar( 'a');
        code.append(aCnt);
        String bCnt = amountChar('b');
        code.append(bCnt);
        String cCnt = amountChar( 'c');
        code.append(cCnt);
        String dCnt = amountChar( 'd');
        code.append(dCnt);
        String eCnt = amountChar('e');
        code.append(eCnt);
        return code.toString();
    }
    //find amount of a color in Tile[], return in String.
    public String amountChar(char type) {
        int cnt = 0;
        int len = tiles.length;
        Tile type1 = new Tile(type);
        for (int i = 0; i < len; i++) {
            if (tiles[i] == null) break;
            if (type1.getCode().equals(tiles[i].getCode())) {
                cnt++;
            }
        }
        String cntS;
        if (cnt < 10){
            cntS = "0"+ cnt;
        }else{
             cntS = String.valueOf(cnt);
        }
        return cntS;
    }

    public void decode(String discard) {
        int a = Integer.parseInt(discard.substring(1,3));
        aTiles = a;
        int b = Integer.parseInt(discard.substring(3,5));
        bTiles = b;
        int c = Integer.parseInt(discard.substring(5,7));
        cTiles = c;
        int d = Integer.parseInt(discard.substring(7,9));
        dTiles = d;
        int e = Integer.parseInt(discard.substring(9));
        eTiles = e;
        Tile[] newTiles = new Tile[a+b+c+d+e];
        for (int i = 0; i < a;i++){
            newTiles[i] = new Tile('a');
        }
        for (int i = 0; i < b;i++){
            newTiles[i + a] = new Tile('b');
        }
        for (int i = 0; i < c;i++){
            newTiles[i + a + b ] = new Tile('c');
        }
        for (int i = 0; i < d;i++){
            newTiles[i + a + b + c] = new Tile('d');
        }
        for (int i = 0; i < e;i++){
            newTiles[i + a + b + c +d ] = new Tile('e');
        }
        placeTiles(newTiles);
    }

    public void placeTiles(char color,int num){
        int numOfTiles = 0;
        for(int i = 0; i < tiles.length;i++){
            if(tiles[i] == null){
                tiles[i] = new Tile(color);
                numOfTiles += 1;
                if(numOfTiles == num){
                    break;
                }
            }
        }

    }

    public void replaceTile(char color){
        int num = 0;
        for(int i = 0;i<tiles.length;i++){
            if(tiles[i] != null){
                num += 1;
            }else {
                break;
            }
        }
        tiles[num-1] = new Tile(color);
    }

    public boolean placeTiles(Tile[] tiles) {
        int cnt = lengthTile();
        if (cnt + tiles.length <= 100) {
            System.arraycopy(tiles, 0, this.tiles, cnt, tiles.length);
            return true;
        }
        return false;
    }
    /**
     * find the number of tiles in current stage
     *
     * @return the number of tiles
     */
    public int lengthTile(){
        for (int i = 0; i < 100; i ++){
            if (this.tiles[i] == null){
                return i;
            }
        }
        return 100;
    }
    /**
     * Determine if diacard is empty
     *
     * @return true if is empty and not otherwise
     */
    public boolean isEmpty() {
        //TODO
        return this.tiles[0] == null;
    }



    public static void main(String[] args) {
        String a = new String("D0409050713");
        Discard d = new Discard();
        d.decode(a);

        for (int i = 0; i< 100; i++) {
            if (d.tiles[i] != null) {
                System.out.print(d.tiles[i].getCode());
            }
        }
        d.replaceTile('a');
        System.out.println(d.getCode());

    }
}
