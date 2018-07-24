package com.mbt.tankWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;


/**
author：Mirage
version:1.0
下午6:08:48
*/
 
public class EnemyTank1 extends Tank {
	
	double MaxSpeedX = 0, MaxSpeedY =0;
	double addSpeedX = 0, addSpeedY =0;
	  boolean randUpState=true;
	  boolean randRightState=true;
	  int randTimeU;
	  int randTimeR;
	  int timeCountU=0;
	  int timeCountR=0;
	  private  AudioClip fireBoom=null;
	public EnemyTank1(int x, int y, int type,TankClient tc) {
		super(x, y, type,tc);
		this.tc=tc;
		type=type;
		setiX(getX()-tc.iX);
		setiY(getY()-tc.iY);
		try {
			fireBoom =  Applet.newAudioClip(new File("sound/fireBoom.wav").toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 模拟路径
	 */
	 public void randMove(){
		 timeCountU++;
		 timeCountR++;
		 if(timeCountU==randTimeU){
			 timeCountU=0;	
			 randUpState=true;
		 }
		 if(timeCountR==randTimeR){
			 timeCountR=0;
			 randRightState=true;
		 }
		 if(randUpState){
			 randUpState=false;
			 int randFlag=(int)(Math.random()*3);
			 randTimeU=(int)(3+Math.random()*8);
			 switch(randFlag){
				 case 0:
					 W=true;
					 S=false;
					 break;
				 case 1:
					 W=false;
					 S=false;
					 break;
				 case 2:
					 W=false;
					 S=true;
					 break; 
			 }
		 }
		 if(randRightState){
			 randRightState=false;
			 int randFlag=(int)(Math.random()*3);
			 randTimeR=(int)(3+Math.random()*8);
			 switch(randFlag){
				 case 0:
					 D=true;
					 A=false;
					 break;                             
				 case 1:
					 D=false;
					 A=false;
					 break;
				 case 2:
					 D=false;
					 A=true;
					 break; 
			 }
		 }
		 move();
		 //模拟发射子弹
		 if((getX()-tc.iX>=0&&getX()-tc.iX<=tc.Fram_width)&&((getY()-tc.iY>=0&&getY()-tc.iY<=tc.Fram_length))&&(int)(Math.random()*10)==5){
			 tc.enemyTank1bullets.add(new Enemy1Bullet(x+tc.homeTank.tankImgW, y+tc.homeTank.tankImgH, lastHeadRad,type,tc));
		 }
	 }

	@Override
	public void draw(Graphics g) {
		
		Graphics2D ga = (Graphics2D) g;
		 AffineTransform at = new AffineTransform();
		 AffineTransform atHead = new AffineTransform();
		// System.out.println(getX()-tc.iX+"   "+(getY()-tc.iY));
		 at.translate(getX()-tc.iX+tankImgW,getY()-tc.iY+tankImgH);
		 atHead.translate(getX()-tc.iX+tankImgW,getY()-tc.iY+tankImgH);
		 if(speedX==0&speedY==0){
			 at.rotate(lastRad);
		 }
		 else {
			 at.rotate(Math.toRadians(180*Math.atan2(speedX, -speedY)/Math.PI));
			 lastRad=Math.toRadians(180*Math.atan2(speedX, -speedY)/Math.PI);
		 }
		 lastHeadRad=Math.toRadians(180*Math.atan2(tc.homeTank.centerX-x-tc.homeTank.tankImgW, -tc.homeTank.centerY+y+tc.homeTank.tankImgH)/Math.PI);
		 atHead.rotate(lastHeadRad);
		 at.translate(-tankImgW,-tankImgH);
		 atHead.translate(-tankImgW,-tankImgH);
		
		 /*(第一个参数是接下来要画的任何图形的旋转的幅度，Math.toRadians()方法是将一个角度转变成幅度,这样方便改变角度；第二、三的参数是旋转点，即以这个点为旋转中心)*/
			ga.drawImage(tankImags[type], at, null);
			ga.drawImage(tankHeadImgs[type], atHead, null);
			Bullet tempB=null;
			for(int i=0;i<tc.homeTankbullets.size();i++){
				tempB=tc.homeTankbullets.get(i);
				if(getRect().intersects(new Rectangle((int)tempB.x,(int)tempB.y,50,50))){
					//System.out.println(life);
					if(type==1){
						life-=tempB.attack;
						if(life<=0)
							setLive(false);
					}else{
						setLive(false);
					}
					if(tempB.type==1){
						tempB.boomState=true;
						tempB.setLive(false);
					}
					
					break;
				}
			}
		randMove();
	}
	
	
	/**
	 * 死亡后的爆炸特效
	 * @param g 
	 */
	public void drawBoom(Graphics g) {
		if(gifFrame==0){
			fireBoom.play();
		}
		if(gifFrame==18) gifLive=false;
		g.drawImage(tankBooms[gifFrame],getX()-tc.iX,getY()-tc.iY,tankBooms[gifFrame].getWidth(null),tankBooms[gifFrame].getHeight(null),null);
		gifFrame+=2;
	}
}

