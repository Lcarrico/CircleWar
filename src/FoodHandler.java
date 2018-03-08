import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class FoodHandler {
	private ArrayList<Rectangle2D.Double> foods = new ArrayList<Rectangle2D.Double>();
	private int maxFoodSize = 20;
	private int windowWidth;
	private int windowHeight;
	
	public FoodHandler(int numOfFood, int maxFoodSize, int windowWidth, int windowHeight) {
		this.maxFoodSize = maxFoodSize;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		
		
		for (int i = 0; i < numOfFood; i++) {
			Rectangle2D.Double f = createRandomFood();
			foods.add(f);
			
		}
		
	}
	
	public Rectangle2D.Double createRandomFood(){
		int x = (int) (1 + (Math.random() * (windowWidth - maxFoodSize - 1)));
		int y = (int) (1 + (Math.random() * (windowHeight - maxFoodSize - 1)));
		
		
		return new Rectangle2D.Double(0, 0, 0, 0);
	}
		
}
