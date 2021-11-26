package entities.core;

import entities.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public record Wave(ArrayList<HashMap<Unit, Integer>> ledger, int delay, int spread, int duration) {}
