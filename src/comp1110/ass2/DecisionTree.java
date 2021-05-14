package comp1110.ass2;

/**
 * @author Ruizheng Shen
 * @date 2021.5.15
 * @version 1.0
 */

import javax.swing.tree.TreeNode;
import java.util.ArrayList;

/**
 * A decision tree to make smart decision on moves.
 */
public class DecisionTree {

    public static final int DEPTH = 3;

    public static class treeNode {
        /**
         * The gameState in treeNode is
         */
        String[] gameState; // current gameState.
        String path; // record the move made by parent.
        double mark; // the evaluate result.
        int depth; // the depth of this node.
        ArrayList<treeNode> children = new ArrayList<>(); // the children of this node.
        treeNode parent; // pointer to parent node.

        public treeNode(String[] gameState, int curDepth, treeNode parent, String path) {
            // TODO
            this.gameState = gameState;
            this.depth = curDepth;
            this.parent = parent;
            this.path = path;
        }
    }

    treeNode root; // The root node.
    ArrayList<treeNode> leaves; // the leaves of this tree.

    /**
     * Constructor of Decision-Tree, given a game state and the depth of searching, build the tree.
     * @param gameState current game state.
     */
    public DecisionTree(String[] gameState) {
        // TODO
        this.root = new treeNode(gameState, 0, null, ""); // set up the root node.
        buildTree(gameState, this.root);
    }

    /**
     * Build up the decision tree.
     *     1) generate all possible moves.
     *     2) For each move, apply move to the current state, build a new child node for the result state.
     *     3) If current depth is up to the set value(3 in this case), this node should be the leaf node.
     *     4) evaluate this node.
     * @param gameState current game state
     * @param parent parent node
     */
    private void buildTree(String[] gameState, treeNode parent) {
        if (parent.depth == DEPTH) {
            // reach to the set depth, this node should be a leaf node.
            this.leaves.add(parent);
        }
        ArrayList<String> moves = Azul.generateMoves(gameState); // find out moves. TODO: write another method to generate all of the moves.
        for (String move: moves) {
            parent.children.add(new treeNode(Azul.applyMove(gameState, move), parent.depth + 1, parent, move + "/")); // use "/" to split the path.
        }
    }

    /**
     * find the leaf node which has the greatest mark, return the first path from the root node.
     * @return code of greatest move.
     */
    public String greatestMove() {
        // TODO test
        double greatest = 0.0;
        String path = "";
        for (treeNode node: this.leaves) {
            if (node.mark > greatest) {
                greatest = node.mark;
                path = node.path;
            }
        }
        String[] splitted = path.split("/"); // split the path by "/"
        return splitted[0]; // return the first move.
    }

    /**
     * Evaluate each state(The treeNodes), find the best one.
     * @return mark
     */
    public double evaluate() {
        // TODO The evaluate method
        return 0.0;
    }
}
