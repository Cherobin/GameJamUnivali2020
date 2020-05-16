package old;
import java.awt.Color;
import java.awt.Graphics2D;


public class Faisca extends Particula
{
	Color cor = null;
	int r;
	int g;
	int b;
	int tVida = 0;
	
	public Faisca(Color cor, float x,float y, float ang,int vel, int tvida) {
		// TODO Auto-generated constructor stub
		this.cor = cor;
		
		r = cor.getRed();
		g = cor.getBlue();
		b = cor.getGreen();		
		
		X = x;
		Y = y;
		
		VelX = (int)(vel*Math.cos(ang));
		VelY = (int)(vel*Math.sin(ang));
		
		tVida = tvida;
	}
	
	@Override
	public void DesenhaSe(Graphics2D dbg, int Xmundo, int Ymundo) {
		// TODO Auto-generated method stub
        int posx = (int)X - Xmundo; 
        int posy = (int)Y - Ymundo;
        
        float alpha = (tVida-timer)/(float)tVida;
        //System.out.println("alpha  "+alpha);
		dbg.setColor(new Color(r,g,b,(int)(255*alpha)));
		dbg.fillRect(posx-1, posy-1, 3, 3);
				
	}

	@Override
	public void SimulaSe(float diftime) {
		// TODO Auto-generated method stub
		timer+=diftime;
		if(timer>tVida){
			vivo = false;
		}
		
        X += (diftime*VelX)/1000.0f;
        Y += (diftime*VelY)/1000.0f;
	}

}
