import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Random random = new Random(System.currentTimeMillis());

    public static final int BLOCK_SIZE = 32;
    private String type;
    private BufferedImage image;
    private static BufferedImage[] blockSprites;
    private static BufferedImage[] blockIconSprites;


    public Block(String type) {
        this.type = type;

    }
    static String getRandomBlock(int y) {
        double randomNumber = random.nextDouble(); // Generates a random number between 0 (inclusive) and 1 (exclusive)
        double stoneProb = 0.95;   // Probability of stone blocks (94%)
        double ironProb = 0.03;    // Probability of iron ore (3%)
        double goldProb = 0.01;    // Probability of gold ore (2%)
        double diamondProb = 0.01; // Probability of diamond ore (1%)
        double airProb = 0.80;
        double grassProb = 0.15;
        double flowerProb = 0.05;
        int level = Chunk.GROUND_LEVEL + 1;

        // Check the probability ranges and return the corresponding block type
        if(Chunk.GROUND_LEVEL < y) {
            if (randomNumber < stoneProb) {
                return "STONE";
            } else if (randomNumber < stoneProb + ironProb) {
                return "IRON_ORE";
            } else if (randomNumber < stoneProb + ironProb + goldProb) {
                return "GOLD_ORE";
            } else {
                return "DIAMOND_ORE";
            }

        } else if (y == Chunk.GROUND_LEVEL - 1) {
            if(randomNumber<airProb){
                return "AIR";
            }
            else if(randomNumber < airProb + grassProb){
                return "GRASS_PLANT";
            }else{
                if(randomNumber < airProb + grassProb + 0.02){
                    return "FLOWER_PURPLE_PLANT";
                }else{
                    return "FLOWER_RED_PLANT";

                }

            }

        }
        return "AIR";

    }

    public static void setBlockSprites(String filepath, int rows, int cols, int height, int width, int newHeight, int newWidth) {
        blockSprites = ImageLoader.gatherSprites( filepath,  rows,  cols,  height,  width,  newHeight,  newWidth);
    }

    public static BufferedImage getBlockSprite(String name) {
        switch (name) {
            case "DIRT":
                return blockSprites[21];
            case "STONE":
                return blockSprites[77];
            case "GRASS":
                return blockSprites[31];
            case "IRON_ORE":
                return blockSprites[79];
            case "GOLD_ORE":
                return blockSprites[76];
            case "DIAMOND_ORE":
                return blockSprites[78];
            case "GRASS_PLANT":
                return blockSprites[99];
            case "FLOWER_RED_PLANT":
                return blockSprites[119];
            case "FLOWER_PURPLE_PLANT":
                return blockSprites[113];
            case "AIR":
                return null;
            default:
                return null;
        }
    }

    public static void setBlockIconSprites(String filepath, int rows, int cols, int height, int width, int newHeight, int newWidth) {
        blockIconSprites = ImageLoader.gatherSprites( filepath,  rows,  cols,  height,  width,  newHeight,  newWidth);
    }
    public static BufferedImage getBlockIconSprites(String name){
        switch (name) {
            case "DIRT":
                return blockIconSprites[1];
            case "STONE":
                return blockIconSprites[10];
            case "GRASS":
                return blockIconSprites[2];
            case "IRON_ORE":
                return blockIconSprites[50];
            case "GOLD_ORE":
                return blockIconSprites[51];
            case "DIAMOND_ORE":
                return blockIconSprites[52];
            default:
                return null;
        }

    }



    public String getType() {
        return type;
    }






}