execute unless entity @s[nbt=!{SelectedItem:{}}] positioned ~ ~1 ~ if block ^ ^ ^2 #redstone_enchants:air run summon item ^ ^ ^2 {Item:{id:"minecraft:acacia_button"},PickupDelay:60,Tags:["redstone_enchants.drop"]}
execute unless entity @s[nbt=!{SelectedItem:{}}] positioned ~ ~1 ~ unless block ^ ^ ^2 #redstone_enchants:air run summon item ~ ~ ~ {Item:{id:"minecraft:oak_button"},PickupDelay:60,Tags:["redstone_enchants.drop"]}
data modify entity @e[type=item,sort=nearest,limit=1,tag=redstone_enchants.drop] Item set from entity @s SelectedItem
execute as @e[type=item,sort=nearest,limit=1,tag=redstone_enchants.drop] run tag @s remove redstone_enchants.drop
item replace entity @s weapon.mainhand with air

playsound minecraft:entity.item.pickup master @s ~ ~ ~