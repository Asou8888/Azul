package comp1110.ass2.member;

/**
 * @author Ruizheng Shen
 * @Date 2021.5.1
 * @version 2.0(improve on Mosaic)
 */

import comp1110.ass2.gui.Game;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class NewMosaic {
    private Tile[][] tiles;
    private TileType[][] pattern; // the beginner mosaic
    private static final int MOSAIC_WIDTH = 5;
    private boolean isVariant; // whether the mosaic is variant, if not, the mosaic will be the beginner mosaic.


    /* Members of javafx */
    private Group mosaicView = new Group();
    int xIndex;
    int yIndex;

    // FIXME: createView
    public NewMosaic() {
        this.tiles = new Tile[MOSAIC_WIDTH][MOSAIC_WIDTH];
        this.isVariant = true;
        createView();
    }

    public NewMosaic(String mosaic) {
        this.tiles = new Tile[MOSAIC_WIDTH][MOSAIC_WIDTH];
        this.isVariant = true;
        createView();
        decode(mosaic);
    }

    public NewMosaic(boolean isVariant) {
        this.isVariant = isVariant;
        if (!isVariant) {
            // input the beginner pattern;
            this.pattern = new TileType[][] {
                    new TileType[]{TileType.Blue, TileType.Green, TileType.Orange, TileType.Purple, TileType.Red},
                    new TileType[]{TileType.Red, TileType.Blue, TileType.Green, TileType.Orange, TileType.Purple},
                    new TileType[]{TileType.Purple, TileType.Red, TileType.Blue, TileType.Green, TileType.Orange},
                    new TileType[]{TileType.Orange, TileType.Purple, TileType.Red, TileType.Blue, TileType.Green},
                    new TileType[]{TileType.Green, TileType.Orange, TileType.Purple, TileType.Red, TileType.Blue}
            };
        }
        createView();
    }

    public NewMosaic(Tile[] tiles) {
        this.isVariant = true;
        createView();
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            for (int j = 0; j < MOSAIC_WIDTH; j++) {
                this.tiles[i][j] = tiles[MOSAIC_WIDTH * i + j];
            }
        }
    }
    public NewMosaic(Tile[][] tiles) {
        this.isVariant = true;
        createView();
        this.tiles = tiles;
    }

    private void createView() {
        // TODO
        if (isVariant) {
            for (int i = 0; i < MOSAIC_WIDTH; i++) {
                for (int j = 0; j < MOSAIC_WIDTH; j++) {
                    this.mosaicView.getChildren().add(new Tile(' ', j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
                }
            }
        } else {
            for (int i = 0; i < MOSAIC_WIDTH; i++) {
                for (int j = 0; j < MOSAIC_WIDTH; j++) {
                    this.mosaicView.getChildren().add(new Tile(pattern[i][j], j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
                }
            }
        }
    }
    public void setLocation(int xIndex, int yIndex) {
        // TODO
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.mosaicView.setLayoutX(this.xIndex);
        this.mosaicView.setLayoutY(this.yIndex);
    }
    public void updateMosaicView() {
        // TODO
        this.mosaicView.getChildren().clear();
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            for (int j = 0; j < MOSAIC_WIDTH; j++) {
                if (tiles[i][j] != (Tile) null) {
                    this.mosaicView.getChildren().add(new Tile(tiles[i][j].getTileType(), j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
                } else {
                    this.mosaicView.getChildren().add(new Tile(' ', j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
                }
            }
        }
    }
    public Group getMosaicView() {
        // TODO
        updateMosaicView();
        return this.mosaicView;
    }

    /**
     * The visitor of xIndex and yIndex
     */
    public int getxIndex() {
        return xIndex;
    }
    public int getyIndex() {
        return yIndex;
    }


    public String getCode() {
        return encode();
    }
    private String encode() {
        StringBuilder code = new StringBuilder("M");
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            for (int j = 0; j < MOSAIC_WIDTH; j++) {
                if (tiles[i][j] != null) {
                    code.append(tiles[i][j].getCode()).append(i).append(j);
                }
            }
        }
        return code.toString();
    }

    public ArrayList<TileType> rowColorList(int row) {
        ArrayList<TileType> colors = new ArrayList<>();
        for (int j = 0; j < MOSAIC_WIDTH; j++) {
            if (tiles[row][j] != null) {
                colors.add(tiles[row][j].getTileType());
            }
        }
        return colors;
    }

    public ArrayList<TileType> colColorList(int col) {
        ArrayList<TileType> colors = new ArrayList<>();
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            if (tiles[i][col] != null) {
                colors.add(tiles[i][col].getTileType());
            }
        }
        return colors;
    }

    public boolean isEmpty() {
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            for (int j = 0; j < MOSAIC_WIDTH; j++) {
                if (this.tiles[i][j] != null)
                    return false;
            }
        }
        return true;
    }
    public boolean isEmpty(int row, int col) {
        return tiles[row][col] == null;
    }

    public boolean isRowFull(int row) {
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            if (tiles[row][i] == null)
                return false;
        }
        return true;
    }

    public int getBonusScore() {
        return -1;
    }
    public int score(int row,int column) {
        int rowscore = 0;
        int colscore = 0;
        for(int i = row;i < MOSAIC_WIDTH;i++){
            if(tiles[i][column] == null){
                break;
            }
            else {
                rowscore += 1;
            }
        }
        for(int i = row;i >=0 ;i--){
            if(tiles[i][column] == null){
                break;
            }
            else {
                rowscore += 1;
            }
        }
        if(rowscore == 2){
            rowscore = 0;
        }else {
            rowscore = rowscore-1;
        }
        for(int i = column;i < MOSAIC_WIDTH;i++){
            if(tiles[row][i] == null){
                break;
            }
            else {
                colscore +=1;
            }
        }
        for(int i = column;i >=0;i--){
            if(tiles[row][i] == null){
                break;
            }
            else {
                colscore +=1;
            }
        }
        if(colscore == 2){
            colscore = 0;
        }else {
            colscore = colscore-1;
        }

        if(rowscore+colscore == 0){
            return 1;
        }else {
            return rowscore +colscore;
        }

    }


    public void decode(String mosaic) {
        // Assume that this state is valid.
        for (int i = 1; i < mosaic.length(); i = i + 3) {
            char colorChar = mosaic.charAt(i); // the color of the tile;
            int row = mosaic.charAt(i + 1) - '0'; // the row of this tile
            int col = mosaic.charAt(i + 2) - '0'; // the column of this tile.
            Tile t = new Tile(colorChar);
            t.setBelong(TileBelonging.Mosaic);
            t.setOnMouseClicked(e -> {
                // FIXME
                t.setOpacity(0.6);
                if (Game.isClick) { //if it is an second click
                    Game.isClick =  false; //clear click
                    Tile p = Game.from;//load the tile from Storage
                    //TODO

                }});
            tiles[row][col] = new Tile(colorChar); // put the tile in this position.
        }
    }

    public Tile[][] move(char color,int row,int column){
        Tile a = new Tile(color);
        tiles[row][column] = a;
        return tiles;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Mosaic: \n");
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            for (int j = 0; j < MOSAIC_WIDTH; j++) {
                s.append(tiles[i][j]).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        Tile[][] tiles = new Tile[][] {
                new Tile[]{null, null, null, new Tile(TileType.Purple),null},
                new Tile[]{null, null, null, new Tile(TileType.Green),null},
                new Tile[]{null, new Tile(TileType.Orange),null, new Tile(TileType.Red), new Tile(TileType.Green)},
                new Tile[]{null,null,null,null,null},
                new Tile[]{null,null,null,null,null}
        };
        NewMosaic m = new NewMosaic(tiles);

        System.out.println(m.score(2,3));
        m.move('a',0,1);
        System.out.println(tiles[0][1].getCode());
    }



}
