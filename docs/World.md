# World
- store the blocks as a 2d array of integers
	- maybe two arrays? need some good way to store left and right
	- could just offset the center of the world from some spot wayyyy to the left but this may be more practical
- each integer corresponds to a block type
- the position in the array corresponds to a position on the grid
	- this is different from the pixel grid for rendering, blocks should be some multiple (16x, 32x) that will be determined based on testing
## Generation
- some function to change block type placement by height