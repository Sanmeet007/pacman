package pacman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PacFood extends JPanel {

    public boolean eaten = false;

    public PacFood(int xPos, int yPos) {
        super();
        ImageIcon foodImage = new ImageIcon("res/food.png");
        JLabel food = new JLabel();
        ImageIcon newImage = new ImageIcon(foodImage.getImage().getScaledInstance(8, 8, 0));
        food.setIcon(newImage);
        food.setPreferredSize(new Dimension(30, 30));
        food.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.add(food);
        this.setBounds((xPos * 30) + 6 + Pac.MIN_X, (yPos * 30) - 3 + Pac.MIN_Y, 30, 30);
        food.setForeground(Color.WHITE);
        this.setBackground(new Color(0, 0, 0, 0));
    }

    public void eat() {
        if (!eaten) {
            new PacEatSound().play();
            eaten = true;
            this.setVisible(false);
        }
    }

    public void eat(boolean muffled) {
        if (muffled) {
            eaten = true;
            this.setVisible(false);
        }
    }
}
