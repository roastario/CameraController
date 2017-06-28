package io.highlylikely.forestry;

import io.highlylikely.maths.Angles;

/**
 * Created by stefano
 */
public class Tree {

    private final double phi;


    public Tree(double phi) {
        this.phi = phi;
    }

    public Tree(int x, int y){
        this.phi = Angles.convertToQuadrantAwareAngle(x, y);
    }

    public double getPhi(){
        return phi;
    }
}
