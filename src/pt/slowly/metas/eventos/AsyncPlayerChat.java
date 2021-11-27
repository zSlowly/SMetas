package pt.slowly.metas.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

import pt.slowly.metas.Main;
import pt.slowly.metas.manager.Meta;

public class AsyncPlayerChat implements Listener {
	
	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		
		Player p = e.getPlayer();
		
		if (Main.pagar.contains(p.getName())) {
			
			e.setCancelled(true);
			
			if (e.getMessage().equalsIgnoreCase("cancelar")) {
				Main.pagar.remove(p.getName());
				p.sendMessage("§cAção cancelada com sucesso!");
				return;
			}
			
			int quantia = 0;
			
			try {
				quantia = Integer.parseInt(e.getMessage());
			} catch (Exception e2) {
				p.sendMessage("§cDigite uma quantia válida.");
			}
	
			Meta meta = Main.metas.get(p.getName());
			int metaPaga = quantia + meta.getMetaPaga();
			
			if (metaPaga > meta.getMetaTotal()) {
				p.sendMessage("§cA meta que vai pagar é superior á meta total, por favor digite uma quantia menor.");
				return;
			}
			
			meta.setMetaPaga(metaPaga);
			Main.pagar.remove(p.getName());
			p.sendMessage("§aValor pago com sucesso para a meta da facção!");
			
		}
		
		if (Main.definir.contains(p.getName())) {
			
			e.setCancelled(true);
			
			if (e.getMessage().equalsIgnoreCase("cancelar")) {
				Main.definir.remove(p.getName());
				p.sendMessage("§cAção cancelada com sucesso!");
				return;
			}
			
			int quantia = 0;
			
			try {
				quantia = Integer.parseInt(e.getMessage());
			} catch (Exception e2) {
				p.sendMessage("§cDigite uma quantia válida.");
			}
			
			MPlayer mp = MPlayer.get(p);
			Faction fac = mp.getFaction();
			
			for (MPlayer members : fac.getMPlayers()) {
				
				Meta meta = Main.metas.get(members.getName());
				meta.setMetaTotal(quantia);
				
			}
			
			p.sendMessage("§aMeta definida com sucesso!");
			Main.definir.remove(p.getName());
			
		}
		
	}

}
