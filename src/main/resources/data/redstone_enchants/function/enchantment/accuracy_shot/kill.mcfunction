# 将当前游戏时间存储到虚拟玩家 #global 的 redstone_enchants.gametime 记分板中
execute store result score #global redstone_enchants.gametime run time query gametime
# 将实体 @s 的 redstone_enchants.gametime 值复制到 redstone_enchants.gametime.temp 中
scoreboard players operation @s redstone_enchants.gametime.temp = @s redstone_enchants.gametime
# 将虚拟玩家 #100 的 redstone_enchants.data 值加到实体 @s 的 redstone_enchants.gametime.temp 中
scoreboard players operation @s redstone_enchants.gametime.temp += #100 redstone_enchants.data
# 从实体 @s 的 redstone_enchants.gametime.temp 中减去虚拟玩家 #global 的 redstone_enchants.gametime 值
scoreboard players operation @s redstone_enchants.gametime.temp -= #global redstone_enchants.gametime
# 如果实体 @s 的 redstone_enchants.gametime.temp 值大于或等于 1，则返回失败状态
execute if entity @s[scores={redstone_enchants.gametime.temp=1..}] run return fail
# 杀死实体
kill @s