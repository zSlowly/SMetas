package pt.slowly.metas.comandos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.massivecraft.factions.entity.MPlayer;

import pt.slowly.metas.Main;
import pt.slowly.metas.inventarios.InventoryLoader;
import pt.slowly.metas.manager.Meta;

public class FMeta implements Listener {
	
	@EventHandler
	public void cmd(PlayerCommandPreprocessEvent e) {
		
		Player p = e.getPlayer();
		MPlayer mp = MPlayer.get(p);
		
		if (Main.definir.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage("§cVocê não pode digitar comandos enquanto define a meta para a sua facção.");
			return;
		}
		
		if (Main.pagar.contains(p.getName())) {
			e.setCancelled(true);
			p.sendMessage("§cVocê não pode digitar comandos enquanto paga a meta para a sua facção.");
			return;
		}
		
		if (e.getMessage().equalsIgnoreCase("/f meta")) {
			
			e.setCancelled(true);
			
			if (!mp.hasFaction()) {
				p.sendMessage("§cVocê não possui uma facção.");
				return;
			}
			
			if (!Main.metas.containsKey(p.getName())) {
				Meta meta = new Meta(0, 0, mp.getFactionTag());
				Main.metas.put(p.getName(), meta);
			}else {
				Meta meta = Main.metas.get(p.getName());
				if (!meta.getFaction_tag().equals(mp.getFactionTag())) {
					meta.setMetaTotal(0);
					meta.setMetaPaga(0);
					meta.setFaction_tag(mp.getFactionTag());
				}
			}
			
			InventoryLoader.openInventory(mp.getFaction());
			
		}
		
	}

}
