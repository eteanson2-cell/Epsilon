package epsilon.controller.gameState.game2.gameAssets;

import java.awt.Color;

public enum SnakeColor{
    BLUE(0,0,255), 
    CYAN(3,175,255), 
    GREEN(0,255,0), 
    LEMON_YELLOW(222,227,52), 
    YELLOW(255,255,0), 
    ORANGE_YELLOW(255,195,75), 
    ORANGE(255,168,50), 
    ORANGE_RED(255,114,43), 
    RED(255,0,0),
    SCARLET(219,45,74), 
    MAGENTA(219,103,230),
    VIOLET(93,73,162);
    private final Color color;
    private SnakeColor(int red, int green, int blue){
        color = new Color(red,green,blue);
    }
    public Color getColor(){
        return color;
    }
}