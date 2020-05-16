import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;

/*
 * Created on 02/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */

/**
 * @author Palm Soft
 import java.awt.Graphics2D;
import java.awt.Image;
import java.io.*;

/*
 * Created on 02/09/2005
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
public class TileMap {
    
    public Image TileSet = null;
    
    int MapX;
    int MapY;
    int NTileX,NTileY;
    int Largura = 60;
    int Altura = 50;
    int TilePLinhaTileset;
	int numLayers;
    
    int [][][]mapa;
///	int [][]mapa2; 
    
    int colision[];
            		
    public TileMap(Image tileset,int tilestelaX,int tilestelaY){
        NTileX = tilestelaX;
        NTileY = tilestelaY;
        TileSet = tileset;
        MapX = 0;
        MapY = 0;
        TilePLinhaTileset = TileSet.getWidth(null) >>4;
    }
    
    public void AbreMapa(String nomemapa){
            	
	try{
    		InputStream In = getClass().getResourceAsStream(nomemapa);
    		DataInputStream data = new DataInputStream(In); 
    	
    		int Versao = data.readInt(); // lê Versao
        	Largura = ReadCInt(data);    // lê Largura
        	Altura = ReadCInt(data);	// lê Largura
        	int ltilex =  ReadCInt(data);// lê Larg Tile
        	int ltiley =  ReadCInt(data);// lê Altura Tile
        	byte nome[] = new byte[32]; 
        	data.read(nome,0,32);       // lê Nome Tilemap
        	data.read(nome,0,32);       // lê Nome TileSet
        	numLayers =  ReadCInt(data);// lê numero de Layers
        	int numTiles =  ReadCInt(data);// lê numero de Tiles
            int BytesPorTiles =  ReadCInt(data); // lê numero de bytes por tile;
            int vago1 =  ReadCInt(data); // lê vago;
            int vago2 =  ReadCInt(data); // lê vago;            
            
        	mapa = new int[numLayers][Largura][Altura];
        	//mapa2 = new int[Largura][Altura];
        	
        	colision = new int[numTiles];
        	
        	if(BytesPorTiles==1){
                for(int l =0; l < numLayers;l++){
	        	    for(int j = 0; j < Altura; j++){            
	                    for(int i = 0; i < Largura; i++){
	                    	int dado;
	                    	
	                    	int b1 = data.readByte();
	                    	int b2 = data.readByte();            	
	                    	
	                    	dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8);            	
	                    	
	                    	mapa[l][j][i] = dado;	            	
	                    	//System.out.println(" "+mapa[j][i]);            	
	                    }
	                }
                }
   	    
                
        	}else if(BytesPorTiles==2){
                for(int l =0; l < numLayers;l++){
	        	    for(int j = 0; j < Altura; j++){            
	                    for(int i = 0; i < Largura; i++){
	                    	int dado;
	                    	
	                    	int b1 = data.readByte();
	                    	int b2 = data.readByte();            	
	                    	int b3 = data.readByte(); 
	                    	int b4 = data.readByte(); 
	                    	
	                    	dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8)|(((int)b3&0x00ff)<<16)|(((int)b4&0x00ff)<<24);            	
	                    	
	                    	mapa[l][j][i] = dado;	            	
	                    	//System.out.println(" "+mapa[j][i]);            	
	                    }
	                }
                }        		

        	}else{
                for(int l =0; l < numLayers;l++){
	        	    for(int j = 0; j < Altura; j++){            
	                    for(int i = 0; i < Largura; i++){
	                    	int dado;
	                    	
	                    	int b1 = data.readByte();            	
	                    	
	                    	dado = ((int)b1&0x00ff);            	
	                    	
	                    	mapa[l][j][i] = dado;	            	
	                    	//System.out.println(" "+mapa[j][i]);            	
	                    }
	                }
                }         		

        	}
  		

        for(int i = 0; i < numTiles; i++){
        	int b1 = data.readByte();            	
        	
        	colision[i] = ((int)b1&0x00ff);
        	
        	System.out.println(" i "+i+" - "+colision[i]);
        }
        
       	data.close();
    		
    	In.close();
    		
	    }//fim try
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage()+ "  abreaMapaPau!!!");
	    }    		
		    	
    	
    }
   
    
    public void DesenhaSe(Graphics2D dbg){
    	int offx = MapX&0x0f; 
    	int offy = MapY&0x0f;
    	int somax,somay;
    	if(offx>0){
    		somax = 1;
    	}else{
    		somax = 0;
    	}
    	if(offy>0){
    		somay = 1;
    	}else{
    		somay = 0;
    	}
        for(int l =0; l < numLayers;l++){
        	if(l==0){
		        for(int j = 0; j < NTileY + somay; j++){            
		            for(int i = 0; i < NTileX + somax; i++){
		            	
		                int tilex = (mapa[l][j+(MapY>>4)][i+(MapX>>4)]%TilePLinhaTileset)<<4;
		                int tiley = (mapa[l][j+(MapY>>4)][i+(MapX>>4)]/TilePLinhaTileset)<<4;
		                dbg.drawImage(TileSet,(i<<4)-offx,(j<<4)-offy,(i<<4)+16-offx,(j<<4)+16-offy,tilex,tiley,tilex+16,tiley+16,null);
		                
		            }
		        }
        	}else{
		        for(int j = 0; j < NTileY + somay; j++){            
		            for(int i = 0; i < NTileX + somax; i++){
		            	
		                int tilex = (mapa[l][j+(MapY>>4)][i+(MapX>>4)]%TilePLinhaTileset)<<4;
		                int tiley = (mapa[l][j+(MapY>>4)][i+(MapX>>4)]/TilePLinhaTileset)<<4;
		                if((tilex == 0)&&(tiley==0)){
		                	
		                }else{
		                	dbg.drawImage(TileSet,(i<<4)-offx,(j<<4)-offy,(i<<4)+16-offx,(j<<4)+16-offy,tilex,tiley,tilex+16,tiley+16,null);
		                }
		                
		            }
		        }        		
        	}
        }
        /*for(int j = 0; j < NTileY + somay; j++){            
            for(int i = 0; i < NTileX + somax; i++){
            	
                int tilex = (mapa2[j+(MapY>>4)][i+(MapX>>4)]%TilePLinhaTileset)<<4;
                int tiley = (mapa2[j+(MapY>>4)][i+(MapX>>4)]/TilePLinhaTileset)<<4;
                dbg.drawImage(TileSet,(i<<4)-offx,(j<<4)-offy,(i<<4)+16-offx,(j<<4)+16-offy,tilex,tiley,tilex+16,tiley+16,null);
                
            }
        } */       
    }
    
    public void Posiciona(int x,int y){
    	int X = x>>4;
    	int Y = y>>4;
    	
        if(X < 0){
            MapX = 0;
        }else if(X >= (Largura-NTileX)){
            MapX = ((Largura-NTileX))<<4;
        }else{
            MapX = x;
            System.out.println(MapX);
        }
        
        if(Y < 0){
            MapY = 0;
        }else if(Y >= (Altura-NTileY)){
            MapY = ((Altura-NTileY))<<4;
        }else{
            MapY = y;
        }        

    }
    
    public int ReadCInt(DataInputStream data) throws IOException{
        int dado;
    	int b1 = data.readByte();
    	int b2 = data.readByte(); 
    	int b3 = data.readByte(); 
    	int b4 = data.readByte();                     	
    	
    	return dado = ((int)b1&0x00ff)|(((int)b2&0x00ff)<<8)|(((int)b3&0x00ff)<<16)|(((int)b4&0x00ff)<<24);            	    	
    }
}