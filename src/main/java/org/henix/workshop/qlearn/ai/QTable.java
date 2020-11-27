package org.henix.workshop.qlearn.ai;

import org.henix.workshop.qlearn.concepts.CellState;
import org.henix.workshop.qlearn.concepts.Grid;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Token;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class QTable {

    private Map<StateAction, Float> valueByStateAction = new ConcurrentHashMap<>(20000);

    public Float getValueFor(CellState[][] gridState, Move playerMove){
        StateAction sa = StateAction.from(gridState, playerMove);
        return valueByStateAction.getOrDefault(sa, 0.0f);
    }

    public Move findBestMove(CellState[][] gridState, List<Move> allMoves){
        Move firstMove = allMoves.get(0);
        if (allMoves.size() == 1) return firstMove;

        SubjectiveState[] state = SubjectiveState.buildFlatArrayFrom(gridState, firstMove.token);

        Float max = Float.NEGATIVE_INFINITY;
        Move best = firstMove;
        for (Move m : allMoves){
            StateAction sa = new StateAction(state, m.x*Grid.SIZE_Y+m.y);
            Float mValue = valueByStateAction.getOrDefault(sa, 0.0f);
            if (mValue > max){
                max = mValue;
                best = m;
            }
        }
        return best;
    }

    public Float findBestValue(CellState[][] gridState, List<Move> allMoves){
        Move best = findBestMove(gridState, allMoves);
        return valueByStateAction.getOrDefault(StateAction.from(gridState, best), 0.0f);
    }

    public void assignValueFor(CellState[][] gridState, Move playerMove, Float value){
        StateAction sa = StateAction.from(gridState, playerMove);
        valueByStateAction.put(sa, value);
    }

    static final class StateAction{

        public final SubjectiveState[] state;
        public final int action;


        StateAction(SubjectiveState[] state, int action) {
            this.state = state;
            this.action = action;
        }

        static StateAction from(CellState[][] gridState, Move playerMove){
            SubjectiveState[] state = SubjectiveState.buildFlatArrayFrom(gridState, playerMove.token);
            int action = playerMove.x * Grid.SIZE_Y + playerMove.y;
            return new StateAction(state, action);
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StateAction that = (StateAction) o;
            return action == that.action &&
                    Arrays.equals(state, that.state);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(action);
            result = 31 * result + Arrays.hashCode(state);
            return result;
        }


    }


    enum SubjectiveState{
        MINE,
        OPPONENT,
        NEUTRAL;

        // The player token is the reference here, signaling which state will be her (SubjState.MINE),
        // those of her opponent (SubState.OPPONENT), or neutral.
        static SubjectiveState[] buildFlatArrayFrom(CellState[][] gridState, Token playerToken){
            SubjectiveState[] state = new SubjectiveState[9];
            int index = 0;
            for (int x=0; x< Grid.SIZE_X; x++) for (int y=0; y< Grid.SIZE_Y; y++){
                CellState gridstate = gridState[x][y];

                if (gridstate == CellState.EMPTY) state[index] = SubjectiveState.NEUTRAL;
                else if (gridstate.matches(playerToken)) state[index] = SubjectiveState.MINE;
                else state[index] = SubjectiveState.OPPONENT;

                index++;
            }
            return state;
        }
    }

}
