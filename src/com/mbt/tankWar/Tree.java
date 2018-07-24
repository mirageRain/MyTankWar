package com.mbt.tankWar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.xml.soap.Text;

/**
author：Mirage
version:1.0
下午5:00:18
*/
public class Tree {
	private int  x, y;
	private int iX,iY;
	private int width;
	public  int flag;
	
	TankClient tc;
	
	public static Image treeImg1=new ImageIcon("images/stone.png").getImage();
	public static Image treeImg2=new ImageIcon("images/river1.png").getImage();
	public static Image treeImg3=new ImageIcon("images/tree2.png").getImage();
	public static Image treeImg4=new ImageIcon("images/stone.png").getImage();
	public static Image [] treeImgs={treeImg1,treeImg2,treeImg3,treeImg4};
	
	public Tree(int x,int y,int flag,TankClient tc){
		this.tc=tc;
		this.x=x;
		this.y=y;
		this.flag=flag;
	}
	public void draw(Graphics g){
		g.drawImage(treeImgs[flag], x-tc.iX, y-tc.iY, null);
		//System.out.println((x-tc.iX)+"  "+(y-tc.iY));
	}
	public Rectangle getRect(int flag){
		if(flag==0){
			return new Rectangle(x+68,y+184,280,330);
		}else if(flag==1){
			return new Rectangle(x,y,treeImgs[flag].getWidth(null),treeImgs[flag].getHeight(null));
		}else if(flag==2){
			return new Rectangle(x+120,y+220,180,180);
		}
		return null;
		
	}
	
}

