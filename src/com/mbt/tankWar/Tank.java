package com.mbt.tankWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Random;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;



/**
author：Mirage
version:1.0
下午4:54:25
*/
public class Tank {
	protected  double speedX = 0, speedY =0;
	protected double addSpeedX = 10, addSpeedY =10;//加速度
	protected double MaxSpeedX = 30, MaxSpeedY =30;// 坦克最大速度
	protected  double lastRad=0,lastHeadRad=0;
	protected  double positionX=0,positionY=0;
	private int count = 0;
	protected final double res=5;
	protected boolean W = false, S = false, A = false, D = false;
	TankClient tc;

	protected int x,y; //逻辑坐标
	protected int iX, iY; //显示窗口坐标
	protected boolean live = true; 
	public  int life = 100; // 初始生命值

	protected boolean bL = false, bU = false, bR = false, bD = false;  //方向加速度
	
	//加载资源
	protected static Image[] tankImags = null; 
	protected static Image[] tankHeadImgs = null; // 存储全局静态
	public static Image homeTankImg=new ImageIcon("images/tankBody.png").getImage();
	public static Image homeTankHeadImg=new ImageIcon("images/tankHead.png").getImage();
	
	public static Image enemyTankImg1=new ImageIcon("images/enemy3TankBody.png").getImage();
	public static Image enemyTankImg2=new ImageIcon("images/enemy1TankBody.png").getImage();
	public static Image enemyTankImg3=new ImageIcon("images/enemy2TankBody.png").getImage();
	
	public static Image enemyTankHeadImg1=new ImageIcon("images/enemy3TankHead.png").getImage();
	public static Image enemyTankHeadImg2=new ImageIcon("images/enemy1TankHead.png").getImage();
	public static Image enemyTankHeadImg3=new ImageIcon("images/enemy2TankHead.png").getImage();
	
	public static Image addBl=new ImageIcon("images/bl.png").getImage();
	public int tankImgW=homeTankImg.getWidth(null)/2;
	public int tankImgH=homeTankImg.getHeight(null)/2;
	public int centerX=x+tankImgW;
	public int  centerY=y+tankImgH;
	public boolean addBlState=false;
	public boolean flagB=true,flagT=true,flagH=true;
	
	public static Image [] tankBooms;
	
	public static Image tankBoom0=new ImageIcon("images/tankBoom0.png").getImage();
	public static Image tankBoom1=new ImageIcon("images/tankBoom1.png").getImage();
	public static Image tankBoom2=new ImageIcon("images/tankBoom2.png").getImage();
	public static Image tankBoom3=new ImageIcon("images/tankBoom3.png").getImage();
	public static Image tankBoom4=new ImageIcon("images/tankBoom4.png").getImage();
	public static Image tankBoom5=new ImageIcon("images/tankBoom5.png").getImage();
	public static Image tankBoom6=new ImageIcon("images/tankBoom6.png").getImage();
	public static Image tankBoom7=new ImageIcon("images/tankBoom7.png").getImage();
	public static Image tankBoom8=new ImageIcon("images/tankBoom8.png").getImage();
	public static Image tankBoom9=new ImageIcon("images/tankBoom9.png").getImage();
	public static Image tankBoom10=new ImageIcon("images/tankBoom10.png").getImage();
	public static Image tankBoom11=new ImageIcon("images/tankBoom11.png").getImage();
	public static Image tankBoom12=new ImageIcon("images/tankBoom12.png").getImage();
	public static Image tankBoom13=new ImageIcon("images/tankBoom13.png").getImage();
	public static Image tankBoom14=new ImageIcon("images/tankBoom14.png").getImage();
	public static Image tankBoom15=new ImageIcon("images/tankBoom15.png").getImage();
	public static Image tankBoom16=new ImageIcon("images/tankBoom16.png").getImage();
	public static Image tankBoom17=new ImageIcon("images/tankBoom17.png").getImage();
	public static Image tankBoom18=new ImageIcon("images/tankBoom18.png").getImage();
	public static Image tankBoom19=new ImageIcon("images/tankBoom19.png").getImage();
	
	static {
		tankImags = new Image[] {homeTankImg,enemyTankImg1,enemyTankImg2,enemyTankImg3};
		tankHeadImgs =new Image[]{homeTankHeadImg,enemyTankHeadImg1,enemyTankHeadImg2,enemyTankHeadImg3};
		tankBooms=new Image []{tankBoom0,tankBoom1,tankBoom2,tankBoom3,tankBoom4,tankBoom5,tankBoom6,tankBoom7,tankBoom8,tankBoom9,tankBoom10,tankBoom11,tankBoom12,tankBoom13,tankBoom14,tankBoom15,tankBoom16,tankBoom17,tankBoom18,tankBoom19};
	}
	private AudioClip addBloodSound=null;

	
	
	
	
	
	public int gifFrame=0;
	public boolean gifLive=true;
	protected  int type;
	public Tank(int x, int y, int type,TankClient tc) {// Tank的构造函数1
		this.tc=tc;
		this.x = x;
		this.y = y;
		this.iX=390;
		this.iY=210;
		this.type = type;
		if(type==1){
			life=3;
			MaxSpeedX = 20;
			MaxSpeedY =20;
		}
	}

	
	public void draw(Graphics g) {
		
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();//旋转坐标系
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
		 
		 move();
		
		/*(第一个参数是接下来要画的任何图形的旋转的幅度，Math.toRadians()方法是将一个角度转变成幅度,这样方便改变角度；第二、三的参数是旋转点，即以这个点为旋转中心)*/

		ga.drawImage(tankImags[type], at, null);
		ga.drawImage(tankHeadImgs[type], atHead, null);
		
	}
	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}
	/**
	 * 坦克移动
	 */
	
	public void move(){
		//根据方向增加速度
		if(W){
			if (speedY >= -MaxSpeedY)
				speedY -= addSpeedY;
		}
		if(S){
			if (speedY <= MaxSpeedY)
				speedY += addSpeedY;
		}
		if(A){
			if (speedX >= -MaxSpeedX)
				speedX -= addSpeedX;
		}
		if(D){
			if (speedX <= MaxSpeedX)
				speedX += addSpeedX;
		}
		
		//防止抖动
		if(Math.abs(speedX-0)<=0.5){
			speedX=0;
		}else if(speedX>0){
			 speedX-=res;
		}else if(speedX<0){
			speedX+=res;
		}
		if(Math.abs(speedY-0)<=0.5){
			speedY=0;
		}else if(speedY>0){
			speedY-=res;
		}else if(speedY<0){
			speedY+=res;
		}
		
		//碰撞检测
		flagB=true;
		flagT=true;
		flagH=true;
		
		//碰撞BOSS
		if(getRect().intersects(tc.boss.get(0).getRect())){
			//AudioPlayer.player.start(inputStream);
			if(x-tc.boss.get(0).getRect().x<=0)x-=10;
			else x+=10;
			if(y-tc.boss.get(0).getRect().y<=0)y-=10;
			else y+=10;
			flagB=false;
		}
		//碰撞敌方坦克
		for(int i=0;i<tc.enemyTank1.size();i++){
			EnemyTank1 tempE=tc.enemyTank1.get(i);
			if(tempE!=this&&getRect().intersects(tempE.getRect())){
				if(x-tempE.getRect().x<=0)x-=10;
				else x+=10;
				if(y-tempE.getRect().y<=0)y-=10;
				else y+=10;
				flagH=false;
				break;
			}
		}
		
		//碰撞障碍物
		for(int i=0;i<tc.trees.size();i++){
			Tree tempT=tc.trees.get(i);
			if(getRect().intersects(tempT.getRect(tempT.flag))){
				//AudioPlayer.player.start(inputStream);
				if(x-tempT.getRect(tempT.flag).x<=0)x-=10;
				else x+=10;
				if(y-tempT.getRect(tempT.flag).y<=0)y-=10;
				else y+=10;
				flagT=false;
				break;
			}
		}
		try {
			addBloodSound =Applet.newAudioClip(new File("sound/addBloodSound.wav").toURL());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			//碰撞出生点
		if(this==tc.homeTank&&getRect().intersects(tc.home.getRect())&&tc.homeTank.life<100){
			
			if(!addBlState) addBloodSound.loop();
			tc.homeTank.life++;
			addBlState=true;
		}else if(this==tc.homeTank){
			addBloodSound.stop();
			addBlState=false;
		}
		
		if(this!=tc.homeTank&&getRect().intersects(tc.home.getRect())){
			if(x-tc.home.getRect().x<=0)x-=10;
			else x+=10;
			if(y-tc.home.getRect().y<=0)y-=10;
			else y+=10;
			flagT=false;
		}
		
		if(flagT&&flagH&&flagB){
			if(this.x+speedX<4000-tc.Fram_width/2-tc.homeTank.tankImgW&&this.x+speedX>=tc.Fram_width/2-tc.homeTank.tankImgW)
				this.x+=speedX;
			
				if(this.y+speedY<4000-tc.Fram_length/2-tc.homeTank.tankImgH&&this.y+speedY>=tc.Fram_length/2-tc.homeTank.tankImgH)
				this.y+=speedY;
			
				centerX=x+tankImgW;
				centerY=y+tankImgH;
		}
		
				
	}

	
	Rectangle getRect(){
		return new Rectangle(x+tankImgW/2+10,y+tankImgH/2+10,tankImgH,tankImgW);
	}

	
	public double getrad() {
		return lastHeadRad;
	}


	public int getiX() {
		return iX;
	}


	public void setiX(int iX) {
		this.iX = iX;
	}


	public int getiY() {
		return iY;
	}


	public void setiY(int iY) {
		this.iY = iY;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}
	
	public double getPositionX() {
		return positionX;
	}


	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}


	public double getPositionY() {
		return positionY;
	}


	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}


	
	

}

