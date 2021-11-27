package pt.slowly.metas.inventarios;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

import pt.slowly.metas.Main;
import pt.slowly.metas.manager.Meta;

public class InventoryLoader {
	
	public static void openInventory(Faction fac) {
		
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "Metas");
		
		int slot = 10;
		
		for (MPlayer members : fac.getMPlayers()) {
			
			if (slot == 17 || slot == 26 || slot == 35 || slot == 44) {
				slot = slot + 2;
			}
			
			Meta meta = Main.metas.get(members.getName());
			ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			ItemMeta imeta = i.getItemMeta();
			
			if (members.isOnline()) {
				imeta.setDisplayName("§a" + members.getName());
			}else {
				imeta.setDisplayName("§c" + members.getName());
			}
			
			List<String> lore = new ArrayList<>();
			lore.add("");
			lore.add("§7Meta que o jogador tem que pagar: §f" + meta.getMetaTotal());
			lore.add("§7Quantia da meta que o jogador pagou: §f" + meta.getMetaPaga());
			lore.add("");
			if (meta.getMetaPaga() == meta.getMetaTotal()) {
				lore.add("§aO jogador já pagou a meta!");
			}else {
				lore.add("§cO jogador ainda não pagou a meta!");
			}
			
			imeta.setLore(lore);
			i.setItemMeta(imeta);
			
			SkullMeta skullmeta = (SkullMeta) i.getItemMeta();
			skullmeta.setOwner(members.getName());
			i.setItemMeta(skullmeta);
			
			inv.setItem(slot, i);
			
		}
		
		ItemStack enviar = new ItemStack(Material.GOLD_INGOT);
		ItemMeta enviarMeta = enviar.getItemMeta();
		enviarMeta.setDisplayName("§ePagar Meta");
		List<String> enviarLore = new ArrayList<>();
		enviarLore.add("§7Clique aqui para pagar");
		enviarLore.add("§7uma certa quantia da sua meta.");
		enviarMeta.setLore(enviarLore);
		enviar.setItemMeta(enviarMeta);
		
		inv.setItem(42, enviar);
		
		ItemStack definir = new ItemStack(Material.GOLD_INGOT);
		ItemMeta definirMeta = enviar.getItemMeta();
		definirMeta.setDisplayName("§eDefinir Meta");
		List<String> definirLore = new ArrayList<>();
		definirLore.add("§7Clique aqui para definir");
		definirLore.add("§7a meta para a facção.");
		definirMeta.setLore(definirLore);
		definir.setItemMeta(definirMeta);
		
		inv.setItem(43, definir);
		
	}

}
