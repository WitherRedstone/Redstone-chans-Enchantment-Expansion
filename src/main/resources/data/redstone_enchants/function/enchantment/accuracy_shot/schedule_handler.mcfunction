# 杀死所有带有 redstone_enchants.accuracy_shot 标签且附近没有玩家的箭矢
execute as @e[type=arrow,tag=redstone_enchants.accuracy_shot] at @s unless entity @a[distance=..5] run function redstone_enchants:enchantment/accuracy_shot/kill

# 如果仍有存活的带有 redstone_enchants.accuracy_shot 标签的箭矢，则在 5 秒后再次运行调度函数，以实现周期性检查
execute if entity @e[type=arrow,tag=redstone_enchants.accuracy_shot] run schedule function redstone_enchants:enchantment/accuracy_shot/schedule_handler 5s