package pacman;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Pac extends JLabel {
    private ArrayList<PacFood> pacFoodArr;
    private Game game;
    private int levelBounds = 0;
    private int foodEatCount = 0;

    public Pac(Game game) {
        this.game = game;
        ImageIcon pac = new ImageIcon("res/pacman.gif");
        Image myPac = pac.getImage().getScaledInstance(20, 20, 0);
        this.setIcon(new ImageIcon(myPac));
        this.setBounds(0, 0, 20, 20);
    }

    public final static int MIN_X = 46;
    public final static int MAX_X = 436;
    public final static int MIN_Y = 103;
    public final static int MAX_Y = 493;
    public final static int height = 30, width = 30;
    public final static int moveVelocityY = 30, moveVelocityX = 30;

    protected static final int UP = 0;
    protected static final int DOWN = 1;
    protected static final int LEFT = 2;
    protected static final int RIGHT = 3;

    public void setLevelBounds(int level) {
        this.levelBounds = level;
    }

    public boolean canMoveRight() {
        try {
            int[][][][] levelArray = Game.getLevelArray(levelBounds);

            int xBlock = ((this.getX() - MIN_X) / 30) + 1;
            int yBlock = ((this.getY() - MIN_Y) / 30);
            int[][][] yWalls = levelArray[1];

            int[][] walls = yWalls[xBlock];

            for (int i = 0; i < walls.length; i++) {
                if (walls[i].length != 0) {

                    int y1 = walls[i][0];
                    int y2 = walls[i][1];

                    for (int j = y1; j < y2; j++) {
                        if (yBlock == j) {
                            return false;
                        }
                    }
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Level Bound is Corrupted");
            e.printStackTrace();
            return false;
        }
    }

    public boolean canMoveLeft() {
        try {
            int[][][][] levelArray = Game.getLevelArray(levelBounds);

            int xBlock = ((this.getX() - MIN_X) / 30);
            int yBlock = ((this.getY() - MIN_Y) / 30);

            int[][][] yWalls = levelArray[1];

            int[][] walls = yWalls[xBlock];

            for (int i = 0; i < walls.length; i++) {
                if (walls[i].length != 0) {

                    int y1 = walls[i][0];
                    int y2 = walls[i][1];
                    for (int j = y1; j < y2; j++) {
                        if (yBlock == j) {
                            return false;
                        }
                    }
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Level Bound is Corrupted");
            e.printStackTrace();
            return false;
        }
    }

    public boolean canMoveUp() {
        try {
            int[][][][] levelArray = Game.getLevelArray(levelBounds);

            int xBlock = ((this.getX() - MIN_X) / 30);
            int yBlock = ((this.getY() - MIN_Y) / 30);

            int[][][] xWalls = levelArray[0];

            int[][] walls = xWalls[yBlock];

            for (int i = 0; i < walls.length; i++) {
                if (walls[i].length != 0) {

                    int y1 = walls[i][0];
                    int y2 = walls[i][1];
                    for (int j = y1; j < y2; j++) {
                        if (xBlock == j) {
                            return false;
                        }
                    }
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println("Level Bound is Corrupted");
            e.printStackTrace();
            return false;
        }
    }

    public void setPacFoodArray(ArrayList<PacFood> arr) {
        this.pacFoodArr = arr;
    }

    public boolean canMoveDown() {
        try {
            int[][][][] levelArray = Game.getLevelArray(levelBounds);

            int xBlock = ((this.getX() - MIN_X) / 30);
            int yBlock = ((this.getY() - MIN_Y) / 30) + 1;

            int[][][] xWalls = levelArray[0];

            int[][] walls = xWalls[yBlock];

            for (int i = 0; i < walls.length; i++) {
                if (walls[i].length != 0) {

                    int y1 = walls[i][0];
                    int y2 = walls[i][1];
                    for (int j = y1; j < y2; j++) {
                        if (xBlock == j) {
                            return false;
                        }
                    }
                }
            }

            return true;

        } catch (

        Exception e) {
            System.out.println("Level Bound is Corrupted");
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public int stepDistanceX(int stepCount) {
        return Pac.moveVelocityX + (MIN_X * stepCount);
    }

    @Deprecated
    public int stepDistanceY(int stepCount) {
        return Pac.moveVelocityY + (MIN_Y * stepCount);
    }

    @Deprecated
    public void moveX(int distance) {
        this.setBounds(distance, this.getY(), Pac.width, Pac.height);
    }

    @Deprecated
    public void moveY(int distance) {
        this.setBounds(this.getX(), distance, Pac.width, Pac.height);
    }

    public void moveUp() {
        if (canMoveUp()) {
            int x = Pac.processX(this.getX());
            int y = Pac.processY(this.getY() - Pac.moveVelocityY);
            this.setBounds(x, y, Pac.width, Pac.height);
            this.eatFood(Pac.UP);
        }
    }

    public void moveDown() {
        if (canMoveDown()) {
            int x = Pac.processX(this.getX());
            int y = Pac.processY(this.getY() + Pac.moveVelocityY);
            this.setBounds(x, y, Pac.width, Pac.height);
            this.eatFood(Pac.DOWN);
        }

    }

    public void moveRight() {
        if (this.canMoveRight()) {
            int x = Pac.processX(this.getX() + Pac.moveVelocityX);
            int y = Pac.processY(this.getY());
            this.setBounds(x, y, Pac.width, Pac.height);
            this.eatFood(Pac.RIGHT);
        }

    }

    public void moveLeft() {
        if (this.canMoveLeft()) {
            int x = Pac.processX(this.getX() - Pac.moveVelocityX);
            int y = Pac.processY(this.getY());
            this.setBounds(x, y, Pac.width, Pac.height);
            this.eatFood(Pac.LEFT);
        }
    }

    public void eatFood(int moveType) {

        int x = this.getX() - Pac.MIN_X;
        int y = this.getY() - Pac.MIN_Y;

        int xCoord = (x / 30);
        int yCoord = (y / 30);

        int index = Game.get1dIndex(yCoord, xCoord, 14);
        PacFood food = pacFoodArr.get(index);

        if (!food.eaten) {
            foodEatCount++;
            int count = Game.getLevelFoodCount(levelBounds);
            if (foodEatCount == count) {
                this.game.levelEnd();
            }
        }

        food.eat();

    }

    public void center() {
        // current Pos 5,6
        this.setBounds(196 + 30, 193 + 90, Pac.width, Pac.height);
    }

    private static int processX(int x) {
        if (x >= MAX_X) {
            return MAX_X;
        }
        if (x <= MIN_X) {
            return MIN_X;
        }
        return x;
    }

    private static int processY(int y) {
        if (y >= MAX_Y) {
            return MAX_Y;
        }
        if (y <= MIN_Y) {
            return MIN_Y;
        }
        return y;
    }

}
