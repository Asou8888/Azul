package comp1110.ass2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageToFloorTest {
    String[] gameState = {"AFCB1208050510D0000000000", "A38Me01a02c03d04d11b12a13c14d22e30c31b33a41c42e43S1e22c33a24d5FbbcdB35Me00c01d02b03d10a12c13a20b21c22e23b30e31a33d41S1e22d33c44b5Ff"};
    String[] nextGameState = new String[]{"AFCB1208050510D0000000000", "A38Me01a02c03d04d11b12a13c14d22e30c31b33a41c42e43S1e22c33a24d5FbbcdeeB35Me00c01d02b03d10a12c13a20b21c22e23b30e31a33d41S1e22d33c44b5Ff"};
    /**
     * @author Ruizheng Shen
     * @date 2021.5.20
     */
    @Test
    public void testStorageToFloorMoveIsValid() {
        assertTrue(Azul.isMoveValid(gameState, "A1F"));
    }
    @Test
    public void testStorageToFloorApplyMoves() {
        assertEquals(nextGameState[0],
                Azul.applyMove(gameState, "A1F")[0]);
    }
    /*
    @Test
    public void testStorageToFloor(){
        String[] newGameState =  {"AFCB1616181617D0000000000","A1Me04S2c13a34a1FbbbeB0MS0c11b12e13d4Ff"};
        assertEquals(newGameState[1],Azul.applyMove(gameState,"A1F")[1]);
    }

     */

}
