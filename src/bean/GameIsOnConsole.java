package bean;

import java.io.Serializable;
import javax.persistence.*;


public class GameIsOnConsole implements Serializable {
	private static final long serialVersionUID = 1L;


	private int idGameIsOnConsole;

	private Game game;

	private Console console;

	public GameIsOnConsole() {
	}

	public int getIdGameIsOnConsole() {
		return this.idGameIsOnConsole;
	}

	public void setIdGameIsOnConsole(int idGameIsOnConsole) {
		this.idGameIsOnConsole = idGameIsOnConsole;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Console getConsole() {
		return this.console;
	}

	public void setConsole(Console console) {
		this.console = console;
	}

}