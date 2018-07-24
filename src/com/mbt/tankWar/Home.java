package com.mbt.tankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Home {
	private int  x, y;
	TankClient tc;
	
	public static Image home=new ImageIcon("images/home.png").getImage();
	
	public Home(int x,int y,TankClient tc){
		this.tc=tc;
		this.x=x;
		this.y=y;
	}
	public void draw(Graphics g){
		g.drawImage(home, x-tc.iX, y-tc.iY, null);
	}
	public Rectangle getRect(){
		return new Rectangle(x+50,y+50,home.getWidth(null)-100,home.getHeight(null)-100);
	}
}
