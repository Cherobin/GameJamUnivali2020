package engine.map;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.view.OrthogonalRenderer;

import engine.base.entities.Component;
import engine.base.entities.GameEntity;
import engine.base.entities.GameObject;
import javafx.scene.transform.Affine;
import old.Constantes;


public class TileMap extends GameEntity implements GameObject {

	public Map map;
	
	private int telaX = 0;
	private int telaY = 0;
	private int blockX = 0;
	private int blockY = 0;
	
	public Map blocks[][];

	public TileMap(String name) {
		super(name);
		layer = -1;
		priority = 0;
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void update(float diffTime) {

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
        final int tileWidth = map.getTileWidth();
        final int tileHeight = map.getTileHeight();
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
 
	public Map getMap() {
		return map;
	}
	
	public void setMap(Map map) {
		this.map = map;
		components = new HashMap<>();
		blocks = new Map[30][30];
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 30; j++) {
				blocks[i][j] = map;
			}
		}
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
		/*TileLayer tl = (TileLayer)map.getLayer(1); // Wall Layer
		
		Tile tile = tl.getTileAt((int) (x/ map.getTileWidth()),(int) (y / map.getTileHeight()));
		
		if(tile!=null) {
			return true;
		}
		
		return false;*/
		int bx = x/1600;
		int by = y/1600;
		
		if(bx>=0&&bx<30&&by>=0&&by<30&&x>0&&y>0) {
			Map mapselected = blocks[bx][by];
			
			TileLayer tl = (TileLayer)mapselected.getLayer(1); // RoofLayer
			
			Tile tile = tl.getTileAt((int) ((x-bx*1600)/ mapselected.getTileWidth()),(int) ((y-by*1600) / mapselected.getTileHeight()));
			
			if(tile!=null) {
				return true;
			}
			return false;
		}else {
			//System.out.println("colidiu");
			return true;
		}
	}

}
