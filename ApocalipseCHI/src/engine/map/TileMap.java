package engine.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.MapObject;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;

import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import engine.base.entities.GameState;
import engine.entities.Enemy;
import engine.utils.Vector2D;
import old.Constantes;


public class TileMap extends GameEntity implements GameObject {

	//public Map map;
	
	private int telaX = 0;
	private int telaY = 0;
	private int blockX = 0;
	private int blockY = 0;
	
	public ArrayList<Map> listaDeMapBlocks;
	public Map blocks[][];
	
	public int tileH = 16;
	public int tileW = 16;
	
	public int roofID = 2;
	public int wallID = 1;
	public int florID = 0;
	
	public int luz[][];
	public int pontosdeluz[][];
	BufferedImage shadowImage;
	int[] sahdowData;

	public TileMap(String name) {
		super(name);
		layer = -1;
		priority = 0;
		listaDeMapBlocks = new ArrayList<Map>();
		luz = new int[Constantes.telaH/8][Constantes.telaW/8];
		pontosdeluz = new int[32][6];
		shadowImage = new BufferedImage(Constantes.telaW,Constantes.telaH,BufferedImage.TYPE_INT_ARGB);
		sahdowData = ((DataBufferInt)shadowImage.getRaster().getDataBuffer()).getData();
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void posicionaTela(int x,int y) {
		telaX = x;
		telaY = y;
		blockX = x/1600;
		blockY = y/1600;
	}
	
	public int getTelaX() {
		return telaX;
	}
	
	public int getTelaY() {
		return telaY;
	}

	@Override
	public void render(Graphics2D g2d) {
		int blockXend = (telaX+Constantes.telaW)/1600;
		int blockYend = (telaY+Constantes.telaH)/1600;
		//Map mapselected = blocks[blockX][blockY];
		
		for(int i = blockX; i <= blockXend;i++ ) {
			for(int j = blockY; j <= blockYend;j++ ) {
				if(i>=0&&j>=0) {
					Map mapselected = blocks[i][j];
					for (MapLayer layer : mapselected.getLayers()) {
						if (layer instanceof TileLayer) {
							if(!layer.getName().equals("roof")) {
								paintTileLayer(g2d, (TileLayer) layer,i,j);
							}
						}
						if(layer instanceof ObjectGroup){
							switch(layer.getName()){
							case "collision":
								// TODO
								break;
							case "objects":
								// TODO
								break;
							case "fx":
								// TODO
								break;
							}
						}
					}
			
				}
			}
		}
		
	}
	
	public void renderRoof(Graphics2D g2d) {		
		int blockXend = (telaX+Constantes.telaW)/1600;
		int blockYend = (telaY+Constantes.telaH)/1600;
		//Map mapselected = blocks[blockX][blockY];
		
		for(int i = blockX; i <= blockXend;i++ ) {
			for(int j = blockY; j <= blockYend;j++ ) {
				if(i>=0&&j>=0) {
					Map mapselected = blocks[i][j];
					for (MapLayer layer : mapselected.getLayers()) {
						if (layer instanceof TileLayer) {
							if(layer.getName().equals("roof")) {
								paintTileLayer(g2d, (TileLayer) layer,i,j);
							}
						}
						if(layer instanceof ObjectGroup){
							switch(layer.getName()){
							case "collision":
								// TODO
								break;
							case "objects":
								// TODO
								break;
							case "fx":
								// TODO
								break;
							}
						}
					}
			
				}
			}
		}
	}
	
    public void paintTileLayer(Graphics2D g, TileLayer layer, int bx,int by) {
    			
		AffineTransform trans = g.getTransform();
		
		
        final Rectangle clip = g.getClipBounds();
        final int tileWidth = tileW;
        final int tileHeight = tileH;
        final Rectangle bounds = layer.getBounds();

        //g.translate(bounds.x * tileWidth, bounds.y * tileHeight);
        //clip.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);

        g.translate(-telaX+bx*1600, -telaY+by*1600);
        clip.translate(telaX-bx*1600, telaY-by*1600);
        
        
        //clip.height += map.getTileHeightMax();

        final int startX = Math.max(0, clip.x / tileWidth);
        final int startY = Math.max(0, clip.y / tileHeight);
        final int endX = Math.min(layer.getWidth(),
                (int) Math.ceil(clip.getMaxX() / tileWidth));
        final int endY = Math.min(layer.getHeight(),
                (int) Math.ceil(clip.getMaxY() / tileHeight));

        //System.out.println("PaintLayer "+tileWidth+" "+tileHeight+" "+bounds+" "+startX+" "+startY+" "+endX+" "+endY);
        
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                final Tile tile = layer.getTileAt(x, y);
                //System.out.println(" "+tile+" "+x+" "+y);
                if (tile == null) {
                    continue;
                }
                //System.out.println(" "+x+" "+y);
                final Image image = tile.getImage();
                if (image == null) {
                    continue;
                }

                Point drawLoc = new Point(x * tileWidth, (y + 1) * tileHeight - image.getHeight(null));
                //System.out.println(""+drawLoc.x+" "+drawLoc.y+" "+x+" "+y);
                
                // Add offset from tile layer property
                drawLoc.x += layer.getOffsetX() != null ? layer.getOffsetX() : 0;
                drawLoc.y += layer.getOffsetY() != null ? layer.getOffsetY() : 0;

                // Add offset from tileset property
                drawLoc.x += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getX() : 0;
                drawLoc.y += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getY() : 0;
                
                //System.out.println(""+drawLoc.x+" "+drawLoc.y);
                
                g.drawImage(image, drawLoc.x, drawLoc.y, null);
            }
        }

        g.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);
        g.setTransform(trans);
    }	

	@Override
	public java.util.Map<String, Component> getComponents() {
		return components;
	}

	@Override
	public int compareTo(GameObject o) {
		return 0;
	}
	
	public void addMap(Map map) {
		listaDeMapBlocks.add(map);
	}
	
	public void setMap(GameState gs) {
		components = new HashMap<>();
		blocks = new Map[30][30];
		Random rnd = new Random();
		int enemycount = 0;
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 30; j++) {
				blocks[i][j] = listaDeMapBlocks.get(rnd.nextInt(listaDeMapBlocks.size()));
				
				/*for(int l = 0; l < blocks[i][j].getLayerCount();l++ ) {
					MapLayer ml = blocks[i][j].getLayer(l);
					System.out.println("maplayer "+l+" "+ml.getClass());
				}*/
				if(blocks[i][j].getLayerCount()>=4) {
					MapLayer ml = blocks[i][j].getLayer(3);
					System.out.println("maplayer "+3+" "+ml.getClass());
					ObjectGroup og = (ObjectGroup)ml;
					for (MapObject mapObject : og) {
						System.out.println(""+mapObject.getName()+" "+mapObject.getType());
						
						if(mapObject.getType().equals("Enemy")) {
							Vector2D position = new Vector2D((float) (mapObject.getX()+j*1600), (float) (mapObject.getY()+i*1600));
							System.out.println(" "+position.x+" "+position.y);
						 	Enemy e = new Enemy(mapObject.getName()+"_"+enemycount, position, mapObject.getWidth().intValue(), mapObject.getHeight().intValue(), 0.0f);
						 	GameObject go = e;
							gs.getEntities().put(go.getName(), go);
							enemycount++;
						}
					}
				}
			}
		}
		System.out.println("enemycount "+enemycount);
	}
	
	public boolean isUnderRoof(int x,int y) {
		int bx = x/1600;
		int by = y/1600;
		if(bx>=0&&bx<30&&by>=0&&by<30) {
			Map mapselected = blocks[bx][by];
			
			TileLayer tl = (TileLayer)mapselected.getLayer(2); // RoofLayer
			
			Tile tile = tl.getTileAt((int) ((x-bx*1600)/ mapselected.getTileWidth()),(int) ((y-by*1600) / mapselected.getTileHeight()));
			
			if(tile!=null) {
				return true;
			}
			return false;
		}else {
			return false;
		}
	}
	
	public boolean colision(int x,int y) {

		int bx = x/1600;
		int by = y/1600;
		
		if(bx>=0&&bx<30&&by>=0&&by<30&&x>0&&y>0) {
			Map mapselected = blocks[bx][by];
			int tlx = (x%1600)>>4;
			int tly = (y%1600)>>4;
					
			TileLayer tl = (TileLayer)mapselected.getLayer(1); // RoofLayer
			
			//Tile tile = tl.getTileAt((int) ((x-bx*1600)/ mapselected.getTileWidth()),(int) ((y-by*1600) / mapselected.getTileHeight()));
			Tile tile = tl.getTileAtFast(tlx,tly);
			
			if(tile!=null) {
				return true;
			}
			return false;
		}else {
			//System.out.println("colidiu");
			return true;
		}
	}
	
	Color cdark = new Color(0,0,0,0.9f);
	public void prepareLights() {
		for(int i = 0; i < sahdowData.length;i++) {
			sahdowData[i] = 0xd0000000;
		}
		for(int i = 0; i < luz.length;i++) {
			for(int j = 0; j < luz[i].length;j++) {
				luz[i][j] = 0;
				for(int l = 0; l < pontosdeluz.length;l++) {
					if(pontosdeluz[l][0] == 1) {
						if(pontosdeluz[l][3]==0) {
							int dx = (j<<3)-pontosdeluz[l][1];
							int dy = (i<<3)-pontosdeluz[l][2];
							int dist = dx*dx+dy*dy;
							if(dist < pontosdeluz[l][4]*pontosdeluz[l][4])  {
								if(!raycolision(pontosdeluz[l][1]+telaX,pontosdeluz[l][2]+telaY,(j<<3)+telaX+4,(i<<3)+telaY+4)) {
									luz[i][j]+=100;
								}
							}
						}else if(pontosdeluz[l][3]==1) {
							int dx = (j<<3)-pontosdeluz[l][1];
							int dy = (i<<3)-pontosdeluz[l][2];
							int dist = dx*dx+dy*dy;
							
							double ang = Math.atan2(dy,dx);
							int distang = angularDistance((int)Math.toDegrees(ang),pontosdeluz[l][5]);
							
							if(distang<30) {
								if(dist < pontosdeluz[l][4]*pontosdeluz[l][4])  {
									if(!raycolision(pontosdeluz[l][1]+telaX,pontosdeluz[l][2]+telaY,(j<<3)+telaX+4,(i<<3)+telaY+4)) {
										luz[i][j]+=100;
									}
								}
							}
							
						}
					}
				}
				/*if(luz[i][j]>0) {
					for(int ly = i<<3; ly < ((i<<3)+8); ly++) {
						int startx = (j<<3)+ly*Constantes.telaW;
						for(int lx = 0;lx < 8; lx++) {
							sahdowData[startx] = 0;
							startx++;
						}
					}
				}else {
				}*/
			}	
		}
		for(int i = 0; i < luz.length;i++) {
			for(int j = 0; j < luz[i].length;j++) {
				float soma = 0;
				int quant = 0;
				for(int ki = i-1; ki < i+2;ki++) {
					for(int kj = j-1; kj < j+2;kj++) {
						if(ki>=0&&kj>=0&&ki<luz.length&&kj<luz[i].length) {
							soma+=luz[ki][kj];
							quant++;
						}
					}
				}
				luz[i][j] = Math.min(100,(int)(soma/quant));
			}
		}
		
		for(int i = 0; i < luz.length;i++) {
			for(int j = 0; j < luz[i].length;j++) {
				if(luz[i][j]>0) {
					int mascara = ((100-luz[i][j])<<24)&0xff000000;
					for(int ly = i<<3; ly < ((i<<3)+8); ly++) {
						int startx = (j<<3)+ly*Constantes.telaW;
						for(int lx = 0;lx < 8; lx++) {
							sahdowData[startx] = mascara;
							startx++;
						}
					}
				}else {
				}
			}
		}
		
	}
	//TODO nao funciona tenho que arrumar
	public void prepareLightsMultthread() {
		for(int i = 0; i < sahdowData.length;i++) {
			sahdowData[i] = 0xe0000000;
		}
		
		Thread t[] = new Thread[luz[0].length];
		for(int i = 0; i < luz.length;i++) {
			for(int j = 0; j < luz[i].length;j++) {
				final int ki = i;
				final int kj = j;
				t[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						luz[ki][kj] = 0;
						for(int l = 0; l < pontosdeluz.length;l++) {
							if(pontosdeluz[l][0] == 1) {
								int dx = (kj<<3)-pontosdeluz[l][1];
								int dy = (ki<<3)-pontosdeluz[l][2];
								int dist = dx*dx+dy*dy;
								if(dist < pontosdeluz[l][4]*pontosdeluz[l][4])  {
									if(!raycolision(pontosdeluz[l][1]+telaX,pontosdeluz[l][2]+telaY,(kj<<3)+telaX+4,(ki<<3)+telaY+4)) {
										luz[ki][kj]+=100;
									}
								}
							}
						}
						if(luz[ki][kj]>0) {
							for(int ly = ki<<3; ly < ((ki<<3)+8); ly++) {
								int startx = (kj<<3)+ly*Constantes.telaW;
								for(int lx = 0;lx < 8; lx++) {
									sahdowData[startx] = 0;
									startx++;
								}
							}
						}else {
						}
					}
				});
			}
			for(int j = 0; j < luz[i].length;j++) {
				t[i].start();
			}
			for(int j = 0; j < luz[i].length;j++) {
				try {
					t[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void renderLigth(Graphics2D g2d) {
		g2d.drawImage(shadowImage, 0,0,null);
	}
	
	public boolean raycolision(int x1,int y1,int x2,int y2) {
		float dx = x2-x1;
		float dy = y2-y1;
		int passos = (int)((Math.abs(dx)+Math.abs(dy))/8.0);
		dx = dx/passos;
		dy = dy/passos;
		float accx = x1;
		float accy = y1;
		for(int i = 0; i <= passos;i++) {
			if(colision((int)accx, (int)accy)) {
				return true;
			}
			accx+=dx;
			accy+=dy;
			
		}
		//System.out.println(" "+accx+" "+accy+" "+x1+" "+y1+" "+x2+" "+y2+" "+dx+" "+dy);
		return false;
	}

    public static int angularDistance(int alpha, int beta) {
        int phi = Math.abs(beta - alpha) % 360;       // This is either the distance or 360 - distance
        int distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }
}
