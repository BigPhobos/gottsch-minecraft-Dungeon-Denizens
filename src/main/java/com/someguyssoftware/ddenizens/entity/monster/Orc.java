/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * 
 * @author Mark Gottschling on Apr 27, 2022
 *
 */
public class Orc extends Monster {

	public Orc(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

}
