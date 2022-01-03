package ukitinu.breakoutclone;

import ukitinu.breakoutclone.objects.*;
import ukitinu.breakoutclone.objects.bricks.Brick;
import ukitinu.breakoutclone.objects.*;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public enum Room {

    INSTANCE;

    private final Map<ObjectType, LinkedList<GameObject>> objects = new HashMap<>();
    private final Map<ObjectType, LinkedList<GameObject>> toAdd = new HashMap<>();
    private final Map<ObjectType, LinkedList<GameObject>> toRemove = new HashMap<>();

    Room() {
        for (var type : ObjectType.values()) {
            objects.put(type, new LinkedList<>());
            toAdd.put(type, new LinkedList<>());
            toRemove.put(type, new LinkedList<>());
        }
    }

    public void add(GameObject obj) {
        toAdd.get(obj.getObjectType()).add(obj);
    }

    public void remove(GameObject obj) {
        toRemove.get(obj.getObjectType()).add(obj);
    }

    public void clear() {
        for (var type : ObjectType.values()) {
            toAdd.get(type).clear();
            toRemove.get(type).clear();
            objects.get(type).clear();
        }
    }

    public Paddle getPlayerPaddle() {
        return (Paddle) objects.get(ObjectType.PADDLE).stream().findFirst().orElseThrow();
    }

    public List<FakePaddle> getFakePaddles() {
        return objects.get(ObjectType.FAKE_PADDLE).stream().map(o -> (FakePaddle) o).collect(Collectors.toList());
    }

    public Ball getBall() {
        return (Ball) objects.get(ObjectType.BALL).stream().findFirst().orElseThrow();
    }

    public List<Brick> getBricks() {
        return objects.get(ObjectType.BRICK).stream().map(o -> (Brick) o).collect(Collectors.toList());
    }

    public int countBricks() {
        return objects.get(ObjectType.BRICK).size();
    }

    public List<Modifier> getModifiers() {
        return objects.get(ObjectType.MODIFIER).stream().map(o -> (Modifier) o).collect(Collectors.toList());
    }

    public List<GameObject> getCollidables(ObjectType... types) {
        if (types == null) types = ObjectType.values();
        List<GameObject> list = new ArrayList<>();
        for (ObjectType type : types) {
            if (type.hasCollision()) list.addAll(objects.get(type));
        }
        return list;
    }

    public void tick() {
        for (var list : objects.values()) list.forEach(GameObject::tick);
        updateObjects();
    }

    public void render(Graphics g) {
        for (var list : objects.values()) list.forEach(r -> r.render(g));
    }

    private void updateObjects() {
        for (var entry : objects.entrySet()) {
            entry.getValue().addAll(toAdd.get(entry.getKey()));
            toAdd.get(entry.getKey()).clear();
            entry.getValue().removeAll(toRemove.get(entry.getKey()));
            toRemove.get(entry.getKey()).clear();
        }
    }
}
