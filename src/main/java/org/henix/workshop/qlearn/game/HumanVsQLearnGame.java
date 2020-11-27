package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.ai.QLearningPlayer;
import org.henix.workshop.qlearn.ai.QLearningPlayerBuilder;
import org.henix.workshop.qlearn.concepts.GameController;
import org.henix.workshop.qlearn.concepts.Token;
import org.henix.workshop.qlearn.impl.HumanPlayer;

public class HumanVsQLearnGame implements GameController {

    @Override
    public void run() {
        HumanPlayer human = new HumanPlayer(Token.X);
        QLearningPlayer qlearn = new QLearningPlayerBuilder()
                                        .newCompetitorPlayer(Token.O)
                                        .withAnyTable()
                                        .build();

        new HumanVsMachineGame(human, qlearn).run();
    }




}
