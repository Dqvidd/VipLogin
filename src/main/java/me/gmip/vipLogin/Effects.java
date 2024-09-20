package me.gmip.vipLogin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Effects implements Listener {

    private final VipLogin plugin;

    public void doAsyncLater(Runnable runnable, long delay) {
        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public Effects(VipLogin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        doAsyncLater(() -> {
            JoinEffects effects = new JoinEffects(plugin);
            effects.getPlayerPermission(player);
            effects.playsound(player);
            effects.particleeffect(player);
            // Hacer el Rayo en el hilo principal (cosas de spigot)
            Bukkit.getScheduler().runTask(plugin, () -> {
                effects.lightning(player);
            });
        }, 20L);
    }
}

