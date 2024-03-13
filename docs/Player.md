# Player
- capture input W/D/Space
- store player x, modify based on some movement speed + current input direction
- jump should accelerate the character up and handle falling down with gravity
	- gravity needs to be simulated on the character
- collision detection (important)
	- get a list of all the blocks around the player on the block grid
	- get the edges on the pixel grid and check if the player intersects
	- also determine what edge it is intersecting and the normal of the edge to properly place the player