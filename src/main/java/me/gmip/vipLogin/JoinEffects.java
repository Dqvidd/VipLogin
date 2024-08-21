package me.gmip.vipLogin;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class JoinEffects {

    private final JavaPlugin plugin;

    public JoinEffects(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void playsound(Player player) {
        String group = getPlayerGroup(player);
        String path = "effects." + group;

        String soundConfig = plugin.getConfig().getString(path + ".sound");
        float volume = (float) plugin.getConfig().getDouble(path + ".volume");
        float pitch = (float) plugin.getConfig().getDouble(path + ".pitch");

        if (soundConfig == null) {
            plugin.getLogger().warning("El nombre del sonido no está configurado para el grupo: " + group);
            return;
        }

        try {
            Sound sound = Sound.valueOf(soundConfig.toUpperCase());
            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("El sonido configurado '" + soundConfig + "' para el grupo '" + group + "' no es válido.");
        }
    }

    public boolean lightning(Player player) {
        String group = getPlayerGroup(player);
        String path = "effects." + group + ".lightning";
        return plugin.getConfig().getBoolean(path, false); // Ponemos por defecto false para no generar errores
    }

    public void particleeffect(Player player) {
        String group = getPlayerGroup(player);

        // Definimos las rutas del config.yml
        String pathParticle = "effects." + group + ".particle";
        String pathNumber = "effects." + group + ".number";
        String pathOffsetX = "effects." + group + ".offsetX";
        String pathOffsetY = "effects." + group + ".offsetY";
        String pathOffsetZ = "effects." + group + ".offsetZ";
        String pathSpeed = "effects." + group + ".optionalvalue";

        // Obtenemos la partícula y comprobamos que exista, si no existe generamos un error y acabamos la función (Usando el valueOf)
        String particleConfig = plugin.getConfig().getString(pathParticle).toUpperCase();
        Particle particle;
        try {
            particle = Particle.valueOf(particleConfig);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Partícula inválida en la config: " + particleConfig);
            return;
        }

        // Obtenemos los parámetros de la config y spawneamos la partícula
        int number = plugin.getConfig().getInt(pathNumber, 20);
        double offsetX = plugin.getConfig().getDouble(pathOffsetX, 1.0);
        double offsetY = plugin.getConfig().getDouble(pathOffsetY, 1.0);
        double offsetZ = plugin.getConfig().getDouble(pathOffsetZ, 1.0);
        double optionalvalue = plugin.getConfig().getDouble(pathSpeed, 0.1);

        player.getWorld().spawnParticle(particle, player.getLocation(), number, offsetX, offsetY, offsetZ, optionalvalue);
    }

    private String getPlayerGroup(Player player) {

        // Ordenado en sentido descendente de el que tiene más permisos al que menos.
        // Esto se hace por los parents de cada grupo, para evitar que se ejecute el de otro grupo

        if (player.hasPermission("owner")) return "owner";
        if (player.hasPermission("admin")) return "admin";
        if (player.hasPermission("dev")) return "dev";
        if (player.hasPermission("moderador")) return "moderador";
        if (player.hasPermission("helper")) return "helper";
        if (player.hasPermission("builder")) return "builder";
        if (player.hasPermission("exodus")) return "exodus";
        if (player.hasPermission("divine")) return "divine";
        if (player.hasPermission("nova")) return "nova";
        return "default";
    }
}
