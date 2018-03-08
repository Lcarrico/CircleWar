import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Creature {
	public static int MIN_HEALTH = 500;
	public static int MAX_HEALTH = 600;
	
	public static int MIN_SPEED = -4; //standard at 1
	public static int MAX_SPEED = 4; 
	
	public static int MAX_SIZE = 50;
	public static int MIN_SIZE = 10;
	
	public static int MAX_HLR = 1;
	public static int MIN_HLR = 1;
	public static Color[] colors = { Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK,
			Color.RED, Color.YELLOW };

	private int x, y, totalHealth, curHealth, hLR, xSpeed, ySpeed, size, time = 0; // hLR is health loss rate or rate
																					// that health decreases by
	private Color color;

	public Creature(int windowWidth, int windowHeight) {
		this.x = (int) (1 + (Math.random() * (windowWidth - size - 1)));
		this.y = (int) (1 + (Math.random() * (windowHeight - size - 1)));

		this.totalHealth = MIN_HEALTH + (int) (Math.random() * (MAX_HEALTH - MIN_HEALTH));
		this.curHealth = totalHealth;

		this.hLR = MIN_HLR + (int) (Math.random() * (MAX_HLR - MIN_HLR));
		
		//randomSpeed;
		this.xSpeed = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));
		this.ySpeed = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));

		this.size = MIN_SIZE + (int) (Math.random() * (MAX_SIZE - MIN_SIZE));

		this.color = colors[(int) (Math.random() * colors.length)];
	}
	
	public Creature(int windowWidth, int windowHeight, int MIN_SIZE, int MAX_SIZE, int MIN_SPEED, int MAX_SPEED) {
		this.MIN_SIZE = MIN_SIZE;
		this.MAX_SIZE = MAX_SIZE;
		this.MIN_SPEED = MIN_SPEED;
		this.MAX_SPEED = MAX_SPEED;
		

		this.x = (int) (1 + (Math.random() * (windowWidth - size - 1)));
		this.y = (int) (1 + (Math.random() * (windowHeight - size - 1)));

		this.totalHealth = MIN_HEALTH + (int) (Math.random() * (MAX_HEALTH - MIN_HEALTH));
		this.curHealth = totalHealth;

		this.hLR = MIN_HLR + (int) (Math.random() * (MAX_HLR - MIN_HLR));
		this.xSpeed = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));
		this.ySpeed = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));

		this.size = MIN_SIZE + (int) (Math.random() * (MAX_SIZE - MIN_SIZE));

		this.color = randomColor();
	}
	
	


	public Creature(int windowWidth, int windowHeight, int totalHealth, int hLR, int xSpeed, int ySpeed, int size, Color color) {
		this.x = (int) (1 + (Math.random() * (windowWidth - size - 1)));
		this.y = (int) (1 + (Math.random() * (windowHeight - size - 1)));
		//System.out.println(windowWidth + " " + windowHeight);
		this.totalHealth = totalHealth;
		this.curHealth = totalHealth;
		this.hLR = hLR;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.size = size;
		this.color = color;
	}

	public static Color randomColor() {
		return colors[(int) (Math.random() * colors.length)];
	}
	public int getX() {
		return x;
	}

	public void randDirection(int windowWidth, int windowHeight) {
		this.x = (int) (1 + (Math.random() * (windowWidth - size - 1)));
		this.y = (int) (1 + (Math.random() * (windowHeight - size - 1)));
	}
	
	public int getY() {
		return y;
	}

	public int getTotalHealth() {
		return totalHealth;
	}

	public int getCurHealth() {
		return curHealth;
	}

	public int getHLR() {
		return hLR;
	}

	public int getXSpeed() {
		return xSpeed;
	}

	public int getYSpeed() {
		return ySpeed;
	}

	public int getSize() {
		return size;
	}

	public Color getColor() {
		return color;
	}

	public int getTime() {
		return time;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setCurHealth(int health) {
		curHealth = health;
	}
	
	public void increaseHealthBy(int health) {
		curHealth += health;
		if (curHealth > MAX_HEALTH)
			curHealth = MAX_HEALTH;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	
	}
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public boolean collidesWithEllipse(Ellipse2D.Double e) {
		Ellipse2D.Double body = getEllipse();
		
		double xDiff = e.getCenterX() - body.getCenterX();
		double yDiff = e.getCenterY() - body.getCenterY();
		double dis = Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)); 
		double minDis = Math.abs((e.width/2) + (body.getWidth()/2));
		
		if (dis < minDis) {
			return true;
		}
		return false;

	}
	
	public Ellipse2D.Double getEllipse(){
		return new Ellipse2D.Double(x, y, size, size);
	}

	public boolean collidesWithRectangle(Rectangle2D.Double rect) {
		Ellipse2D.Double body = new Ellipse2D.Double(x, y, size, size);

		if (body.intersects(rect)) {
			return true;
		} else
			return false;

	}

	public void update(int windowWidth, int windowHeight) {

		// turns around when hits wall
		if (x < 0 || x + size > windowWidth)
			xSpeed *= -1;
		else if (y < 0 || y + size > windowHeight)
			ySpeed *= -1;

		if (x < 0)
			x = 0;
		if (x + size > windowWidth)
			x = windowWidth - size;
		if (y < 0)
			y = 0;
		if (y + size > windowHeight)
			y = windowHeight - size;

		// moves
		x += xSpeed;
		y += ySpeed;

		curHealth -= hLR;
		time++;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.drawOval(x, y, size, size);
	}

	public Creature breed(Creature c, int windowWidth, int windowHeight) {
		int rand = (int) (Math.random() * 2);

		int totalHealth, hLR, xSpeed, ySpeed, size;
		Color color;

		if (rand > 0) {
			totalHealth = this.totalHealth;
		} else
			totalHealth = c.getTotalHealth();

		if (rand > 0) {
			hLR = this.hLR;
		} else
			hLR = c.getHLR();

		if (rand > 0) {
			xSpeed = this.xSpeed;
		} else
			xSpeed = c.getXSpeed();

		if (rand > 0) {
			ySpeed = this.ySpeed;
		} else
			ySpeed = c.getYSpeed();

		if (rand > 0) {
			size = this.size;
		} else
			size = c.getSize();

		if (rand > 0) {
			color = this.color;
		} else
			color = c.getColor();

		return new Creature(windowWidth, windowHeight, totalHealth, hLR, xSpeed, ySpeed, size, color);
	}

	public void mutate(double rate, int windowWidth, int windowHeight) {
		double r = rate * 100;

		double rand = Math.random() * 100;
		
		//this randomizes x and y
//		if (r > rand) {
//			this.x = (int) (1 + (Math.random() * (windowWidth - size - 1)));
//		}
//		
//		rand = Math.random() * 100;
//		if (r > rand) {
//			this.y = (int) (1 + (Math.random() * (windowHeight - size - 1)));
//		}
		
		rand = Math.random() * 100;
		if (r > rand) {
			this.totalHealth = MIN_HEALTH + (int) (Math.random() * (MAX_HEALTH - MIN_HEALTH));
			this.curHealth = totalHealth;
		}
		
		rand = Math.random() * 100;
		if (r > rand) {
			this.hLR = MIN_HLR + (int) (Math.random() * (MAX_HLR - MIN_HLR));
		}
		
		rand = Math.random() * 100;
		if (r > rand) {
			this.xSpeed = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));
		}
		
		rand = Math.random() * 100;
		if (r > rand) {
			this.ySpeed = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));
		}
		
		rand = Math.random() * 100;
		if (r > rand) {
			this.size = MIN_SIZE + (int) (Math.random() * (MAX_SIZE - MIN_SIZE));
		}
		
		rand = Math.random() * 100;
		if (r > rand) {
			this.color = colors[(int) (Math.random() * colors.length)];
		}
	}
	
		public Creature split(int windowWidth, int windowHeight) {
			//curHealth = curHealth/2;
			
			Creature c = new Creature(windowWidth, windowHeight, totalHealth, hLR, xSpeed, ySpeed, size, color);
			c.randDirection(windowWidth, windowHeight);
			//c.setCurHealth(curHealth);
			
			int xTemp = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));
			int yTemp = MIN_SPEED + (int) (Math.random() * (MAX_SPEED - MIN_SPEED));
			System.out.println(xTemp + " " + yTemp);
			
			c.setXSpeed(xTemp);
			c.setYSpeed(yTemp);
			
			c.setX(this.x);
			c.setY(this.y);
			
			return c;
		}
		

}
