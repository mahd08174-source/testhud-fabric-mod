package com.testhud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class TestHudRenderer implements HudRenderCallback {

    private static final char c = '\u00a7';

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.options.hudHidden) return;

        PlayerEntity player = client.player;
        int screenWidth = context.getScaledWindowWidth();

        int panelX = 4, panelY = 4, panelW = 160, panelH = 70;

        context.fill(panelX, panelY, panelX + panelW, panelY + panelH, 0x88000000);
        context.fill(panelX, panelY, panelX + panelW, panelY + 1, 0xFFFFAA00);
        context.fill(panelX, panelY + panelH - 1, panelX + panelW, panelY + panelH, 0xFFFFAA00);
        context.fill(panelX, panelY, panelX + 1, panelY + panelH, 0xFFFFAA00);
        context.fill(panelX + panelW - 1, panelY, panelX + panelW, panelY + panelH, 0xFFFFAA00);

        int textX = panelX + 5, lineH = 10, y = panelY + 5;

        context.drawTextWithShadow(client.textRenderer, c + "6" + c + "l[ TEST HUD ]" + c + "r", textX, y, 0xFFFFAA00);
        y += lineH + 2;

        BlockPos pos = player.getBlockPos();
        context.drawTextWithShadow(client.textRenderer,
            String.format(c + "7XYZ: " + c + "f%d, %d, %d", pos.getX(), pos.getY(), pos.getZ()),
            textX, y, 0xFFFFFFFF);
        y += lineH;

        int health = (int) player.getHealth();
        int maxHealth = (int) player.getMaxHealth();
        String hc = health > 10 ? c + "a" : health > 6 ? c + "e" : c + "c";
        context.drawTextWithShadow(client.textRenderer,
            c + "7Health: " + hc + health + c + "7/" + maxHealth, textX, y, 0xFFFFFFFF);
        y += lineH;

        int food = player.getHungerManager().getFoodLevel();
        String fc = food > 14 ? c + "a" : food > 7 ? c + "e" : c + "c";
        context.drawTextWithShadow(client.textRenderer,
            c + "7Hunger: " + fc + food + c + "7/20", textX, y, 0xFFFFFFFF);
        y += lineH;

        String dim = player.getWorld().getRegistryKey().getValue().getPath();
        context.drawTextWithShadow(client.textRenderer, c + "7Dim: " + c + "b" + dim, textX, y, 0xFFFFFFFF);

        int fps = client.getCurrentFps();
        String fpsc = fps >= 60 ? c + "a" : fps >= 30 ? c + "e" : c + "c";
        String fpsText = fpsc + fps + " " + c + "7FPS";
        int fpsW = client.textRenderer.getWidth(fpsText);
        context.drawTextWithShadow(client.textRenderer, fpsText, screenWidth - fpsW - 4, 4, 0xFFFFFFFF);
    }
}
