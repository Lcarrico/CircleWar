import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Population {
	public static double RATE = 0.5;

	private int windowWidth;
	private int windowHeight;
	private ArrayList<Creature> creatures = new ArrayList<Creature>();
	private int minSize = 5;
	private int maxSize = 9999;
	private Color color = null;

	public Population(int windowWidth, int windowHeight, int MIN_SIZE, int MAX_SIZE, int MIN_SPEED, int MAX_SPEED,
			int numOfCreatures) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
		this.minSize = numOfCreatures;

		for (int i = 0; i < numOfCreatures; i++) {
			creatures.add(new Creature(windowWidth, windowHeight, MIN_SIZE, MAX_SIZE, MIN_SPEED, MAX_SPEED));
		}
	}

	public Population(int windowWidth, int windowHeight, int numOfCreatures) {
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

		this.minSize = numOfCreatures;
		for (int i = 0; i < numOfCreatures; i++) {
			creatures.add(new Creature(windowWidth, windowHeight));
		}
	}

	public ArrayList<Creature> getCreatures() {
		return creatures;
	}

	public void update() {
		if (color != null) {
			for (Creature c : creatures) {
				c.setColor(color);
			}
		}

		for (Creature c : creatures)
			c.update(windowWidth, windowHeight);

		//if creature's health is < 0, remove creature
		for (int i = 0; i < creatures.size(); i++) {
			if (creatures.get(i).getCurHealth() <= 0) {
				// Creature c = randomLivingCreature().breed(randomLivingCreature(),
				// windowWidth, windowHeight);
				// c.mutate(RATE, windowWidth, windowHeight);
				// creatures.set(i, c);
				creatures.remove(i);
			}
		}

		// if (creatures.size() < this.maxSize && creatures.size() > this.minSize) {
		// creatures.add(new Creature(windowWidth, windowHeight));
		// }
	}

	public void draw(Graphics g) {
		for (Creature c : creatures)
			c.draw(g);

	}

	public void setMinSize(int size) {
		this.minSize = size;
	}

	public void setMaxSize(int size) {
		this.maxSize = size;
	}

	public void setRate(double rate) {
		RATE = rate;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void addCreature(Creature c) {
		creatures.add(c);
	}

	public Creature fitRandomCreature() {
		int sum = 0;
		for (Creature c : creatures) {
			sum += c.getTime();
		}
		int cre[] = new int[sum];
		int i = 0;
		for (int k = 0; k < creatures.size(); k++) {
			for (int j = 0; j < creatures.get(k).getTime(); j++) {
				// System.out.println((k+j) + " " + sum);
				cre[i] = k;
				i++;
			}
		}

		int rand = (int) (Math.random() * cre.length);
		// System.out.println(cre.length + " " + rand);
		return creatures.get(cre[rand]);
	}

	public Creature randomLivingCreature() {
		if (creatures.size() <= 0)
			return null;

		int rand = (int) (Math.random() * creatures.size());
		Creature c = creatures.get(rand);

		while (c.getCurHealth() <= 0) {
			rand = (int) (Math.random() * creatures.size());
			c = creatures.get(rand);
		}
		return c;
	}

	public void killCreatureAt(int i) {
		creatures.get(i).setCurHealth(0); // 0 health is the same as dead
	}

	public void eat(Population p) {
		ArrayList<Creature> c = p.getCreatures();
		for (int i = 0; i < creatures.size(); i++) {
			for (int j = 0; j < c.size(); j++) {
				if (creatures.get(i).collidesWithEllipse(c.get(j).getEllipse())) {
					creatures.get(i).increaseHealthBy(c.get(j).getCurHealth());
					c.get(j).setCurHealth(0);
				}
			}
		}
	}

	public void eatSmaller(Population p) {
		ArrayList<Creature> c = p.getCreatures();
		for (int i = 0; i < creatures.size(); i++) {
			for (int j = 0; j < c.size(); j++) {
				if (creatures.get(i).collidesWithEllipse(c.get(j).getEllipse()) // if they collide and if one is smaller
																				// than the other
						&& creatures.get(i).getSize() > c.get(j).getSize()) {

					creatures.get(i).increaseHealthBy(c.get(j).getCurHealth());
					c.get(j).setCurHealth(0);

				}
			}
		}
	}

	public int getMinSize() {
		return this.minSize;
	}

	public int getMaxSize() {
		return this.maxSize;
	}

	public void splitCreatures(int windowWidth, int windowHeight, int num) {
		if (creatures.size() > 0)
			for (int i = 0; i < num; i++) {
				Creature c = randomLivingCreature().split(windowWidth, windowHeight);
				c.mutate(RATE, windowWidth, windowHeight);
				
				creatures.add(c);
			}
	}
}
