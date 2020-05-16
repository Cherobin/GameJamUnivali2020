package old;
import java.awt.Graphics2D;

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

public abstract class Objeto {
    
    abstract public void DesenhaSe(Graphics2D dbg,int Xmundo,int Ymundo);

    abstract public void SimulaSe(float diftime);    
    
}
