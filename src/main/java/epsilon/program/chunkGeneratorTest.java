package epsilon.program;

import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import static epsilon.utils.FunctionUtils.randomNumber;

public class chunkGeneratorTest{
    public static void main(String[] args) {
        int seed = 0;
        int height = 0;
        LinkedList lines;
        int twists = randomNumber(0, seed);
        double currentX = randomNumber(0.0, 640);
        double prevAngle = randomNumber(45.0, 135);
        double length = randomNumber(50.0, height-1);
        double currentY = length*Math.sin(Math.toRadians(prevAngle));
        LinkedList drawnLines = new LinkedList();
        while(twists > 1){
            double newAngle;
            do { 
                newAngle = randomNumber(0.0, 360);
            } while (Math.abs(newAngle-prevAngle)+180 > 30);
            twists--;
        }
        
    }
}