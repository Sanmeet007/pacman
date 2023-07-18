package pacman;

import java.awt.Font;

import javax.swing.JButton;

public class PacButton extends JButton {
    public final static int START = 0, STOP = 1;
    public final int type;

    PacButton(int type) {
        this.setBackground(Settings.Pallete.btnColor);
        this.setForeground(Settings.Pallete.lightColor);
        this.setFont(new Font("Robotto", Font.PLAIN, 15));
        this.setFocusable(false);

        this.type = type;

        if (type == PacButton.START) {
            this.setText("START");
            this.setBounds(10, 10, 90, 40);

        } else if (type == PacButton.STOP) {
            this.setText("STOP");
            this.setBounds(110, 10, 90, 40);
            this.setEnabled(false);
        }
    }
}
