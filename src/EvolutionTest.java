import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EvolutionTest extends GameEngine {
	ArrayList<Population> community = new ArrayList<Population>();
	
	int popNum = 5;
	int numPerPop = 1;
	
	int screenWidth;
	int screenHeight;
	int tick = 1;
	
	
	public static void main(String[] args) {

		EvolutionTest g = new EvolutionTest();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = (int) screenSize.getWidth();
		windowHeight = (int) screenSize.getHeight();
		g.screenWidth = windowWidth;
		g.screenHeight = windowHeight;
		g.setExtendedState(MAXIMIZED_BOTH);
		g.setUndecorated(true);
		g.setVisible(true);
		g.init();
		g.run();
		System.exit(0);
	}

	void init() {
		
		
		for (int i = 0; i < popNum; i++) {
			community.add(new Population(windowWidth, windowHeight, numPerPop));
			community.get(i).setRate(0.01);
			community.get(i).setColor(Creature.randomColor());
		}
	}

	void update() {
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			isRunning = false;
		}
		
		//if a population is empty, remove it from community
		for (int i = 0; i < community.size(); i++) {
			if (community.get(i).getCreatures().size() <= 0) {
				community.remove(i);
			}
		}
		
		
		for (int i = 0; i < community.size(); i++) {
			for (int j = 0; j < community.size(); j++) {
				if (i != j) {
					community.get(i).eatSmaller(community.get(j));
				}
			}
		}
		
		for (int i = 0; i < community.size(); i++) {
			community.get(i).update();
		}
		
		//every 2 seconds, 30 ticks = 1 second, split one random creature from every population
		if (tick % 60 == 0) {
			for (int i = 0; i < community.size(); i++) {
				community.get(i).splitCreatures(screenWidth, screenHeight, 1);
			}
			
		}
		
		
		
		tick++;
	}

	void draw(Graphics g) {
		g = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenWidth, screenHeight);
		
		for (int i = 0; i < community.size(); i++) {
			community.get(i).draw(g);
		}

	}

}