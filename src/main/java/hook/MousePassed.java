package hook;

public class MousePassed {
    private int x;
    private int y;

    MousePassed(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "MousePassed{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
