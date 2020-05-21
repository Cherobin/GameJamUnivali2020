package engine.map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import org.mapeditor.core.Map;
import org.mapeditor.core.ObjectGroup;
import org.mapeditor.core.Tile;
import org.mapeditor.core.TileLayer;
import org.mapeditor.view.MapRenderer;

public class TileMapRenderer implements MapRenderer {

	protected Map map;

	public TileMapRenderer(Map map) { 
		this.map = map;
	}
	
	public void paintObjectLayer(Graphics2D g, ObjectGroup layer) {
		final Rectangle clip = g.getClipBounds();
		final int tileWidth = map.getTileWidth();
		final int tileHeight = map.getTileHeight();
		final Rectangle bounds = layer.getBounds();

		g.translate(bounds.x * tileWidth, bounds.y * tileHeight);
		clip.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);

		g.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);

	}

	@Override
	public Dimension getMapSize() {
		return new Dimension(map.getWidth() * map.getTileWidth(), map.getHeight() * map.getTileHeight());
	}

	@Override
	public void paintTileLayer(Graphics2D g, TileLayer layer) {
		final Rectangle clip = g.getClipBounds();
		final int tileWidth = map.getTileWidth();
		final int tileHeight = map.getTileHeight();
		final Rectangle bounds = layer.getBounds();

		g.translate(bounds.x * tileWidth, bounds.y * tileHeight);
		clip.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);

		clip.height += map.getTileHeightMax();

		final int startX = Math.max(0, clip.x / tileWidth);
		final int startY = Math.max(0, clip.y / tileHeight);
		final int endX = Math.min(layer.getWidth(), (int) Math.ceil(clip.getMaxX() / tileWidth));
		final int endY = Math.min(layer.getHeight(), (int) Math.ceil(clip.getMaxY() / tileHeight));

		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				final Tile tile = layer.getTileAt(x, y);
				if (tile == null) {
					continue;
				}
				final Image image = tile.getImage();
				if (image == null) {
					continue;
				}

				g.drawImage(image, x * tileWidth, (y + 1) * tileHeight - image.getHeight(null), null);
			}
		}

		g.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);
	}


	@Override
	public void paintObjectGroup(Graphics2D g, ObjectGroup group) {
		// TODO Auto-generated method stub
		
	}

}
