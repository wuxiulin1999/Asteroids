package Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import View.GameUser;
import Element.Message;
public class Brick implements Movement{
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
			p.addPoint((int)x,(int)(y - 27));
			p.addPoint((int)(x + 15),(int)y);
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
	public Brick(double x,double y,Boolean player,int type) {
		this.x=x;
		this.y=y;
		this.Player=player;
		this.angle=r.nextInt(360);
		this.Type=type;
		this.Score=5-Type;
		this.Shape(type);
	}
	public Brick(double x,double y,Boolean player,GameUser gu,int type) {
		this(x,y,player,type);
		this.gu=gu;
	}
	public void drawBrick(Graphics g) {
		Graphics2D g1=(Graphics2D)g;
		if(!this.live) {
			if(!this.Player) {
				gu.Bricks.remove(this);
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
	public boolean IsCollision(ArrayList<Brick> Bricks) {  
		for(int i=0;i<Bricks.size();i++)
		{
			Brick b=Bricks.get(i);
			if(this.live && b.islive() && this.getRect().intersects(b.getRect())) 
			{
					stay();
					return true;
			}
		}
				return false;
	}
	public void destroy() {
		if(Type>0) {
		gu.Bricks.add(new Brick(this.x, this.y, false, this.gu, this.Type-1));
		gu.Bricks.add(new Brick(this.x, this.y, false, this.gu, this.Type-1));
		}
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

	@Override
	public void turn() {
		
	}

}