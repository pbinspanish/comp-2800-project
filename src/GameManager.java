import java.awt.*;
import java.util.LinkedList;

public class GameManager {
	
	public LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

	public void tick() {
		// update ALL game objects
        for (GameObject gameObject : gameObjects) gameObject.tick();
	}
	
	public void render(Graphics2D g2d) {
		// render ALL game objects
        for (GameObject gameObject : gameObjects) gameObject.render(g2d);
	}

	public void addGameObject(GameObject gameObject) {
        // add a game object TO list
		gameObjects.add(gameObject);
	}

	public void removeGameObject(GameObject gameObject) {
        // remove a game object FROM list
		gameObjects.remove(gameObject);
	}
}
