package com.testhud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class TestHudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.options.hudHidden) return;

        PlayerEntity player = client.player;
        int screenWidth = context.getScaledWindowWidth();

        int panelX = 4;
        int panelY = 4;
        int panelW = 160;
        int panelH = 70;

        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0x88000000);
        context.fill(panelX, panelY, panelX + panelW, panelY + 1, 0xFFFFAA00);
        context.fill(panelX, panelY + panelH - 1, panelX + panelW, panelY + panelH, 0xFFFFAA00);
        context.fill(panelX, panelY, panelX + 1, panelY + panelH, 0xFFFFAA00);
        context.fill(panelX + panelW - 1, panelY, panelX + panelW, panelY + panelH, 0xFFFFAA00);

        int textX = panelX + 5;
        int lineH = 10;
        int y = panelY + 5;

        context.drawTextWithShadow(client.textRenderer, "\¹6\¹l[ TEST HUD ]\u00a72", textX, y, 0xFFFFAA00);
        y += lineH + 2;

        BlockPos pos = player.getBlockPos();
        context.drawTextWithShadow(client.textRenderer,
                String.format("\u00a77XYZ: \u00a7f%d, %d, %d", pos.getX(), pos.getY(), pos.getZ()),
                textX, y, 0xFFFFFFFF);
        y += lineH;

        int health = (int) player.getHealth();
        int maxHealth = (int) player.getMaxHealth();
        String healthColor = health > 10 ? "\u00a7a" : health > 6 ? "\u00a7e" : "\u00a7c";
        context.drawTextWithShadow(client.textRenderer,
                "\u00a77Health: " + healthColor + health + "\u00a77/" + maxHealth,
                textX, y, 0xFFFFFFFF);
        y += lineH;

        int food = player.getHungerManager().getFoodLevel();
        String foodColor = food > 14 ? "\u00a7a" : food > 7 ? "\u00a7e" : "\u00a7c";
        context.drawTextWithShadow(client.textRenderer,
                "\u00a77Hunger: " + foodColor + food + "\u00a77/20",
                textX, y, 0xFFFFFFFF);
        y += lineH;

        String dim = player.getWorld().getRegistryKey().getValue().getPath();
        context.drawTextWithShadow(client.textRenderer,
                "\u00a77Dim: \u00a7b" + dim, textX, y, 0xFFFFFFFF);

        int fps = client.getCurrentFps();
        String fpsColor = fps >= 60 ? "\u00a7a" : fps >= 30 ? "\u00a7e" : "\u00a7c";
        String fpsText = fpsColor + fps + " \u00a77FPS";
        int fpsW = client.textRenderer.getWidth(fpsText);
        context.drawTextWithShadow(client.textRenderer, fpsText,
                screenWidth - fpsW - 4, 4, 0xFFFFFFFF);
    }
}