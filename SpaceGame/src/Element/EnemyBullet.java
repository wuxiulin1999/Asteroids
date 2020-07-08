package Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import View.GameUser;
import Element.Message;
public class EnemyBullet implements Movement{
	private double speed;
	private double x,y;
	private boolean live=true;
	private Boolean Player;
	private GameUser gu;
	private Polygon p;
	private Random r=new Random();
	private double angle;
	private double PeriodX;
	private double PeriodY;
	private int Score;
	private int Type;
	void Shape(int Type) {
		p=new Polygon();
		if(Type==0) {
			p.addPoint((int)x,(int)(y -5));
			p.addPoint((int)(x +5),(int)y);
			p.addPoint((int)(x -5),(int)y);
			this.speed = Message.Small_Bullet_Speed;
		}
	}
	public EnemyBullet(double x,double y,Boolean player,int type) {
		this.x=x;
		this.y=y;
		this.Player=player;
		this.angle=r.nextInt(360);
		this.Type=0;
		this.Score=5-Type;
		this.Shape(0);
	}
	public EnemyBullet(double x,double y,Boolean player,GameUser gu,int type) {
		this(x,y,player,type);
		this.gu=gu;
	}
	public void drawEnemyBullet(Graphics g) {
		Graphics2D g1=(Graphics2D)g;
		if(!live) {
			if(!Player) {
				gu.Legs.remove(this);
			}
		}
		g1.setColor(Color.yellow);
		g1.fillPolygon(p);
		move();
	}
	public void move() {
		PeriodX=x;
		PeriodY=y;
		x=x+speed * Math.sin(angle);
		y=y+speed * Math.cos(angle+Math.PI);
		Shape(this.Type);
		if (x < 0 || y < 0 || x > GameUser.Game_Width|| y > GameUser.Game_Height) {
			 live=false;
		
		}
	}
	public void stay() {
		x=PeriodX;
		y=PeriodY;
	}
	public boolean IsCollision(ArrayList<EnemyBullet> EnemyBullets) {  
		for(int i=0;i<EnemyBullets.size();i++){
			EnemyBullet b=EnemyBullets.get(i);
			if(this.live && b.islive() && this.getRect().intersects(b.getRect())) 
			{
					stay();
					return true;
			}
		}
				return false;
	}
	public boolean islive() {
		return live;
	}
	
	public boolean isPlayer() {
		return this.Player;
	}

	public void setLive(boolean l) {
		live = l;
	}

	public Rectangle getRect() {
		return this.p.getBounds();
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public int getScore() {
		return Score;
	}

	@Override
	public void turn() {
		
	}

}