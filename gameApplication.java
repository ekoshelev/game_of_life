
//CT255
//Assignment 9
//13/03/17

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.io.*;

	

public class gameApplication extends JFrame implements Runnable, MouseListener, MouseMotionListener{
	//member data

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Dimension WindowSize = new Dimension(800,800);
	
	private static boolean gameState[][][] = new boolean[40][40][2];

	private BufferStrategy strategy;
	
	private boolean checkplaying = false;
	private boolean random = false;
	//constructor
	public gameApplication() {
		this.setTitle("Game of Life");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Display the window, centered on the screen
		Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int x = screensize.width/2 - WindowSize.width/2;
		int y = screensize.height/2 - WindowSize.height/2;
		setBounds(x, y, WindowSize.width, WindowSize.height);
		setVisible(true);
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		Thread t = new Thread(this);
		t.start();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	
	//thread's entry point

	public void run() {
		 while (true) {
	            try {	            	
            	if (checkplaying==true){
					for (int x=0;x<40;x++) {
	            		for (int y=0;y<40;y++) {
		            		int liveneighbors=0;
		            		for (int xx=-1;xx<=1;xx++) {
			            		for (int yy=-1;yy<=1;yy++) {
				            		if (xx!=0 || yy!=0) {
				            			if (x+xx!=-1 && x+xx!=40 && y+yy!=-1 && y+yy!=40){
						            		if (gameState[x+xx][y+yy][0]==true){
						            			liveneighbors++;
						            		}
				            			}
				            		}
			            		}
		            		}
		            		
		            		if (liveneighbors<2 || liveneighbors>3){
		            			gameState[x][y][1]=false;
		            		}
		            		if (liveneighbors==3){
		            			gameState[x][y][1]=true;
		            		}
	            		}
	            	}
	            	for (int x=0;x<40;x++) {
	            		for (int y=0;y<40;y++) {
	            			gameState[x][y][0]=gameState[x][y][1];
	            		}
	            	}
	            	}
	  	            this.repaint();
	                Thread.sleep(200);
	            } catch (InterruptedException e) { 
	            	System.out.println("Error!");
	            }

	      }   

	}
	
	public void loadgame(){
		String filename2 = "C:\\Users\\Elizabeth\\Desktop\\NUI Courses\\Next Gen Tech II\\gameofLife\\src\\gameofLife\\gameApplication.txt";
		String filename = "C:\\Users\\Elizabeth\\Desktop\\NUI Courses\\Next Gen Tech II\\gameofLife\\src\\gameofLife\\gameApplication.java";
		String textinput = null;
		try {
		BufferedReader reader = new BufferedReader(new FileReader(filename2));
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        while (reader.readLine() != null) {
        	textinput = reader.readLine();
        	writer.write(textinput);
        }
    	writer.close();
		reader.close();
		}
		catch (IOException e) { }
	}
	
	public void savegame(){
		String filename = "C:\\Users\\Elizabeth\\Desktop\\NUI Courses\\Next Gen Tech II\\gameofLife\\src\\gameofLife\\gameApplication.txt";
		String filename2 = "C:\\Users\\Elizabeth\\Desktop\\NUI Courses\\Next Gen Tech II\\gameofLife\\src\\gameofLife\\gameApplication.java";
		String textinput = null;
		try {
		BufferedReader reader = new BufferedReader(new FileReader(filename2));
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        while (reader.readLine() != null) {
        	textinput = reader.readLine();
        	writer.write(textinput);
        }
    	writer.close();
		reader.close();
		}
		catch (IOException e) { }
	}

	@Override

	public void mouseClicked(MouseEvent e) {
		int i = e.getX();
		int j = e.getY();	
		if (i<70 && i>10 && j<65 && j>25){
			if (checkplaying==false){
			checkplaying=true;
			} else {
				checkplaying=false;
			}
		} 
		if (i<200 && i>120 && j<65 && j>25){
			randomizestates();
		}
		
		if (i<330 && i>230 && j<65 && j>25){
			loadgame();
			System.out.println("loaded");
		}
		
		if (i<460 && i>340 && j<65 && j>25){
			savegame();
			System.out.println("saved");
		}
		int x = i/20;
		int y = j/20;
		if (gameState[x][y][0]==true){
			gameState[x][y][0]=false;
		} else {
			gameState[x][y][0]=true;
		}
		
	}
	public void mouseMoved(MouseEvent e){
		
	}
	public void mouseDragged(MouseEvent e){
		int i = e.getX();
		int j = e.getY();	
		if (i<70 && i>10 && j<65 && j>25){
			checkplaying=true;
		} 
		if (i<200 && i>120 && j<65 && j>25){
			randomizestates();
		}
		
		int x = i/20;
		int y = j/20;
		if (gameState[x][y][0]==true){
			gameState[x][y][0]=false;
		} else {
			gameState[x][y][0]=true;
		}
		
	}
	public void mouseEntered(MouseEvent e) {	
	}



	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
	
	public void randomizestates(){
		for (int i=0; i<40; i++){
        	for (int j=0; j<40; j++){
				double random = Math.random();
				if (random>.5){
					gameState[i][j][0]=true;
				} else {
					gameState[i][j][0]=false;
				}
		     }
		}
	}
	
	public void paint(Graphics g){
		g = strategy.getDrawGraphics();
		g.setColor(Color.WHITE);
        g.fillRect(0, 0, WindowSize.width, WindowSize.height); 
        for (int i=0; i<40; i++){
        	for (int j=0; j<40; j++){
        		if (gameState[i][j][0]==false){
        			g.setColor(Color.BLACK);
        			g.fillRect(i*20, j*20, 20, 20);  
        			 g.setColor(Color.GREEN);
        		        g.fillRect(40, 40, 60, 30);  
        		        g.fillRect(150, 40, 60, 30); 
        		        g.fillRect(260, 40, 60, 30);  
        		        g.fillRect(370, 40, 60, 30); 
        		        g.setColor(Color.BLACK);
        		        g.drawString("Start", 40, 60);
        		        g.drawString("Random", 150, 60);
        		        g.drawString("Load", 260, 60);
        		        g.drawString("Save", 370, 60);
        		} else {
        			g.setColor(Color.WHITE);
        			g.fillRect(i*20, j*20, 20, 20);  
        		}
        	}
        }
		g.dispose();
		strategy.show();
	
	}

	//application's entry point

	public static void main(String[] args){
		new gameApplication();
	}

}
