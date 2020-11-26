package org.henix.workshop.qlearn.ai;

import org.henix.workshop.qlearn.concepts.Token;

import java.util.concurrent.ThreadLocalRandom;

public class QLearningPlayerBuilder {

    public static final float DEFAULT_ALPHA = 0.05f;
    public static final float DEFAULT_GAMMA = 0.9f;
    public static final float DEFAULT_GREEDINESS = 0.1f;

    public static final QTable table1 = new QTable();
    public static final QTable table2 = new QTable();

    private Token token;

    private float alpha = DEFAULT_ALPHA;
    private float gamma = DEFAULT_GAMMA;
    private float greediness = DEFAULT_GREEDINESS;
    private boolean competitive=false;
    private QTable qtable = table1;

    private ThreadLocalRandom random = ThreadLocalRandom.current();


    public QTableSelector<LearnerBuilder> newLearningPlayer(Token token){
        this.token = token;
        return new QTableSelector<LearnerBuilder>(new LearnerBuilder());
    }

    public QTableSelector<CompetitorBuilder> newCompetitorPlayer(Token token){
        this.token = token;
        this.competitive = true;
        return new QTableSelector(new CompetitorBuilder());
    }


    public class QTableSelector<PBF extends PlayerBuilderFinalizer>{

        PBF finalizer;

        public QTableSelector(PBF finalizer) {
            this.finalizer = finalizer;
        }

        public PBF withTable1(){
            qtable = table1;
            return finalizer;
        }

        public PBF withTable2(){
            qtable = table2;
            return finalizer;
        }

        public PBF withAnyTable(){
            qtable = (random.nextInt(1) < 0.5) ? table1 : table2;
            return finalizer;
        }

    }

    interface PlayerBuilderFinalizer{

        QLearningPlayer build();
    }


    public class LearnerBuilder implements PlayerBuilderFinalizer{

        public LearnerBuilder withLearningParameters(float alpha, float gamma, float greediness){
            QLearningPlayerBuilder.this.alpha = alpha;
            QLearningPlayerBuilder.this.gamma = gamma;
            QLearningPlayerBuilder.this.greediness = greediness;
            return this;
        }

        public LearnerBuilder withDefaultLearningParameters(){
            QLearningPlayerBuilder.this.alpha = DEFAULT_ALPHA;
            QLearningPlayerBuilder.this.gamma = DEFAULT_GAMMA;
            QLearningPlayerBuilder.this.greediness = DEFAULT_GREEDINESS;
            return this;
        }

        public LearnerBuilder withGreediness(float greediness){
            QLearningPlayerBuilder.this.greediness = greediness;
            return this;
        }

        public QLearningPlayer build(){
            return new QLearningPlayer(token, qtable, alpha, gamma, greediness, false);
        }

    }

    public class CompetitorBuilder implements PlayerBuilderFinalizer{

        public QLearningPlayer build(){
            return new QLearningPlayer(token, qtable, 0.0f, 0.0f, 1.01f, true);
        }
    }

}
