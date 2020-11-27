package org.henix.workshop.qlearn.ai;

import org.henix.workshop.qlearn.concepts.CellState;
import org.henix.workshop.qlearn.concepts.Grid;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Token;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class QTable {

    private Map<StateAction, Float> valueByStateAction = new ConcurrentHashMap<>(20000);

    public Float getValueFor(CellState[][] gridState, Move playerMove){
        StateAction sa = StateAction.from(gridState, playerMove);
        return valueByStateAction.get(sa);
    }

    public void assignValueFor(CellState[][] gridState, Move playerMove, Float value){
        StateAction sa = StateAction.from(gridState, playerMove);
        valueByStateAction.put(sa, value);
    }


    public enum SubjectiveState{
        MINE,
        OPPONENT,
        NEUTRAL;
    }

    static final class StateAction{
        public final SubjectiveState[] state;
        public final int action;

        StateAction(SubjectiveState[] state, int action) {
            this.state = state;
            this.action = action;
        }

        static StateAction from(CellState[][] gridState, Move playerMove){
            // The player token is the reference here, signaling which state will be his.
            Token playerToken = playerMove.token;
            SubjectiveState[] state = new SubjectiveState[9];

            int index = 0;
            for (int x=0; x< Grid.SIZE_X; x++) for (int y=0; y< Grid.SIZE_Y; y++){
                CellState gridstate = gridState[x][y];

                if (gridstate == CellState.EMPTY) state[index] = SubjectiveState.NEUTRAL;
                else if (gridstate.matches(playerToken)) state[index] = SubjectiveState.MINE;
                else state[index] = SubjectiveState.OPPONENT;

                index++;
            }

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

}
