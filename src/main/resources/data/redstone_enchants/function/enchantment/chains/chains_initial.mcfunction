particle item_cobweb{item:"chain"} ~ ~1 ~ 0.2 0.5 0.2 0 8 normal
playsound minecraft:block.chain.place hostile @a ~ ~ ~ 1 1
effect give @s minecraft:slowness 6 9 true
effect give @s minecraft:weakness 6 9 true

execute as @e[type=!#minecraft:non_living_entity,type=!player,sort=nearest,distance=..8,limit=3] at @s run function redstone_enchants:enchantment/chains/chains_final