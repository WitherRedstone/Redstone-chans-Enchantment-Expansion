scoreboard objectives add entityPos dummy

playsound minecraft:entity.snowball.throw player @a ~ ~ ~ 1 1

tag @s add this
summon marker ^ ^ ^1 {Tags:["this"]}
tag @e[limit=1,sort=nearest,tag=!this,type=!#minecraft:non_living_entity,distance=..16] add direction

execute store result score #ricochetX entityPos run data get entity @e[type=marker,tag=this,limit=1] Pos[0] 1000
execute store result score #ricochetY entityPos run data get entity @e[type=marker,tag=this,limit=1] Pos[1] 1000
execute store result score #ricochetZ entityPos run data get entity @e[type=marker,tag=this,limit=1] Pos[2] 1000
execute store result score #targetX entityPos as @e[tag=direction,limit=1] run data get entity @s Pos[0] 1000
execute store result score #targetY entityPos as @e[tag=direction,limit=1] run data get entity @s Pos[1] 1000
execute store result score #targetZ entityPos as @e[tag=direction,limit=1] run data get entity @s Pos[2] 1000
scoreboard players operation #targetX entityPos -= #ricochetX entityPos
scoreboard players operation #targetY entityPos -= #ricochetY entityPos
scoreboard players operation #targetZ entityPos -= #ricochetZ entityPos

summon snowball ~ ~1 ~ {pickup:2b,player:1b,life:600,damage:1.0d,crit:1b,Tags:["projectile"]}
execute store result entity @e[type=snowball,tag=projectile,limit=1] Motion[0] double 0.0005 run scoreboard players get #targetX entityPos
execute store result entity @e[type=snowball,tag=projectile,limit=1] Motion[1] double 0.0005 run scoreboard players get #targetY entityPos
execute store result entity @e[type=snowball,tag=projectile,limit=1] Motion[2] double 0.0005 run scoreboard players get #targetZ entityPos

kill @e[tag=this,type=marker]
tag @e[tag=projectile] remove projectile
tag @e[tag=direction] remove direction
tag @s remove this