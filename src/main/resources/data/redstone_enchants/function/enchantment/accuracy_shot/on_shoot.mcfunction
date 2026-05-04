# 使执行该命令的实体不受重力影响
data modify entity @s NoGravity set value 1b
# 给执行该命令的实体添加一个名为 redstone_enchants.accuracy_shot 的标签
tag @s add redstone_enchants.accuracy_shot
# 将当前游戏时间存储到实体 @s 的 redstone_enchants.gametime 记分板中
execute store result score @s redstone_enchants.gametime run time query gametime
# 在 5 秒后调用函数
schedule function redstone_enchants:enchantment/accuracy_shot/schedule_handler 5s append