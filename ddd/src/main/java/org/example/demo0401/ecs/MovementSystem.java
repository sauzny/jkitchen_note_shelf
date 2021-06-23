package org.example.demo0401.ecs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class MovementSystem {
    List<Vector> list;

    // System的行为
    public void update(float delta) {
        for(Vector pos : list) { // 这个loop直接走了CPU缓存，性能很高，同时可以用SIMD优化
            pos.x = pos.x + delta;
            pos.y = pos.y + delta;
        }
    }

    @Test
    public void test() {
        MovementSystem system = new MovementSystem();
        system.list = new ArrayList<>();
        list.add(new Vector(0, 0));
        Entity entity = new Entity(list.get(0));
        system.update(0.1F);
        assertTrue(entity.position.x == 0.1);
    }
}
