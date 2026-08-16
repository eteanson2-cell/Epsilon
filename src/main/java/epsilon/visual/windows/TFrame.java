package epsilon.visual.windows;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import epsilon.visual.panels.BoardPanel;

public class TFrame extends JFrame{
    public TFrame(){
        setTitle("Frame");
        setContentPane(new BoardPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }
    
} 