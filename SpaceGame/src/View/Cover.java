package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Element.Brick;
import Element.EnemyBullet;
import Element.EnemyShip;
import Element.RRandom;

public class Cover extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int GAME_WIDTH = 1500;
	public static final int GAME_HEIGHT = 800;
	
	int x = 80,y = 600;
	
	int angle = 0;
	
	RRandom r = new RRandom();
	
	private String name;
	private GameUser gu;
	
	public ArrayList<Brick> bricks = new ArrayList<Brick>();
	public ArrayList<EnemyShip> EnemyShip = new ArrayList<EnemyShip>();
	public ArrayList<EnemyBullet> Legs = new ArrayList<EnemyBullet>();
	public Cover(GameUser gu) {
		this.gu = gu;
		this.CreateFrame();
	}

	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < bricks.size(); i++) {
			Brick s = bricks.get(i);
			s.drawBrick(g);
		}
		for (int i = 0; i < EnemyShip.size(); i++) {
			EnemyShip s = EnemyShip.get(i);
			s.drawEnemyShip(g);
		}
		for (int i = 0; i < Legs.size(); i++) {
			EnemyBullet s = Legs.get(i);
			s.drawEnemyBullet(g);
		}
		//Title Font
		Font font = new Font("Arial",Font.PLAIN,100);
		g.setFont(font);
		g.setColor(Color.cyan);
		g.drawString("Asteroids" + "\n",560,380);
		
		//Instruction Font
		font = new Font("Arial",Font.PLAIN,40);
		g.setFont(font);
		g.setColor(Color.green);
		g.drawString("Press                      To Start",520,470);
		g.setColor(Color.red);
		g.drawString("'ENTER'\n",670,470);
		
		//Game Information Font
		font = new Font("Arial", Font.BOLD, 30);
		g.setFont(font);
		g.setColor(Color.magenta);
		g.drawString("#Control your plane to deal with the aerolites and enemy's ships#", 330,520);		
		g.setColor(Color.gray);
		g.drawString("Version:",600, 650);
		g.setColor(Color.white);
		g.drawString("Official Server",740, 650);
	}

	//Thread Performance
	public void threadPart() {
		this.repaint();
	}

	public void CreateFrame() {
		for(int i = 0;i < 10;i++) {
			switch (r.nextInt(3)) {
			case 0:
				bricks.add(new Brick(0, r.nextInt(Cover.GAME_HEIGHT), false, 2));
				EnemyShip.add(new EnemyShip(r.nextInt(GAME_WIDTH),0,false,2));
				break;
			case 1:
				bricks.add(new Brick(Cover.GAME_WIDTH, r.nextInt(Cover.GAME_HEIGHT), false, 2));
				EnemyShip.add(new EnemyShip(r.nextInt(GAME_WIDTH),0,false,2));
				break;
			case 2:
				bricks.add(new Brick(r.nextInt(Cover.GAME_WIDTH), 0, false, 2));
				EnemyShip.add(new EnemyShip(r.nextInt(GAME_WIDTH),0,false,2));
				break;
			}
		}
		
		this.setSize(GameUser.Game_Width, GameUser.Game_Width);
		this.setBackground(Color.black);
		this.setVisible(true);
		
		JOptionPane.showMessageDialog(null, "Are You Ready To Play Asteroids! ^_^");
		
		//Get User's Name
		this.name = JOptionPane.showInputDialog(null,"Enter Your Name:");
		this.gu.SetPlayerName(name);
		
		new Thread(new PaintThread()).start();
	}

	class PaintThread implements Runnable {

		public void run() {
			while (true) {
				threadPart();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}