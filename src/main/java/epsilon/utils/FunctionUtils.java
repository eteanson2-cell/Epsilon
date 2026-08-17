package epsilon.utils;

import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.dynamic.NumericList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.auxiliar.Pixel;

public class FunctionUtils{
	public static double getMax(double a, double b){
		if(a > b){
			return a;
		}
		else{
			return b;
		}
	}
	public static double getMin(double a, double b){
		if(a < b){
			return a;
		}
		else{
			return b;
		}
	}
	//takes an array of doubles an returns the maximun value
    public static double getMax(double[] array){
		double max = array[0];
		for (int i = 0; i < array.length; i++){
			if(array[i] > max){
				max = array[i];
			}
		}
		return max;
	}
	//takes an array of doubles an returns the minimun value
	public static double getMin(double[] array){
		double min = array[0];
		for (int i = 0; i < array.length; i++){
			if(array[i] < min){
				min = array[i];
			}
		}
		return min;
	}
	//prints a message
    public static void showMessage(String message){
        System.out.println(message);
    }
	//scans an array and return the shortest distante to a point
	public static double getClosestPoint(double point, double[] array){
		double difference = Math.abs(point - array[0]);
        int selectedIndex = 0;
        for(int i = 0; i < array.length ;i++){
            if(Math.abs(point - array[i]) < difference){
                difference = Math.abs(point - array[i]);
                selectedIndex = i;
            }
        } 
        return array[selectedIndex];
	}
	//takes a double array and returns it as an arrayList
	public static LinkedList ArrayToList(Object[] array){
		LinkedList list = new LinkedList();
        for (Object array1 : array) {
            list.add(array1);
        }
		return list;
	} 
	public static int roundDouble(double number){
		return (int)Math.round(number);
	}
	//verifies if a number is in between two numbers
	public static boolean isInRange(double minimum,double maximum,double value){
		return (minimum <= value) && (value <= maximum);
	}
	public static double degreeToRadians(double degree){
		return Math.toRadians(degree);
	}
	public static double radiansToDegrees(double radian){
		return Math.toDegrees(radian);
	}
	public static double radianCosine(double radian){
		return Math.cos(radian);
	}
	public static double radianSine(double radian){
		return Math.sin(radian);
	}
	public static double degreeCosine(double degree){
		return Math.cos(degreeToRadians(degree));
	}
	public static double degreeSine(double degree){
		return Math.sin(degreeToRadians(degree));
	}
	//returns a random number in between a minimum value and a maximum value
	public static double randomNumber(double minNumber, double maxNumber){
		double dif = Math.abs(maxNumber - minNumber);
		return minNumber+(dif*Math.random());
	}
	public static int randomNumber(int minNumber, int maxNumber){
		int dif =  maxNumber - minNumber;
		return minNumber+(int)(dif*Math.random());
	}
	public static NumericArray generateRandomIntegers(int minNumber, int maxNumber, int arraySize){
		int difference = maxNumber-minNumber;
		if(Math.abs(difference) < arraySize){
			NumericArray randomNumbers = new NumericArray(arraySize);
			while (randomNumbers.isFilled() == false){
				int randomNumber = randomNumber(minNumber, maxNumber);
				if(((int)randomNumbers.find(randomNumber)) == -1){
					randomNumbers.add(randomNumber);
				}
			}
			return randomNumbers;
		}
		else{
			return null;
		}
	}
	public static NumericArray generateRandomDoubles(double minNumber, double maxNumber, int arraySize){
		NumericArray randomNumbers = new NumericArray(arraySize);
		while (randomNumbers.isFilled() == false){
			double randomNumber = randomNumber(minNumber, maxNumber);
			if(((int)randomNumbers.find(randomNumber)) == -1){
				randomNumbers.add(randomNumber);
			}
		}
		return randomNumbers;
	}
	public static boolean isNumeric(Object object){
        return (object instanceof Number);
    }
	public static boolean isNumericList(DataList dataList){
		if(dataList instanceof NumericArray || dataList instanceof NumericList){
            return true;
        }
		else if(dataList instanceof Array array){
            for (int i = 0; i < array.size(); i++) {
                if(isNumeric(array.get(i)) == false){
					return false;
				}
            }
            return true;
        }
        else if(dataList instanceof LinkedList list){
            list.initializeIterator();
            while (list.validIterator()) { 
                if(isNumeric(list.getIterator()) == false){
					return false;
				}
                list.moveIteratorToRight();
            }
            return true;
        }
        else{
            return false;
        }
	}
	public static Double objectToDouble(Object object){
		if(isNumeric(object)){
			Number number = (Number)object;
			return number.doubleValue();
		}	
		else{
			return null;
		}
	}
	public static Array createGradient(Array pixels, int length){
		Array gradient = new Array(length);
		for (int i = 0; i < pixels.size(); i++) {
			Object object = pixels.get(i);
			if(object instanceof Pixel == false){
				return null;
			}
		}
		int subGradientSize = length/pixels.size(); 
		for (int i = 0; i < pixels.size()-1; i++) {
			Pixel pixel1 = (Pixel)pixels.get(i);
			Pixel pixel2 = (Pixel)pixels.get(i+1);
			Array subGradient = pixel1.createGradient(pixel2, subGradientSize);
			gradient.addList(subGradient);
		}
		return gradient;
	}
	public static Array createGradient(Pixel[] pixels, int length){
		Array gradient = new Array(length);
		int subGradientSize = length/pixels.length; 
		for (int i = 0; i < pixels.length-1; i++) {
			Pixel pixel1 = pixels[i];
			Pixel pixel2 = pixels[i+1];
			Array subGradient = pixel1.createGradient(pixel2, subGradientSize);
			gradient.addList(subGradient);
		}
		return gradient;
	}
	public static byte compareObjects(Object object1, Object object2){
		String objectString1 = object1.toString();
		String objectString2 = object2.toString();
		if(isNumeric(object1) && isNumeric(object2)){
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
			return (byte)objectString1.compareToIgnoreCase(objectString2);
		}
	}
	public static double euclideanDistance(Point pointA, Point pointB){
		return Math.sqrt(Math.pow(pointB.getX()-pointA.getX(), 2) + 
						 Math.pow(pointB.getY()-pointA.getY(), 2));
	}
	public static int getSign(Number num){
		double dec = num.doubleValue();
		if(dec > 0){
			return 1;
		}
		else if(dec < 0){
			return -1;
		}
		else{
			return 0;
		}
	}

}