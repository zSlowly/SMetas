package pt.slowly.metas.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import pt.slowly.metas.Main;
import pt.slowly.metas.manager.Meta;
import pt.slowly.metas.utils.SQLite;

public class PlayerQuit implements Listener {
	
	private Main plugin;
	
	public PlayerQuit(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		SQLite sqlite = new SQLite(plugin);
		
		if (Main.metas.containsKey(p.getName())) {
			Meta meta = Main.metas.get(p.getName());
			sqlite.saveJogador(meta.getMetaTotal(), meta.getMetaPaga(), meta.getFaction_tag(), p.getName());
		}
		
	}

}
