package pt.slowly.metas;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.milkbowl.vault.economy.Economy;
import pt.slowly.metas.comandos.FMeta;
import pt.slowly.metas.eventos.AsyncPlayerChat;
import pt.slowly.metas.eventos.InventoryClick;
import pt.slowly.metas.eventos.PlayerJoin;
import pt.slowly.metas.eventos.PlayerQuit;
import pt.slowly.metas.manager.Meta;
import pt.slowly.metas.utils.SQLite;

public class Main extends JavaPlugin {
	
	private Economy econ;
	public static HashMap<String, Meta> metas = new HashMap<>();
	public static List<String> definir = new ArrayList<>();
	public static List<String> pagar = new ArrayList<>();
	
	@Override
	public void onEnable() {
		
		if (!setupEconomy()) {
            this.getLogger().severe("A dependência do Vault não foi encontrada.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
		
		File file = new File(this.getDataFolder().toString());
		try {
			file.mkdir();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		saveJogadores();
		
		SQLite sqlite = new SQLite(this);
		sqlite.connect();
		
		Bukkit.getPluginManager().registerEvents(new FMeta(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChat(), this);
		
	}
	
	@Override
	public void onDisable() {
	}
	
	private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	private void saveJogadores() {
		
		SQLite sqlite = new SQLite(this);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for (Entry<String, Meta> meta : metas.entrySet()) {
					sqlite.saveJogador(meta.getValue().getMetaTotal(), meta.getValue().getMetaPaga(), meta.getValue().getFaction_tag(), meta.getKey());
				}
				
				System.out.println("Foram salvas " + metas.entrySet().size() + " metas com sucesso!");
				
			}
		}.runTaskTimer(this, 20 * 60 * 5, 20 * 60 * 5);
		
	}

}
