package io.highlylikely.camera;

import io.highlylikely.forestry.Tree;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.highlylikely.forestry.TreesTest.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by stefano
 */
public class CameraControllerTest {

    @Test
    public void shouldCorrectlyDetermineAngleWithNoWrap() throws Exception {

        List<Tree> trees = Arrays.asList(
                ZERO_DEGREES,
                THIRTY_DEGREES,
                SIXTY_DEGREES,
                NINETY_DEGREES,
                ONE_HUNDED_AND_FIVE_DEGREES,
                TWO_HUNDED_AND_TEN_DEGREES,
                new Tree(Math.PI/4 + 0.001),
                new Tree(Math.PI/4 + 0.002),
                TWO_HUNDED_AND_SEVENTY_DEGREES,
                THREE_HUNDED_AND_FORTY_FIVE_DEGREES
        );

        //90 degree viewport
        CameraController unit = new CameraController(trees, Math.PI / 2);

        CameraController.CameraSetup cameraSetup = unit.determineOptimalSetup();

        //30 -> 120 (30, 60, 90, 105, 90.001, 90.002) (6)
        //60 -> 150 (60, 90, 105, 90.001, 90.002) (5)

        Assert.assertThat(cameraSetup.getAngle(), is(trees.get(1).getPhi()));
        Assert.assertThat(cameraSetup.getNumberSeen(), is(6));

    }

    @Test
    public void shouldCorrectlyDetermineOptimalAngleWhenWrapping() {
        List<Tree> trees = Arrays.asList(
                ZERO_DEGREES,
                THIRTY_DEGREES,
                FORTY_FIVE_DEGREES,
                SIXTY_DEGREES,
                TWO_HUNDED_AND_TEN_DEGREES,
                THREE_HUNDED_AND_FORTY_FIVE_DEGREES
        );

        //90 degree viewport
        CameraController unit = new CameraController(trees, Math.PI / 2);

        CameraController.CameraSetup cameraSetup = unit.determineOptimalSetup();

        //0 (0 -> 90):      0, 30, 45, 60 (4)
        //1: (30 -> 120):   30, 45, 60 (3)
        //2: (45 -> 135):   45, 60 (2)
        //3: (60 -> 150):   60 (1)
        //4: (210 -> 290):  210 (1)
        //5  (345 -> 75):   345, 0, 30, 45, 60

        Assert.assertThat(cameraSetup.getAngle(), is(trees.get(5).getPhi()));
        Assert.assertThat(cameraSetup.getNumberSeen(), is(5));
    }
}