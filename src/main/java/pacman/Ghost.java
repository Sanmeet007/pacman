package pacman;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ghost extends JPanel {
    final public static int SPEED = 300; // in millis
    final public int id;
    public static int ghostCount = 1;
    private JLabel ghost = new JLabel();
    public static int WIDTH = 30, HEIGHT = 30;
    public volatile boolean shouldMove = false;
    private int level = 0;

    public Ghost() {

        this.setBackground(new Color(0, 0, 0, 0));

        int ghostNumber = ghostCount > 4 ? 4 : ghostCount;
        ImageIcon ghostImage = new ImageIcon(
                new ImageIcon("res/ghost_" + ghostNumber + ".gif").getImage().getScaledInstance(15, 20, 0));
        ghost.setIcon(ghostImage);
        this.add(ghost);
        this.setPreferredSize(new Dimension(Ghost.WIDTH, Ghost.HEIGHT));

        this.id = Ghost.ghostCount;
        Ghost.ghostCount += 1;
    }

    public void setPosition(int xCoord, int yCoord) {
        int x = Pac.MIN_X + (xCoord * 30) - 5;
        int y = Pac.MIN_Y + (yCoord * 30) + 5;
        this.setBounds(x, y, Ghost.WIDTH, Ghost.HEIGHT);
    }

    public void setLevel(int level) {
        this.level = level;
        switch (level) {
            case 1:
                this.setGhostPositionsForLevelOne();
                break;

            default:
                break;
        }
    }

    public void setGhostPositionsForLevelOne() {
        switch (this.id) {
            case 1:
                this.setPosition(2, 0);
                break;
            case 2:
                this.setPosition(5, 6);
                break;
            case 3:
                this.setPosition(8, 10);
                break;

            case 4:
                this.setPosition(13, 3);
                break;

            default:
                break;
        }
    }

    public void loop(Game game) throws Exception {

        Ghost ghost = this;
        final int[][][] ghostsMovementsArr;

        switch (this.level) {
            case 1:
                ghostsMovementsArr = ghostsMovementsForLevelOne();
                break;
            default:
                throw new Exception("Ghost Movements for level not implemented");
        }

        final Pac pac = game.window.pacman;
        this.shouldMove = true;
        boolean reverse = false;

        int[][] ghostMovementsArr = ghostsMovementsArr[this.id - 1];
        int reverseCount = 0;
        int maxMovements = ghostMovementsArr.length - 1;

        while (true) {

            if (game.isGameRunnig) {

                int pacXCoord = (pac.getX() - Pac.MIN_X) / 30;
                int pacYCoord = (pac.getY() - Pac.MIN_Y) / 30;

                int xCoord = (ghost.getX() + 5 - Pac.MIN_X) / 30;
                int yCoord = (ghost.getY() - 5 - Pac.MIN_Y) / 30;

                if (pacXCoord == xCoord && pacYCoord == yCoord) {
                    ghost.shouldMove = false;
                    game.gameOver();
                }

                if (shouldMove) {

                    if (reverseCount < 0)
                        reverseCount = 0;

                    int[] ghostMovement = ghostMovementsArr[reverseCount];

                    if (reverseCount == maxMovements) {
                        reverse = true;
                    } else if (reverseCount == 0) {
                        reverse = false;
                    }

                    if (reverse) {
                        reverseCount -= 1;
                    } else {
                        reverseCount += 1;
                    }

                    if (ghostMovement.length > 0) {
                        int ghostX = ghostMovement[0];
                        int ghostY = ghostMovement[1];
                        this.setPosition(ghostX, ghostY);
                    }
                }
                try {
                    Thread.sleep(Ghost.SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void stopLoop() {
        this.shouldMove = false;
    }

    public static int[][][] ghostsMovementsForLevelOne() {
        int[][][] arr = {
                {
                        { 2, 0 },
                        { 2, 1 },
                        { 2, 2 },
                        { 1, 2 },
                        { 1, 1 },
                        { 1, 0 },
                        { 0, 0 },
                        { 0, 1 },
                        { 0, 2 },
                        { 0, 3 },
                        { 0, 4 },
                        { 0, 5 },
                        { 0, 6 },
                        { 0, 7 },
                        { 0, 8 },
                        { 0, 9 },
                        { 0, 10 },
                        { 0, 11 },
                        { 0, 12 },
                        { 0, 13 },
                        { 1, 13 },
                        { 2, 13 },
                        { 3, 13 },
                        { 4, 13 },
                        { 5, 13 },
                        { 5, 12 },
                        { 5, 11 },
                        { 5, 10 },
                        { 5, 9 },
                        { 5, 8 },
                        { 4, 8 },
                        { 4, 9 },
                        { 4, 10 },
                        { 4, 11 },
                        { 4, 12 },
                        { 4, 13 },
                        { 3, 13 },
                        { 2, 13 },
                        { 1, 13 },
                        { 0, 13 },
                        { 0, 12 },
                        { 0, 11 },
                        { 0, 10 },
                        { 0, 9 },
                        { 0, 8 },
                        { 0, 7 },
                        { 0, 6 },
                        { 0, 5 },
                        { 0, 4 },
                        { 0, 3 },
                        { 0, 2 },
                        { 0, 1 },
                        { 0, 0 },
                        { 1, 0 },
                        { 1, 1 },
                        { 1, 2 },
                        { 2, 2 },
                        { 2, 1 },
                        { 2, 0 },
                        { 1, 0 },
                        { 0, 0 },
                        { 0, 1 },
                        { 0, 2 },
                        { 0, 3 },
                        { 0, 5 },
                        { 0, 6 },
                        { 0, 7 },
                        { 0, 8 },
                        { 0, 9 },
                        { 0, 10 },
                        { 0, 11 },
                        { 0, 12 },
                        { 1, 12 },
                        { 2, 12 },
                        { 2, 11 },
                        { 2, 10 },
                        { 2, 9 },
                        { 2, 8 },
                        { 2, 7 },
                        { 2, 6 },
                        { 1, 6 },
                        { 1, 7 },
                        { 1, 9 },
                        { 1, 10 },
                        { 1, 11 },
                        { 1, 12 },
                        { 0, 12 },
                        { 1, 12 },
                        { 2, 12 },
                        { 3, 12 },
                        { 3, 11 },
                        { 3, 10 },
                        { 3, 9 },
                        { 3, 8 },
                        { 3, 7 },
                        { 3, 6 },
                        { 3, 5 },
                        { 3, 4 },
                        { 3, 3 },
                        { 3, 2 },
                        { 3, 1 },
                        { 3, 0 },
                }, // for ghost 1

                {
                        { 5, 6 },
                        { 5, 5 },
                        { 5, 4 },
                        { 5, 3 },
                        { 5, 2 },
                        { 5, 1 },
                        { 5, 0 },
                        { 6, 0 },
                        { 6, 1 },
                        { 6, 2 },
                        { 6, 3 },
                        { 6, 4 },
                        { 6, 5 },
                        { 6, 6 },
                        { 6, 7 },
                        { 6, 8 },
                        { 6, 9 },
                        { 6, 10 },
                        { 6, 11 },
                        { 6, 12 },
                        { 6, 13 },
                        { 7, 13 },
                        { 7, 12 },
                        { 7, 11 },
                        { 7, 10 },
                        { 7, 9 },
                        { 7, 8 },
                        { 7, 7 },
                        { 7, 6 },
                        { 7, 5 },
                        { 7, 6 },
                        { 7, 7 },
                        { 7, 8 },
                        { 7, 9 },
                        { 7, 10 },
                        { 7, 11 },
                        { 7, 12 },
                        { 7, 13 },
                        { 8, 13 },
                        { 9, 13 },
                        { 8, 13 },
                        { 7, 13 },
                        { 6, 13 },
                        { 6, 12 },
                        { 6, 11 },
                        { 6, 10 },
                        { 6, 9 },
                        { 6, 8 },
                        { 6, 7 },
                        { 6, 6 },
                        { 6, 5 },
                        { 6, 4 },
                        { 6, 3 },
                        { 6, 2 },
                        { 6, 1 },
                        { 6, 0 },
                        { 5, 0 },
                        { 5, 1 },
                        { 5, 2 },
                        { 5, 3 },
                        { 5, 4 },
                        { 5, 5 },
                        { 5, 6 },
                        { 5, 7 },
                        { 5, 8 },
                        { 5, 9 },
                        { 5, 10 },
                        { 5, 11 },
                        { 5, 12 },
                        { 5, 13 },
                        { 4, 13 },
                        { 4, 12 },
                        { 4, 11 },
                        { 4, 10 },
                        { 4, 9 },
                        { 4, 8 },
                        { 4, 7 },
                        { 4, 6 },
                        { 4, 5 },
                        { 4, 4 },
                        { 4, 3 },
                        { 4, 2 },
                        { 4, 1 },
                        { 4, 0 },
                        { 3, 0 },
                        { 3, 1 },
                        { 3, 2 },
                        { 3, 3 },
                        { 4, 3 },
                        { 4, 4 },
                        { 4, 5 },
                        { 4, 6 },
                        { 4, 7 },
                        { 4, 8 },
                        { 4, 9 },
                        { 4, 10 },
                        { 4, 11 },
                        { 4, 12 },
                        { 4, 13 },
                }, // for ghost 2

                {
                        { 8, 10 },
                        { 8, 9 },
                        { 9, 9 },
                        { 9, 10 },
                        { 9, 11 },
                        { 9, 12 },
                        { 9, 11 },
                        { 9, 9 },
                        { 10, 9 },
                        { 10, 8 },
                        { 10, 7 },
                        { 10, 6 },
                        { 10, 5 },
                        { 10, 4 },
                        { 11, 4 },
                        { 12, 4 },
                        { 13, 4 },
                        { 13, 5 },
                        { 12, 5 },
                        { 11, 5 },
                        { 10, 5 },
                        { 10, 6 },
                        { 10, 7 },
                        { 10, 8 },
                        { 10, 9 },
                        { 10, 10 },
                        { 10, 11 },
                        { 10, 13 },
                        { 11, 13 },
                        { 11, 12 },
                        { 11, 11 },
                        { 11, 10 },
                        { 11, 9 },
                        { 11, 8 },
                        { 11, 7 },
                        { 11, 6 },
                        { 12, 6 },
                        { 12, 7 },
                        { 12, 8 },
                        { 12, 9 },
                        { 12, 10 },
                        { 12, 11 },
                        { 12, 13 },
                        { 12, 12 },
                        { 12, 11 },
                        { 12, 10 },
                        { 12, 9 },
                        { 12, 8 },
                        { 12, 7 },
                        { 12, 6 },
                        { 13, 6 },
                        { 13, 7 },
                        { 13, 8 },
                        { 13, 9 },
                        { 13, 10 },
                        { 13, 11 },
                        { 13, 13 },
                }, // for ghost 3

                {
                        { 13, 3 },
                        { 13, 2 },
                        { 13, 1 },
                        { 13, 0 },
                        { 12, 0 },
                        { 12, 1 },
                        { 12, 2 },
                        { 12, 3 },
                        { 11, 3 },
                        { 11, 2 },
                        { 11, 1 },
                        { 11, 0 },
                        { 10, 0 },
                        { 10, 1 },
                        { 10, 2 },
                        { 10, 3 },
                        { 9, 3 },
                        { 9, 4 },
                        { 9, 5 },
                        { 9, 6 },
                        { 9, 7 },
                        { 9, 8 },
                        { 8, 8 },
                        { 8, 7 },
                        { 8, 6 },
                        { 8, 5 },
                        { 8, 4 },
                        { 8, 3 },
                        { 8, 2 },
                        { 8, 1 },
                        { 8, 0 },
                } // for ghost 4
        };
        return arr;
    };
}
