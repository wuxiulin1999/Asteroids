package Element;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Scoreboard {
	private Score[] score;
	public Scoreboard() throws IOException{
		this.score=new Score[5];
		for(int i=0;i<5;i++) {
			score[i]=new Score("None Player",0);
		}
		this.LoadScore();
	}
	public void SortScore() {
		Arrays.sort(score,new Comparator<Score>(){
			public int compare(Score s1,Score s2) {
				return Integer.compare(s1.getScore(), s2.getScore());
			}
		});
	}
	public void LoadScore() throws IOException{
		File a=new File("Scoreboard.txt");
		if(!a.exists()) {
			a.createNewFile();
		}
		FileReader b=new FileReader(a);
		try(BufferedReader c=new BufferedReader(b)){
			String Line=c.readLine();
			int x=0;
			while(Line!=null&&x<5) {
				Scanner d=new Scanner(Line);
				String Name=d.next();
				int Score=d.nextInt();
				score[x]=new Score(Name,Score);
				x++;
				Line=c.readLine();
				d.close();
				}
		}catch(Exception e) {
			System.err.println("Reading has mistake!");
			e.printStackTrace();
		}
	}
	public Score[] GetScore(){
		Score[] get=new Score[5];
		for(int x=0;x<get.length;x++) {
			get[x]=score[x];
		}
		return get;
	}
	public void SaveScore(){
		try(BufferedWriter bw=new BufferedWriter(new FileWriter("Scoreboard.txt"))){
			for(int x=0;x<score.length;x++) {
				 bw.write(score[x].getScore() + " " + score[x].getName() + "\n");
			}
			bw.close();
		}catch(Exception e) {
			System.err.println("Writing has mistake");
		}
	}
	public void AddScore(Score a) {
		SortScore();
		for(int i=4;i>=0;i--) {
			if(score[i].getScore()<a.getScore()) {
				score[i]=a;
			}
		}
		SortScore();
	}
	

}