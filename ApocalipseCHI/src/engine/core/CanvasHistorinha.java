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

public class CanvasHistorinha extends MyCanvas {

	int timer = 0;
	
	CanvasGame theGame = null;
	boolean firstdraw = true;

	@Override
	public void update(float diftime) {
		// TODO Auto-generated method stub
		timer += diftime;
		if(firstdraw==false && theGame==null) {
			theGame = new CanvasGame();
		}

		// if (timer > 8000) {
		// GamePanel.telaAtiva = new CanvasGame();
		// }
	}

	@Override
	public void render(Graphics2D dbg) {

		dbg.setColor(Color.black);
		dbg.fillRect(0, 0, Constantes.telaW, Constantes.telaH);

		dbg.setFont(Constantes.font.deriveFont(Font.PLAIN, 30));
		dbg.setColor(Color.YELLOW);
		dbg.drawString("  Janeiro 2020 ", 0, 0);
		dbg.drawString("  Um virus surge na China e rapidamente se espalha pelo mundo ", 0, 50);
		dbg.drawString("  A população é orientada a ficar em casa para evitar o contagio ", 0, 100);
		dbg.drawString("  Depois de 90 dias em casa alguns acontecimentos estranhos acontecem. ", 0, 150);
		dbg.drawString("  O sinal de tv e a internet começam a falhar ", 0, 200);
		dbg.drawString("  As pessoas são orientadas a não sair de casa nem para comprar alimentos ", 0, 250);
		dbg.drawString("  30 dias depois a TV e a internet falha de Vez ", 0, 300);
		dbg.drawString("  Ja se passaram 150 dias do inicio da quarentena, a comida esta acabando. ", 0, 350);
		dbg.drawString("  A energia começa a falhar. ", 0, 400);
		dbg.drawString("  A comida acaba... ", 0, 450);
		dbg.drawString("  Você decide sair de casa para comprar comida ", 0, 500);
		dbg.drawString("  São 12:00 e você olha para o ceu, ele esta escuro. ", 0, 550);
		dbg.drawString("  O sol está coberto por grossas nuvens de cinzas. ", 0, 600);
		dbg.drawString("  Tudo está muito diferente. ", 0, 650);
		dbg.drawString("  .... ", 0, 700);
		
		firstdraw = false;
		
		if(theGame!=null) {
			dbg.setColor(Color.RED);
			dbg.drawString("  Clique Na Tela Para Continuar ", 250, 700);
		}

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
		if(theGame!=null) {
			GamePanel.telaAtiva = theGame;
		}
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
