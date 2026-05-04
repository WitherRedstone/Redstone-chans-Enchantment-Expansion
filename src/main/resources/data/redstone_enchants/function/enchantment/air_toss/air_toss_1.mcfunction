# 选择距离命令执行位置最近的实体，并将其运动方向设置为向上（Y轴正方向），速度为每秒1个方块
data merge entity @e[limit=1,sort=nearest] {Motion:[0.0,1.0,0.0]}