package epsilon.program;

import epsilon.model.dataStructure.linearStructure.statik.HashTable;

public class hashTableTest{
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(
            (Object key) -> {
                if(key instanceof Number num){
                    return num.intValue();
                }
                return 0;
        }, 100);
        hashTable.add(520);
        hashTable.add(345);
        hashTable.add(193);
        hashTable.add(297);
        hashTable.print();
        hashTable.resize(126);
        hashTable.print();
    }
}