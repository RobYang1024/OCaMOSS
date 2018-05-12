package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Game;
import model.Location;
import model.NotImplementedException;
import model.Player;

public class DumbAI extends Controller {

	public DumbAI(Player me) {
		super(me);
	}

	protected @Override Location nextMove(Game g) {

		Board b = g.getBoard();
		for (int row = 0;row<Board.NUM_ROWS;row++) {
			for(int col = 0;col<Board.NUM_COLS;col++) {
				Location loc = new Location(row,col);
				if (b.get(loc) == null) {
					delay();


					return loc;
				}

			}
		}
		delay();










																								
		return null;
	}
}
