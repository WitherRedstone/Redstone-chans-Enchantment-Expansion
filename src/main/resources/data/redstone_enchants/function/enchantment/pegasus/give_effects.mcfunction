$execute as @s[y=$(init_height),dy=30] at @s run effect give @n[type=#redstone_enchants:rideable] minecraft:levitation 1 1 true
$execute as @s[y=$(init_height),dy=30] at @s run effect give @n[type=#redstone_enchants:rideable] minecraft:speed 3 9 true

effect give @n[type=#redstone_enchants:rideable] minecraft:slow_falling 10 0 true
effect give @s minecraft:slow_falling 10 0 true
