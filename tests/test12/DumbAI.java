package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Game;
import model.Location;
import model.NotImplementedException;
import model.Player;

/**
 * A DumbAI is a Controller that always chooses the blank space with the
 * smallest column number from the row with the smallest row number.
 */
public class DumbAI extends Controller {

	public DumbAI(Player me) {
		super(me);
		// TODO Auto-generated constructor stub
		//throw new NotImplementedException();
	}

	protected @Override Location nextMove(Game g) {
		// Note: Calling delay here will make the CLUI work a little more
		// nicely when competing different AIs against each other.
		
		// TODO Auto-generated method stub
		//throw new NotImplementedException();
		
		Board b = g.getBoard();
		// find available moves
		for (int row = 0;row<Board.NUM_ROWS;row++) {
			for(int col = 0;col<Board.NUM_COLS;col++) {
				Location loc = new Location(row,col);
				if (b.get(loc) == null) {
					delay();
					return loc;
				}
				
			}
		}
		// wait a bit
		delay();
		
		return null;
	}
}
