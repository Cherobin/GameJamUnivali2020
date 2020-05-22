package old;
import java.awt.Color;
import java.awt.Graphics2D;


public class Fireball extends Objeto{

    float X,Y;
    int VelX,VelY;
    int FrameTime;
    long Tempo;
    int Anim;
    int Frame;
    boolean vivo = true;
    
    Object father = null;
 
    public Fireball(Object father) {
		// TODO Auto-generated constructor stub
    	X = 0;
    	Y = 0;
    	VelX = 0;
    	VelY = 0;
    	
    	this.father = father;
	}
	
	@Override
	public void DesenhaSe(Graphics2D dbg,int Xmundo,int Ymundo) {
		// TODO Auto-generated method stub
        int posx = (int)X - Xmundo; 
        int posy = (int)Y - Ymundo;
        
        dbg.setColor(Color.black);
        dbg.fillOval(posx-1, posy-1, 2, 2);
	}

	@Override
	public void SimulaSe(float diftime) {
		// TODO Auto-generated method stub
        Tempo += diftime;
     /*   
        float oldx = X;
        float oldy = Y;
        
        X += (diftime*VelX)/1000.0f;
        Y += (diftime*VelY)/1000.0f;
        
        boolean invert = false;
        
        double angbase = Math.atan2(VelY, VelX);
        
    	if(((int)((Y)/16))>=1024||((int)((X)/16))>=1024||X<0||Y<0){
    		X = oldx;
    		Y = oldy;
    		
    		vivo = false;
    	}
    	
    	float difx = X-oldx;
        float dify = Y-oldy;
        
        double dist = Math.sqrt(difx*difx+dify*dify);
        
        if(dist>10){    	
        	int stps = (int)(dist/5);
        	float timestep = (diftime/stps);
        	
        	float stepx = oldx;
        	float stepy = oldy;
        	
        	for(int i = 0; i < stps;i++){
        		stepx += (timestep*VelX)/1000.0f;
        		stepy += (timestep*VelY)/1000.0f;
    	    	if(CanvasMAIN.MAPA.mapa[1][(int)((stepy)/16)][(int)((stepx)/16)]>0){
    	    		
    	    		for(int j = 0; j < 4; j ++){
    	    			double ang = angbase;
    	    			ang+=Math.PI;
    	    			ang+=0.6f-(1.2f*Math.random());
    	    			Faisca fai = new Faisca(Color.WHITE, stepx, stepy,(float) ang,(int)( 100+(100*Math.random())), 400);
    	    			CanvasMAIN.ListaParticulas.add(fai);
    	    		}
    	    		
    	    		X = stepx;
    	    		Y = stepy;
    	    		
    	    		vivo = false;
    	    		explode();
    	    		
    	    		return;
    	    	}
        		
        	}
        	
        }
        
    	if(CanvasMAIN.MAPA.mapa[1][(int)((Y)/16)][(int)((X)/16)]>0){
    		
    		for(int i = 0; i < 4; i ++){
    			double ang = angbase;
    			ang+=Math.PI;
    			ang+=0.6f-(1.2f*Math.random());
    			Faisca fai = new Faisca(Color.WHITE, X, Y,(float) ang,(int)( 100+(100*Math.random())), 400);
    			CanvasMAIN.ListaParticulas.add(fai);
    		}
    		
    		X = oldx;
    		Y = oldy;
    		
    		vivo = false;
    		explode();
    		return;
    	}        
        
    	
    	
    	float minfist = (12+1)*(12+1);
    	for(int i = 0; i < CanvasMAIN.ListaObjetos.size(); i ++){
    		Sprite enemy = CanvasMAIN.ListaObjetos.get(i); 
    			if(enemy==father){
    				continue;
    			}
    			//System.out.println("entro i "+i);
    			float dx = (enemy.X+12)-X;
    			float dy = (enemy.Y+16)-Y;
    			
    			dist = (dx*dx)+(dy*dy);
    			
  			
    			if(dist<minfist){
    				
    	    		for(i = 0; i < 4; i ++){
    	    			double ang = angbase;
    	    			ang+=Math.PI;
    	    			ang+=0.6f-(1.2f*Math.random());
    	    			Faisca fai = new Faisca(Color.RED, X, Y,(float) ang,(int)( 50+(100*Math.random())), 600);
    	    			CanvasMAIN.ListaParticulas.add(fai);
    	    		}    				
    				
    	    		X = oldx;
    	    		Y = oldy;
    	    		enemy.life-=50;
    	    		vivo = false;
    	    		explode();
    	    		break;
    			}
    	}
        
        //Frame = ((int)(Tempo/FrameTime))%3;
        
        if(invert){
        	VelX=-VelX;
        	VelY=-VelY;
        }
        
        double ang2 = angbase+Math.PI;
        
        for(int i = 0; i < 1; i++){
			 double ang =ang2+0.5f-(1.0f*Math.random());
			 double size = 50*Math.random();
			
			 
			//double ang =ang2;
			//double size = 1;        	
			
	        //Fumaca fum = new Fumaca((int)(X+(size*Math.cos(ang))),(int)( Y+(size*Math.sin(ang))), 1200);

			 int vx = (int)(size*Math.cos(ang));
			 int vy = (int)(size*Math.sin(ang));
			 
			 Fumaca fum = new Fumaca((int)(X+vx*0.07),(int)(Y+vy*0.07), 1200);
			 
			 fum.VelX = vx;
			 fum.VelY = vy;
			 
			 CanvasMAIN.ListaParticulas.add(fum);
        }
        
        */
	}

public void explode(){
    for(int i = 0; i < 10; i++){
		 double ang =((Math.PI*2)*Math.random());
		 double size = 70*Math.random();
		
 
		//double ang =ang2;
		//double size = 1;        	
		
       //Fumaca fum = new Fumaca((int)(X+(size*Math.cos(ang))),(int)( Y+(size*Math.sin(ang))), 1200);

		 int vx = (int)(size*Math.cos(ang));
		 int vy = (int)(size*Math.sin(ang));
		 
		 explosao fum = new explosao((int)(X+vx*0.07),(int)(Y+vy*0.07), (int)(200+(300-(size*2))));
		 
		 fum.VelX = vx;
		 fum.VelY = vy;
		 
		 fum.tipo = (int)(2*Math.random());
		 
		 CanvasMAIN.ListaParticulas.add(fum);
   }	
}

}
