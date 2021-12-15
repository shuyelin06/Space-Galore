package entities.core;

import entities.units.Player;
import entities.units.Unit;
import main.Values;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public record EntitySpawner(ArrayList<Wave> waves) implements Runnable {

    public void run() {
        waves.get(Values.LEVEL - 1).getLedger().forEach((HashMap<Unit, Integer> m) -> {
            for (Map.Entry<Unit, Integer> en : m.entrySet()) {
                for (int i = 0; i < en.getValue(); i++) {
                    try {
                        //System.out.println("---- SPAWNING ENEMY ----");
                        //System.out.println("X: " + (Player.Player_X_Spawn + (i * waves.get(0).getSpread()) - (int) ((double) en.getValue() / 2) * 5));
                        en.getKey().getClass()
                                .getConstructor(float.class, float.class, Entity.Team.class)
                                .newInstance(Player.Player_X_Spawn + (i * waves.get(0).getSpread()) - (int) ((double) en.getValue() / 2) * 5, 48, Entity.Team.Enemy);
                        System.out.println(i + " is being spawned right now");
                    } catch (InstantiationException
                            | IllegalAccessException
                            | InvocationTargetException
                            | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(waves.get(0).getDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
