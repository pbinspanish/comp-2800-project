import java.awt.Color;
import java.awt.Graphics2D;

public class BlockInteractionManager extends GameObject {
    private ChunkManager cm;

    private Player player;
    private Camera camera;
    private Inventory inventory;

    private Block hoveredBlock;
    private boolean canReach = false;
    private boolean canBreak = false;
    private boolean canPlace = false;
    private int reach = 3;

    public BlockInteractionManager(ChunkManager cm, Player player, Inventory inventory, Camera camera,
            int renderPriority) {
        this.cm = cm;
        this.player = player;
        this.camera = camera;
        this.inventory = inventory;

        this.hoveredBlock = null;
        this.renderPriority = renderPriority;
    }

    @Override
    public void tick(InputManager im) {
        hoveredBlock = mouseToBlock(im.mouseX, im.mouseY);

        
        if (hoveredBlock != null) {
            handleBlockInteractions(im);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (hoveredBlock != null) {
            drawCollidedBlocks(g2d, hoveredBlock);
        }
    }

    public Block mouseToBlock(int mouseX, int mouseY) {
        // Determine where the mouse clicked in the world
        int mouseWorldX = camera.screenXToWorldX(mouseX);
        int mouseWorldY = camera.screenYToWorldY(mouseY);

        // Determine in which chunk the mouse is
        Chunk[] blockChunks = cm.locatedIn(mouseWorldX, mouseWorldY, 0, 0);
        Chunk blockChunk = null;

        for (Chunk chunk : blockChunks) {
            if (chunk != null) {
                blockChunk = chunk;
            }
        }

        // Determine which block in the chunk the mouse is hovered over
        GameObject mouse = new GameObject(mouseWorldX, mouseWorldY, 0, 0);
        assert blockChunk != null;
        for (Block[] blocks : blockChunk.blocks) {
            for (Block block : blocks) {
                boolean[] collisions = mouse
                        .isCollidingWith(new GameObject(block.getBlockWorldX(), block.getBlockWorldY(),
                                Block.BLOCK_SIZE, Block.BLOCK_SIZE));

                boolean collided = false;
                for (boolean collision : collisions) {
                    if (collision) {
                        collided = true;
                    }
                }

                if (collided) {
                    return block;
                }
            }
        }

        return null;
    }

    public void handleBlockInteractions(InputManager im) {
        // determine whether the hovered block is reachable
        int diffX = Math.abs(hoveredBlock.getBlockWorldX() - (player.x + (player.width / 2)));
        int diffY = Math.abs(hoveredBlock.getBlockWorldY() - (player.y + (player.height / 2)));
        if (diffX <= Block.BLOCK_SIZE * reach && diffY <= Block.BLOCK_SIZE * reach) {
            canReach = true;

            if (hoveredBlock.getType().compareTo("AIR") == 0) {
                canPlace = true;
                canBreak = false;
            }
            else {
                canPlace = false;
                canBreak = true;
            }
        } else {
            canReach = false;
            canBreak = false;
            canPlace = false;
        }


        if (canReach) {
            // Break the block
            if (im.leftClicked && canBreak) {
                hoveredBlock.parent.breakBlock(hoveredBlock.x, hoveredBlock.y);

                // add the item to the inventory
                if (hoveredBlock.getType().compareTo("GRASS_PLANT") != 0
                        && hoveredBlock.getType().compareTo("FLOWER_PURPLE_PLANT") != 0
                        && hoveredBlock.getType().compareTo("FLOWER_RED_PLANT") != 0) {
                    Item newItem = new Item(hoveredBlock.getType(), 1);
                    inventory.addItem(newItem);
                }
            }
            // Place the block
            else if (im.rightClicked && canPlace) {
                Item selectedItem = inventory.getSelectedItem();
                if (selectedItem != null) {
                    hoveredBlock.parent.placeBlock(hoveredBlock.x, hoveredBlock.y, selectedItem.getItemName());

                    // remove item from the inventory
                    inventory.removeItem(selectedItem.getSlot(), selectedItem);
                }
            }
        }
    }

    public void drawCollidedBlocks(Graphics2D g2d, Block block) {
        if (canReach) {
            g2d.setColor(Color.BLUE);
        } else {
            g2d.setColor(Color.RED);
        }

        int blockX = camera.worldXToScreenX(block.getBlockWorldX());
        int blockY = camera.worldYToScreenY(block.getBlockWorldY());

        g2d.drawRect(blockX, blockY, 1, Block.BLOCK_SIZE);
        g2d.drawRect(blockX + Block.BLOCK_SIZE, blockY, 1, Block.BLOCK_SIZE);

        g2d.drawRect(blockX, blockY, Block.BLOCK_SIZE, 1);
        g2d.drawRect(blockX, blockY + Block.BLOCK_SIZE, Block.BLOCK_SIZE, 1);
    }
}
