package io.highlylikely.maths;

/**
 * Created by stefano on 28/06/2017.
 */
public class Angles {


    public static double convertToQuadrantAwareAngle(int x, int y) {

        double theta = Math.tan(x/(double)y);

        if (x >= 0 && y >= 0) {
            //quadrant 1
            return theta;

        } else if (x < 0 && y >= 0) {
            //quadrant 2
            return theta + Math.PI;

        }else if (x<0 && y<0){
            //quadrant 3
            return theta + Math.PI;

        }else{
            //quadrant 3
            return theta + 2 * Math.PI;
        }

    }


}
