package demo;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import controler.AbstractControler;
import controler.GameControler;
import model.Game;
import vue.Puzzle;

public class MainApp {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		Game game = new Game(3,3);
		AbstractControler controler = new GameControler(game,20);
		Puzzle puzzle = new Puzzle(controler);
		game.addObserver(puzzle);

	}

}
