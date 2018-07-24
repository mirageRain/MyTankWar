package com.mbt.tankWar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;


/**
author：Mirage
version:1.0
下午5:38:23
*/
public class Bullet {
	
	//初始化
	protected  int speedX = 80;
	protected  int speedY = 80; //子弹速度
	protected double rad=0;
	protected  int attack;
	protected double x;
	protected double y;
	protected double iX;
	protected double iY;
	private int wX,wY;
	
	private boolean good;
	protected boolean live = true;
	public boolean boomState=false;
	public boolean flagT=true;
	public TankClient tc;
	protected int type;//子弹类型
	//加载子弹图片
	public static Image bulletImage0=new ImageIcon("images/bullte0.png").getImage();
	public static Image bulletImage1=new ImageIcon("images/bullte1.png").getImage();
	public static Image bulletImage2=new ImageIcon("images/enemy1Bullte.png").getImage();
	public static Image bulletImage3=new ImageIcon("images/enemy2Bullte.png").getImage();
	public static Image bulletImage4=new ImageIcon("images/fireball.png").getImage();
	public static Image [] bulletImages={bulletImage0,bulletImage1,bulletImage2,bulletImage3,bulletImage4};
	
	public static Image hited=new ImageIcon("images/hited.png").getImage();
	public static Image hited2=new ImageIcon("images/boom2.png").getImage();
	public static Image hited3=new ImageIcon("images/boom4.png").getImage();
	protected  int bulletImgW = bulletImages[type].getWidth(null);
	protected  int bulletImgH = bulletImages[type].getHeight(null);
	
	
	private InputStream snowHited=null;
	private InputStream fire1Hited=null;
	
	public Bullet(double x, double y, double rad,int type,TankClient tc) {
		this.tc=tc;
		this.x = x;
		this.y = y;
		this.rad = rad;
		this.type=type;
		if(type==0)
			initType0();
		else if(type==1)
			initType1();
		else if(type==2)
			initType2();
		else if(type==3)
			initType3();
		else if(type==4)
			initType4();
	}
	/**
	 * 画出子弹
	 * @param g
	 */
	public void draw(Graphics g) {
		move();
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();
		 AffineTransform atHead = new AffineTransform();
		 at.translate(iX,iY);
		 at.rotate(rad);
		 at.translate(-bulletImgW/2,- bulletImgH/2);
		ga.drawImage(bulletImages[type], at, null);
		
		//System.out.println("X:" +x+"Y: "+y+"sX:  "+speedX+"sy：  "+speedY);
		
	}
	
	/**
	 * 子弹击中效果
	 * @param g
	 */
	
	public void hitDraw(Graphics g) {
		if(type==3){
			g.drawImage(hited2, (int)tc.Fram_width/2-hited2.getWidth(null)/2, (int)tc.Fram_length/2-hited2.getHeight(null)+40,null);

		}
		else if(type==2){
			g.drawImage(hited3, (int)tc.Fram_width/2-hited2.getWidth(null)/2, (int)tc.Fram_length/2-hited2.getHeight(null)+40,null);

		}
		else{
			
			try {
				fire1Hited= new FileInputStream(new File("sound/fire1Hited.wav"));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AudioPlayer.player.start(fire1Hited);
			g.drawImage(hited, (int)iX-bulletImgW/2, (int)iY- bulletImgH/2, null);

		}
	}
	public void move(){
		this.x+=speedX*Math.sin(rad);
		this.y+=speedY*-Math.cos(rad);
		this.iX=x-tc.iX;
		this.iY=y-tc.iY;
		
		for(int i=0;i<tc.trees.size();i++){
			Tree tempT=tc.trees.get(i);
			if(tempT.flag!=1&&getRect().intersects(tempT.getRect(tempT.flag))){
				
				this.live=false;
				
			}
		}
		if(getRect().intersects(tc.boss.get(0).getRect())){
			this.boomState=true;
			this.live=false;
		}
		if(!flagT&&(x>4000-bulletImgW/2||y>4000-bulletImgH/2||x<=bulletImgW/2||y<=bulletImgH/2)){
			this.live=false;
		}
		if(tc.bossCircle.flag==true&&getRect().intersects(tc.bossCircle.getRect())){
			this.boomState=true;
			this.live=false;
		}
	}
	
	/**初始化子弹
	 * 
	 */
	public void initType0(){
		this.speedX=60;
		this.speedY=60;
		this.attack=3;
	}
	public void initType1(){
		this.speedX=160;
		this.speedY=160;
		this.attack=1;
	}
	public void initType2(){
		this.speedX=60;
		this.speedY=60;
		this.attack=5;
	}
	public void initType3(){
		this.speedX=60;
		this.speedY=60;
		this.attack=2;
	}
	public void initType4(){
		this.speedX=15;
		this.speedY=15;
		this.attack=2;
	}
	/**获取矩形区域
	 * 
	 * @return
	 */
	public Rectangle getRect(){
		return new Rectangle((int)x,(int) y, bulletImages[type].getWidth(null), bulletImages[type].getHeight(null));
	}
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public boolean isGood() {
		return good;
	}
	public void setGood(boolean good) {
		this.good = good;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public double getRad() {
		return rad;
	}
	public void setRad(double rad) {
		this.rad = rad;
	}
	public double getiX() {
		return iX;
	}
	public void setiX(double iX) {
		this.iX = iX;
	}
	public double getiY() {
		return iY;
	}
	public void setiY(double iY) {
		this.iY = iY;
	}
	public int getBulletImgW() {
		return bulletImgW;
	}
	public void setBulletImgW(int bulletImgW) {
		this.bulletImgW = bulletImgW;
	}
	public int getBulletImgH() {
		return bulletImgH;
	}
	public void setBulletImgH(int bulletImgH) {
		this.bulletImgH = bulletImgH;
	}
	
	
	
}

