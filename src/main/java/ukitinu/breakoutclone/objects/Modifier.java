package ukitinu.breakoutclone.objects;

import ukitinu.breakoutclone.ObjectType;
import ukitinu.breakoutclone.Room;
import ukitinu.breakoutclone.game.GameConst;

import java.awt.*;

public class Modifier extends MovingGameObject {
    public static final int WIDTH = 12;
    public static final int HEIGHT = 12;
    private static final int DEFAULT_SPEED = 2;
    private static final int MOD_R = 64;
    private static final int MOD_G = 96;
    private static final int MOD_B = 32;

    public Modifier(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ObjectType.MODIFIER);
        setVelX(0);
        setVelY(DEFAULT_SPEED);
    }

    @Override
    public void tick() {
        if (y >= GameConst.HEIGHT) {
            Room.INSTANCE.remove(this);
        } else {
            super.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(MOD_R, MOD_G, MOD_B));
        g.fillOval(x, y, width, height);
    }
}
