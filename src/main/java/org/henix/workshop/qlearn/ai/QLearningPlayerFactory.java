package org.henix.workshop.qlearn.ai;

import org.henix.workshop.qlearn.concepts.Token;

public class QLearningPlayerFactory {

    public static final float DEFAULT_ALPHA = 0.05f;
    public static final float DEFAULT_GAMMA = 0.9f;
    public static final float DEFAULT_GREEDINESS = 0.6f;

    static private final QTable table1 = new QTable();
    static private final QTable table2 = new QTable();

    private Token token;

    private float alpha = DEFAULT_ALPHA;
    private float gamma = DEFAULT_GAMMA;
    private float greediness = DEFAULT_GREEDINESS;
    private boolean competitive=false;
    private QTable qtable = table1;


    public QLearningPlayerFactory newLearningPlayer(Token token){
        this.token = token;
    }


    public QLearningPlayerFactory withLearningParameters(float alpha, )


    



}
