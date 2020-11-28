package org.henix.workshop.qlearn.game;

import org.henix.workshop.qlearn.ai.QLearningPlayerBuilder;
import org.henix.workshop.qlearn.concepts.Token;
import org.henix.workshop.qlearn.impl.HumanPlayer;

public class HumanVsQLearnGame extends Game{

    public HumanVsQLearnGame() {
        super(new HumanPlayer(Token.X), new QLearningPlayerBuilder()
                                        .newCompetitorPlayer(Token.O)
                                        .withAnyTable()
                                        .build());
    }




}
