public class BlockInteractionManager extends GameObject {
    private ChunkManager chunkManager;

    private Player player;
    private Inventory inventory;

    public BlockInteractionManager(ChunkManager chunkManager, Player player, Inventory inventory) {
        this.chunkManager = chunkManager;
        this.player = player;
        this.inventory = inventory;
    }

    @Override
    public void tick(InputManager im) {
        super.tick(im);
        handleBlockInteractions(im);
    }

    public void handleBlockInteractions(InputManager inputManager) {
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
                Chunk chunk = chunkManager.loadedChunks.get(Chunk.getID(blockX / Chunk.CHUNK_WIDTH_WORLD, blockY / Chunk.CHUNK_HEIGHT_WORLD));
                if (chunk != null) {
                    Block block = chunk.blocks[blockX % Chunk.CHUNK_WIDTH_WORLD][blockY % Chunk.CHUNK_HEIGHT_WORLD];
                    chunk.breakBlock(blockX % Chunk.CHUNK_WIDTH_WORLD, blockY % Chunk.CHUNK_HEIGHT_WORLD);
                    if (!block.getType().equals("AIR")) {
                        inventory.addItem(new Item(block.getType(), 1)); // Add the broken block to the inventory
                    }

                }
            }
            if (inputManager.rightClicked) {
                // Place a block at the calculated coordinates
                Chunk chunk = chunkManager.loadedChunks.get(Chunk.getID(blockX / Chunk.CHUNK_WIDTH_WORLD, blockY / Chunk.CHUNK_HEIGHT_WORLD));
                if (chunk != null) {
                    chunk.placeBlock(blockX % Chunk.CHUNK_WIDTH_WORLD, blockY % Chunk.CHUNK_HEIGHT_WORLD, "STONE"); // Change "STONE" to the desired block type
                }
                // Reset the rightClicked flag after processing
                inputManager.rightClicked = false;
            }

        }
    }

}
