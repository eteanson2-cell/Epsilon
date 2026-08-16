package epsilon.program;

import epsilon.model.dataStructure.nonLinearStructure.TreeMap;

public class treeMapTest{
    public static void main(String[] args) {
        TreeMap map = new TreeMap((Object obj1, Object obj2) -> {
            return obj1.toString().compareToIgnoreCase(obj2.toString());
        });
        String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
                            "Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        for (String letter : letters) {
            map.addKey(letter);
        }
        map.printKeys();
        for (int i = 16; i >= 0; i--) {
            map.removeKey(letters[i]);
            map.printKeys();
        }
        /*Line[] hlines = new Line[10];
        TreeMap lines = new TreeMap(new LineComparator());
        for (int i = 0; i < 10; i++) {
            Line newLine = new Line(randomNumber(-50,50), 
                                  randomNumber(-50,50), 
                                  randomNumber(-50,50), 
                                  randomNumber(-50,50));
            lines.addKey(newLine);
            hlines[i] = newLine;
        }
        int randomInt = randomNumber(0, 10);
        System.out.println(hlines[randomInt]);
        lines.addObject(1, hlines[randomInt]);
        lines.print(TreeTraversal.BREADTH_FIRST_SEARCH);

        System.out.println(lines.addKey(new Line(5, 5, 10, 10)));
        System.out.println(lines.addKey(new Line(10, 10, 5, 5)));*/
    }
}