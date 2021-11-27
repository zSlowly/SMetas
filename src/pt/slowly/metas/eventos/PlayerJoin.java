package pt.slowly.metas.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pt.slowly.metas.Main;
import pt.slowly.metas.utils.SQLite;

public class PlayerJoin implements Listener {
	
	private Main plugin;
	
	public PlayerJoin(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		SQLite sqlite = new SQLite(plugin);
		
		if (sqlite.hasJogador(p.getName())) {
			sqlite.loadJogador(p.getName());
		}
		
	}

}
