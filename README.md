# COMP-2800 Project
> Group 8

## Assets Used

## Features
- [ ] Camera
	- [ ] Scrolls in the x and y direction
	- [ ] Follows the player
- [ ] World
	- [ ] Made of blocks (tiles) on a fixed grid in the foreground
		- [ ] Blocks can be broken and placed
			- [ ] Blocks drop the item version of themselves when broken
		- [ ] Blocks are not affected by gravity
			- [ ] Exceptions?
	- [ ] Separate background layer for visual separation
		- [ ] Different background based on world height
		- [ ] Different background based on biome
	- [ ] Finite size
	- [ ] Generation
		- [ ] Biomes varied across x-axis
		- [ ] Caves generate underground
		- [ ] Hills/plains/plateaus above ground
- [ ] Player
	- [ ] Moves left / right
	- [ ] Affected by gravity
	- [ ] Collides with blocks
	- [ ] Jumps one block high
	- [ ] Inventory
		- [ ] Holds item versions of destroyed blocks
		- [ ] Tools?
		- [ ] Hotbar
			- [ ] Shows easily accessible items
	- [ ] animation system to change sprites based on actions
- [ ] UI
	- [ ] Main Menu
		- [ ] Start
  			- [ ] Input Name
			- [ ] Select Skin
		- [ ] Exit
	- [ ] Inventory
		- [ ] Grid of item stacks
		- [ ] Can be moved between slots
		- [ ] Shows hotbar
	- [ ] Pause
		- [ ] Resume
		- [ ] Exit
	- [ ] In-game
		- [ ] Hotbar
		- [ ] Player status
