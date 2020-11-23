package org.henix.workshop.qlearn;

import org.henix.workshop.qlearn.game.HumanVsMachineGameController;

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
            print("\t1 : human vs stupid ai");
            print("\tq : quit");

            command = scanner.nextLine();

            switch (command){
                case "1" : new HumanVsMachineGameController().run(); break;
                case "q" : break;
                default: print("uh ?"); break;
            }

        }

        print("coward");


    }


}
