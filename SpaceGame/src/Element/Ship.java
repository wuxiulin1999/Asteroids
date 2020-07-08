package Element;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import View.GameUser;

public class Ship implements Movement {
	private GameUser gu;
	public static final int SHIP_WIDTH = 80;
	public static final int SHIP_HEIGHT = 70;
	public static final double SHIP_accelration = 2;
	public static final double SHIP_Rangular = Math.PI / 20;
	private int x, y;
	private double angle;
	private boolean player;
	private boolean live = true;
	Operation dir;
	boolean bu = false, br = false, bl = false, bd = false,ju=false;
	private Polygon p;
	int oldx, oldy;
	private double oldangle;
	private int speedx = 0;
	private int speedy = 0;
	private int life = 10;
	public Ship() {
	}

	public Ship(int x, int y, double angle, GameUser m) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.gu = m;
		shape();
	}

	public Ship(int x, int y, double angle, boolean good, GameUser m) {
		this(x, y, angle, m);
		this.player = good;
		this.oldx = x;
		this.oldy = y;
		shape();
	}
	public void shape() {
		p = new Polygon();
		p.addPoint(x,y - 40);
		p.addPoint(x + 40,y + 50);
		p.addPoint(x,y + 25);
		p.addPoint(x - 40,y + 50);
	}

	public void rotateToAngle(int[] x, int[] y, double theta, int x0, int y0) {
		for (int i = 0; i < x.length; i++) {
			int tempx = x[i];
			x[i] = (int) ((x[i] - x0) * Math.cos(theta) - (y[i] - y0) * Math.sin(theta) + x0);
			y[i] = (int) ((tempx - x0) * Math.sin(theta) + (y[i] - y0) * Math.cos(theta) + y0);
		}
	}

	public void rotateToOffSetAngle() {
		shape();
		rotateToAngle(p.xpoints, p.ypoints, this.angle, x, y);
	}

	public void setOffsetAngle(double dTheta) {
		this.angle = (this.angle + dTheta) % (2 * Math.PI);
	}

	public void drawShip(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (!live) {
			return;
		}
		Color c = g2.getColor();
		if (player)
			g2.setColor(Color.yellow);

		g2.fillPolygon(p);
		g.setColor(c);
		move();
	}

	@Override
	public void move() {
		oldx = x;
		oldy = y;
		oldangle = angle;

		if (player) {
			x = (x + speedx);
			y = (y - speedy);
			if(speedx>Message.Ship_MaxSpeed) {
				speedx=(int) Message.Ship_MaxSpeed;
			}
			if(speedx<0) {
				speedx=0;
			}
			if(speedy>Message.Ship_MaxSpeed) {
				speedy=(int) Message.Ship_MaxSpeed;
			}
			if(speedy<0) {
				speedy=0;
			}
			rotateToOffSetAngle();
			if (bd) {
				speedx = (int) (speedx + SHIP_accelration * Math.sin(angle));
				speedy = (int) (speedy - SHIP_accelration * Math.cos(angle));
				rotateToOffSetAngle();
			}

			if (br) {
				setOffsetAngle(Math.PI / 30);
				rotateToOffSetAngle();
			}
			
			if (bl) {
				setOffsetAngle(-Math.PI / 30);
				rotateToOffSetAngle();
			}
			
			if (bu) {
				speedx = (int) (speedx - SHIP_accelration * Math.sin(angle));
				speedy = (int) (speedy + SHIP_accelration * Math.cos(angle));
				rotateToOffSetAngle();
				}
			if(ju) {
				Random a=new Random();
				this.x=a.nextInt(Message.Game_Width);
				this.y=a.nextInt(Message.Game_Height);
			}

			

		if (x > GameUser.Game_Width) {
			x = 0;
		}
		if (y > GameUser.Game_Height) {
			y = 0;
		}
		if (x < 0) {
			x = GameUser.Game_Width;
		}
		if (y < 0) {
			y = GameUser.Game_Height;
		}
	}
	}

	private Bullet fire() {
		if (!live)
			return null;
		Bullet m = new Bullet(p.xpoints[0], p.ypoints[0], player, this.angle, gu);// change
		gu.Arms.add(m);
		return m;
	}
	public Rectangle getRect() {
		return this.p.getBounds();
	}

	public boolean islive() {
		return this.live;
	}

	public int getlife() {
		return this.life;
	}

	public boolean isPlayer() {
		return this.player;
	}

	public void setlive(boolean live) {
		this.live = live;
	}
	public boolean iscollision(ArrayList<Brick> bricks,ArrayList<EnemyShip> EnemyShips,ArrayList<EnemyBullet> EnemyBullets, Ship playerShip) { 
		for (int i = 0; i < bricks.size(); i++) {
			Brick s = bricks.get(i);
			if (this.live && s.islive() && this.getRect().intersects(s.getRect())) {
				stay();
				if (this.player != s.isPlayer()) {
					if (life != 1) {
						s.setLive(false);
						life--;
					} else {
						this.setlive(false);
						s.setLive(false);
						life--;
					}
					return true;
				}
			}
		}
			for (int i1 = 0; i1 < EnemyShips.size(); i1++) {
				EnemyShip s1 = EnemyShips.get(i1);
				if (this.live && s1.islive() && this.getRect().intersects(s1.getRect())) {
					stay();
					if (this.player != s1.isPlayer()) {
						if (life != 1) {
							s1.setLive(false);
							life--;
						} else {
							this.setlive(false);
							s1.setLive(false);
							life--;
						}
						return true;
					}
					
				}
			}
			for (int i2 = 0; i2 < EnemyBullets.size(); i2++) {
				EnemyBullet s2 = EnemyBullets.get(i2);
				if (this.live && s2.islive() && this.getRect().intersects(s2.getRect())) {
					stay();
					if (this.player != s2.isPlayer()) {
						if (life != 1) {
							s2.setLive(false);
							life--;
						} else {
							this.setlive(false);
							s2.setLive(false);
							life--;
						}
						return true;
					}
					gu.Legs.remove(this);
				}
			}
			return false;
		}


	public void stay() {
		x = oldx;
		y = oldy;
	}
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_LEFT:
			bl = true;
			break;
		case KeyEvent.VK_UP:
			bu = true;
			break;
		case KeyEvent.VK_RIGHT:
			br = true;
			break;
		case KeyEvent.VK_DOWN:
			bd = true;
			break;
		case KeyEvent.VK_N:
			ju=true;
			break;
		}
		changeDir();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bl = false;
			break;
		case KeyEvent.VK_UP:
			bu = false;
			break;
		case KeyEvent.VK_RIGHT:
			br = false;
			break;
		case KeyEvent.VK_DOWN:
			bd = false;
			break;
		case KeyEvent.VK_N:
			ju=false;
			break;
		}
		changeDir();
	}

	private void changeDir() {
		if (bl && !bu && !br && !bd)
			dir = Operation.Left;
		else if (!bl && bu && !br && !bd)
			dir = Operation.Up;
		else if (!bl && !bu && br && !bd)
			dir = Operation.Right;
		else if (!bl && !bu && !br && bd)
			dir = Operation.Down;
		else if (bl && !bu && !br && !bd)
			dir = Operation.Left;
	}

	@Override
	public void turn() {
	}
}