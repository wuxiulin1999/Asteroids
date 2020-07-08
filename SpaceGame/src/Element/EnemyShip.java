package Element;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import java.util.Random;

import View.GameUser;
import Element.Message;
public class EnemyShip implements Movement{
	private double speed;
	private double x,y;
	Timer a = new Timer();
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
	Timer timer = new Timer();
	
	void Shape(int Type) {
		p=new Polygon();
		if(Type==0) {
			p.addPoint((int)x,(int)(y - 27));
			p.addPoint((int)(x + 15),(int)y);
			p.addPoint((int)(x),(int)(y-12));
			p.addPoint((int)(x - 15),(int)y);
			this.speed = Message.Small_Brick_Speed;
		}
		if(Type == 1) {
			p.addPoint((int)x,(int)(y - 40));
			p.addPoint((int)(x + 40),(int)y);
			p.addPoint((int)x,(int)(y + 40));
			p.addPoint((int)(x - 40),(int)y);
			this.speed = Message.Middle_Brick_Speed;
		}
		if(Type == 2) {
			p.addPoint((int)(x + 30),(int)(y - 51));
			p.addPoint((int)(x + 60),(int)y);
			p.addPoint((int)(x + 30),(int)(y + 51));
			p.addPoint((int)(x - 30),(int)(y + 51));
			p.addPoint((int)(x - 60),(int)y);
			p.addPoint((int)(x - 30), (int)(y - 51));
			this.speed = Message.Big_Brick_Speed;
		}
	}
	public EnemyShip(double x,double y,Boolean player,int type) {
		this.x=x;
		this.y=y;
		this.Player=player;
		this.angle=r.nextInt(360);
		this.Score=5-Type;
		this.Type=0;
		this.Shape(type);
	}
	public EnemyShip(double x,double y,Boolean player,GameUser gu,int type) {
		this(x,y,player,type);
		this.gu=gu;
	}
	public void drawEnemyShip(Graphics g) {
		Graphics2D g1=(Graphics2D)g;
		if(!live) {
			if(!Player) {
				gu.EnemyShips.remove(this);
			}
		}
		g1.setColor(Color.white);
		g1.fillPolygon(p);
		move();
	}
	public void move() {
		PeriodX=x;
		PeriodY=y;
		x=x+speed * Math.sin(angle);
		y=y+speed * Math.cos(angle+Math.PI);
		Shape(this.Type);
		if(x >GameUser.Game_Width) {
			x = 0;
		}
		if(y >GameUser.Game_Height){
			y = 0;
		}
		if(x < 0) {
			x = GameUser.Game_Width;
		}
		if(y < 0){
			y =GameUser.Game_Height;
		}
	}
	public void stay() {
		x=PeriodX;
		y=PeriodY;
	}
	public boolean IsCollision(ArrayList<EnemyShip> EnemyShip) {  
		for(int i=0;i<EnemyShip.size();i++){
			EnemyShip b=EnemyShip.get(i);
			if(this.live && b.islive() && this.getRect().intersects(b.getRect())) {
					stay();
					return true;
			}
		}
				return false;
	}
	public boolean islive() {
		return this.live;
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

	public void turn() {
		
	}
	private EnemyBullet fire() {

		EnemyBullet m = new EnemyBullet(p.xpoints[0], p.ypoints[0], false,gu,0);// change
		gu.Legs.add(m);
		return m;
	}
	public void Fire() {
		
			fire();
	
	

	}
}