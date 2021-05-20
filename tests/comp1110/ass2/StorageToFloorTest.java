package comp1110.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageToFloorTest {
    String[] gameState = {"AFCB1208050510D0000000000", "A38Me01a02c03d04d11b12a13c14d22e30c31b33a41c42e43S1e22c33a24d5FbbcdB35Me00c01d02b03d10a12c13a20b21c22e23b30e31a33d41S1e22d33c44b5Ff"};
    String[] nextGameState = {"AFCB1208050510D0000000000", "A38Me01a02c03d04d11b12a13c14d22e30c31b33a41c42e43S2c33a24d5FbbcdeeB35Me00c01d02b03d10a12c13a20b21c22e23b30e31a33d41S1e22d33c44b5Ff"};
    String[] gameState1 = {"AFCB0000000000D1508130910", "A38Md00c01b02e03a11e12b13b21a22c23e24a33c42d43S2d33d2FbbbddddB26Mc01b03e11b12d13b21a22c23e33a41c42S1c12e34b3Feef"};
    String[] nextGameState1 = {"BFCB0000000000D1508131210", "A38Md00c01b02e03a11e12b13b21a22c23e24a33c42d43S3d2FbbbddddB26Mc01b03e11b12d13b21a22c23e33a41c42S1c12e34b3Feef"};
    String[] gameState2 = {"AFCB0000000000D1215101107", "A25Me00d01c02e11a12d13b21e22a31c33d34d42S3e44c5FaadddefB51Md01a02e03b04c11e12b13a14b21c22a23e24a31b32d33c43S4e2F"};
    String[] nextGameState2 = {"AFCB0000000000D1215101111", "A25Me00d01c02e11a12d13b21e22a31c33d34d42S4c5FaadddefB51Md01a02e03b04c11e12b13a14b21c22a23e24a31b32d33c43S4e2F"};

    /**
     * @author Ruizheng Shen      * @date 2021.5.20
     */
    @Test
    public void testNormalMoveIsValid() {
        assertTrue(Azul.isMoveValid(gameState, "A1F"));
    }

    @Test
    public void testFloorIsFullMoveValid() {
        assertTrue(Azul.isMoveValid(gameState1, "A2F"));
        assertTrue(Azul.isMoveValid(gameState2, "A3F"));
    }

    @Test
    public void testNormalApplyMoves() {
        assertEquals(nextGameState[0], Azul.applyMove(gameState.clone(), "A1F")[0]);
        System.out.println(gameState[0]);
        System.out.println(gameState[1]);
        assertEquals(nextGameState[1], Azul.applyMove(gameState.clone(), "A1F")[1]);
    }

    @Test
    public void testFloorIsFullApplyMoves() {
        assertEquals(nextGameState1[0], Azul.applyMove(gameState1.clone(), "A2F")[0]);
        assertEquals(nextGameState1[1], Azul.applyMove(gameState1.clone(), "A2F")[1]);
        assertEquals(nextGameState2[0], Azul.applyMove(gameState2.clone(), "A3F")[0]);
        assertEquals(nextGameState2[1], Azul.applyMove(gameState2.clone(), "A3F")[1]);
    }
    /*     @Test     public void testStorageToFloor(){
    String[] newGameState =  {"AFCB1616181617D0000000000","A1Me04S2c13a34a1FbbbeB0MS0c11b12e13d4Ff"};
    assertEquals(newGameState[1],Azul.applyMove(gameState,"A1F")[1]);     }       */
}
