package org.henix.workshop.qlearn.ai;

import org.henix.workshop.qlearn.concepts.CellState;
import org.henix.workshop.qlearn.concepts.Move;
import org.henix.workshop.qlearn.concepts.Player;
import org.henix.workshop.qlearn.concepts.Token;
import org.henix.workshop.qlearn.game.TrainingSession;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QLearningPlayer implements Player {

    private Token token;

    private QTable qtable;
    private float alpha;
    private float gamma;
    private float greediness;
    private boolean competitive;

    private CellState[][] lastState;
    private Move lastMove;

    private ThreadLocalRandom random = ThreadLocalRandom.current();

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
        return "Qlearning AI " + getToken();
    }


    @Override
    public Token getToken() {
        return token;
    }

    void remember(CellState[][] state, Move move){
        this.lastState=state;
        this.lastMove = move;
    }

    public void reset(){
        this.lastMove = null;
        this.lastState = null;
    }

    @Override
    public Move nextMove(CellState[][] gridState, List<Move> availableMoves) {
        // choose the strategy : exploration or proven efficiency ?
        boolean isGreedy = competitive || random.nextFloat() < greediness;

        Move move;
        if (isGreedy){
            move = qtable.findBestMove(gridState, availableMoves);
        }
        else{
            int randIdx = random.nextInt(availableMoves.size());
            move = availableMoves.get(randIdx);
        }

        // remember what just happened
        remember(gridState, move);

        return move;
    }

    // Reminder of the Bell formula : Q(s,a) <- (1-alpha)Q(s,a) + alpha(reward + gamma*max(Q(s',a'))
    // with (s,a) = (last state, last move) and (s',a') = (new state, best move for that new state)
    public void train(CellState[][] newState, List<Move> availableMove, float reward, boolean gameOver){

        if (lastMove == null) return;

        Float qsa = qtable.getValueFor(lastState, lastMove);

        // if the game is over, there is no next action (Q(s'a') is zero)
        // otherwise we can lookup for the value of the best next action
        Float qsa_prime = (gameOver) ? 0.0f : qtable.findBestValue(newState, availableMove);

        // now we apply the formula
        Float newQsa = (1.0f - alpha)*qsa + alpha*(reward + gamma*qsa_prime);

        // lastly we update the qtable
        qtable.assignValueFor(lastState, lastMove, newQsa);
    }


}
