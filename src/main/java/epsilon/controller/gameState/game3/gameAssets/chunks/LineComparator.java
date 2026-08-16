package epsilon.controller.gameState.game3.gameAssets.chunks;

import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.entities.figures.Line;
import static epsilon.utils.FunctionUtils.getMin;

public class LineComparator implements Comparator{

    @Override
    public int compare(Object obj1, Object obj2) {
        if(obj1 instanceof Line line1 && obj2 instanceof Line line2){
            double m1 = line1.getSlope();
            double m2 = line2.getSlope();
            for (int i = 0; i < 4; i++) {
                double d1 = m1;
                double d2 = m2;
                switch (i) {
                    case 1 -> {
                        d1 = line1.getFirstY() - (m1*line1.getFirstX());
                        d2 = line2.getFirstY() - (m2*line2.getFirstX());
                    }
                    case 2 -> {
                        d1 = getMin(line1.getFirstX(),line1.getSecondX());
                        d2 = getMin(line2.getFirstX(),line2.getSecondX());
                    }
                    case 3 -> {
                        d1 = Math.abs(line1.getFirstX()-line1.getSecondX());
                        d2 = Math.abs(line2.getFirstX()-line2.getSecondX());
                    }
                }
                int comparison = compareDoubles(d1, d2);
                if(comparison != 0){
                    return comparison;
                }
            }
            return 0;
        }
        else{
            throw new Error("Invalid object type, " + obj1 + " cannot be compared to " + obj2);
        }
    }

    protected int compareDoubles(double d1, double d2){
        if(d1 > d2){
            return 1;
        }
        else if(d1 < d2){
            return -1;
        }
        else{
            return 0;
        }
    }
}