package epsilon.controller.gameState.game3.gameAssets;

import epsilon.model.dataStructure.interfaces.Comparator;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.entities.figures.Point;

public class PointGraphComparator implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        Array arr1 = (Array)obj1;
        Array arr2 = (Array)obj2;
        Point p1 = (Point)arr1.get(1);
        Point p2 = (Point)arr2.get(1);
        if(p1.getY() < p2.getY()){
            return -1;
        }
        else if(p1.getY() > p2.getY()){
            return 1;
        }
        else{
            if(p1.getX() < p2.getX()){
                return -1;
            }
            else if(p1.getX() > p2.getX()){
                return 1;
            }
            else{
                return 0;
            }
        }
    }
}