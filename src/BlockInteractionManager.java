public class BlockInteractionManager extends GameObject {
    private ChunkManager chunkManager;

    private Player player;

    public BlockInteractionManager(ChunkManager chunkManager, Player player) {
        this.chunkManager = chunkManager;
        this.player = player;
    }

    @Override
    public void tick(InputManager im) {
        super.tick(im);
        handleBlockInteractions(im);
    }

    public void handleBlockInteractions(InputManager inputManager) {
        if (inputManager.leftClicked || inputManager.rightClicked) {
            // Calculate the player's block coordinates relative to the camera
            int playerBlockX = (player.x + player.width / 2) / Block.BLOCK_SIZE;
            int playerBlockY = (player.y + player.height / 2) / Block.BLOCK_SIZE;

            // Calculate the block coordinates relative to the camera
            int blockX = (inputManager.mouseX + chunkManager.camera.x - chunkManager.camera.width / 2) / Block.BLOCK_SIZE;
            int blockY = (inputManager.mouseY + chunkManager.camera.y - chunkManager.camera.height / 2) / Block.BLOCK_SIZE;

            // Check if the distance between the mouse position and the player's position is within 1 block
            int distanceX = Math.abs(blockX - playerBlockX);
            int distanceY = Math.abs(blockY - playerBlockY);

            if (distanceX <= 1 && distanceY <= 1) {
                if (inputManager.leftClicked) {
                    // Break the block at the calculated coordinates
                    chunkManager.loadedChunks.values().forEach(chunk -> chunk.breakBlock(blockX, blockY));
                }
                if (inputManager.rightClicked) {
                    // Place a block at the calculated coordinates
                    chunkManager.loadedChunks.values().forEach(chunk -> chunk.placeBlock(blockX, blockY, "STONE")); // Change "STONE" to the desired block type
                }
            }

            inputManager.leftClicked = false; // Reset the flag
            inputManager.rightClicked = false; // Reset the flag
        }
    }
}
