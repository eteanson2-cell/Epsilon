package epsilon.program;

import java.nio.ByteBuffer;

import epsilon.model.dataStructure.linearStructure.statik.HashTable;
import static epsilon.utils.FunctionUtils.randomNumber;

public class hashTableTest{
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(
            (Object key) -> {
            if(key != null){
                int hash = 216613626;
                int prime = 16777619;
                if(key instanceof Number num){
                    long bits = Double.doubleToRawLongBits(num.doubleValue());
                    byte[] bytes = ByteBuffer.allocate(8).putLong(bits).array();
                    for (byte byt : bytes) {
                        hash = hash ^ byt;
                        hash = hash * prime;
                    }
                }
                else{
                    String keyString = key.toString();
                    for (int i = 0; i < keyString.length(); i++) {
                        char keyChar = keyString.charAt(i);
                        int byt = (int)keyChar;
                        hash = hash ^ byt;
                        hash = hash * prime;
                    }
                }
                return hash;
            }
            else{
                throw new Error("The object is null");
            }
        },200000000);
        int randomNumber = randomNumber(-2000000,20000000);
        while(hashTable.add(randomNumber) == true){
            System.out.println("no collision");
            randomNumber = randomNumber(-2000000,20000000);
        }
        System.out.print("a collision has ocurred with the number " + randomNumber);
        System.out.println(" at " + hashTable.find(randomNumber));
        System.out.println("Key = " + hashTable.find(randomNumber) + 
                         "| Number = " + hashTable.get(hashTable.find(randomNumber)));
        //hashTable.print();
        
    }
}