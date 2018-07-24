package com.mbt.tankWar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
author：Mirage
version:1.0
上午12:52:27
*/
public class BossBullet extends Bullet{
	private InputStream inputStream=null;
	
	public BossBullet(double x, double y, double rad, int type,TankClient tc) {
		super(x, y, rad, type,tc);
		// TODO Auto-generated constructor stub
	}
	public void draw(Graphics g) {
		move();
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();
		 AffineTransform atHead = new AffineTransform();
		 at.translate(iX+bulletImages[type].getWidth(null)/2,iY+bulletImages[type].getHeight(null)/2);
		 
		 at.rotate(rad);
		 at.translate(-bulletImages[type].getWidth(null)/2,-bulletImages[type].getHeight(null)/2);
		 rad+=0.1;
		// at.translate(-bulletImgW/2,-bulletImgH/2);
		ga.drawImage(bulletImages[type], at, null);
		if(getRect().intersects(tc.homeTank.getRect())){
			this.boomState=true;
			tc.homeTank.life-=this.attack;
			//System.out.println("X:" +x+"Y: "+y+"sX:  "+speedX+"sy：  "+speedY);
		}
		
		
	}
	public void move(){
		this.y+=speedY;
		this.iX=x-tc.iX;
		this.iY=y-tc.iY;

		if(!flagT&&(x>4000-bulletImgW/2||y>4000-bulletImgH/2||x<=bulletImgW/2||y<=bulletImgH/2)){
			setLive(false);
		}
	}
	public Rectangle getRect(){
		return new Rectangle((int)x+30,(int) y+30, bulletImages[type].getWidth(null)-60, bulletImages[type].getHeight(null)-60);
	}
}

