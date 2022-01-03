package org.ukitinu.breakoutclone.objects;

import org.ukitinu.breakoutclone.Game;
import org.ukitinu.breakoutclone.HUD;
import org.ukitinu.breakoutclone.ObjectType;
import org.ukitinu.breakoutclone.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FakePaddle extends MovingGameObject implements ActionListener, HasScore {
    private static final int WIDTH = Paddle.WIDTH / 2;
    private static final int HEIGHT = Paddle.HEIGHT;
    private static final int FADE_OUT_MILLIS = 5_000;
    private static final float FADE_OUT = 0.35f;
    private static final int DELTA_DIV = 50;
    private static final int MAX_SPEED = 4;
    private static final int SPEED = 2;

    private float alpha = 1;

    public FakePaddle(int x, int y) {
        super(x, y, WIDTH, HEIGHT, ObjectType.FAKE_PADDLE);
        Timer timer = new Timer(FADE_OUT_MILLIS, this);
        timer.start();
        setVelX(SPEED);
    }

    @Override
    public void onHit() {
        HUD.INSTANCE.updateScore(this);
        Room.INSTANCE.remove(this);
    }

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public void tick() {
        if (x <= 0) {
            velX = Math.abs(velX);
        } else if (x >= Game.WIDTH - width - width / 3) {
            velX = -Math.abs(velX);
        }

        super.tick();
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (alpha > 0) alpha = Math.max(0, alpha - FADE_OUT);
        else Room.INSTANCE.remove(this);
    }

    /*private void calcVel() {
        int centre = x + width / 2;
        Paddle paddle = Room.INSTANCE.getPlayerPaddle();
        int paddleCentre = paddle.getX() + paddle.getWidth() / 2;

        int delta = paddleCentre - centre;
        if (delta == 0) {
            velX = 0;
        } else {
            int speed = Utils.minMax(0, 1 + Math.abs(delta / DELTA_DIV), MAX_SPEED);
            velX = delta > 0 ? speed : -speed;
        }
    }*/
}
