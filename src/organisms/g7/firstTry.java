package organisms.g7;

import organisms.Move;
import organisms.ui.OrganismsGame;
import organisms.OrganismsPlayer;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;

public class firstTry implements OrganismsPlayer {
    private OrganismsGame game;
    private int dna;
    private ThreadLocalRandom random;

    private int energy;
    private ArrayList<Action> pastActions = new ArrayList<Action>();

    @Override
    public void register(OrganismsGame game, int dna) throws Exception {
        this.game = game;
        this.dna = dna;
        this.random = ThreadLocalRandom.current();
    }

    @Override
    public String name() {
        return "firstTry Player";
    }

    @Override
    public Color color() {
        return new Color(255, 124, 0, 255);
    }

    @Override
    public Move move(int foodHere, int energyLeft, boolean foodN, boolean foodE,
                     boolean foodS, boolean foodW, int neighborN, int neighborE,
                     int neighborS, int neighborW) {
        // Moves are totally random and may not be valid depending on whether
        // neighboring squares are occupied!
        if(energyLeft>255) {
            this.energy = 255;
        }
        else{
            this.energy=energyLeft;
        }

        if(foodHere>0){
            return Move.movement(Action.STAY_PUT);
        }

        if(energyLeft>250){
            int childPosIndex = this.random.nextInt(1, 5);
            Action childPosChoice = Action.fromInt(childPosIndex);
            int childKey = this.random.nextInt();
            return Move.reproduce(childPosChoice, childKey);
        }

        if(foodN && neighborN==-1){
            return Move.movement(Action.NORTH);
        }
        if(foodS && neighborS==-1){
            return Move.movement(Action.SOUTH);
        }
        if(foodW && neighborW==-1){
            return Move.movement(Action.WEST);
        }
        if(foodE && neighborE==-1){
            return Move.movement(Action.EAST);
        }
        else{
            if(neighborN>=0 && neighborN<50){
                return Move.movement(Action.SOUTH);
            }
            if(neighborS>=0 && neighborS<50){
                return Move.movement(Action.NORTH);
            }
            if(neighborW>=0 && neighborW<50){
                return Move.movement(Action.EAST);
            }
            if(neighborE>=0 && neighborE<50){
                return Move.movement(Action.WEST);
            }

            int actionIndex = this.random.nextInt(1,5);
            Action actionChoice = Action.fromInt(actionIndex);
            return Move.movement(actionChoice);
        }
    }

    @Override
    public int externalState() {
        //return 0;
        return this.energy;
    }


}
