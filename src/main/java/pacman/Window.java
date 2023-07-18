package pacman;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements KeyListener {
    final public Pac pacman;
    final public JPanel panel;
    final public static int width = 522, height = 605;
    boolean allowControls = false;

    public void setGameStatus(boolean status) {
        allowControls = status;
    }

    public Window(Game game) {
        super("Pacman");

        // Window settings
        this.setIconImage(new ImageIcon("res/favicon.png").getImage());
        this.setSize(Window.width, Window.height);

        // Panel
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(500, 500));
        this.panel.setBackground(Settings.Pallete.panelColor);

        // Pacman
        this.pacman = new Pac(game);
        this.addKeyListener(this);
        pacman.center();
    }

    public void render() {
        this.panel.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (allowControls) {
            switch (e.getKeyCode()) {
                case 37:
                    pacman.moveLeft();
                    break;
                case 38:
                    pacman.moveUp();
                    break;
                case 39:
                    pacman.moveRight();
                    break;
                case 40:
                    pacman.moveDown();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
