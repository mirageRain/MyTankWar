package com.mbt.tankWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Random;

import javax.lang.model.element.VariableElement;
import javax.swing.ImageIcon;

import sun.audio.AudioPlayer;

/**
*author：Mirage
*version:1.0
**/
public class Boss {
	private int  x, y;
	private int iX,iY;
	public  int flag;
	public int blood=20;
	public boolean live=true;
	public boolean boomOver=false;
	private int gifFrame=0;
	TankClient tc;
	
	public static Image boss=new ImageIcon("images/bossHited.png").getImage();
	public static Image bossFont=new ImageIcon("images/bossFont.png").getImage();
	Image victory=new ImageIcon("images/victory.png").getImage();
	public static InputStream bossBoom=null;
	private  AudioClip bossFire=null;
	private InputStream victorySound=null;
	public Boss(int x,int y ,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	public void draw(Graphics g){
		g.drawImage(boss, x-tc.iX, y-tc.iY, null);
		
	}
	//绘制BOSS血条
	public void drawBlood(Graphics g){
		if(!live){
			if(!boomOver){
				drawBoom(g);
			}
		}else{
			if((tc.frameCount+1)%100==0) addBossBullet();
			for(int i=0;i<tc.homeTankbullets.size();i++){
				Bullet tempB=tc.homeTankbullets.get(i);
				if(getRect().intersects(tempB.getRect())){
					tc.boss.get(0).blood-=tempB.attack;
					if(tc.boss.get(0).blood<=0) {
						live=false;
						tc.bgm.stop();
						//全部敌方坦克爆炸
						for(EnemyTank1 tempE : tc.enemyTank1){
							tempE.live=false;
						}
						try {
							bossBoom= new FileInputStream(new File("sound/bossBoom.wav"));
						} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
						}
						AudioPlayer.player.start(bossBoom);
					}
					tc.boss.get(0).draw(g);
				}
			}
		}
		
	
		Color c = g.getColor();
		g.setColor(new Color(155,55,199));
		g.drawImage(bossFont,  tc.Fram_width/2-330, 45, null);
		 g.drawRect(tc.Fram_width/2-200,50, 500, 10);
		g.fillRect(tc.Fram_width/2-200,50, blood*2, 10);
	}
	
	/*Boss攻击*/
	public void addBossBullet(){
		//System.out.println("ok");
		int tempRand=(int) (Math.random()*2);
		//System.out.println(tempRand);
		if(tempRand==0){
			tc.bossCircle.flag=true;
		}
		else{
			try {
				bossFire =  Applet.newAudioClip(new File("sound/bossFire.wav").toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bossFire.play();
			tc.bossBullets.add(new BossBullet(1800+Math.random()*400,boss.getHeight(null)-400  ,-0, 4,tc));
			tc.bossCircle.flag=false;
		}
		
	}
	public Rectangle getRect(){
			return new Rectangle(x+500,y+200,350,350);
	}
	public void drawBoom(Graphics g) {
		if(gifFrame<=19) {
			g.drawImage(Tank.tankBooms[gifFrame],x-tc.iX+400, y-tc.iY+300,Tank.tankBooms[gifFrame].getWidth(null)*2,Tank.tankBooms[gifFrame].getHeight(null)*2,null);
			gifFrame+=1;
		}else if(gifFrame<=59){
			g.drawImage(victory, tc.Fram_width/2-victory.getWidth(null)/2,tc.Fram_length/2-victory.getHeight(null)/2, null);
			gifFrame+=1;
			
		}else{
			boomOver=true;
		}
		if(gifFrame==19){
			try {
				victorySound= new FileInputStream(new File("sound/victorySound.wav"));
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
			AudioPlayer.player.start(victorySound);
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

