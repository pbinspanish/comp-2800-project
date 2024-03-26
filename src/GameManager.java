import java.awt.*;
import java.util.*;

///
/// Stores and manages all objects in the game.
///
public class GameManager {

    public ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    public void tick(InputManager im) {
        // tick() all GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.tick(im);
        }
    }

    public void render(Graphics2D g2d) {
        // render() all GameObjects
        for (GameObject gameObject : gameObjects) {
            gameObject.render(g2d);
        }
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        Collections.sort(gameObjects);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }
}
