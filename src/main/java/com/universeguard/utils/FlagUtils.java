/* 
 * Copyright (C) JimiIT92 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Jimi, December 2017
 * 
 */
package com.universeguard.utils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.text.format.TextColors;

import com.universeguard.UniverseGuard;
import com.universeguard.region.enums.EnumRegionExplosion;
import com.universeguard.region.enums.EnumRegionFlag;
import com.universeguard.region.enums.EnumRegionInteract;
import com.universeguard.region.enums.EnumRegionVehicle;
import com.universeguard.region.enums.RegionText;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class FlagUtils {

	/**
	 * Set the flags
	 * @param configNode
	 */
	public static void setDefaultValues(CommentedConfigurationNode configNode) {
		for(EnumRegionFlag flag : EnumRegionFlag.values()) {
			if(configNode.getNode("flags", flag.getName()).isVirtual())
				configNode.getNode("flags", flag.getName()).setValue(flag.getValue());
		}
		for(EnumRegionInteract interact : EnumRegionInteract.values()) {
			if(configNode.getNode("interacts", interact.getName()).isVirtual())
				configNode.getNode("interacts", interact.getName()).setValue(interact.getValue());
		}
		for(EnumRegionVehicle vehicle : EnumRegionVehicle.values()) {
			if(configNode.getNode("vehicleplace", vehicle.getName()).isVirtual())
				configNode.getNode("vehicleplace", vehicle.getName()).setValue(vehicle.getPlace());
		}
		for(EnumRegionVehicle vehicle : EnumRegionVehicle.values()) {
			if(configNode.getNode("vehicledestroy", vehicle.getName()).isVirtual())
				configNode.getNode("vehicledestroy", vehicle.getName()).setValue(vehicle.getDestroy());
		}
		for(EnumRegionExplosion explosion : EnumRegionExplosion.values()) {
			if(configNode.getNode("explosiondamage", explosion.getName()).isVirtual())
				configNode.getNode("explosiondamage", explosion.getName()).setValue(explosion.getDamage());
		}
		for(EnumRegionExplosion explosion : EnumRegionExplosion.values()) {
			if(configNode.getNode("explosiondestroy", explosion.getName()).isVirtual())
				configNode.getNode("explosiondestroy", explosion.getName()).setValue(explosion.getDestroy());
		}
		if(configNode.getNode("timers", "hunger").isVirtual())
			configNode.getNode("timers", "hunger").setValue(UniverseGuard.HUNGER_TIMER).setComment("The update frequency (in seconds) of the hunger flag timer");
		if(configNode.getNode("timers", "gamemode").isVirtual())
			configNode.getNode("timers", "gamemode").setValue(UniverseGuard.GAMEMODE_TIMER).setComment("The update frequency (in seconds) of the gamemode flag timer");
		if(configNode.getNode("timers", "enter_flag").isVirtual())
			configNode.getNode("timers", "enter_flag").setValue(UniverseGuard.ENTER_FLAG_TIMER).setComment("The update frequency (in milliseconds) of the enter flag timer");
		if(configNode.getNode("players", "unique_regions").isVirtual())
			configNode.getNode("players", "unique_regions").setValue(UniverseGuard.UNIQUE_REGIONS).setComment("Sets if players can be in more Regions");
		if(configNode.getNode("selector", "item").isVirtual())
			configNode.getNode("selector", "item").setValue(UniverseGuard.SELECTOR_ITEM.getId());
	}
	
	/**
	 * Get the flags
	 * @param configNode
	 */
	public static void getValues(Game game, CommentedConfigurationNode configNode) {
		for(EnumRegionFlag flag : EnumRegionFlag.values()) {
			flag.setValue(configNode.getNode("flags", flag.getName()).getBoolean());
		}
		for(EnumRegionInteract interact : EnumRegionInteract.values()) {
			interact.setValue(configNode.getNode("interacts", interact.getName()).getBoolean());
		}
		for(EnumRegionVehicle vehicle : EnumRegionVehicle.values()) {
			vehicle.setPlace(configNode.getNode("vehicleplace", vehicle.getName()).getBoolean());
			vehicle.setDestroy(configNode.getNode("vehicledestroy", vehicle.getName()).getBoolean());
		}
		for(EnumRegionExplosion explosion : EnumRegionExplosion.values()) {
			explosion.setDamage(configNode.getNode("explosiondamage", explosion.getName()).getBoolean());
			explosion.setDestroy(configNode.getNode("explosiondestroy", explosion.getName()).getBoolean());
		}
		UniverseGuard.HUNGER_TIMER = configNode.getNode("timers", "hunger").getInt();
		UniverseGuard.GAMEMODE_TIMER = configNode.getNode("timers", "gamemode").getInt();
		UniverseGuard.ENTER_FLAG_TIMER = configNode.getNode("timers", "enter_flag").getInt();
		UniverseGuard.UNIQUE_REGIONS = configNode.getNode("players", "unique_regions").getBoolean();
		if(!configNode.getNode("selector", "item").isVirtual()) {
			String id = configNode.getNode("selector", "item").getString();
			Optional<ItemType> type = game.getRegistry().getType(ItemType.class, id);
			if(type.isPresent())
				UniverseGuard.SELECTOR_ITEM = type.get();
			else
				LogUtils.print(TextColors.RED, RegionText.TEXT_WRONG_SELECTOR_ITEM.getValue() + UniverseGuard.SELECTOR_ITEM.getId());
		}
	}
	
	/**
	 * Get a flag from name
	 * @param name The name of the flag
	 * @return The flag with the given name if exists, null othewrise
	 */
	public static EnumRegionFlag getFlag(String name) {
		for(EnumRegionFlag flag : EnumRegionFlag.values()) {
			if(flag.getName().equalsIgnoreCase(name))
				return flag;
		}
		return null;
	}
	
	/**
	 * Get a interact from name
	 * @param name The name of the interact
	 * @return The interact with the given name if exists, null othewrise
	 */
	public static EnumRegionInteract getInteract(String name) {
		for(EnumRegionInteract interact : EnumRegionInteract.values()) {
			if(interact.getName().equalsIgnoreCase(name))
				return interact;
		}
		return null;
	}
	
	/**
	 * Get a interact from block
	 * @param name The block of the interact
	 * @return The interact with the given block if exists, null othewrise
	 */
	public static EnumRegionInteract getInteract(BlockType block) {
		if(block.equals(BlockTypes.CRAFTING_TABLE))
			return getInteract("craftingtable");
		else if(block.equals(BlockTypes.ANVIL))
			return getInteract("anvil");
		else if(block.equals(BlockTypes.ENCHANTING_TABLE))
			return getInteract("enchantingtable");
		else if(block.equals(BlockTypes.HOPPER))
			return getInteract("hopper");
		else if(block.equals(BlockTypes.LEVER))
			return getInteract("lever");
		else if(block.equals(BlockTypes.STONE_BUTTON) || block.equals(BlockTypes.WOODEN_BUTTON))
			return getInteract("button");
		else if(block.equals(BlockTypes.FURNACE))
			return getInteract("furnace");
		else if(block.equals(BlockTypes.WOODEN_DOOR) || block.equals(BlockTypes.BIRCH_DOOR) || block.equals(BlockTypes.SPRUCE_DOOR) ||
				block.equals(BlockTypes.JUNGLE_DOOR) || block.equals(BlockTypes.ACACIA_DOOR) || block.equals(BlockTypes.DARK_OAK_DOOR) ||
				block.equals(BlockTypes.IRON_DOOR))
			return getInteract("door");
		else if(block.equals(BlockTypes.FENCE_GATE) || block.equals(BlockTypes.BIRCH_FENCE_GATE) || block.equals(BlockTypes.SPRUCE_FENCE_GATE) ||
				block.equals(BlockTypes.JUNGLE_FENCE_GATE) || block.equals(BlockTypes.ACACIA_FENCE_GATE) || block.equals(BlockTypes.DARK_OAK_FENCE_GATE))
			return getInteract("fencegate");
		else if(block.equals(BlockTypes.TRAPDOOR) || block.equals(BlockTypes.IRON_TRAPDOOR))
			return getInteract("trapdoor");
		else if(block.equals(BlockTypes.STANDING_SIGN) || block.equals(BlockTypes.WALL_SIGN))
			return getInteract("sign");
		else
			return null;
	}
	
	/**
	 * Get a vehicle from entity
	 * @param name The entity of the vehicle
	 * @return The vehicle with the given entity if exists, null othewrise
	 */
	public static EnumRegionVehicle getVehicle(EntityType entity) {
		if(entity.equals(EntityTypes.CHESTED_MINECART) || entity.equals(EntityTypes.COMMANDBLOCK_MINECART)
				|| entity.equals(EntityTypes.FURNACE_MINECART) || entity.equals(EntityTypes.HOPPER_MINECART) 
				|| entity.equals(EntityTypes.MOB_SPAWNER_MINECART) || entity.equals(EntityTypes.RIDEABLE_MINECART)
				|| entity.equals(EntityTypes.TNT_MINECART))
			return EnumRegionVehicle.MINECART;
		else if(entity.equals(EntityTypes.BOAT))
			return EnumRegionVehicle.BOAT;
		else
			return null;
	}
	
	/**
	 * Get a interact from entity
	 * @param name The entity of the interact
	 * @return The interact with the given entity if exists, null othewrise
	 */
	public static EnumRegionInteract getInteract(EntityType entity) {
		if(entity.equals(EntityTypes.ARMOR_STAND))
			return getInteract("armorstand");
		else if(entity.equals(EntityTypes.ITEM_FRAME))
			return getInteract("itemframe");
		else
			return null;
	}
	
	/**
	 * Get a vehicle from name
	 * @param name The name of the vehicle
	 * @return The vehicle with the given name if exists, null othewrise
	 */
	public static EnumRegionVehicle getVehicle(String name) {
		for(EnumRegionVehicle vehicle : EnumRegionVehicle.values()) {
			if(vehicle.getName().equalsIgnoreCase(name))
				return vehicle;
		}
		return null;
	}
	
	/**
	 * Get an explosion from name
	 * @param name The name of the explosion
	 * @return The explosion with the given name if exists, null othewrise
	 */
	public static EnumRegionExplosion getExplosion(String name) {
		for(EnumRegionExplosion explosion : EnumRegionExplosion.values()) {
			if(explosion.getName().equalsIgnoreCase(name))
				return explosion;
		}
		return null;
	}
	
	/**
	 * Get an explosion from entity
	 * @param name The name of the explosion
	 * @return The explosion with the given name if exists, null othewrise
	 */
	public static EnumRegionExplosion getExplosion(EntityType type) {
		if(type.equals(EntityTypes.TNT_MINECART) || type.equals(EntityTypes.PRIMED_TNT))
			return getExplosion("tnt");
		else if(type.equals(EntityTypes.CREEPER))
			return getExplosion("creeper");
		else if(type.equals(EntityTypes.ENDER_CRYSTAL))
			return getExplosion("endercrystal");
		else if(type.equals(EntityTypes.FIREBALL))
			return getExplosion("fireball");
		else if(type.equals(EntityTypes.ENDER_DRAGON))
			return getExplosion("enderdragon");
		else
			return null;
	}
	
	/**
	 * Check if an EntityType is a Block Entity
	 * @param type The EntityType
	 * @return true if the EntityType is a Block Entity, false otherwise
	 */
	public static boolean isBlockEntity(EntityType type) {
		return type.equals(EntityTypes.ENDER_CRYSTAL) || 
				type.equals(EntityTypes.PAINTING) || 
				type.equals(EntityTypes.ITEM_FRAME) || 
				type.equals(EntityTypes.ARMOR_STAND);
	}
	
	/**
	 * Check if an EntityType is an Interact
	 * @param type The EntityType
	 * @return true if the EntityType is an Interact, false otherwise
	 */
	public static boolean isInteract(BlockType type) {
		return type.equals(BlockTypes.CRAFTING_TABLE) || type.equals(BlockTypes.ENCHANTING_TABLE) ||
				type.equals(BlockTypes.ANVIL);
	}
	
	/**
	 * Check if an EntityType is a Vehicle
	 * @param type The EntityType
	 * @return true if the EntityType is a Vehicle, false otherwise
	 */
	public static boolean isVehicle(EntityType type) {
		return type.equals(EntityTypes.CHESTED_MINECART) || type.equals(EntityTypes.COMMANDBLOCK_MINECART)
				|| type.equals(EntityTypes.FURNACE_MINECART) || type.equals(EntityTypes.HOPPER_MINECART) 
				|| type.equals(EntityTypes.MOB_SPAWNER_MINECART) || type.equals(EntityTypes.RIDEABLE_MINECART)
				|| type.equals(EntityTypes.TNT_MINECART) || type.equals(EntityTypes.BOAT);
	}
	
	/**
	 * Check if an EntityType is an Explosion
	 * @param type The EntityType
	 * @return true if the EntityType is an Explosion, false otherwise
	 */
	public static boolean isExplosion(EntityType type) {
		return type.equals(EntityTypes.TNT_MINECART) || type.equals(EntityTypes.PRIMED_TNT)
				|| type.equals(EntityTypes.CREEPER) || type.equals(EntityTypes.ENDER_CRYSTAL) ||
				type.equals(EntityTypes.FIREBALL) || type.equals(EntityTypes.ENDER_DRAGON);
	}
	
	/**
	 * Check if a BlockType is a Crop
	 * @param type The BlockType
	 * @return true if the BlockType is a Crop, false otherwise
	 */
	public static boolean isCrop(BlockType type) {
		return type.equals(BlockTypes.WHEAT) || type.equals(BlockTypes.BEETROOTS) 
				|| type.equals(BlockTypes.MELON_STEM) || type.equals(BlockTypes.PUMPKIN_STEM)
				|| type.equals(BlockTypes.CARROTS) || type.equals(BlockTypes.NETHER_WART) 
				|| type.equals(BlockTypes.FARMLAND);
	}
	
	/**
	 * Get the mob id from name
	 * @param name The name of the mob
	 * @return The mob id for a mob with that name if exists, null otherwise
	 */
	public static String getMobId(String name) {
		for(String id : Sponge.getRegistry().getAllOf(EntityType.class).stream()
	    .map(CatalogType::getId)
	    .filter(id -> id.contains(name.toLowerCase()))
	    .collect(Collectors.toList())) {
			if(name.contains(":")) {
				if(id.equalsIgnoreCase(name))
					return id;
			}
			else
				if(id.substring(id.indexOf(":") + 1).equalsIgnoreCase(name))
					return id;
		}
		return null;
	}
	
	/**
	 * Get the mob id from name
	 * @param name The name of the mob
	 * @return The mob id for a mob with that name if exists, null otherwise
	 */
	public static List<String> getAllMobIds() {
		return Sponge.getRegistry().getAllOf(EntityType.class).stream()
			    .map(CatalogType::getId)
			    .collect(Collectors.toList());
	}
}
