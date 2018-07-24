package com.mbt.tankWar;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

/**
 * author：Mirage version:1.0 上午3:07:45
 */
public class BossCircle {
	public int x, y;
	public boolean flag = true;
	public int count = 0;
	public static int frameCount = 0;
	public int attack = 2;
	TankClient tc;
	public static Image bossCircle1 = new ImageIcon("images/fireCircle1.png").getImage();
	public static Image bossCircle2 = new ImageIcon("images/fireCircle2.png").getImage();
	public static Image bossCircle3 = new ImageIcon("images/fireCircle3.png").getImage();
	public static Image bossCircle4 = new ImageIcon("images/fireCircle4.png").getImage();
	public static Image[] bossCircles = { bossCircle1, bossCircle2, bossCircle3, bossCircle4 };

	public BossCircle(int x, int y, TankClient tc) {
		super();
		this.tc = tc;
		this.x = x;
		this.y = y;
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g) {

		// System.out.println((frameCount/2)%bossCircles.length);
		g.drawImage(bossCircles[(frameCount / 2) % bossCircles.length], x - tc.iX, y - tc.iY, null);
		// System.out.println(x+" "+y+" "+(x-tc.iX)+" "+(y-tc.iY));
		if (getRect().intersects(tc.homeTank.getRect())) {
			tc.homeTank.life -= this.attack;
		}
		frameCount++;

	}

	public Rectangle getRect() {
		return new Rectangle(x + 50, y + 50, bossCircle1.getWidth(null) - 100, bossCircle1.getHeight(null) - 100);
	}
}
