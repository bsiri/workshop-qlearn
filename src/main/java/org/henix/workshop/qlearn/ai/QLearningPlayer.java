package org.henix.workshop.qlearn.ai;

import org.henix.workshop.qlearn.concepts.CellState;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Player;
import org.henix.workshop.qlearn.concepts.Token;

import java.util.Collection;

public class QLearningPlayer implements Player {

    private Token token;

    private QTable qtable;
    private float alpha;
    private float gamma;
    private float greediness;
    private boolean competitive;



    QLearningPlayer(Token token, QTable qtable, float alpha, float gamma, float greediness, boolean competitive) {
        this.token = token;
        this.qtable = qtable;
        this.alpha = alpha;
        this.gamma = gamma;
        this.greediness = greediness;
        this.competitive = competitive;
    }

    @Override
    public String toString(){
        return "Qlearning AI "+getToken();
    }


    @Override
    public Token getToken() {
        return token;
    }

    @Override
    public Move nextMove(CellState[][] gridState, Collection<Move> availableMoves) {

        // ???
        return availableMoves.iterator().next();

    }

    public void train(/* I'm sure I need parameters here */){
        // ????
    }
}
