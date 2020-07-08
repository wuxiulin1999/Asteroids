package View;

import java.awt.*;
import java.io.IOException;

import javax.swing.JPanel;

import Element.Message;
import Element.Scoreboard;
import Element.Score;

public class FinalCover extends JPanel {

    private static final long serialVersionUID = 1L;

    public static final int GAME_WIDTH = 1500;
    public static final int GAME_HEIGHT = 800;
    Scoreboard board;

    public FinalCover(Scoreboard sb) throws IOException {
        board = sb;
        luanchFrame();
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Message.Game_Width, Message.Game_Width);
        
        g.setFont(new Font("Arial", Font.PLAIN, 36));
        g.setColor(Color.red);
        g.drawString("Asteroids Top List", 10, 50);
        Score[] scores = board.GetScore();
        
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        
        for (int i = 0; i < scores.length; i++) {
            Score score = scores[i];
            g.drawString(score.getName(), 2 * Message.Game_Width / 6, 100 + i * 32);
            g.drawString("" + score.getScore(), 4 * Message.Game_Width / 6, 100 + i * 32);
        }
        g.drawString("Press 'M' to return to the Main Menu",100,500);
    }

    public void luanchFrame() throws IOException {
        board.LoadScore();
        board.SortScore();
        setSize(FinalCover.GAME_WIDTH, FinalCover.GAME_HEIGHT);
        setBackground(Color.BLACK);
        setVisible(false);

    }

}