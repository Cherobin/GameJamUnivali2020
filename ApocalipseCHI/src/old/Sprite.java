package old;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

/*
 * Created on 24/08/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author Palm Soft
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Sprite extends Objeto{
    
    public Image Charset = null;
    
    float X,Y;
    int VelX,VelY;
    int FrameTime;
    long Tempo;
    int Anim;
    int Character;
    int Frame;
    int charx,chary;
    
    int life = 50;
    
    public Sprite(Image charset,int x,int y,int charac){
        X = x;
        Y = y;
        
        VelX = 0;
        VelY = 0;
        
        FrameTime = 100;
        
        Tempo = 0;
        
        Anim = 0;
        Frame = 0;
        
        Character = charac;
        
        charx = (Character&0x03)*72; 
        chary = (Character>>2)*128;
        
        Frame = 0;
        
        Charset = charset;
        
    }   
    
    public void SimulaSe(float diftime) {
        Tempo += diftime;
        
        float oldx = X;
        float oldy = Y;
        
        X += (diftime*VelX)/1000.0f;
        Y += (diftime*VelY)/1000.0f;
        
        
        boolean invert = false;
        /*
    	if(((int)((Y+24)/16))>=1024||((int)((X+12)/16))>=800||X<0||Y<0){
    		X = oldx;
    		Y = oldy;
    		
    		invert = true;
    	}
    	if(CanvasMAIN.MAPA.mapa[1][(int)((Y+24)/16)][(int)((X+12)/16)]>0){
    		X = oldx;
    		Y = oldy;
    		
    		invert = true;
    	}     
    	*/
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

	@Override
	public void DesenhaSe(Graphics2D dbg, int Xmundo, int Ymundo) {
		// TODO Auto-generated method stub
        int posx = (int)X - Xmundo; 
        int posy = (int)Y - Ymundo;
        dbg.drawImage(Charset,posx,posy,posx+24,posy+32,charx+(Frame*24),chary+(Anim*32),charx+(Frame*24)+24,chary+(Anim*32)+32,null);
        
        dbg.setColor(Color.red);
        dbg.drawRect(posx, posy, 24, 32);
        
        dbg.setColor(Color.blue);
        dbg.drawOval(posx, posy+4, 24, 24);
        
        dbg.setColor(Color.black);
        dbg.drawRect(posx, posy-5, 24, 4);   
        
        dbg.setColor(Color.green);
        dbg.fillRect(posx, posy-5, (int)(life/50.0f*24), 4);
        
	}

}
