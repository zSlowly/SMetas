package pt.slowly.metas.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;

import pt.slowly.metas.Main;

public class InventoryClick implements Listener {
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		
		if (e.getInventory().getName().equals("Metas")) {
			e.setCancelled(true);
			
			Player p = (Player) e.getWhoClicked();
			MPlayer mp = MPlayer.get(p);
			
			if (e.getSlot() == 42) {
				
				p.closeInventory();
				p.sendMessage("");
				p.sendMessage("§aQue quantia deseja pagar para a sua facção?");
				p.sendMessage("§aDigite uma §equantia §aou §ccancelar§a.");
				p.sendMessage("");
				Main.pagar.add(p.getName());
				
			}
			
			if (e.getSlot() == 43) {
				
				if (mp.getRole() != Rel.LEADER) {
					p.closeInventory();
					p.sendMessage("§cApenas Líder pode definir uma meta para a facção.");
					return;
				}
				
				p.closeInventory();
				p.sendMessage("");
				p.sendMessage("§aQue quantia deseja definir de meta para a sua facção?");
				p.sendMessage("§aDigite uma §equantia §aou §ccancelar§a.");
				p.sendMessage("");
				Main.definir.add(p.getName());
				
			}
			
		}
		
	}

}
