package ukitinu.breakoutclone.collision;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CollidableTest {

    @Test
    void findCollision_onSelf() {
        Collidable coll = () -> null;
        assertEquals(Collision.NONE, coll.findCollision(coll));
    }

    @Test
    void findCollision_null() {
        Collidable coll1 = () -> null;
        Collidable coll2 = () -> null;
        Collidable coll3 = Rectangle::new;
        assertEquals(Collision.NONE, coll1.findCollision(coll2));
        assertEquals(Collision.NONE, coll3.findCollision(coll2));
        assertEquals(Collision.NONE, coll1.findCollision(coll3));
    }

    @Test
    void findCollision() {
        Collidable coll1 = () -> new Rectangle(0, 0, 2, 2);
        Collidable coll2 = () -> new Rectangle(0, 1, 2, 2);
        Collidable coll3 = () -> new Rectangle(1, 0, 2, 2);
        Collidable coll4 = () -> new Rectangle(2, 2, 1, 1);
        assertEquals(Collision.HORIZONTAL, coll1.findCollision(coll2)); // intersection is 0+2, 1+1 (x+width, y+height)
        assertEquals(Collision.VERTICAL, coll1.findCollision(coll3)); // intersection is 1+1, 0+2
        assertEquals(Collision.HORIZONTAL, coll2.findCollision(coll3)); // intersection is 1+1, 1+1
        assertEquals(Collision.NONE, coll1.findCollision(coll4));
    }
}