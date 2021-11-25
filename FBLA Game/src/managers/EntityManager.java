package managers;

import entities.core.Entity;
import entities.projectiles.Projectile;
import entities.units.Unit;
import gamestates.Game;
import main.Engine;

import java.util.ArrayList;
import java.util.HashMap;

public class EntityManager {
    private DisplayManager displayManager;
    private HashMap<Entity.EntityType, ArrayList<Entity>> entities;

    public EntityManager(Game game) {
        this.entities = game.getEntities();
    }

    public void unloadProjectiles() {
        for(Entity e: entities.get(Entity.EntityType.Projectile)) {
            Projectile p = (Projectile) e;

        }
    }

//    private boolean offScreen(Entity e) {
//        float posOnScreen
//        Engine.game.displayManager.positionOnScreen(e.getPosition().getX(), e.getPosition().getY());
//
//    }
}
