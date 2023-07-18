package pacman;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

class MyLabel extends JLabel {

}

public class TimerBox extends JLabel {
    TimerBox() {
        super("0:00", SwingConstants.CENTER);
        this.setFont(new Font("VT323", Font.PLAIN, 18));
        this.setBounds(435, 10, 65, 40);
        this.setBackground(Settings.Pallete.lightColor);
        this.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        this.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        this.setOpaque(true);
    }

    public void updateTime(Integer time) {
        int seconds = time % 60;
        int minutes = time / 60;
        if (seconds > 9) {
            this.setText(minutes + ":" + seconds);
        } else {
            this.setText(minutes + ":0" + seconds);
        }
    }
}
