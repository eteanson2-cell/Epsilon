package epsilon.program;

import epsilon.controller.gameState.game3.gameAssets.MetallicRock;
import epsilon.controller.gameState.game3.gameAssets.Player;

public class game3Physics{
    public static void main(String[] args) {
        Player player = new Player();
        MetallicRock mr = new MetallicRock(50, 80);
        player.hookRock(mr);
        for(int i = 0; i < 100; i++){
            player.update();
            System.out.println("|x = " + player.circle.getXCenter() + 
                               "|y = " + player.circle.getYCenter() + 
                               "|speedx = " + player.speedx + 
                               "|speedy = " + player.speedy + 
                               "|magnitude = " + player.getMagnitude());
            if(i == 30){
                mr = new MetallicRock(-50,100);
                player.hookRock(mr);
            }
            if(i == 40){
                mr = new MetallicRock(100,90);
                player.hookRock(mr);
            }
            if(i == 50){
                player.unhookRock();
            }
        }
    }
}