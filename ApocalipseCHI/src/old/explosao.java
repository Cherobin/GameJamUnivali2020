package old;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;


public class explosao extends Particula
{


	int tVida = 0;
	
	public int tipo = 0;
	
	public explosao(float x,float y, int tvida) {
		// TODO Auto-generated constructor stub
	
		
		X = x;
		Y = y;
		
		VelX = (int)((Math.random()*15)-30);
		VelY = (int)((Math.random()*15)-30);
		
		tVida = tvida;
	}
	
	@Override
	public void DesenhaSe(Graphics2D dbg, int Xmundo, int Ymundo) {
		// TODO Auto-generated method stub
        int posx = (int)X - Xmundo; 
        int posy = (int)Y - Ymundo;
        
        AffineTransform trans = dbg.getTransform();
        
        dbg.translate(posx, posy);
        float scale = (timer)/(float)tVida; 
        dbg.scale((scale*1.5)+0.2, (scale*1.5)+0.2);
        
        float alpha = (1.0f-scale);
        //System.out.println("alpha  "+alpha);
		//dbg.setColor(new Color(r,g,b,(int)(255*alpha)));
        
       /* float[] scales = { 1, alpha, 1, 1};
        float[] offsets = new float[4];
        RescaleOp rop = new RescaleOp(scales, offsets, null);*/
        
        Composite comp = dbg.getComposite();
        
       
        
		//dbg.drawImage(GamePanel.fumaca, rop, 0, 0);
        
        if(tipo==0){
	        if(scale<0.3){
	        	dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	        	dbg.drawImage(CanvasMAIN.ExplosaoFT3, null, -32, -32);
	
	        }else{
	
	        	float alphafactor = 1.0f-(scale);
	        	float alphafactorinv = 1.0f-alphafactor;
	            
	            	dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha*alphafactor));
	            	dbg.drawImage(CanvasMAIN.ExplosaoFT3, null, -32, -32);
	           	 	dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha*alphafactorinv));
	    	      	dbg.drawImage(CanvasMAIN.ExplosaoFT3, null, -32, -32);
	
			}
        }else{
	        if(scale<0.3){
	        	dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	        	dbg.drawImage(CanvasMAIN.ExplosaoFT3, null, -32, -32);
	
	        }else{
	
	        	float alphafactor = 1.0f-(scale);
	        	float alphafactorinv = 1.0f-alphafactor;
	            
	            	dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha*alphafactor));
	            	dbg.drawImage(CanvasMAIN.ExplosaoFT3, null, -32, -32);
	           	 	dbg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha*alphafactorinv));
	    	      	dbg.drawImage(CanvasMAIN.ExplosaoFT3, null, -32, -32);
	
			}
        }
        
        dbg.setComposite(comp);
		
		dbg.setTransform(trans);
				
	}

	@Override
	public void SimulaSe(float diftime) {
		// TODO Auto-generated method stub
		
		double doubledif = diftime/1000.0f;
		
		timer+=diftime;
		if(timer>tVida){
			vivo = false;
		}
		
        X += (doubledif*VelX);
        Y += (doubledif*VelY);
	}

}
