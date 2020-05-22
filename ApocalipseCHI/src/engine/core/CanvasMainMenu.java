package engine.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

import engine.core.game.CanvasGame;
import old.CanvasMAIN;
import old.Constantes;

public class CanvasMainMenu extends MyCanvas {

	int timer = 0;

	@Override
	public void update(float diftime) {
		// TODO Auto-generated method stub
		timer += diftime;

		//if (timer > 8000) {
		//	GamePanel.telaAtiva = new CanvasGame();
		//}
	}

	@Override
	public void render(Graphics2D dbg) {

		dbg.setColor(Color.black);
		dbg.fillRect(0, 0, Constantes.telaW, Constantes.telaH);

		dbg.setFont(Constantes.font.deriveFont(Font.PLAIN, 50));
		dbg.setColor(Color.WHITE);
		dbg.drawString("  NEW GAME ", Constantes.telaW / 2 - 321, Constantes.telaH / 2 + 50);
		dbg.setColor(Color.LIGHT_GRAY);
		dbg.drawString("  LOAD GAME ", Constantes.telaW / 2 - 321, Constantes.telaH / 2 + 100);
		dbg.drawString("  OPÇÕES ", Constantes.telaW / 2 - 321, Constantes.telaH / 2 + 150);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		GamePanel.telaAtiva = new  CanvasHistorinha();//new CanvasGame();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub

	}

}
