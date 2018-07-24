package com.mbt.tankWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

import org.omg.CORBA.TCKind;

import sun.audio.AudioPlayer;


/**
author：Mirage
version:1.0
下午2:16:38
*/
public class Enemy1Bullet extends Bullet{

	  private InputStream snowHited=null;
			private InputStream fireHited=null;
			private InputStream Hited=null;
	
	public Enemy1Bullet(double x, double y, double rad,int type, TankClient tc) {
		super(x, y, rad,type,tc);
		this.tc=tc;
		try {
			snowHited= new FileInputStream(new File("sound/snowHited.wav"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			fireHited= new FileInputStream(new File("sound/fireHited.wav"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		try {
//			//Hited= new FileInputStream(new File("sound/Hited.wav"));
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
	}
	public void draw(Graphics g) {
		move();
		Graphics2D ga = (Graphics2D)g;
		 AffineTransform at = new AffineTransform();
		 AffineTransform atHead = new AffineTransform();
		 at.translate(getiX(),getiY());
		 at.rotate(getRad());
		 at.translate(-getBulletImgW()/2,- getBulletImgH()/2);
		ga.drawImage(bulletImages[type], at, null);
		if(getRect().intersects(tc.homeTank.getRect())){
			setLive(false);
			this.boomState=true;
			tc.homeTank.life-=this.attack;
			if(tc.homeTank.life<=0)tc.homeTank.live=false;
			if(type==3) {
				AudioPlayer.player.start(snowHited);
				tc.homeTank.speedX=0;
				tc.homeTank.speedY=0;
			}else if(type==2){
				
				AudioPlayer.player.start(fireHited);
			}else{
				
				//AudioPlayer.player.start(Hited);
			}
		}
		//System.out.println("X:" +x+"Y: "+y+"sX:  "+speedX+"sy：  "+speedY);
		
	}
	
}

