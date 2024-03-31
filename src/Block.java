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
    public Block(String type) {
        this.type = type;
    }
    static String getRandomBlock(int y) {
        double randomNumber = random.nextDouble(); // Generates a random number between 0 (inclusive) and 1 (exclusive)
        double stoneProb = 0.94;   // Probability of stone blocks (94%)
        double ironProb = 0.03;    // Probability of iron ore (3%)
        double goldProb = 0.02;    // Probability of gold ore (2%)
        double diamondProb = 0.01; // Probability of diamond ore (1%)
        double airProb = 0.90;
        double grassProb = 0.05;
        double flowerProb;
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
                return "FLOWER_RED_PLANT";
            }

        }
        return "AIR";

    }



    public String getType() {
        return type;
    }






}