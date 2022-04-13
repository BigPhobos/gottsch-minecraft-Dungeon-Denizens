/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class Troll extends Monster {

	public Troll(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
	}

}
