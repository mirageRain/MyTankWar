package com.mbt.tankWar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;

/**
 * author：Mirage version:1.0 上午12:41:22
 */
public class HomeTank extends Tank {
	public boolean returnHome=false;
	public int returnHomeCount=0;
	public int returnHomeFrame=0;
	public boolean boomOver=false;
	public static Image tankWing0=new ImageIcon("images/wing0.png").getImage();
	public static Image tankWing1=new ImageIcon("images/wing1.png").getImage();
	public static Image tankWing2=new ImageIcon("images/wing2.png").getImage();
	public static Image tankWing3=new ImageIcon("images/wing1.png").getImage();
	public static Image tankWingBg=new ImageIcon("images/wingBg.png").getImage();
	public static Image [] tankWings={tankWing0,tankWing1,tankWing2,tankWing3};
	
	Image fail=new ImageIcon("images/fail.png").getImage();
	
	public static InputStream tankBoom=null;
	
	public HomeTank(int x, int y, int type,TankClient tc) {
		super(x, y, type,tc);
		// TODO Auto-generated constructor stub
	}

	public void keyPressDown(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case 'W':
			W = true;
			break;
		case 'S':
			S = true;
			break;
		case 'A':
			A = true;
			break;
		case 'D':
			D = true;
			break;
		}

	}

	// 按键抬起时
	public void keyPressUp(KeyEvent e) {
		
		int key = e.getKeyCode();

		switch (key) {
		case 'W':
			W = false;
			break;
		case 'S':
			S = false;
			break;
		case 'A':
			A = false;
			break;
		case 'D':
			D = false;
			break;
		}
	}
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		setPositionX(e.getX()); 
		setPositionY(e.getY());  
	}
	
	public void drawWing(Graphics g){
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();
		 at.translate(tc.Fram_width/2,tc.Fram_length/2);
		 at.rotate(Math.toRadians(180*Math.atan2(speedX+1, -(speedY-5))/Math.PI));
		 at.translate(-tankWings[returnHomeFrame%tankWings.length].getWidth(null)/2,-tankWings[returnHomeFrame%tankWings.length].getHeight(null)/2);
		 ga.drawImage(tankWings[returnHomeFrame%tankWings.length], at, null);
		 returnHomeFrame++;
		 if(returnHomeFrame>=9) {
			 x=1700;
			y=3350;
			 returnHomeFrame=0;
			 returnHome=false;
		 }
	}
	public void drawWingBg(Graphics g){
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();
		 at.translate(tc.Fram_width/2,tc.Fram_length/2);
		 at.rotate(Math.toRadians(180*Math.atan2(speedX+1, -(speedY-5))/Math.PI));
		 at.translate(-tankWingBg.getWidth(null)/2,-tankWingBg.getHeight(null)/2);
		 ga.drawImage(tankWingBg, at, null);
		
	}
	public void drawBoold(Graphics g){
		Color c = g.getColor();
		g.setColor(new Color(255,0,0));
		Graphics2D ga = (Graphics2D)g;
		ga.setColor(Color.red);
		AffineTransform at2 = new AffineTransform();
		at2.translate(tc.Fram_width/2,tc.Fram_length/2);
		if(speedX==0&speedY==0){
			 at2.rotate(lastRad);
		 }
		 else {
			 at2.rotate(Math.toRadians(180*Math.atan2(speedX, -speedY)/Math.PI));
		 }		at2.translate(-tankImgW,-tankImgH);
		 g.drawRect(tc.Fram_width/2-tankImgW,tc.Fram_length/2-tankImgH, tankImgW*2, 10);
		g.fillRect(tc.Fram_width/2-tankImgW,tc.Fram_length/2-tankImgH, tankImgW*2*life/100, 10);
		
	}
	
public void draw(Graphics g) {
	if(!live){
		if(!boomOver){
			drawBoom(g);
		}
	}else{
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();
		 AffineTransform atHead = new AffineTransform();
		 at.translate(tc.Fram_width/2,tc.Fram_length/2);
		 atHead.translate(tc.Fram_width/2,tc.Fram_length/2);
		 if(speedX==0&speedY==0){
			 at.rotate(lastRad);
		 }
		 else {
			 at.rotate(Math.toRadians(180*Math.atan2(speedX, -speedY)/Math.PI));
			 lastRad=Math.toRadians(180*Math.atan2(speedX, -speedY)/Math.PI);
		 }
		 lastHeadRad=Math.toRadians(180*Math.atan2(positionX-tc.Fram_width/2, -positionY+tc.Fram_length/2)/Math.PI);
		 atHead.rotate(lastHeadRad);
		 at.translate(-tankImgW,-tankImgH);
		 atHead.translate(-tankImgW,-tankImgH);
		 if(returnHome&&(returnHomeFrame==0||returnHomeFrame==1||returnHomeFrame==2))drawWingBg(g);
		 ga.drawImage(homeTankImg, at, null);
		ga.drawImage(homeTankHeadImg, atHead, null);
		drawBoold(g);
		if(returnHome) drawWing(g);
		 move();
			if(addBlState) ga.drawImage(addBl, at, null);
		
		/*(第一个参数是接下来要画的任何图形的旋转的幅度，Math.toRadians()方法是将一个角度转变成幅度,这样方便改变角度；第二、三的参数是旋转点，即以这个点为旋转中心)*/
	}
	}
public void drawBoom(Graphics g) {
	if(gifFrame<=19) {
		g.drawImage(Tank.tankBooms[gifFrame],x-tc.iX, y-tc.iY,Tank.tankBooms[gifFrame].getWidth(null),Tank.tankBooms[gifFrame].getHeight(null),null);
		gifFrame+=1;
	}else if(gifFrame<=59){
		g.drawImage(fail, tc.Fram_width/2-fail.getWidth(null)/2,tc.Fram_length/2-fail.getHeight(null)/2, null);
		gifFrame+=1;
		
	}else{
		boomOver=true;
	}
	if(gifFrame==19){
		try {
			tankBoom= new FileInputStream(new File("sound/fail.wav"));
		} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		AudioPlayer.player.start(tankBoom);
	}
	try {
		Thread.sleep(100);
	} catch (InterruptedException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}
	gifFrame+=1;
	
	
}
	
}
