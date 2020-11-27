package org.henix.workshop.qlearn;

import org.henix.workshop.qlearn.game.HumanVsQLearnGame;
import org.henix.workshop.qlearn.game.HumanVsMachineGame;
import org.henix.workshop.qlearn.game.TrainingSession;

import java.util.Scanner;
import static org.henix.workshop.qlearn.tools.StaticFunctions.*;

public class Main {

    public static void main(String[] args) {
        repl();
    }


    static void repl(){

        Scanner scanner= new Scanner(System.in);

        String command = "";

        while (! command.equals("q")) {
            print("what now ?\n");
            print("\thvs : human vs stupid ai");
            print("\thvq : human vs qlearning");
            print("\tt : ai training session");
            print("\tq : quit");

            command = scanner.nextLine();

            switch (command){
                case "hvs" : new HumanVsMachineGame().run(); break;
                case "hvq" : new HumanVsQLearnGame().run(); break;
                case "t" : new TrainingSession().run(); break;
                case "q" : break;
                default: print("uh ?"); break;
            }

        }

        print("coward");


    }


}
