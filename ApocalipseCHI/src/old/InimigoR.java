package old;
import java.awt.Image;


public class InimigoR extends Sprite{

	public InimigoR(Image charset, int x, int y, int charac) {
		super(charset, x, y, charac);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void SimulaSe(float diftime) {
        Tempo += diftime;
        
        float oldx = X;
        float oldy = Y;
        
        X += (diftime*VelX)/1000.0f;
        Y += (diftime*VelY)/1000.0f;
        
        boolean invert = false;
        
    	if(((int)((Y+24)/16))>=1024||((int)((X+12)/16))>=800||X<0||Y<0){
    		X = oldx;
    		Y = oldy;
    		
    		invert = true;
    	}
    	/*if(GamePanel.MAPA.mapa[1][(int)((Y+24)/16)][(int)((X+12)/16)]>0){
    		X = oldx;
    		Y = oldy;
    		
    		invert = true;
    	}*/     
    	
    	for(int i = 0; i < CanvasMAIN.ListaObjetos.size(); i ++){
    		Sprite enemy = CanvasMAIN.ListaObjetos.get(i); 
    		if(enemy!=this){
    			//System.out.println("entro i "+i);
    			float x1 = enemy.X;
    			float y1 = enemy.Y;
    			float x2 = enemy.X+24;
    			float y2 = enemy.Y+32;    			
    			if((X<x2)&&((X+24)>x1)&&(Y<y2)&&((Y+32)>y1)){
    	    		X = oldx;
    	    		Y = oldy;
    	    		invert = true;
    	    		break;
    			}
    		}
    	}
        
        Frame = ((int)(Tempo/FrameTime))%3;
        
        if(invert){
        	VelX=-VelX;
        	VelY=-VelY;
        }
        
	}

}
