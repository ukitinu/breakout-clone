package org.ukitinu.breakoutclone.objects.bricks;

import lombok.AccessLevel;
import lombok.Getter;
import org.ukitinu.breakoutclone.ObjectType;
import org.ukitinu.breakoutclone.objects.AbstractGameObject;

import java.awt.*;

@Getter(AccessLevel.PROTECTED)
public abstract class AbstractBrick extends AbstractGameObject implements Brick {
    private final int xCentre;
    private final int yCentre;
    private final int xUnit;
    private final int yUnit;

    AbstractBrick(int x, int y) {
        super(x, y, Brick.WIDTH, Brick.HEIGHT, ObjectType.BRICK);
        this.xCentre = x + getWidth() / 2;
        this.yCentre = y + getHeight() / 2;
        this.xUnit = getWidth() / 20;
        this.yUnit = getHeight() / 12;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);

        drawSymbol(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, width, height);
    }

}
