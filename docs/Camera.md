# Camera
- should have some bounds based on the pixel grid, not the block grid
- the coordinate visible in the middle of the window is the camera's position
- the camera size is the size of the window
- orthographic perspective

- we need to store camera width height (tied to window size)
- current world coordinate
- world grid bounds of the camera
	- we use this to get a section of the blocks in an area we need to render