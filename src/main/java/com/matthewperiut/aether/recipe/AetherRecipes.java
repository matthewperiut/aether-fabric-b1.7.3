package com.matthewperiut.aether.recipe;

import com.matthewperiut.aether.block.AetherBlocks;
import com.matthewperiut.aether.item.AetherItems;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;

import java.util.Objects;

public class AetherRecipes
{
    @EventListener
    public void registerRecipes(RecipeRegisterEvent event)
    {
        RecipeRegisterEvent.Vanilla type = RecipeRegisterEvent.Vanilla.fromType(event.recipeId);
        switch (Objects.requireNonNull(type))
        {
            case CRAFTING_SHAPELESS ->
            {
                CraftingRegistry.addShapelessRecipe(new ItemStack(AetherItems.DartShooter, 1, 1), new ItemStack(AetherItems.DartShooter, 1, 0), AetherItems.AechorPetal);
                CraftingRegistry.addShapelessRecipe(new ItemStack(AetherItems.Zanite, 4), AetherBlocks.ZaniteBlock);
                CraftingRegistry.addShapelessRecipe(new ItemStack(Item.DYE_POWDER, 2, 5), AetherBlocks.PurpleFlower);
            }
            case CRAFTING_SHAPED ->
            {
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.PickHolystone, 1), "ZZZ", " Y ", " Y ", 'Z', AetherBlocks.Holystone, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.PickHolystone, 1), "ZZZ", " Y ", " Y ", 'Z', AetherBlocks.Holystone, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.AxeHolystone, 1), "ZZ", "ZY", " Y", 'Z', AetherBlocks.Holystone, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ShovelHolystone, 1), "Z", "Y", "Y", 'Z', AetherBlocks.Holystone, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.SwordHolystone, 1), "Z", "Z", "Y", 'Z', AetherBlocks.Holystone, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.PickZanite, 1), "ZZZ", " Y ", " Y ", 'Z', AetherItems.Zanite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.AxeZanite, 1), "ZZ", "ZY", " Y", 'Z', AetherItems.Zanite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ShovelZanite, 1), "Z", "Y", "Y", 'Z', AetherItems.Zanite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.SwordZanite, 1), "Z", "Z", "Y", 'Z', AetherItems.Zanite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.PickGravitite, 1), "ZZZ", " Y ", " Y ", 'Z', AetherBlocks.EnchantedGravitite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.AxeGravitite, 1), "ZZ", "ZY", " Y", 'Z', AetherBlocks.EnchantedGravitite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ShovelGravitite, 1), "Z", "Y", "Y", 'Z', AetherBlocks.EnchantedGravitite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.SwordGravitite, 1), "Z", "Z", "Y", 'Z', AetherBlocks.EnchantedGravitite, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.PickSkyroot, 1), "ZZZ", " Y ", " Y ", 'Z', AetherBlocks.Plank, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.AxeSkyroot, 1), "ZZ", "ZY", " Y", 'Z', AetherBlocks.Plank, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ShovelSkyroot, 1), "Z", "Y", "Y", 'Z', AetherBlocks.Plank, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.SwordSkyroot, 1), "Z", "Z", "Y", 'Z', AetherBlocks.Plank, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.Bucket, 1, 0), "Z Z", " Z ", 'Z', AetherBlocks.Plank);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.Stick, 4), "#", "#", '#', AetherBlocks.Plank);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.WORKBENCH, 1), "UU", "UU", 'U', AetherBlocks.Plank);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherBlocks.AmbrosiumTorch, 2), " Z", " Y", 'Z', AetherItems.AmbrosiumShard, 'Y', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.Dart, 1, 0), "X", "Z", "Y", 'X', AetherItems.GoldenAmber, 'Z', AetherItems.Stick, 'Y', Item.FEATHER);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.Dart, 8, 1), "XXX", "XZX", "XXX", 'X', new ItemStack(AetherItems.Dart, 1, 0), 'Z', new ItemStack(AetherItems.Bucket, 1, 2));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.WhiteCape, 1), "XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 0));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.RedCape, 1), "XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 14));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.BlueCape, 1), "XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 11));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.BlueCape, 1), "XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 3));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.BlueCape, 1), "XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 9));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.YellowCape, 1), "XX", "XX", "XX", 'X', new ItemStack(Block.WOOL, 1, 4));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherBlocks.Incubator, 1), "XXX", "XZX", "XXX", 'X', AetherBlocks.Holystone, 'Z', AetherBlocks.AmbrosiumTorch);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherBlocks.Freezer, 1), "XXX", "XZX", "YYY", 'X', AetherBlocks.Holystone, 'Z', AetherBlocks.Icestone, 'Y', AetherBlocks.Plank);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherBlocks.ZaniteBlock, 1), "XX", "XX", 'X', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.DartShooter, 1), "X", "X", "Y", 'X', AetherBlocks.Plank, 'Y', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.CloudParachute, 1), "UU", "UU", 'U', new ItemStack(AetherBlocks.Aercloud, 0));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.CloudParachuteGold, 1), "UU", "UU", 'U', new ItemStack(AetherBlocks.Aercloud, 1, 2));
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.SADDLE, 1), "XXX", "XZX", 'X', Item.LEATHER, 'Z', Item.STRING);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.NatureStaff, 1), "Y", "X", 'X', AetherItems.Stick, 'Y', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GravititeHelmet, 1), "XXX", "X X", 'X', AetherBlocks.EnchantedGravitite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GravititeBodyplate, 1), "X X", "XXX", "XXX", 'X', AetherBlocks.EnchantedGravitite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GravititePlatelegs, 1), "XXX", "X X", "X X", 'X', AetherBlocks.EnchantedGravitite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GravititeBoots, 1), "X X", "X X", 'X', AetherBlocks.EnchantedGravitite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZaniteHelmet, 1), "XXX", "X X", 'X', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZaniteChestplate, 1), "X X", "XXX", "XXX", 'X', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZaniteLeggings, 1), "XXX", "X X", "X X", 'X', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZaniteBoots, 1), "X X", "X X", 'X', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.CHEST, 1), "PPP", "P P", "PPP", 'P', AetherBlocks.Plank);
                CraftingRegistry.addShapedRecipe(new ItemStack(Item.WOOD_DOOR), "PP", "PP", "PP", 'P', AetherBlocks.Plank);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.FENCE, 2), "SSS", "SSS", 'S', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.LADDER, 4), "S S", "SSS", "S S", 'S', AetherItems.Stick);
                CraftingRegistry.addShapedRecipe(new ItemStack(Block.JUKEBOX), "PPP", "PGP", "PPP", 'P', AetherBlocks.Plank, 'G', AetherBlocks.EnchantedGravitite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherBlocks.Plank, 4), "L", 'L', AetherBlocks.Log);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherBlocks.Enchanter), "HHH", "HZH", "HHH", 'H', AetherBlocks.Holystone, 'Z', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.CloudParachute), "CC", "CC", 'C', new ItemStack(AetherBlocks.Aercloud, 1, 0));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.CloudParachuteGold), "CC", "CC", 'C', new ItemStack(AetherBlocks.Aercloud, 1, 2));
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.LeatherGlove), "C C", 'C', Item.LEATHER);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.IronGlove), "C C", 'C', Item.IRON_INGOT);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GoldGlove), "C C", 'C', Item.GOLD_INGOT);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.DiamondGlove), "C C", 'C', Item.DIAMOND);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZaniteGlove), "C C", 'C', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GravititeGlove), "C C", 'C', AetherBlocks.EnchantedGravitite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.IronRing), " C ", "C C", " C ", 'C', Item.IRON_INGOT);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GoldRing), " C ", "C C", " C ", 'C', Item.GOLD_INGOT);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZaniteRing), " C ", "C C", " C ", 'C', AetherItems.Zanite);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.IronPendant), "SSS", "S S", " C ", 'S', Item.STRING, 'C', Item.IRON_INGOT);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.GoldPendant), "SSS", "S S", " C ", 'S', Item.STRING, 'C', Item.GOLD_INGOT);
                CraftingRegistry.addShapedRecipe(new ItemStack(AetherItems.ZanitePendant), "SSS", "S S", " C ", 'S', Item.STRING, 'C', AetherItems.Zanite);
            }
        }
    }
}
