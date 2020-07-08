package View;

import java.io.IOException;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Control.control;
import Element.EnemyShip;
public class Screen extends JPanel {
	private static final long serialVersionUID = 1L;
	private GameUser gu;
	private Cover c;
	private FinalCover fc;
	static JFrame create = new JFrame("GameUser");

	Screen() throws IOException {
		gu = new GameUser();
		fc = new FinalCover(gu.GetScoreBoard());
		c = new Cover(gu);
	}

	public GameUser getGameUser() {
		return gu;
	}

	public Cover getCover() {
		return c;
	}

	public JFrame getCreate() {
		return create;
	}

	public FinalCover getFinalCover() {
		return fc;
	}

	public void switchs(int state) {
		switch(state) {
		case 1:
			this.getFinalCover().setVisible(false);
			this.getGameUser().setVisible(false);
			this.getCover().setVisible(true);
			break;
		case 2:
			this.getFinalCover().setVisible(false);
			this.getGameUser().setVisible(true);
			this.getCover().setVisible(false);
			break;
		case 3:
			this.getFinalCover().setVisible(true);
			this.getGameUser().setVisible(false);
			this.getCover().setVisible(false);
			break;
		}
	}
	
	//Creating A Frame
	public void CreateFrame() {
		create.setLocation(15,0);
		create.setSize(GameUser.Game_Width, GameUser.Game_Height);
		control pc=new control(this);
		create.addKeyListener(pc);
		create.add(this.getGameUser());
		create.add(this.getCover());
		create.add(this.getFinalCover());
		
		create.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		create.setResizable(false);
		create.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		Screen s = new Screen();
		s.CreateFrame();
	}
}