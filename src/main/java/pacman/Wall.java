package pacman;

import javax.swing.JPanel;

public class Wall extends JPanel {
    Wall() {
        this.setBackground(Settings.Pallete.wallColor);
        this.setOpaque(true);
    }

    public void setWallDepthUsingCoord(int x, int startCoord, int endCoord) {
        int blockSize = endCoord - startCoord;
        this.setWallDepth(x, blockSize, startCoord);
    }

    public void setWallWidthUsingCoord(int x, int startCoord, int endCoord) {
        int blockSize = endCoord - startCoord;
        this.setWallWidth(x, blockSize, startCoord);
    }

    public void setWallDepth(int x, int blockSize) {
        // steps range 0 - 14
        this.setBounds((x * 30) + 30 + Game.gridOffsetX, 90 + Game.gridOffsetY, 3, 30 * blockSize);
    }

    public void setWallDepth(int x, int blockSize, int offsetSteps) {
        // steps range 0 - 14
        int offset = offsetSteps * 30;
        this.setBounds((x * 30) + 30 + Game.gridOffsetX, 90 + Game.gridOffsetY + offset, 3, (30 * blockSize));
    }

    public void setWallWidth(int y, int blockSize) {
        // steps range 0 - 14
        this.setBounds(30 + Game.gridOffsetX, (y * 30) + 90 + Game.gridOffsetY, (blockSize * 30) + 3, 3);

    }

    public void setWallWidth(int y, int blockSize, int offsetSteps) {
        // steps range 0 - 14
        int offset = offsetSteps * 30;
        this.setBounds(30 + Game.gridOffsetX + offset, (y * 30) + 90 + Game.gridOffsetY,
                (blockSize * 30) + 3, 3);

    }
}
