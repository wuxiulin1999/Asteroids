package Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import View.Screen;

public class control implements KeyListener {
	private Screen screen;

	public control() {
	}

	public control(Screen s) {
		this.screen = s;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		screen.getGameUser().PlayerShip.keyPressed(e);
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			screen.switchs(2);
			screen.getCreate().repaint();
		}
		if (key == KeyEvent.VK_M) {
			screen.switchs(1);
			screen.getCreate().repaint();
		}
		if (key == KeyEvent.VK_S) {
			if (screen.getGameUser().GetState() == 1) {
				screen.switchs(3);
				screen.getCreate().repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		screen.getGameUser().PlayerShip.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}