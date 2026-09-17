package epsilon;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import epsilon.visual.panels.Game3Panel;

public class App2 {
    public static void main(String[] args) {
        JFrame magnetClimbFrame = new JFrame("Magnet Climb");
        magnetClimbFrame.setTitle("Frame");
        magnetClimbFrame.setContentPane(new Game3Panel());
		magnetClimbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		magnetClimbFrame.setResizable(false);
		magnetClimbFrame.pack();
		magnetClimbFrame.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		magnetClimbFrame.setLocation(dim.width / 2 - magnetClimbFrame.getSize().width / 2, 
                                    dim.height / 2 - magnetClimbFrame.getSize().height / 2);
    }
}
  