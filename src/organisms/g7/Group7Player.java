package organisms.g7;

import organisms.Move;
import organisms.OrganismsPlayer;
import organisms.ui.OrganismsGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Group7Player implements OrganismsPlayer {

    private OrganismsGame game;

    private int dna;

    private int energy = 0;

    private ThreadLocalRandom random;

    @Override
    public void register(OrganismsGame game, int dna) throws Exception {
        this.game= game;
        this.dna = dna;
        this.random = ThreadLocalRandom.current();
    }

    @Override
    public String name() { return "Group 7 Player"; }

    @Override
    public Color color() {
        return new Color(87, 6, 236, 204);
    }

    @Override
    public Move move(int foodHere, int energyLeft, boolean foodN, boolean foodE,
                     boolean foodS, boolean foodW, int neighborN, int neighborE,
                     int neighborS, int neighborW) {

        Action action = null;
        boolean[] food = new boolean[]{foodN, foodE, foodS, foodW};
        int[] neighbors = new int[]{neighborN, neighborE, neighborS, neighborW};
        ArrayList<Integer> emptySquares = new ArrayList<>();
        Action[] dirs = new Action[]{Action.NORTH, Action.EAST, Action.SOUTH, Action.WEST};

        //if present square has food, stay put
        if (foodHere > 0) {
            return Move.movement(Action.STAY_PUT);
        }
        else {
            //move to adjacent square with food if present
            for (int i=0; i<4; ++i) {
                if (food[i] && neighbors[i] == -1) {
                    action = dirs[i];
                    break;
                }
                if (neighbors[i] == -1) {
                    emptySquares.add(i);
                }
            }
            //if no food present
            if (action == null) {
                //if no open spaces, stay put
                if (emptySquares.size() == 0) {
                    return Move.movement(Action.STAY_PUT);
                }
                //otherwise choose random empty square to move to
                int randIndex = this.random.nextInt(0, emptySquares.size());
                action = dirs[emptySquares.get(randIndex)];
            }
            //if sufficient energy, reproduce
            if (energyLeft >= 100) {
                return Move.reproduce(action, 3);
            }
            else {
                return Move.movement(action);
            }
        }
    }

    @Override
    public int externalState() {
        return energy;
    }
}

