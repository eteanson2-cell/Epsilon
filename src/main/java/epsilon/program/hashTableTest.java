package epsilon.program;

import java.nio.ByteBuffer;

import epsilon.model.dataStructure.linearStructure.statik.HashMap;
import static epsilon.utils.FunctionUtils.randomNumber;

public class hashTableTest{
    public static void main(String[] args) {
        HashMap hashMap = new HashMap((Object key) -> {
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
        },20000000);
        double key = randomNumber(0.0,1000.0);
        while(hashMap.addKey(key)){
            hashMap.addObject(randomNumber(0, 100), key);
            key = randomNumber(0.0,1000.0);
        }
        hashMap.print();
    }
}