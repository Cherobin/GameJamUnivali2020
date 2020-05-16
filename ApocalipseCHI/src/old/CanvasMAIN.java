package old;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import engine.core.GamePanel;
import engine.core.MyCanvas;


public class CanvasMAIN extends MyCanvas{

private boolean gameOver = false; // for game termination


public Image Hero;
public Image Monsters;

//private BufferedImage ImageBuffered;

Random rnd =  new Random();

float x,y;
int vel;
boolean AUP,ADOWN,ALEFT,ARIGHT,FIRE;
int mouseX,mouseY;


//NossaImagem minhaImage1;

Sprite Personagem; 
public static ArrayList<Sprite> ListaObjetos; 
public static ArrayList<Fireball> FireballList;
public static ArrayList<Particula> ListaParticulas;

public static BufferedImage fumaca;
public static BufferedImage fumaca2;
public static BufferedImage fumaca3;
public static BufferedImage fumaca4;
public static BufferedImage fumaca5;

public static BufferedImage ExplosaoT1;
public static BufferedImage ExplosaoT2;
public static BufferedImage ExplosaoT3;
public static BufferedImage ExplosaoFT3;

public CanvasMAIN()
{
		
	x = 60;
	y = 60;
	vel = 200;
	AUP = false;
	ADOWN = false;
	ALEFT = false;	
	ARIGHT = false;
	FIRE = false;
	
	mouseX = 0;
	mouseY = 0;

	 //   Monsters = LoadImage("Monstros.png");
	
	//    Hero = LoadImage("Chara1O.png");
  
	    
		/*

	    fumaca = LoadImage("fumaca.png");
	    
	    fumaca2 = LoadImage("fumaca2.png");
	    
	    fumaca3 = LoadImage("fumaca3.png");	    

	    fumaca4 = LoadImage("fumaca4.png");	
	    
	    fumaca5 = LoadImage("fumaca5.png");	
	    
	    ExplosaoT1 = LoadImage("ExplosaoT1.png");
	    
	    ExplosaoT2 = LoadImage("ExplosaoT2.png");
	    
	    ExplosaoT3 = LoadImage("ExplosaoT3.png");	 
	    
	    ExplosaoFT3 = LoadImage("ExplosaoFT2.png");	
	
	Personagem = new Sprite(Hero,(int)x,(int)y,0);
	Personagem.FrameTime = 200;
	
	ListaObjetos = new ArrayList<Sprite>();
	FireballList = new ArrayList<Fireball>();
	ListaParticulas = new ArrayList<Particula>();
	
	Sprite UmMonstro;
	
	for(int i = 0; i < 10;i++){
	    UmMonstro = new Sprite(Monsters,rnd.nextInt(500),rnd.nextInt(400),rnd.nextInt(8));
	    UmMonstro.FrameTime = 100 + rnd.nextInt(400);
	    UmMonstro.VelX = -100 + rnd.nextInt(200);
	    UmMonstro.VelY = -100 + rnd.nextInt(200);
	    UmMonstro.Anim = rnd.nextInt(4);
	    
	    ListaObjetos.add(UmMonstro);
	    
	}
	
for(int i = 0; i < 10;i++){
	    UmMonstro = new InimigoR(Monsters,rnd.nextInt(500),rnd.nextInt(400),rnd.nextInt(8));
	    UmMonstro.FrameTime = 100 + rnd.nextInt(400);
	    UmMonstro.VelX = -100 + rnd.nextInt(200);
	    UmMonstro.VelY = -100 + rnd.nextInt(200);
	    UmMonstro.Anim = rnd.nextInt(4);
	    
	    ListaObjetos.add(UmMonstro);
	    
	}*/	
	//ListaObjetos.add(Personagem);
	
} // end of GamePanel()

int firetimer = 100;
@Override
	public void update(float diffTime) {
		// TODO Auto-generated method stub
	float oldx = x;
	float oldy = y;
	/*
	
	firetimer+=diffTime;
	
	TESTEMAPA.update(diffTime);
	
	if(ALEFT){
		//x += -((vel*DiffTime)/1000.0f);
		Personagem.Anim = 3; 
		Personagem.VelX = -vel; 
	}else if(ARIGHT){
		//x += ((vel*DiffTime)/1000.0f);
		Personagem.Anim = 1;
		Personagem.VelX = +vel; 
	}else{
		Personagem.VelX = 0;
	}
	
	if(AUP){
		//y += -((vel*DiffTime)/1000.0f);
		Personagem.Anim = 0;
		Personagem.VelY = -vel; 
	}else if(ADOWN){
		//y += ((vel*DiffTime)/1000.0f);
		Personagem.Anim = 2;
		Personagem.VelY = vel; 
	}else{
		Personagem.VelY = 0;
	}
    /*
	if(FIRE==true){
		if(firetimer>150){
			Fireball fireball = new Fireball(Personagem);
		
			
				float difx = (mouseX+MAPA.MapX) - (Personagem.X+12);
				float dify = (mouseY+MAPA.MapY) - (Personagem.Y+16);
				
				float ang = (float)Math.atan2(dify, difx);
				
				float sen = (float)Math.sin(ang);
				float cos = (float)Math.cos(ang);
				
				fireball.VelY = (int)(sen*400);
				fireball.VelX = (int)(cos*400);
				
				fireball.X = (Personagem.X+12)+12*cos;
				fireball.Y = (Personagem.Y+16)+12*sen;	
					
				
				FireballList.add(fireball);
				firetimer = 0;
			}
		
	}

	
	for(int i  = 0; i < ListaObjetos.size();i++){
	    ((Objeto)ListaObjetos.get(i)).SimulaSe(diffTime);
	    if(ListaObjetos.get(i).life<=0){
	    	ListaObjetos.remove(i);
	    }
	}
	
	for(int i  = 0; i < FireballList.size();i++){
	    ((Objeto)FireballList.get(i)).SimulaSe(diffTime);
	    if(FireballList.get(i).vivo==false){
	    	FireballList.remove(i);
	    }
	}	
	
	for(int i  = 0; i < ListaParticulas.size();i++){
	    ((Objeto)ListaParticulas.get(i)).SimulaSe(diffTime);
	    if(ListaParticulas.get(i).vivo==false){
	    	ListaParticulas.remove(i);
	    }
	}	
	*/
	//MAPA.Posiciona(((int)Personagem.X-320),((int)Personagem.Y-240));
}

@Override
	public void render(Graphics2D dbg) {
	
	dbg.setColor(Color.white);
	dbg.fillRect(0, 0, Constantes.telaW, Constantes.telaH);
	 
			// draw game elements
	dbg.setColor(Color.white);
	dbg.fillRect(0, 0, 140, 15);
	dbg.setColor(Color.BLACK);	
	dbg.drawString("FPS: "+GamePanel.FPS, 10, 10);	

		
	dbg.setColor(Color.BLUE);	
	
	/*Personagem.DesenhaSe(dbg);
	for(int i  = 0; i < ListaObjetos.size();i++){
	    ((Objeto)ListaObjetos.elementAt(i)).DesenhaSe(dbg);
	}
	//Personagem.DesenhaSeCorrigido(dbg,MAPA.MapX, MAPA.MapY);
	for(int i  = 0; i < ListaObjetos.size();i++){
	    ((Sprite)ListaObjetos.get(i)).DesenhaSe(dbg,MAPA.MapX, MAPA.MapY);
	}
	
	for(int i  = 0; i < FireballList.size();i++){
	    FireballList.get(i).DesenhaSe(dbg,MAPA.MapX, MAPA.MapY);
	}	
	
	for(int i  = 0; i < ListaParticulas.size();i++){
		ListaParticulas.get(i).DesenhaSe(dbg,MAPA.MapX, MAPA.MapY);
	}	
	*/	
	if (gameOver)
		dbg.drawString("Fim", 0, 0);
} // end of gameRender()

public BufferedImage LoadImage(String filename){
    BufferedImage imtmp;
    try {
        imtmp = ImageIO.read( getClass().getResource(filename) );
    } catch (IOException e) {
        imtmp = null;
        System.out.println("PAU AO CARREGAR IMAGEN ---> "+filename);
        e.printStackTrace();
    }
    BufferedImage imgfinal = new BufferedImage(imtmp.getWidth(),imtmp.getHeight(),BufferedImage.TYPE_INT_ARGB);
    imgfinal.getGraphics().drawImage(imtmp, 0, 0, null); 
    imtmp = null;
    return imgfinal;
}


@Override
public void keyPressed(KeyEvent e) {
	int keyCode = e.getKeyCode();
	
	if ((keyCode == KeyEvent.VK_ESCAPE) ||
		(keyCode == KeyEvent.VK_Q) ||
		(keyCode == KeyEvent.VK_END) ||
		((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
			//running = false;
	}
	
	
	if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
		ALEFT = true;			
	}else{
		ALEFT = false;	
	}
	if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
		ARIGHT = true;			
	}else{
		ARIGHT = false;		
	}
	if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
		AUP = true;			
	}else{
		AUP = false;	
	}
	if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
		ADOWN = true;			
	}else{
		ADOWN = false;		
	}
	
	if(keyCode == KeyEvent.VK_SPACE){
		FIRE = true;
	}
}


@Override
public void keyReleased(KeyEvent e) {
	int keyCode = e.getKeyCode();
	
	if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
		ALEFT = false;			
	}
	if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
		ARIGHT = false;			
	}
	if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
	    AUP = false;			
	}
	if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
	    ADOWN = false;			
	}
	
	if(keyCode == KeyEvent.VK_SPACE){
		FIRE = false;
	}	
}


@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	mouseX = e.getX();
	mouseY = e.getY();
}


@Override
public void mouseDragged(MouseEvent e) {
	mouseX = e.getX();
	mouseY = e.getY();
}


@Override
public void mouseReleased(MouseEvent arg0) {
	if(arg0.getButton() == 1){
		FIRE = false;
	}
}


@Override
public void mousePressed(MouseEvent arg0) {
	if(arg0.getButton() == 1){
		FIRE = true;
	}
}


@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}


@Override
public void mouseWheelMoved(MouseWheelEvent arg0) {
	// TODO Auto-generated method stub
	
}


} // end of GamePanel class

