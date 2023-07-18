package pacman;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageBox extends JPanel {

    final private JLabel label = new JLabel();

    MessageBox() {
        super();
        // setVisible(false);
        label.setText("Pacman");
        label.setFont(new Font("Robotto", Font.BOLD, 20));
        this.add(label);
        this.setBackground(Color.WHITE);
        this.setBounds(210, 10, 215, 40);
    }

    void setLevel(int level) {
        label.setText("Level " + level);
    }

    void setWin() {
        label.setText("You Win");
    }

    void setGameOver() {
        label.setText("Game Over");
    }
}
