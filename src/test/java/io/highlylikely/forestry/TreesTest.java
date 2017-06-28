package io.highlylikely.forestry;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.PI;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by stefano
 */
public class TreesTest {


    public static final Tree ZERO_DEGREES = new Tree(0);
    public static final Tree FIFTEEEN_DEGREES = new Tree(PI / 12);
    public static final Tree THIRTY_DEGREES = new Tree(PI / 6);
    public static final Tree FORTY_FIVE_DEGREES = new Tree(PI / 4);
    public static final Tree SIXTY_DEGREES = new Tree(PI / 3);
    public static final Tree SEVENTY_FIVE_DEGREES = new Tree(5 * PI / 12);
    public static final Tree NINETY_DEGREES = new Tree(PI / 2);
    public static final Tree ONE_HUNDED_AND_FIVE_DEGREES = new Tree(7 * PI / 12);
    public static final Tree ONE_HUNDED_AND_TWENTY_DEGREES = new Tree(2 * PI / 3);
    public static final Tree ONE_HUNDED_AND_THIRTY_FIVE_DEGREES = new Tree(3 * PI / 4);
    public static final Tree ONE_HUNDED_AND_FIFTY_DEGREES = new Tree(5 * PI / 6);
    public static final Tree ONE_HUNDED_AND_SIXTY_FIVE_DEGREES = new Tree(11 * PI / 12);
    public static final Tree ONE_HUNDED_AND_EIGHTY_DEGREES = new Tree(PI);
    public static final Tree TWO_HUNDED_AND_TEN_DEGREES = new Tree(7 * PI / 6);
    public static final Tree TWO_HUNDED_AND_FORTY_DEGREES = new Tree(4 * PI / 3);
    public static final Tree TWO_HUNDED_AND_SEVENTY_DEGREES = new Tree(3 * PI / 2);
    public static final Tree THREE_HUNDED_AND_FORTY_FIVE_DEGREES = new Tree(23 * PI/ 12);

    @Test
    public void shouldCorrectlyDetermineHowManyForNonWrapped() throws Exception {


        List<Tree> trees = Arrays.asList(
                ZERO_DEGREES,
                THIRTY_DEGREES,
                FORTY_FIVE_DEGREES,
                SIXTY_DEGREES,
                NINETY_DEGREES,
                ONE_HUNDED_AND_FIVE_DEGREES,
                ONE_HUNDED_AND_TWENTY_DEGREES,
                ONE_HUNDED_AND_EIGHTY_DEGREES
        );

        Trees treeManager = new Trees(trees);
        double viewPort = PI / 6;

        Trees.ScanResult scanResult = treeManager.howManyCanBeSeenFromStartingTree(0, viewPort);

        Assert.assertThat(scanResult.getHighestIdxSeen(), is(0));
        Assert.assertThat(scanResult.getNumberSeen(), is(1));

        scanResult = treeManager.howManyCanBeSeenFromStartingTree(1, viewPort);
        Assert.assertThat(scanResult.getNumberSeen(), is(2));
        Assert.assertThat(scanResult.getHighestIdxSeen(), is(2));

        scanResult = treeManager.howManyCanBeSeenFromStartingTree(2, Math.PI / 2);

        //starting angle = 45
        //end angle = 45 + 90 = 135
        //45,60,90,105, 120 should be seen

        Assert.assertThat(scanResult.getHighestIdxSeen(), is(6));
        Assert.assertThat(scanResult.getNumberSeen(), is(5));
    }


    @Test
    public void shouldCorrectlyCountTheNumberOfTreesWhenWrapping() throws Exception {
        List<Tree> trees = Arrays.asList(
                ZERO_DEGREES,
                THIRTY_DEGREES,
                FORTY_FIVE_DEGREES,
                ONE_HUNDED_AND_FIVE_DEGREES,
                new Tree(2 * Math.PI - 0.00000001)
        );


        Trees treeManager = new Trees(trees);
        Trees.ScanResult scanResult = treeManager.howManyCanBeSeenFromStartingTree(4, Math.PI / 2);

        //starting at 2*PI, ending angle = 90
        //so 2PI, 0, 30, 45  should be seen
        //105 should not be
        System.out.println(scanResult);
        Assert.assertThat(scanResult.getNumberSeen(), is(4));
    }
}