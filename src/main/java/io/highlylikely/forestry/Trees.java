package io.highlylikely.forestry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.PI;

/**
 * Created by stefano
 */
public class Trees {

    private final List<Tree> trees;

    public Trees(List<Tree> trees) {

        ArrayList<Tree> newTrees = new ArrayList<Tree>(trees);
        Collections.sort(newTrees, new Comparator<Tree>() {
            public int compare(Tree o1, Tree o2) {
                return Double.compare(o1.getPhi(), o2.getPhi());
            }
        });
        this.trees = newTrees;
    }


    public ScanResult howManyTreesCanBeSeenBetweenAngles(int startingIdx, double unwrappedMaxAngle) {

        int numberSeen = 0;
        int currentMaxSeen = 0;

        double startingAngle = trees.get(startingIdx).getPhi();
        double maxAngle = unwrappedMaxAngle % (2 * PI);
        for (int i = 0; i < trees.size(); i++) {
            int wrappedI = (i + startingIdx) % trees.size();
            double phiToInvestigate = trees.get(wrappedI).getPhi();
            boolean isVisible = false;
            if (maxAngle < startingAngle) {
                //we have wrapped round
                //a tree is visible if it's angle is between startingAngle and 2PI
                //or if it's angle is between 0 and maxAngle
                //we will never have an angle of 2PI (2PI -> 0)
                if (phiToInvestigate >= startingAngle && phiToInvestigate < 2 * PI) {
                    isVisible = true;
                } else if (phiToInvestigate < maxAngle && phiToInvestigate >= 0) {
                    isVisible = true;
                }
            } else {
                if (phiToInvestigate < maxAngle && phiToInvestigate >= startingAngle) {
                    isVisible = true;
                }
            }
            if (isVisible) {
                numberSeen++;
                currentMaxSeen = wrappedI;
            } else {
                break;
            }
        }

        return new ScanResult(numberSeen, currentMaxSeen);
    }

    public ScanResult howManyCanBeSeenFromStartingTree(int startingIdx, double viewPort) {
        double startingAngle = trees.get(startingIdx).getPhi();
        double maxAngle = (startingAngle + viewPort);
        return howManyTreesCanBeSeenBetweenAngles(startingIdx, maxAngle);

    }

    public int sizeOfForest() {
        return trees.size();
    }

    public double getAngleOfTree(int idx) {
        return trees.get(idx).getPhi();
    }


    public static class ScanResult {
        private final int numberSeen;
        private final int highestIdxSeen;

        public ScanResult(int numberSeen, int highestIdxSeen) {
            this.numberSeen = numberSeen;
            this.highestIdxSeen = highestIdxSeen;
        }

        @Override
        public String toString() {
            return "ScanResult{" +
                    "numberSeen=" + numberSeen +
                    ", highestIdxSeen=" + highestIdxSeen +
                    '}';
        }

        public int getNumberSeen() {
            return numberSeen;
        }

        public int getHighestIdxSeen() {
            return highestIdxSeen;
        }
    }
}
