package epsilon.model.dataStructure.auxiliar;

import epsilon.model.dataStructure.interfaces.Comparator;
import static epsilon.utils.FunctionUtils.isNumeric;

public class BaseObjectComparator implements Comparator{
    @Override
    public int compare(Object obj1, Object obj2) {
        String objectString1 = obj1.toString();
		String objectString2 = obj2.toString();
		if(isNumeric(obj1) && isNumeric(obj2)){
			Double num1 = Double.valueOf(objectString1);
			Double num2 = Double.valueOf(objectString2);
			if(num1 > num2){
				return 1;
			}
			else if(num2 > num1){
				return -1;
			}
			else{
				return 0;
			}
		}
		else{
			return objectString1.compareToIgnoreCase(objectString2);
		}
    }

}