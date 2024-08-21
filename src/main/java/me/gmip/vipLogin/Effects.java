package me.gmip.vipLogin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Effects implements Listener {

    private final VipLogin plugin;

    public Effects(VipLogin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Location location = player.getLocation();

        // El runTaskLater etrasa la ejecución del código por 20 ticks (1 segundo)
        new BukkitRunnable() {
            @Override
            public void run() {
                // Ejecutamos un sonido cada vez que el usuario entra
                JoinEffects soundEffect = new JoinEffects(plugin);
                soundEffect.playsound(player);

                // Ejecutamos un trueno cada vez que el usuario entra
                if (soundEffect.lightning(player)) {
                    location.getWorld().strikeLightningEffect(location);
                }

                // Ejecutamos partículas cada vez que el usuario entra
                soundEffect.particleeffect(player);

            }
        }.runTaskLater(plugin, 20L);
    }
}
