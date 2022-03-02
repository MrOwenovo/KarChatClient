package KarChat.Game.Domain;

/**
 *表示棋子位置的类，实现以像素为单位的坐标与矩阵中行列坐标之间的转换
 */
public class Position {
    private int x;
    private int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public void setY(int y) {
        this.y = y;
    }
}
