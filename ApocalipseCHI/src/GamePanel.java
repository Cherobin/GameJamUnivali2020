import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class GamePanel extends JPanel implements Runnable
{
private static final int PWIDTH = 640; // size of panel
private static final int PHEIGHT = 480;
private Thread animator; // for the animation
private boolean running = false; // stops the animation
private boolean gameOver = false; // for game termination

private Graphics2D dbg;
private Image dbImage = null;

public Image Hero;
public Image Monsters;

//private BufferedImage ImageBuffered;

int FPS,SFPS;
long SegundoAtual = 0;
long NovoSegundo = 0;



Random rnd =  new Random();

float x,y;
int vel;
boolean AUP,ADOWN,ALEFT,ARIGHT,FIRE;


//NossaImagem minhaImage1;

Sprite Personagem;
public static TileMap MAPA;
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

public GamePanel()
{
	
	setBackground(Color.white); // white background
	setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

	// create game components
	setFocusable(true);

	requestFocus(); // JPanel now receives key events
	
	// Adiciona um Key Listner
	
	
	addKeyListener( new KeyAdapter() {
	// listen for esc, q, end, ctrl-c
		public void keyPressed(KeyEvent e)
			{ 
				TratadorDeTeclado(e);
			}
		public void keyReleased(KeyEvent e)
			{ 
				TratadorDeTecladoReleased(e);
			}		
	});
	
	
	addMouseListener( new MouseAdapter() {
		public void mousePressed(MouseEvent e)
		{ TratadorDoMouseDown(e); }
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			TratadorDoMouseUp(e);
		}
	});	


	
	x = 60;
	y = 60;
	vel = 200;
	AUP = false;
	ADOWN = false;
	ALEFT = false;	
	ARIGHT = false;
	FIRE = false;
		

	    Monsters = LoadImage("Monstros.png");
	
	    Hero = LoadImage("Chara1O.png");

	    Image tileset = LoadImage("Bridge.png");
	    MAPA = new TileMap(tileset,40,30); 
	    MAPA.AbreMapa("Mapa01.map");

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
	
	/*for(int i = 0; i < 10;i++){
	    UmMonstro = new InimigoR(Monsters,rnd.nextInt(500),rnd.nextInt(400),rnd.nextInt(8));
	    UmMonstro.FrameTime = 100 + rnd.nextInt(400);
	    UmMonstro.VelX = -100 + rnd.nextInt(200);
	    UmMonstro.VelY = -100 + rnd.nextInt(200);
	    UmMonstro.Anim = rnd.nextInt(4);
	    
	    ListaObjetos.add(UmMonstro);
	    
	}*/	
	ListaObjetos.add(Personagem);
	
} // end of GamePanel()


private void TratadorDoMouseDown(MouseEvent e)
{
	if(e.getButton() == 1){
		FIRE = true;
	}
}

private void TratadorDoMouseUp(MouseEvent e)
{
	if(e.getButton() == 1){
		FIRE = false;
	}
}

public void TratadorDeTeclado(KeyEvent e){ 
	int keyCode = e.getKeyCode();
	
	if ((keyCode == KeyEvent.VK_ESCAPE) ||
		(keyCode == KeyEvent.VK_Q) ||
		(keyCode == KeyEvent.VK_END) ||
		((keyCode == KeyEvent.VK_C) && e.isControlDown()) ) {
			running = false;
	}
	
	
	if (keyCode == KeyEvent.VK_LEFT){
		ALEFT = true;			
	}else{
		ALEFT = false;	
	}
	if (keyCode == KeyEvent.VK_RIGHT){
		ARIGHT = true;			
	}else{
		ARIGHT = false;		
	}
	if (keyCode == KeyEvent.VK_UP){
		AUP = true;			
	}else{
		AUP = false;	
	}
	if (keyCode == KeyEvent.VK_DOWN){
		ADOWN = true;			
	}else{
		ADOWN = false;		
	}
	
	if(keyCode == KeyEvent.VK_SPACE){
		FIRE = true;
	}
	
}
public void TratadorDeTecladoReleased(KeyEvent e){
	int keyCode = e.getKeyCode();
	
	if (keyCode == KeyEvent.VK_LEFT){
		ALEFT = false;			
	}
	if (keyCode == KeyEvent.VK_RIGHT){
		ARIGHT = false;			
	}
	if (keyCode == KeyEvent.VK_UP){
	    AUP = false;			
	}
	if (keyCode == KeyEvent.VK_DOWN){
	    ADOWN = false;			
	}
	
	if(keyCode == KeyEvent.VK_SPACE){
		FIRE = false;
	}	
}


public void addNotify()
{
super.addNotify(); // creates the peer
startGame(); // start the thread
}


private void startGame()
// initialise and start the thread
{
	if (animator == null || !running) {
		animator = new Thread(this);
		animator.start();
	}
} // end of startGame()


public void stopGame()
// called by the user to stop execution
{ running = false; }


public void run()
/* Repeatedly update, render, sleep */
{
	running = true;
	
	long DifTime,TempoAnterior;
	
	DifTime = 0;
	TempoAnterior = System.currentTimeMillis();
	SegundoAtual = (long) (TempoAnterior/1000); 
	
	while(running) {
	
		gameUpdate(DifTime); // game state is updated
		gameRender(); // render to a buffer
		paintImmediately(0, 0, 640, 480); // paint with the buffer
				
		try {
			Thread.sleep(2); // sleep a bit
		}	
		catch(InterruptedException ex){}
		
		DifTime = System.currentTimeMillis() - TempoAnterior;
		TempoAnterior = System.currentTimeMillis();
		NovoSegundo = (long) (TempoAnterior/1000);
			
		if(NovoSegundo!=SegundoAtual){
			FPS = SFPS;
			SegundoAtual = NovoSegundo;
			SFPS = 1;
		}else{
			SFPS++;
		}
		
	
	}
System.exit(0); // so enclosing JFrame/JApplet exits
} // end of run()

int firetimer = 100;
private void gameUpdate(long DiffTime)
{ 
	
	float oldx = x;
	float oldy = y;
	
	firetimer+=DiffTime;
	
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
    
	if(FIRE==true){
		if(firetimer>150){
			Fireball fireball = new Fireball(Personagem);
			
			if(getMousePosition()!=null){
			
				float difx = (getMousePosition().x+MAPA.MapX) - (Personagem.X+12);
				float dify = (getMousePosition().y+MAPA.MapY) - (Personagem.Y+16);
				
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
	}

	
	for(int i  = 0; i < ListaObjetos.size();i++){
	    ((Objeto)ListaObjetos.get(i)).SimulaSe(DiffTime);
	    if(ListaObjetos.get(i).life<=0){
	    	ListaObjetos.remove(i);
	    }
	}
	
	for(int i  = 0; i < FireballList.size();i++){
	    ((Objeto)FireballList.get(i)).SimulaSe(DiffTime);
	    if(FireballList.get(i).vivo==false){
	    	FireballList.remove(i);
	    }
	}	
	
	for(int i  = 0; i < ListaParticulas.size();i++){
	    ((Objeto)ListaParticulas.get(i)).SimulaSe(DiffTime);
	    if(ListaParticulas.get(i).vivo==false){
	    	ListaParticulas.remove(i);
	    }
	}	
	MAPA.Posiciona(((int)Personagem.X-320),((int)Personagem.Y-240));
}


private void gameRender()
// draw the current frame to an image buffer
{
	if (dbImage == null){ // create the buffer
		dbImage = createImage(PWIDTH, PHEIGHT);
		if (dbImage == null) {
			System.out.println("dbImage is null");
			return;
		}else{
			dbg = (Graphics2D)dbImage.getGraphics();
		}
	}
	
	// clear the background
	//dbg.setColor(Color.white);
	//dbg.fillRect (0, 0, PWIDTH, PHEIGHT);
	MAPA.DesenhaSe(dbg);
			// draw game elements
	dbg.setColor(Color.white);
	dbg.fillRect(0, 0, 140, 15);
	dbg.setColor(Color.BLACK);	
	dbg.drawString("FPS: "+FPS+"  "+ListaParticulas.size(), 10, 10);	

		
	dbg.setColor(Color.BLUE);	
	
	/*Personagem.DesenhaSe(dbg);
	for(int i  = 0; i < ListaObjetos.size();i++){
	    ((Objeto)ListaObjetos.elementAt(i)).DesenhaSe(dbg);
	}*/	
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
	
	if (gameOver)
		dbg.drawString("Fim", 0, 0);
} // end of gameRender()


public void paintComponent(Graphics g)
{
	super.paintComponent(g);
	if (dbImage != null)
		g.drawImage(dbImage, 0, 0, null);
	
}


public static void main(String args[])
{
	GamePanel ttPanel = new GamePanel();

  // create a JFrame to hold the timer test JPanel
  JFrame app = new JFrame("Swing Timer Test");
  app.getContentPane().add(ttPanel, BorderLayout.CENTER);
  app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  app.pack();
  app.setResizable(false);  
  app.setVisible(true);
} // end of main()

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


} // end of GamePanel class

