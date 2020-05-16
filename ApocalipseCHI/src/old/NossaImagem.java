package old;
/*
 * Created on 11/08/2005
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
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class NossaImagem {
	
	public int W,H,Cores;
	public int paleta[];
	
	public int dados[];
	
	//public BufferedImage PlotImage;
	
	
	public NossaImagem(){		
	}
	
	public void LoadImagePaletizada16(String MapaName){
    	try{
    		InputStream In = getClass().getResourceAsStream(MapaName);
    		
    		byte readbuff[] = new byte[4];
    		
    		In.read(readbuff,0,1);
    		
    		Cores = readbuff[0];
    		
    		In.read(readbuff,0,2);
    		//W = (readbuff[0]<<8) | (readbuff[1]);
    		W = ((0x00ff&((int)readbuff[1]))<<8) | (0x00ff&((int)readbuff[0]));
    		
    		In.read(readbuff,0,2);    		
    		H = ((0x00ff&((int)readbuff[1]))<<8) | (0x00ff&((int)readbuff[0]));
    		
    		System.out.println(" W "+W+" H "+H+" Cores " + Cores);
    		//Read Palet
    			paleta = new int[Cores];
    			int acor = 0;
    			for(int i = 0; i < Cores;i++){
    				In.read(readbuff,0,2);
    				acor = ((0x000f&((int)readbuff[1]))<<20)|((0x00f0&((int)readbuff[0]))<<8)|((0x000f&((int)readbuff[0]))<<4)|0xff000000;
    				
    				paleta[i] = acor;
    			}
    		//Read Pixels    			
    			//PlotImage = (BufferedImage) GamePanel.instance.createImage(W, H);
    			//PlotImage = new BufferedImage(W,H,BufferedImage.TYPE_4BYTE_ABGR);
    			dados = new int[W*H];
    			//PlotImage.setRGB(0,0,100);
				int paletpix;
    			for(int j = 0; j < H;j++){
    				for(int i = 0; i < (W>>1);i++){
    					In.read(readbuff,0,1);
    					paletpix = (((int)readbuff[0])&0x000f); 
    					//PlotImage.setRGB(i<<1,j,paleta[paletpix]);
    					dados[(i<<1) + (j*W)] = paleta[paletpix];
    					paletpix = (((int)readbuff[0])&0x00f0)>>4;
    					dados[((i<<1)+1) + (j*W)] = paleta[paletpix];
    					//PlotImage.setRGB((i<<1)+1,j,paleta[paletpix]);    					
    				}    			
    			}
    			In.close();
    		
	    }//fim try
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage()+ "  abreaMapaPau!!!");
	    }    		
		
	}
	
	public void LoadImagePaletizada256(String MapaName){
    	try{
    		InputStream In = getClass().getResourceAsStream(MapaName);
    		
    		byte readbuff[] = new byte[4];
    		
    		In.read(readbuff,0,1);
    		
    		Cores = ((int)readbuff[0])&0x00ff;
    		
    		In.read(readbuff,0,2);
    		//W = (readbuff[0]<<8) | (readbuff[1]);
    		W = ((0x00ff&((int)readbuff[1]))<<8) | (0x00ff&((int)readbuff[0]));
    		
    		In.read(readbuff,0,2);    		
    		H = ((0x00ff&((int)readbuff[1]))<<8) | (0x00ff&((int)readbuff[0]));
    		
    		System.out.println(" W "+W+" H "+H+" Cores " + Cores);
    		//Read Palet
    			paleta = new int[Cores];
    			int acor = 0;
    			for(int i = 0; i < Cores;i++){
    				In.read(readbuff,0,2);
    				acor = ((0x000f&((int)readbuff[1]))<<20)|((0x00f0&((int)readbuff[0]))<<8)|((0x000f&((int)readbuff[0]))<<4)|0xff000000;
    				
    				paleta[i] = acor;
    			}
    		//Read Pixels    			
    			//PlotImage = (BufferedImage) GamePanel.instance.createImage(W, H);
    			//PlotImage = new BufferedImage(W,H,BufferedImage.TYPE_4BYTE_ABGR);
    			//PlotImage.setRGB(0,0,100);
    			
    			dados = new int[W*H];
    			
				int paletpix;
    			for(int j = 0; j < H;j++){
    				for(int i = 0; i < W;i++){
    					In.read(readbuff,0,1);
    					paletpix = (((int)readbuff[0])&0x00ff); 
    					//PlotImage.setRGB(i,j,paleta[paletpix]);
    					dados[i + (j*W)] = paleta[paletpix];
    				}    			
    			}
    			In.close();
    		
	    }//fim try
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage()+ "  abreaMapaPau!!!");
	    }    		
		
	}
	
	public void LoadImageBMP(String MapaName){
    	try{
    		InputStream In = getClass().getResourceAsStream(MapaName);
    		
    		byte readbuff[] = new byte[54];
    		
    		In.read(readbuff,0,54);
    		
    		Cores = 16777216;
    		
    		W = ((0x00ff&((int)readbuff[21]))<<24)|((0x00ff&((int)readbuff[20]))<<16)|((0x00ff&((int)readbuff[19]))<<8) | (0x00ff&((int)readbuff[18]));
    		  		
    		H = ((0x00ff&((int)readbuff[25]))<<24)|((0x00ff&((int)readbuff[24]))<<16)|((0x00ff&((int)readbuff[23]))<<8) | (0x00ff&((int)readbuff[22]));
    		
    		System.out.println(" W "+W+" H "+H+" Cores " + Cores);
    		
    		//Read Pixels    			
    			//PlotImage = (BufferedImage) GamePanel.instance.createImage(W, H);
    			//PlotImage = new BufferedImage(W,H,BufferedImage.TYPE_4BYTE_ABGR);
			    dados = new int[W*H];    		
    			//PlotImage.setRGB(0,0,100);
				int corpix;
    			for(int j = 0; j < H;j++){
    				for(int i = 0; i < W;i++){
    					In.read(readbuff,0,3);
    					corpix = 0xff000000|((((int)readbuff[2])&0x00ff)<<16)|((((int)readbuff[1])&0x00ff)<<8)|((((int)readbuff[0])&0x00ff)); 
    					//PlotImage.setRGB(i,(H-1)-j,corpix);  
    					dados[i+(((H-1)-j)*W)] = corpix;
    				}    	
    				if(((W*3)&0x03)>0){
    					In.read(readbuff,0,((W*3)&0x03));
    				}
    			}
    			In.close();
    		
	    }//fim try
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage()+ "  abreaMapaPau!!!");
	    }    		
		
	}
	
	public void DesanhaImagem(BufferedImage Buffer,int X,int Y){
	    int tamBX = Buffer.getWidth(); 
	    int tamBY = Buffer.getHeight();

	    int lini = 0;
	    int lfim  = H;
	    if(Y<0){
	        lini=-Y;
	    }
	    if((Y+H)>=tamBY){
	        lfim = tamBY-Y;
	    }
	    
	    int cini = 0;
	    int cfim  = W;
	    if(X<0){
	        cini=-X;
	    }
	    if((X+W)>=tamBX){
	        cfim = tamBX-X;
	    }	    
	    
	    for(int j = lini; j < lfim; j++){
		    for(int i = cini; i < cfim; i++){
		            Buffer.setRGB(i+X,j+Y,dados[i+(j*W)]);
		    }
	    }
	    	    
	}
	
	public void DesanhaImagemComTransparencia(BufferedImage Buffer,int X,int Y,int CordeTransparencia){
	    
	    int tamBX = Buffer.getWidth(); 
	    int tamBY = Buffer.getHeight();
	    
	    int cor;
	    
	    int lini = 0;
	    int lfim  = H;
	    if(Y<0){
	        lini=-Y;
	    }
	    if((Y+H)>=tamBY){
	        lfim = tamBY-Y;
	    }
	    
	    int cini = 0;
	    int cfim  = W;
	    if(X<0){
	        cini=-X;
	    }
	    if((X+W)>=tamBX){
	        cfim = tamBX-X;
	    }
	    
	    int poslinha = lini*W;
	    int pospix;
	    int posX = X+cini;
	    int posY = Y+lini;
	    
	    for(int j = lini; j < lfim; j++){
	        pospix = poslinha+cini;
		    for(int i = cini; i < cfim; i++){
	            cor = dados[pospix];	            
		        if(cor != CordeTransparencia){
		            Buffer.setRGB(posX,posY,cor);
		        }
		        pospix++;
		        posX++;
		    }
		    posY++;
		    posX = X+cini; 
		    poslinha +=W;
	    }	    
	    	    
	}	

	public void DesanhaImageRect(BufferedImage Buffer,int X,int Y,int CordeTransparencia,int SX,int SY,int SW,int SH){
	    
	    
	    int tamBX = Buffer.getWidth(); 
	    int tamBY = Buffer.getHeight();
	    
	    int cor;
	    
	    if(SX<0){
	        SX = 0;
	    }
	    if(SY<0){
	        SY = 0;
	    }	
	    
	    if(SW>W){
	        SW = W;
	    }
	    
	    if(SH>H){
	        SH = H;
	    }	    
	    
	    int lini = SY;
	    int lfim  = SH;
	    if(Y<0){
	        lini=SY-Y;
	    }
	    if((Y+SH)>=tamBY){
	        lfim = tamBY-Y;
	    }
	    
	    int cini = 0;
	    int cfim  = SW;
	    if(X<0){
	        cini=SX-X;
	    }
	    if((X+SW)>=tamBX){
	        cfim = tamBX-X;
	    }
	    
	    
	    int poslinha = lini*W;
	    int pospix;
	    int posX = X+cini;
	    int posY = Y+lini;
	    
	    for(int j = lini; j < lfim; j++){
	        pospix = poslinha+cini;
		    for(int i = cini; i < cfim; i++){
	            cor = dados[pospix];	            
		        if(cor != CordeTransparencia){
		            Buffer.setRGB(posX,posY,cor);
		        }
		        pospix++;
		        posX++;
		    }
		    posY++;
		    posX = X+cini; 
		    poslinha +=W;
	    }
	}	    
	    
	
}
