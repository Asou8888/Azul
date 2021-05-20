package comp1110.ass2;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageToFloorTest {
    String[] gameState = {"AFCB1616181617D0000000000","A1Me04S1b22c13a34a1FbeB0MS0c11b12e13d4Ff"};

    @Test
    public void testStorageToFloor(){
        String[] newGameState =  {"AFCB1616181617D0000000000","A1Me04S2c13a34a1FbbbeB0MS0c11b12e13d4Ff"};
        assertEquals(newGameState[1],Azul.applyMove(gameState,"A1F")[1]);
    }

}
