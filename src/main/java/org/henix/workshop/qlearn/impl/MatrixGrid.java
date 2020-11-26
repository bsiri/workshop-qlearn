package org.henix.workshop.qlearn.impl;

import org.henix.workshop.qlearn.concepts.*;

import java.util.*;

/*
    Matrix-based implementation of Grid
 */
public class MatrixGrid implements Grid {

    public static final int MAX_MOVE_CNT = Grid.SIZE_X * Grid.SIZE_Y;

    private final CellState state[][];

    int _movecount = 0;
    private List<int[]> _x_moves = new ArrayList<>(5);
    private List<int[]> _o_moves = new ArrayList<>(5);


    MatrixGrid(CellState state[][]){
        this.state = state;
        // also must update the corresponding moves
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++){
            int[] move = new int[]{x,y};
            switch (state[x][y]){
                case X: _x_moves.add(move); break;
                case O: _o_moves.add(move); break;
                default: break;
            }
        }
        _movecount = _x_moves.size() + _o_moves.size();
    }

    public MatrixGrid(){
        state = new CellState[Grid.SIZE_X][Grid.SIZE_Y];
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++) state[x][y] = CellState.EMPTY;
    }

    @Override
    public void play(int x, int y, Token token) {
        play(new Move(x, y, token));
    }

    @Override
    public void play(Move move) {
        CellState moveState = fromToken(move.token);
        CellState current = state[move.x][move.y];
        if (current != CellState.EMPTY) throw new RuntimeException("Hey, you can't play that cell because there's already a "+current + " here !");
        state[move.x][move.y] = moveState;

        // update internal state variables
        int[] pos = new int[]{move.x, move.y};
        if (moveState == CellState.X) _x_moves.add(pos); else _o_moves.add(pos);
        _movecount++;
    }

    @Override
    public CellState[][] getState() {
        CellState copy[][] = new CellState[Grid.SIZE_X][Grid.SIZE_Y];
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++) copy[x][y] = state[x][y];
        return copy;
    }

    @Override
    public List<Move> getAvailableMoveFor(Player player) {
        List<Move> moves = new ArrayList<>();
        for (int x=0; x<Grid.SIZE_X; x++) for (int y=0; y<Grid.SIZE_Y; y++){
            if (state[x][y] == CellState.EMPTY) moves.add(new Move(x, y, player.getToken()));
        }
        return moves;
    }

    @Override
    public boolean isGameOver() {
        CellState winnertoken= searchWinner();
        if (winnertoken != null)
            return true;
        else
            return (_movecount >= MAX_MOVE_CNT);
    }

    @Override
    public GameOutcome getOutcome() {
        CellState winnertoken = searchWinner();
        if (winnertoken == CellState.X) return GameOutcome.PLAYER_X_WINS;
        if (winnertoken == CellState.O) return GameOutcome.PLAYER_O_WINS;
        if (_movecount >= MAX_MOVE_CNT) return GameOutcome.DRAW;
        return GameOutcome.NOT_OVER_YET;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder
                .append(" |0|1|2\n")
                .append("-+-+-+-\n")
                .append(String.format("0|%s|%s|%s\n", state[0][0], state[0][1], state[0][2]))
                .append("-+-+-+-\n")
                .append(String.format("1|%s|%s|%s\n", state[1][0], state[1][1], state[1][2]))
                .append("-+-+-+-\n")
                .append(String.format("2|%s|%s|%s\n", state[2][0], state[2][1], state[2][2]))
                .append("-+-+-+-\n");
        return builder.toString();

    }

    //************ private stuffs *************

    private static int _x = 0;
    private static int _y = 1;


    // returns null if there is no winner yet
    private CellState searchWinner(){

        // shortcut : if less than 5 moves were played,
        // there can be no winner
        if (_movecount < 5) return null;
        if (areWiningMoves(_x_moves)) return CellState.X;
        if (areWiningMoves(_o_moves)) return CellState.O;
        return null;
    }



    /*
     * Search within that position list if the victory condition is met.
     * We do that by inspecting all triplets and check that the following properties :
     * - all their 'x' component are either the same, either all different
     * - all their 'y' component are either the same, either all different
     * - in the special case both 'x' and 'y' components meet the criteria 'all different', the central position (1,1) must also belong to the set.
     */
    private boolean areWiningMoves(List<int[]> positionList){
        int len = positionList.size();

        boolean isWin = false;

        // jeu caché :
        // - je paye un coca à celui qui lit ça
        // - et un deuxième s'il me dit ce que c'est que ce outerloop
        outerloop:
        for (int i1 = 0; i1 < len-2; i1++)
            for (int i2 = i1+1; i2 < len-1; i2++)
                for (int i3 = i2+1; i3 < len; i3++){

                    int[] p1 = positionList.get(i1);
                    int[] p2 = positionList.get(i2);
                    int[] p3 = positionList.get(i3);

                    isWin = arePositionAligned(p1, p2, p3);

                    if (isWin) {
                        break outerloop;
                    }
                }

        return isWin;
    }



    boolean arePositionAligned(int[] p1, int[] p2, int[] p3){

        boolean diffX = allDifferent(p1[_x], p2[_x], p3[_x]);
        boolean diffY = allDifferent(p1[_y], p2[_y], p3[_y]);

        if (diffX && diffY){
            return hasCenter(p1, p2, p3);
        }
        else{
            boolean conditionX = areXEither(p1, p2, p3, this::allSame, this::allDifferent);
            boolean conditionY = areYEither(p1, p2, p3, this::allSame, this::allDifferent);
            return (conditionX && conditionY);
        }

    }


    private boolean allSame(int v1, int v2, int v3){
        return (v1==v2) && (v2==v3);
    }

    private boolean allDifferent(int v1, int v2, int v3){
        if (v1 == v2) return false;
        if (v2 == v3) return false;
        if (v3 == v1) return false;
        return true;
    }

    private boolean hasCenter(int[] ...positions){
        for (int[] p : positions) {
            if (p[_x] == 1 && p[_y] == 1) return true;
        }
        return false;
    }


    private boolean areXEither(int[] p1, int[] p2, int[] p3, AlignmentPredicate...predicates){
        for (AlignmentPredicate p : predicates){
            if (p.predict(p1[_x], p2[_x], p3[_x])) return true;
        }
        return false;
    }

    private boolean areYEither(int[] p1, int[] p2, int[] p3, AlignmentPredicate...predicates){
        for (AlignmentPredicate p : predicates){
            if (p.predict(p1[_y], p2[_y], p3[_y])) return true;
        }
        return false;
    }

    @FunctionalInterface
    private interface AlignmentPredicate {
        boolean predict(int v1, int v2, int v3);
    }

    private CellState fromToken(Token token){
        return (token == Token.X) ? CellState.X : CellState.O;
    }

}
