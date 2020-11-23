package org.henix.workshop.qlearn.concepts;

public enum GameOutcome {
    PLAYER_X_WINS(){
        public String toString(){
            return "Player X wins !";
        }
    },
    PLAYER_O_WINS(){
        public String toString(){
            return "Player O wins !";
        }
    },
    DRAW(){
        public String toString(){
            return "Draw, no one wins";
        }
    },
    NOT_OVER_YET(){
        public String toString(){
            return "Hey, not done yet !";
        }
    };
}
