package comp1110.ass2.member;

import java.util.ArrayList;

public class NewMosaic {
    private Tile[][] tiles;
    private TileType[][] pattern; // the beginner mosaic
    private static final int MOSAIC_WIDTH = 5;
    private boolean isVariant; // whether the mosaic is variant, if not, the mosaic will be the beginner mosaic.

    public NewMosaic() {
        this.tiles = new Tile[MOSAIC_WIDTH][MOSAIC_WIDTH];
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
    }

    public NewMosaic(Tile[] tiles) {
        for (int i = 0; i < MOSAIC_WIDTH; i++) {
            for (int j = 0; j < MOSAIC_WIDTH; j++) {
                this.tiles[i][j] = tiles[MOSAIC_WIDTH * i + j];
            }
        }
    }
    public NewMosaic(Tile[][] tiles) {
        this.tiles = tiles;
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
        int score = 0;
        for(int i = row;i < MOSAIC_WIDTH;i++){
            if(tiles[i][column] == null){
                break;
            }
            else {
               score += 1;
            }
        }
        for(int i = row;i >=0 ;i--){
            if(tiles[i][column] == null){
                break;
            }
            else {
                score += 1;
            }
        }
        if(score == 2){
            score = 0;
        }else {
            score = score-1;
        }
        for(int i = column;i < MOSAIC_WIDTH;i++){
            if(tiles[row][i] == null){
                break;
            }
            else {
                score +=1;
            }
        }
        for(int i = column;i >=0;i--){
            if(tiles[row][i] == null){
                break;
            }
            else {
                score +=1;
            }
        }
        if(score == 2){
            score = 1;
        }else {
            score = score-1;
        }
        return score;
    }

    public void decode(String mosaic) {
        // Assume that this state is valid.
        for (int i = 1; i < mosaic.length(); i = i + 3) {
            char colorChar = mosaic.charAt(i); // the color of the tile;
            int row = mosaic.charAt(i + 1) - '0'; // the row of this tile
            int col = mosaic.charAt(i + 2) - '0'; // the column of this tile.
            tiles[row][col] = new Tile(colorChar); // put the tile in this position.
        }
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
    }

}