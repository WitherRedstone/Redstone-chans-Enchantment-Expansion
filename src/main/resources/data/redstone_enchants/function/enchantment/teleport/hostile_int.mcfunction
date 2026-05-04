execute as @s[type=!#redstone_enchants:non_living] run data modify storage redstone_enchants:hostile_loc posx set from entity @s Pos[0]
execute as @s[type=!#redstone_enchants:non_living] run data modify storage redstone_enchants:hostile_loc posy set from entity @s Pos[1]
execute as @s[type=!#redstone_enchants:non_living] run data modify storage redstone_enchants:hostile_loc posz set from entity @s Pos[2]
