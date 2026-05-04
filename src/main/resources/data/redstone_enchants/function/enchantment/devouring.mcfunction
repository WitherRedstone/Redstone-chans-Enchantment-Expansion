effect give @s saturation 1 0 true
  execute \
  if data entity @s { foodLevel: 20 } \
  run effect give @s regeneration 1 0 true
  return 0
