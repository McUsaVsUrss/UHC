package net.minetopix.library.main.item.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import net.minetopix.library.main.item.simplemeta.SimpleFireworkEffect;

public class FireworkData extends ItemData {
	private List<FireworkEffect> allEffects = new ArrayList<FireworkEffect>();
	
	public FireworkData(SimpleFireworkEffect... effects) {
		for(SimpleFireworkEffect effect : effects) {
			allEffects.add(effect.build());
		}
	}
	
	@Override
	public void applyOn(ItemStack applyOn) throws WrongDataException{
		try {
			if(!(applyOn.getType() == Material.FIREWORK)) {
				return;
			}
			
			FireworkMeta fireworkMeta = (FireworkMeta) applyOn.getItemMeta();
			fireworkMeta.addEffects(allEffects);
			applyOn.setItemMeta(fireworkMeta);
		} catch(Exception e) {
			try {
				throw new WrongDataException(this);
			} catch (WrongDataException e1) {
				
			}
		}
	}
}
