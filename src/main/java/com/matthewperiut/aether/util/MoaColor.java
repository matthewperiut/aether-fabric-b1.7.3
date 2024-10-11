package com.matthewperiut.aether.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoaColor {
    private static final Random random = new Random();
    public static List<MoaColor> colors = new ArrayList();
    private static int totalChance;

    static {
        new MoaColor(0, 7829503, 3, 100, "Blue");
        new MoaColor(1, 2236962, 8, 5, "Black");
        new MoaColor(2, 16777215, 4, 20, "White");
    }

    public int ID;
    public int colour;
    public int jumps;
    public int chance;
    public String name;

    public MoaColor(int i, int j, int k, int l, String s) {
        this.ID = i;
        this.colour = j;
        this.jumps = k;
        this.chance = l;
        totalChance += l;
        this.name = s;
        colors.add(this);
    }

    public static MoaColor pickRandomMoa() {
        int i = random.nextInt(totalChance);

        for (int j = 0; j < colors.size(); ++j) {
            if (i < colors.get(j).chance) {
                return colors.get(j);
            }

            i -= colors.get(j).chance;
        }

        return colors.get(0);
    }

    public static MoaColor getColour(int ID) {
        for (int i = 0; i < colors.size(); ++i) {
            if (colors.get(i).ID == ID) {
                return colors.get(i);
            }
        }

        return colors.get(0);
    }

    public String getTexture(boolean saddled) {
        return "aether:stationapi/textures/mobs/" + this.name + (saddled ? "MoaSaddle.png" : "Moa.png");
    }
}
