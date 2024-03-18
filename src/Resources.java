//this can be changed fully!! Just how i implemented it in my assignment 2 :)
public class Resources { 
    // static variables to hold different resources
    public static SpriteSheet spriteSheet;

    // load resources in a static initializer block
    static {
        try {
            spriteSheet = new SpriteSheet("char_blue.png", 8, 8, 64, 64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
