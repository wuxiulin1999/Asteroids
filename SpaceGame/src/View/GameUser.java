package View;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import Element.Brick;
import Element.Bullet;
import Element.EnemyBullet;
import Element.EnemyShip;
import Element.RRandom;
import Element.Scoreboard;
import Element.Score;
import Element.Ship;
public class GameUser extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public static final int Game_Width =1500;
	public static final int Game_Height=800;
;
	int x=Game_Width/2;
	int y=Game_Height/2;
	int angle=0;
	RRandom r=new RRandom();
	private int score;
	private Thread T;
	private int i;
	private int j;
	private int k;
	private int h=-1;
	private String PlayerName;
	private Scoreboard SB;
	public Ship PlayerShip=new Ship(x,y,angle,true,this);
	public ArrayList<Bullet>Arms=new ArrayList<Bullet>();
	public ArrayList<EnemyBullet>Legs=new ArrayList<EnemyBullet>();
	public ArrayList<EnemyShip>EnemyShips=new ArrayList<EnemyShip>();
	public ArrayList<Brick>Bricks=new ArrayList<Brick>();
	public int State=0;
	public GameUser() throws IOException{
		this.creatFrame();
		SB=new Scoreboard();
	}
	public void SetState(int x) {
		this.State=x;
	}
	public int GetState() {
		return this.State;
	}
	public void AddScore(int x) {
		score=score+x;
	}
	public Thread GetThread() {
		return T;
	}
	@Override
	public void paint(Graphics g) {
		
		super.paint(g);
		i+=1;
		if(!PlayerShip.iscollision(Bricks,EnemyShips,Legs,PlayerShip)) {
			PlayerShip.drawShip(g);
		}
		if(EnemyShips.size()==0&&Bricks.size()==0) {
			k=j;
			while(k>=4000) {
				Brick a=new Brick(0,0,false,this,2);
				Bricks.add(a);
				k-=4000;
			}
			k=j;
			while(k>=3000) {
				EnemyShip a=new EnemyShip(0,0,false,this,2);
				EnemyShips.add(a);
				k-=3000;
			}
			k=j;
			while(k>=2000) {
				Brick b=new Brick(0,0,false,this,1);
				k-=2000;
				Bricks.add(b);
			}
			k=j;
			while(k>=1000) {
				Brick c=new Brick(0,0,false,this,0);
				k-=1000;
				Bricks.add(c);
			}
			j+=1000;
			h+=1;
		}
		for(int x=0;x<Arms.size();x++) {
			Bullet b=Arms.get(x);
			b.hitBrick(Bricks);
			b.hitEnemyShip(EnemyShips);
			b.isHit(PlayerShip);
			b.drawBullet(g);
		}
		for(int x=0;x<Bricks.size();x++) {
			Brick b=Bricks.get(x);
			if (b.islive()!=true) {
				Bricks.remove(b);
			}
			b.drawBrick(g);
			
		}
		for(int x=0;x<EnemyShips.size();x++) {
			EnemyShip b=EnemyShips.get(x);
			if (b.islive()!=true) {
				EnemyShips.remove(b);
			}
			b.drawEnemyShip(g);
			if(i%45==0) {
			b.Fire();
			}

		}
		for(int x=0;x<Legs.size();x++) {
			EnemyBullet b=Legs.get(x);
			b.drawEnemyBullet(g);
		}
		g.setColor(Color.white);
		Font font=new Font("Board",Font.PLAIN,18);
		g.setFont(font);
		g.drawString("Score:"+score,100,80);
		g.drawString("Life:"+PlayerShip.getlife(), 100, 100);
		g.drawString("Level"+h, 100, 120);
		if(PlayerShip.getlife() == 0) {
			Font fo=new Font("Board",Font.ITALIC,100);
			g.setFont(fo);
			g.drawString("Game over",Game_Width/2, Game_Height/2);
			Font fn = new Font("Board",Font.PLAIN,18);
			g.setFont(fn);
			g.drawString("Enter S into scoreboard", Game_Width/2, Game_Height/2+100);
			if(this.State == 0) {
				this.SB.AddScore(new Score(PlayerName,score));
				this.SB.SaveScore();
				this.SetState(1);
			}
		}
	}
	public void ThreadPart() {
		this.repaint();
	}
	public void creatFrame() {
		setSize(Game_Width,Game_Height);
		setBackground(Color.BLACK);
		setVisible(false);
		
		T = new Thread(new PaintThread());
		T.start();
	}
	public void SetPlayerName(String PlayerName) {
		this.PlayerName = PlayerName;
	}
	public String GetPlayerName() {
		return PlayerName;
	}
	public void SetScoreBoard(Scoreboard sb) {
		SB = sb;
	}
	public Scoreboard GetScoreBoard() {
		return SB;
	}
	
	class PaintThread implements Runnable{
		@Override
		public void run() {
			while(true) {
				ThreadPart();
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace(); 
				}
			}
		}
	}

}