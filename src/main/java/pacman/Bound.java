package pacman;

import javax.swing.JLabel;

public class Bound extends JLabel {
    public Bound() {
        super();
        this.setBounds(10, 60, 490, 500);
        this.setBackground(Settings.Pallete.baseColor);
        this.setOpaque(true);
    }
}
