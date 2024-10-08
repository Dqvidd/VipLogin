package me.gmip.vipLogin;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class VipLogin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println("El plugin VipLogin ha sido cargado!");
        saveDefaultConfig();
        reloadConfig();

        Bukkit.getPluginManager().registerEvents(new Effects(this), this);
        this.getCommand("viplogin").setExecutor(new VipLoginReload(this));
    }

    @Override
    public void onDisable() {
        System.out.println("El plugin VipLogin ha sido desactivado!");
    }
}
