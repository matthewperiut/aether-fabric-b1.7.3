package com.matthewperiut.aether.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoaColour {
    private static final Random random = new Random();
    public static List<MoaColour> colours = new ArrayList();
    private static int totalChance;

    static {
        new MoaColour(0, 7829503, 3, 100, "Blue");
        new MoaColour(1, 2236962, 8, 5, "Black");
        new MoaColour(2, 16777215, 4, 20, "White");
    }

    public int ID;
    public int colour;
    public int jumps;
    public int chance;
    public String name;

    public MoaColour(int i, int j, int k, int l, String s) {
        this.ID = i;
        this.colour = j;
        this.jumps = k;
        this.chance = l;
        totalChance += l;
        this.name = s;
        colours.add(this);
    }

    public static MoaColour pickRandomMoa() {
        int i = random.nextInt(totalChance);

        for (int j = 0; j < colours.size(); ++j) {
            if (i < colours.get(j).chance) {
                return colours.get(j);
            }

            i -= colours.get(j).chance;
        }

        return colours.get(0);
    }

    public static MoaColour getColour(int ID) {
        for (int i = 0; i < colours.size(); ++i) {
            if (colours.get(i).ID == ID) {
                return colours.get(i);
            }
        }

        return colours.get(0);
    }

    public String getTexture(boolean saddled) {
        return "/aether/mobs/" + this.name + (saddled ? "MoaSaddle.png" : "Moa.png");
    }
}
