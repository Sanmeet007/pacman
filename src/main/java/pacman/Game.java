package pacman;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game {
    public boolean isGameRunnig = false;
    public static int gridOffsetX = 10, gridOffsetY = 12;
    public ArrayList<PacFood> pacFoodArr = new ArrayList<PacFood>();
    public Timer timer;
    public final Window window;
    private final TimerBox timerBox = new TimerBox();
    private final PacButton startButton = new PacButton(PacButton.START);
    private final PacButton stopButton = new PacButton(PacButton.STOP);
    public final MessageBox messageBox = new MessageBox();
    public final Ghost[] ghosts = { new Ghost(), new Ghost(), new Ghost(), new Ghost() };
    public final ArrayList<Thread> ghostThreads = new ArrayList<Thread>();

    Game() {
        final Game game = this;
        this.window = new Window(game);
        this.timer = new Timer(timerBox);
        this.timer.start();

        // Adding Listeners
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isGameRunnig) {
                    game.runGame();
                }

                stopButton.setEnabled(true);
                if (timer.isPaused()) {
                    startButton.setText("RESUME");
                    timer.pauseTimer();
                } else {
                    startButton.setText("PAUSE");
                    timer.resumeTimer();
                }
            }
        });

        stopButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                timer.resetTimer();
                startButton.setText("START");
                stopButton.setEnabled(false);
            }

        });

        // Rendering window
        window.render();
    }

    public static int[] get2dIndex(int oneDIndex, int rowSize, int columnSize) {
        int x, y;
        x = oneDIndex % columnSize;
        y = oneDIndex / rowSize;
        int[] k = { x, y };
        return k;
    }

    public static int get1dIndex(int row, int col, int gridSize) {
        return (col * gridSize) + row;
    }

    public Game buildLevel(int level) {
        // Setting pacman level bounds
        this.window.pacman.setLevelBounds(level);
        final Game game = this;

        try {

            // Setting Ghosts position
            for (final Ghost ghost : ghosts) {
                ghost.setLevel(level);
                ghostThreads.add(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ghost.loop(game);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }));
            }

            // Buliding food
            for (int i = 0; i < 14; i++) {
                for (int j = 0; j < 14; j++) {
                    PacFood food = new PacFood(i, j);
                    this.pacFoodArr.add(food);
                    this.window.panel.add(food);
                    if (i == 6 && j == 6) {
                        food.eat(true);
                    }
                }
            }

            // Setting pacfood array
            this.window.pacman.setPacFoodArray(pacFoodArr);

            int[][][][] levelArr = getLevelArray(level);
            int[][][] xWalls = levelArr[0];
            int[][][] yWalls = levelArr[1];

            // Building Ywalls
            for (int i = 0; i < 15; i++) {
                int[][] walls = yWalls[i];
                for (int j = 0; j < walls.length; j++) { // [][][]
                    if (walls[j].length != 0) {

                        int coordArray[] = walls[j];

                        // get coords
                        int y1 = coordArray[0];
                        int y2 = coordArray[1];

                        // Draw wall
                        Wall verticalWall = new Wall();

                        verticalWall.setWallDepthUsingCoord(i, y1, y2);
                        window.panel.add(verticalWall);
                    }
                }
            }

            // Building Xwalls
            for (int i = 0; i < 15; i++) {
                int[][] walls = xWalls[i];

                for (int j = 0; j < walls.length; j++) { // [][][]
                    if (walls[j].length != 0) {

                        int coordArray[] = walls[j];

                        // get coords
                        int x1 = coordArray[0];
                        int x2 = coordArray[1];

                        // Draw wall
                        Wall horizontalWall = new Wall();

                        horizontalWall.setWallWidthUsingCoord(i, x1, x2);
                        window.panel.add(horizontalWall);
                    }
                }
            }

        } catch (

        Exception e) {
            System.out.println("Game level not implemented");
            e.printStackTrace();
        }
        return this;
    }

    public void runGame() {
        for (final Thread thread : ghostThreads) {
            thread.start();
        }
        timer.resetTimer();
        isGameRunnig = true;
        Game.playPacSound();
        this.window.setGameStatus(isGameRunnig);
    }

    public void levelEnd() {

        stopButton.setEnabled(false);
        startButton.setText("START");

        isGameRunnig = false;
        this.window.setGameStatus(isGameRunnig);

        PacThemeMusic music = PacThemeMusic.instance;
        PacWonMusic wonMusic = PacWonMusic.instance;
        wonMusic.play();
        music.stop();
        messageBox.setWin();
        timer.pauseTimer();
    }

    public void gameOver() {

        stopButton.setEnabled(false);
        startButton.setText("START");

        isGameRunnig = false;
        this.window.setGameStatus(isGameRunnig);

        PacThemeMusic music = PacThemeMusic.instance;
        PacGameOverMusic gameOverMusic = PacGameOverMusic.instance;
        gameOverMusic.play();
        music.stop();
        messageBox.setGameOver();
        timer.pauseTimer();
    }

    public void buildGame() {

        JPanel loadPanel = new JPanel();
        loadPanel.setBounds(0, 0, Window.width, Window.height);
        loadPanel.setBackground(Settings.Pallete.darkColor);

        loadPanel.setLayout(new GridLayout(1, 1));

        JLabel label = new JLabel("<PACMAN/>", SwingConstants.CENTER);
        label.setFont(new Font("", Font.PLAIN, 25));
        label.setForeground(Settings.Pallete.accentColor);
        loadPanel.add(label);

        window.panel.add(window.pacman);

        for (Ghost ghost : ghosts) {
            window.panel.add(ghost);
        }

        window.panel.add(this.startButton);
        window.panel.add(this.stopButton);
        window.panel.add(this.timerBox);
        window.panel.add(new Bound());
        window.panel.add(messageBox);

        window.add(loadPanel);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loadPanel.setVisible(false);

        window.add(window.panel);

        try {
            this.timer.join();

            for (Thread thread : ghostThreads) {
                thread.join();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playPacSound() {
        PacThemeMusic music = PacThemeMusic.instance;
        music.play();
    }

    public static int[][][][] getLevelArray(int level) throws Exception {
        switch (level) {
            case 1:
                return levelOne();
            default:
                throw new Exception("Invalid level");
        }
    }

    public static int getLevelFoodCount(int level) {
        switch (level) {
            case 1:
                return 195;
            // return 2;
            default:
                return 0;
        }
    }

    private static int[][][][] levelOne() {
        // [x] [y] [...x] [...y]

        int[][][][] levelArr = {
                {
                        {
                                { 0, 14 }
                        }, // 0
                        {
                                {}
                        }, // 1
                        {
                                {}
                        }, // 2
                        {
                                { 1, 3 }
                        }, // 3
                        {
                                { 10, 14 }

                        }, // 4
                        {
                                { 7, 8 }
                        }, // 5
                        {
                                { 11, 14 }

                        }, // 6
                        {
                                {}
                        }, // 7
                        {
                                {}
                        }, // 8
                        {
                                {}
                        }, // 9
                        {
                                {}
                        }, // 10
                        {
                                {}
                        }, // 11
                        {
                                {}
                        }, // 12
                        {
                                { 1, 4 },
                                { 8, 10 }
                        }, // 13
                        {
                                { 0, 14 }
                        }, // 14

                },
                {
                        {
                                { 0, 14 }
                        }, // 0
                        {
                                { 1, 12 }
                        }, // 1
                        {
                                { 7, 12 }
                        }, // 2
                        {
                                { 0, 7 }
                        }, // 3
                        {
                                { 4, 13 }
                        }, // 4
                        {
                                { 0, 6 },
                                { 9, 13 },
                        }, // 5
                        {
                                { 2, 10 },
                                { 12, 14 }
                        }, // 6
                        {
                                { 5, 13 }
                        }, // 7
                        {
                                { 0, 9 }
                        }, // 8
                        {
                                { 0, 5 },
                                { 6, 8 },
                                { 10, 13 }
                        }, // 9
                        {
                                { 0, 3 },
                                { 5, 9 },
                                { 10, 14 }
                        }, // 10
                        {
                                { 6, 12 }
                        }, // 11
                        {
                                { 0, 3 },
                                { 8, 14 }
                        }, // 12
                        {
                                { 9, 14 }
                        }, // 14
                        {
                                { 0, 14 }
                        }, // 14
                },
        };
        return levelArr;
    }
}
