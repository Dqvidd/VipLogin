package me.gmip.vipLogin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class VipLoginReload implements CommandExecutor {

    private final JavaPlugin plugin;

    public VipLoginReload(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("viplogin.reload")) {
            plugin.reloadConfig();
            sender.sendMessage("§aConfiguración recargada correctamente.");
            return true;
        } else {
            sender.sendMessage("§cNo tienes permiso para ejecutar este comando.");
            return false;
        }
    }
}
