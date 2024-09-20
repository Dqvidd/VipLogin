package me.gmip.vipLogin;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Set;

public class JoinEffects {

    private final JavaPlugin plugin;

    private String sound;
    private double volume;
    private double pitch;
    private boolean lightning;
    private String particle;
    private int particleAmount;
    private double offsetX;
    private double offsetY;
    private double offsetZ;
    private double optionalValue;
    private String node;
    private File file;
    private FileConfiguration config;

    public JoinEffects(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    public void playsound(Player player) {
        if (sound == null) {
            return;
        }

        try {
            Sound finalSound = Sound.valueOf(sound.toUpperCase());
            player.playSound(player.getLocation(), finalSound, (float) volume, (float) pitch);
        } catch (IllegalArgumentException ex) {
            plugin.getLogger().warning("El sonido configurado '" + sound + "' para el grupo '" + node + "' no es válido.");
        }
    }

    public void particleeffect(Player player) {
        if (particle == null) {
            return;
        }

        Particle particlefinal;
        try {
            particlefinal = Particle.valueOf(particle);
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Partícula inválida en la config: " + particle);
            return;
        }

        player.getWorld().spawnParticle(particlefinal, player.getLocation(), particleAmount, offsetX, offsetY, offsetZ, optionalValue);

    }

    public void lightning(Player player) {
        if (lightning) {
            player.getLocation().getWorld().strikeLightningEffect(player.getLocation());
        }
    }

    public void getPlayerPermission(Player player) {
        ConfigurationSection seccion = plugin.getConfig().getConfigurationSection("vip-join.permissions");

        if (seccion != null) {
            Set<String> nodos = seccion.getKeys(false);
            for (String nodo : nodos) {
                if (player.hasPermission(nodo)) {
                    setConfigVariables(seccion, nodo);
                    return;
                }
            }
        }
    }

    private void setConfigVariables(ConfigurationSection permissionsSection, String node) {
        String nodePath = "vip-join.permissions." + node;

        // Usamos el this. este para hacer las variables globales y así usarlas en las otras funciones
        this.sound = plugin.getConfig().getString(nodePath + ".sound");
        this.volume = plugin.getConfig().getDouble(nodePath + ".volume");
        this.pitch = plugin.getConfig().getDouble(nodePath + ".pitch");
        this.lightning = plugin.getConfig().getBoolean(nodePath + ".lightning");
        this.particle = plugin.getConfig().getString(nodePath + ".particle");
        this.particleAmount = plugin.getConfig().getInt(nodePath + ".particle-amount");
        this.offsetX = plugin.getConfig().getDouble(nodePath + ".offset-x");
        this.offsetY = plugin.getConfig().getDouble(nodePath + ".offset-y");
        this.offsetZ = plugin.getConfig().getDouble(nodePath + ".offset-z");
        this.optionalValue = plugin.getConfig().getDouble(nodePath + ".optional-value");
    }

}
