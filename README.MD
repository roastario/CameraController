After being caught out during a tech challenge during an interview. 
This is my implementation of the following task

"Given a series of trees (their x and y coordinates), and a known field of vision, 
determine the best angle for a camera (at the origin) 
to face in order to capture the most trees possible within its field of vision";

Originally during the challenge, the lead suggested to use the atan2 function.
This greatly complicates the issue, as there is a discontinuity at PI when the camera rotates.

My solution removes this problem by using a quadrant aware cartesian -> polar conversion. 


The algorithm used to determine the best angle is as follows

```
highestIdxSeen = 0
currentIdx = 0
mostSeen = 0
for all trees
    determine the number visible from highestIdxSeen -> maxAngle
    update highestIdx, mostSeen
```
By only scanning from the currently highest seen IDX, we avoid the n^2 behaviour of scanning from currentIdx -> maxAngle

the main logic is contained within the CameraController class
