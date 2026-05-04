scoreboard objectives add entityPos dummy

playsound minecraft:entity.arrow.shoot player @a ~ ~ ~ 1 1

tag @s add this
execute as @e[type=!#non_living_entity,type=!player,distance=2..12,limit=1,sort=nearest] at @s anchored eyes run summon marker ~ ~1 ~ {Tags:["direction"]}

execute store result score #ricochetX entityPos run data get entity @s Pos[0] 1000
execute store result score #ricochetY entityPos run data get entity @s Pos[1] 1000
execute store result score #ricochetZ entityPos run data get entity @s Pos[2] 1000
execute store result score #targetX entityPos as @e[tag=direction,limit=1] run data get entity @s Pos[0] 1000
execute store result score #targetY entityPos as @e[tag=direction,limit=1] run data get entity @s Pos[1] 1000
execute store result score #targetZ entityPos as @e[tag=direction,limit=1] run data get entity @s Pos[2] 1000
scoreboard players operation #targetX entityPos -= #ricochetX entityPos
scoreboard players operation #targetY entityPos -= #ricochetY entityPos
scoreboard players operation #targetZ entityPos -= #ricochetZ entityPos

execute store result entity @e[type=#arrows,tag=this,limit=1] Motion[0] double 0.00025 run scoreboard players get #targetX entityPos
execute store result entity @e[type=#arrows,tag=this,limit=1] Motion[1] double 0.00025 run scoreboard players get #targetY entityPos
execute store result entity @e[type=#arrows,tag=this,limit=1] Motion[2] double 0.00025 run scoreboard players get #targetZ entityPos

kill @e[tag=direction,type=marker]
tag @s remove this