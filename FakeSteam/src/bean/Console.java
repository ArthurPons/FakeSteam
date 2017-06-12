package bean;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;



public class Console implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idConsole;

	private String nameConsole;

	private List<Game> games;

	public Console() {
	}

	public int getIdConsole() {
		return this.idConsole;
	}

	public void setIdConsole(int idConsole) {
		this.idConsole = idConsole;
	}

	public String getNameConsole() {
		return this.nameConsole;
	}


	public void setNameConsole(String nameConsole) {
		this.nameConsole = nameConsole;
	}

	public List<Game> getGames() {
		return this.games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

}