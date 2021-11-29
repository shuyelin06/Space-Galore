package entities.core;

import entities.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class Wave {
    ArrayList<HashMap<Unit, Integer>> ledger;
    int delay;
    int spread;
    int duration;

    public Wave(ArrayList<HashMap<Unit, Integer>> ledger, int delay, int spread, int duration) {
        this.ledger = ledger;
        this.delay = delay;
        this.spread = spread;
        this.duration = duration;
    }
}
