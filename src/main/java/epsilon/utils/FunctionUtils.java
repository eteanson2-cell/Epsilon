package epsilon.utils;

import epsilon.model.dataStructure.interfaces.DataBatch;
import epsilon.model.dataStructure.interfaces.DataList;
import epsilon.model.dataStructure.interfaces.Iterator;
import epsilon.model.dataStructure.interfaces.NumberList;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicQueue;
import epsilon.model.dataStructure.linearStructure.dynamic.DynamicStack;
import epsilon.model.dataStructure.linearStructure.dynamic.LinkedList;
import epsilon.model.dataStructure.linearStructure.statik.Array;
import epsilon.model.dataStructure.linearStructure.statik.NumericArray;
import epsilon.model.entities.figures.Point;
import epsilon.model.entities.figures.auxiliar.Pixel;
import epsilon.model.enums.TreeTraversal;

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
	public static double[] quicksort(double[] array){
		return quicksort(array, array.length);
	}
        @SuppressWarnings("ManualArrayToCollectionCopy")
	private static double[] quicksort(double[] array, int length){
		if(length > 1){
			int pivot = randomNumber(0, length);
			double[] leftArray = new double[length];
			double[] rightArray = new double[length];
			int leftIndex = 0;
			int rightIndex = 0;
			double dPivot = array[pivot];
			for (int i = 0; i < length; i++) {
				if(i != pivot){
					if(array[i] < dPivot){
						leftArray[leftIndex] = array[i];
						leftIndex++;
					}
					else{
						rightArray[rightIndex] = array[i];
						rightIndex++;
					}
				}
			}
			quicksort(leftArray, leftIndex);
			quicksort(rightArray, rightIndex);
			for (int i = 0; i < leftIndex; i++) {
				array[i] = leftArray[i];
			}
			array[leftIndex] = dPivot;
			for (int i = 0; i < rightIndex; i++) {
				array[i + leftIndex + 1] = rightArray[i];
			}
		}
		return array;
	}
	public static double[] mergesort(double[] array){
		return mergesort(array, array.length);
	}
	private static double[] mergesort(double[] array, int length){
		if(length > 1){
			int leftIndex = 0;
			int rightIndex = 0;
			double[] leftArray = new double[length];
			double[] rightArray = new double[length];
			int half = length/2;
			for (int i = 0; i < half; i++) {
				leftArray[leftIndex] = array[i];
				leftIndex++;
			}
			for (int i = half; i < length; i++) {
				rightArray[rightIndex] = array[i];
				rightIndex++;
			}
			mergesort(leftArray, leftIndex);
			mergesort(rightArray, rightIndex);
			int i = 0, j = 0;
			for (int k = 0; k < length; k++) {
				boolean a = (i < leftIndex);
				boolean b = (j < rightIndex);
				if(a && b){
					double num1 = leftArray[i];
					double num2 = rightArray[j];
					if(num1 <= num2){
						array[k] = num1;
						i++;
					}
					else{
						array[k] = num2;
						j++;
					}
				}
				else if(a){
					array[k] = leftArray[i];
					i++;
				}
				else if(b){
					array[k] = rightArray[j];
					j++;
				}
			}
		}
		return array;
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
	public static double radianTan(double radian){
		return Math.tan(radian);
	}
	public static double degreeCosine(double degree){
		return Math.cos(degreeToRadians(degree));
	}
	public static double degreeSine(double degree){
		return Math.sin(degreeToRadians(degree));
	}
	public static double degreeTan(double degree){
		return Math.tan(degreeToRadians(degree));
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
		if(Math.abs(difference) > arraySize){
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
		if(dataList != null){
			if(dataList instanceof NumberList){
				return true;
			}
			else{
				Array bool = new Array(1);
				dataList.iterateList((Object nodeObject) -> {
					if(isNumeric(nodeObject) == false){
						bool.add(false);
						return false;
					}
					return true;
				});
				return bool.add(true);
			}
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
	public static DataBatch selectBatch(TreeTraversal treeTraversal){
        DataBatch batch;
        switch (treeTraversal) {
            case DEPTH_FIRST_SEARCH -> batch = new DynamicStack();
            case BREADTH_FIRST_SEARCH -> batch = new DynamicQueue();
            default -> throw new AssertionError();
        }
        return batch;
    }
	public static void iterateLists(DataList[] lists, Iterator iterator){
        for (DataList list : lists) {
            list.initializeIterator();
        }
		boolean valid = true;
		while (valid) { 
			Array objects = new Array(lists.length);
			for (DataList list : lists) {
				if(list.validIterator() == false){
					valid = false;
					break;
				}
				objects.add(list.getIterator());
			}
			iterator.iterate(objects);
		}
	}

}