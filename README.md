# COMP-2800 Project
> Group 8

## Assets Used
- [ ] Links to images
	- [ ] Icons (armour, weapons, extras)
	https://merchant-shade.itch.io/16x16-mixed-rpg-icons
  	- [ ] Inventory Bar, crafting
  	 https://kasayaa.itch.io/kasayas-inventory-and-frames
	- [ ] Character (with all movements)
	(image available on the shared word doc)
	- [ ] Blocks
	https://devilsworkshop.itch.io/big-pixel-isometric-block-pack-free-2d-sprites
	- [ ] Main background 
	https://digitalmoons.itch.io/pixel-skies-demo
	- [ ] Cave background
	FILL IN

## Features
- [ ] Camera
	- [ ] Scrolls in the x and y direction
	- [ ] Follows the player
- [ ] World
	- [x] Made of blocks (tiles) on a fixed grid in the foreground
		- [ ] Blocks can be broken and placed
			- [ ] Blocks drop the item version of themselves when broken
		- [x] Blocks are not affected by gravity
			- [ ] Exceptions?
	- [x] Separate background layer for visual separation
		- [ ] Different background based on world height
		- [ ] Different background based on biome
	- [ ] Finite size
	- [ ] Generation
		- [ ] Biomes varied across x-axis
		- [ ] Caves generate underground
		- [ ] Hills/plains/plateaus above ground
- [ ] Player
	- [x] Moves left / right
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
