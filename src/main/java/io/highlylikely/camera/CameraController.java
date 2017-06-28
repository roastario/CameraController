package io.highlylikely.camera;

import io.highlylikely.forestry.Tree;
import io.highlylikely.forestry.Trees;

import java.util.List;

/**
 * Created by stefano
 */
public class CameraController {


    private final Trees backingTrees;
    private final double viewPort;

    public CameraController(List<Tree> treeDistribution, double viewPort) {
        backingTrees = new Trees(treeDistribution);
        this.viewPort = viewPort;
    }

    //normally we would have to check every tree for every tree
    //but as if a tree (T_X) is visible between T_A and T_B, we know it will be visible between T_A+1 and T_C
    //aslong as X is > A+1
    //so all we have to do is check how many "new" trees are visible
    //to do this, we can start scanning from the previously highest tested tree
    //we then add this to the gap between A+1 and B
    //initial = B - (A+1)
    //total = initial + new
    //this should keep the complexity of search of a pre-sorted list to O(n)
    public CameraSetup determineOptimalSetup() {
        int currentHighestCheckedIdx = 0;
        int maxSeen = 0;
        int maxSeenAtIdx = 0;
        for (int currentIdx = 0; currentIdx < backingTrees.sizeOfForest(); currentIdx++) {
            int indexToScanFrom = Math.max(currentHighestCheckedIdx, currentIdx);
            int initiallyVisible = indexToScanFrom - currentIdx;
            double maxAngle = (backingTrees.getAngleOfTree(indexToScanFrom) + viewPort);
            Trees.ScanResult scanResult = backingTrees.howManyTreesCanBeSeenBetweenAngles(indexToScanFrom, maxAngle);
            int totalNumberVisible = initiallyVisible + scanResult.getNumberSeen();
            if (totalNumberVisible > maxSeen) {
                maxSeen = totalNumberVisible;
                maxSeenAtIdx = currentIdx;
            }
            currentHighestCheckedIdx = scanResult.getHighestIdxSeen();
        }
        return new CameraSetup(backingTrees.getAngleOfTree(maxSeenAtIdx), maxSeen);
    }


    public static class CameraSetup {
        private final double angle;
        private final int numberSeen;

        public CameraSetup(double angle, int numberSeen) {
            this.angle = angle;
            this.numberSeen = numberSeen;
        }

        @Override
        public String toString() {
            return "CameraSetup{" +
                    "angle=" + angle +
                    ", numberSeen=" + numberSeen +
                    '}';
        }

        public double getAngle() {
            return angle;
        }

        public int getNumberSeen() {
            return numberSeen;
        }
    }
}
