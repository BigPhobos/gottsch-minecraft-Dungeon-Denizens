# Changelog for Dungeon Denizens 1.20.1

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.2.0] - 2024-01-09 - Beholderkin Update

### Changed

- Fixed Gazer LAYER_LOCATION
- Fixed CommonSpawnConfig constructor. There was a mix-up with the weight and minSpawn, maxSpawn values.
- Fixed some config label naming.
- Refactored spawn rules classes for nether mobs.
- Renamed spell classes and resources.
- Updated Daemon's jaw and horns.
- Updated Daemon's animation to include mouth bob.
- Reduced Daemon's speed, knockback.
- Daemon and Shadowlord will now despawn;
- Nether spawnable mobs only spawn in the nether wastes
- Reduced spawn weights for all nether spawning mobs

### Added

- Beholder mob (beholderkin).
- Death Tyrant mob (beholderkin).
- Spectator mob (beholderkin).
- Disintegrate spell.
- Disarm spell.
- Daemon glowing eyes layer.
- Owner and lifespan properties to Daemon if summoned.
- Shadow glowing eyes layer.
- Enable mob config option.
- Supports Patchouli - DD Bestiary book contains Beholder, Death Tyrant, Gazer and Spectator. 
- Tooltips on mob eggs (see info as DD Bestiary book).

## [1.1.0] - 2023-10-18

### Changed

- Changed spawn rules to require the correct darkness for mobs to spawn (like vanilla Minecraft)


## [1.0.0] - 2023-10-17

### Changed

### Added 

- Port from 1.19.3-1.0.0