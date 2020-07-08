package Element;

import java.awt.*;
import java.util.ArrayList;

import View.GameUser;
import Element.Brick;
import Element.Ship;
public class Bullet implements Movement{
	private GameUser gu;
	public static final int Bullet_Width=5;
	public static final int Bullet_Height=5;
	public static final double Bullet_Speed=15;
	double x,y;
	
	boolean live=true;
	boolean player;
	private double angle;
	public Bullet(double x,double y,double angle){
		this.x=x;
		this.y=y;
		this.angle=angle;
	}
	public Bullet(double x,double y,boolean player,double angle,GameUser gu) {
		this(x,y,angle);
		this.gu=gu;
		this.player=player;
	}
	public void drawBullet(Graphics g) {
		if(!live) {
			gu.Arms.remove(this);
			return;
		}
		Color c=g.getColor();
		g.setColor(Color.WHITE);
		g.fillOval((int)x,(int) y,Bullet_Width, Bullet_Height);
		g.setColor(c);
		move();
	}

	@Override
	public void move() {
		x += Bullet_Speed*Math.sin(angle);
		y += Bullet_Speed*Math.cos(angle+Math.PI);
		if (x < 0 || y < 0 || x > GameUser.Game_Width|| y > GameUser.Game_Height) {
			 live=false;
			 gu.Arms.remove(this);
		}
	}
	@Override
	public void turn() {
		
	}
	public Rectangle getRect() {
		 return new Rectangle((int)(x-Bullet_Width),(int)(y-Bullet_Height),Bullet_Width,Bullet_Height);
	}
	 public boolean isLive() {
		 return live;
	 }
	 public boolean isHit(Ship s) {
			if(this.live && s.islive() && this.getRect().intersects(s.getRect())&&this.player!=s.isPlayer()) {
				s.setlive(false);
				this.live=false;
				return true;
			}
			return false;
		}
	 public boolean hitShips(ArrayList<Ship> ships) {
		 for(int i=0;i<ships.size();i++) {
			 Ship s=ships.get(i);
			 	if(isHit(s)){
			 		return true;
			 	}
		 }
		 return false;
	 }
	 public boolean isHit(Brick b) {
		 if(this.live && b.islive() && this.getRect().intersects(b.getRect())&&this.player!=b.isPlayer()) {
			 b.setLive(false);
			 b.destroy();
			 this.live=false;
			 gu.AddScore(b.getScore());
			 return true;
			 
	}
		 return false;
	 }
	 public boolean isHit(EnemyShip b) {
		 if(this.live && b.islive() && this.getRect().intersects(b.getRect())&&this.player!=b.isPlayer()) {
			 b.setLive(false);
			 this.live=false;
			 gu.AddScore(b.getScore());
			 return true;
		 }
		 return false;
	}

	 
	 public boolean hitBrick(ArrayList<Brick> Bricks) {
		 for(int i=0;i<Bricks.size();i++) {
			 Brick s=Bricks.get(i);
			 if(isHit(s)){
			 return true;
		 }
	 }
	 return false;
	 }
	public boolean hitEnemyShip(ArrayList<EnemyShip> EnemyShips) {
		 for(int i=0;i<EnemyShips.size();i++) {
			 EnemyShip s=EnemyShips.get(i);
			 if(isHit(s)){
			 return true;
		 }
	 }
	 return false;
	 }
}