package com.mbt.tankWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.omg.CORBA.PUBLIC_MEMBER;

import sun.audio.AudioPlayer;



/**
 * author：Mirage version:1.0 下午4:51:39
 */
public class TankClient extends Frame implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static int Fram_width = 1080; // 静态全局窗口大小
	public static  int Fram_length = 720;
	public  boolean printable = true;
	public   long frameCount=0;
	public   int homeTankBullet2Cd=0;
	//初始化个组件
	public static  HomeTank homeTank ;
	public static  List<Bullet> homeTankbullets;
	public static  List<Enemy1Bullet> enemyTank1bullets ;
	public static  List<BossBullet> bossBullets ;
	public  static List<Tree> trees;
	public static  List<Boss> boss ;
	public  static Home home;
	public  static BossCircle bossCircle;
	
	
	Image screenImage = null;
	Image bg=new ImageIcon("images/bg6.jpg").getImage();
	
	Image fail=new ImageIcon("images/fail.png").getImage();
	public Graphics gOffScreen;
	public static List<EnemyTank1> enemyTank1=new ArrayList<EnemyTank1>();
	public  static int iX=0,iY=0;
	private InputStream fire1=null;
	private InputStream fire2=null;
	private InputStream returnHome=null;
	
	public  static AudioClip bgm=null;
	public static  BeginUi beginUi=null;
	public TankClient() {
		homeTankbullets = new ArrayList<Bullet>();
		enemyTank1bullets = new ArrayList<Enemy1Bullet>();
		bossBullets = new ArrayList<BossBullet>();
		 trees =new ArrayList<Tree>();
		 boss =new ArrayList<Boss>();
		homeTank = new HomeTank(1700, 3350, 0, this);
		home=new Home(1500, 3300,this);
		addEnemy(20);
		bossCircle=new BossCircle(1900, 0,this);	
		//添加障碍物
		trees.add(new Tree(2300, 2300,0,this));
		trees.add(new Tree(0, 1860,1,this));
		trees.add(new Tree(2200, 1860,1,this));
		trees.add(new Tree(1400, 2600,2,this));
		
		//添加BOSS
		boss.add(new Boss(1680, 5,this));

		//设置窗口配置
		this.setVisible(true);
		this.setSize(Fram_width, Fram_length); // 设置界面大小
		this.setLocationRelativeTo(null); // 设置界面出现的位置,页面居中显示
		this.setTitle("坦克大战——保卫光明");
		this.addWindowListener(new WindowAdapter() { // 窗口监听关闭
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//自适应窗口大小
		final Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		 this.addWindowStateListener(new WindowStateListener() {
	            public void windowStateChanged(WindowEvent e) {
	                if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
	                	Fram_width = (int)screensize.getWidth(); // 静态全局窗口大小
	                	Fram_length =(int)screensize.getHeight();
	                	if(homeTank.x>=4000-Fram_width/2-homeTank.tankImgW){
	                		homeTank.x=4000-Fram_width/2-homeTank.tankImgW-5;
	                	}
	                	if(homeTank.y>=4000-Fram_length/2-homeTank.tankImgH){
	                		homeTank.y=4000-Fram_length/2-homeTank.tankImgH-5;
	                	}
	                	if(homeTank.x<=Fram_width/2-homeTank.tankImgW){
	                		homeTank.x=Fram_width/2-homeTank.tankImgW+5;
	                	}
	                	if(homeTank.y<=Fram_length/2-homeTank.tankImgH){
	                		homeTank.y=Fram_length/2-homeTank.tankImgH+5;
	                	}
	                }
	                else{
	                	Fram_width = 1080; // 静态全局窗口大小
	                	Fram_length =720;
	                	
	                }
	            }
	        });
	 
		//添加监听事件
		this.setResizable(true);
		this.setVisible(true);
		this.addKeyListener(new KeyMonitor());
		this.addMouseListener(new myMouseListener());// 设置键盘监听器
		this.addMouseMotionListener(new myMouseListener());
		
		try {
			bgm =Applet.newAudioClip(new File("sound/bgm1.wav").toURL());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		bgm.loop();;
	}
	
	/**
	 * 生成敌方坦克
	 * @param num敌方坦克数量
	 */
	
	public void addEnemy(int num){
		for(int j=0;j<num;j++){
			boolean flagR=true;
			int randX = 0;
			int randY=0;
			while(flagR){
				randX=(int)(50+Math.random()*3500);
				randY=(int)(50+Math.random()*3500);
				Rectangle tempR=new Rectangle((int)(randX+Tank.homeTankImg.getWidth(null)/4),(int) (randY+Tank.homeTankImg.getHeight(null)/4),Tank.homeTankImg.getWidth(null)/2 ,Tank.homeTankImg.getHeight(null)/2);
				
				if(tempR.intersects(homeTank.getRect())){
					flagR=true;
					break;
				}else{
					flagR=false;
				}
				if(!flagR)
				if(tempR.intersects(home.getRect())){
					flagR=true;
					break;
				}else{
					flagR=false;
				}
				if(!flagR)
				for(int i=0;i<this.enemyTank1.size();i++){
					EnemyTank1 tempE=enemyTank1.get(i);
					if(tempR.intersects(tempE.getRect())){
						flagR=true;
						break;
					}else{
						flagR=false;
					}
				}
				if(!flagR)
				for(int i=0;i<this.trees.size();i++){
					Tree tempT=this.trees.get(i);
					if(tempR.intersects(tempT.getRect(tempT.flag))){
						flagR=true;
						break;
					}else{
						flagR=false;
					}
				}
			}
			
			int tempRN=(int)(1+Math.random()*3);//随机坦克类型
			enemyTank1.add(new EnemyTank1(randX, randY,tempRN ,this));//添加坦克
		}
	}
	
	public static void main(String[] args) {
		//new BeiJing();
		beginUi=new BeginUi();
		// 实例化
	}

	@Override
	public void paint(Graphics g) {
		
		if(homeTank.life<=0)homeTank.live=false;//刷新home坦克的生命
		
		if(!boss.get(0).boomOver&&!homeTank.boomOver){//判断游戏是否结束
		home.draw(g);
		if(bossCircle.flag==true) bossCircle.draw(g);
		homeTank.draw(g);//画出主坦克
		
		//画出子弹
		Bullet tempB=null;
		for(int i=0;i<homeTankbullets.size();i++){
			tempB=homeTankbullets.get(i);
			if(!tempB.isLive()){
				if(tempB.boomState) tempB.hitDraw(g);
				homeTankbullets.remove(i);
			}else{
				tempB.draw(g);
			}
		}
		Enemy1Bullet tempEB=null;
		for(int i=0;i<enemyTank1bullets.size();i++){
			tempEB=enemyTank1bullets.get(i);
			if(!tempEB.isLive()){
				if(tempEB.boomState) tempEB.hitDraw(g);
				enemyTank1bullets.remove(i);
			}else{
				tempEB.draw(g);
			}
		}	
		
		//画出敌方坦克
		
		EnemyTank1 tempE=null;
		for(int i=0;i<enemyTank1.size();i++){
			tempE=enemyTank1.get(i);
			if(!tempE.isLive()){
				if(tempE.gifLive)
					tempE.drawBoom(g);
				else
				enemyTank1.remove(i);
			}else{
				tempE.draw(g);
			}
		}	
		//画出树
		
		Tree tempT=null;
		for(int i=0;i<trees.size();i++){
			tempT=trees.get(i);
			tempT.draw(g);
		}	
		
		//画出boss
		Boss tempBo=null;
		for(int i=0;i<boss.size();i++){
			tempBo=boss.get(i);
			tempBo.drawBlood(g);
		}
		
		//画出Boss子弹
		BossBullet tempBoB=null;
		for(int i=0;i<bossBullets.size();i++){
			tempBoB=bossBullets.get(i);
			tempBoB.draw(g);
		}
		
		try {
			Thread.sleep(80);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		frameCount++;
		if(frameCount%400==0)
			addEnemy(20);
		homeTankBullet2Cd++;
		repaint();
		}
		else{
			AudioPlayer.player.stop(boss.get(0).bossBoom);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			AudioPlayer.player.stop(boss.get(0).bossBoom);
			boss.get(0).boomOver=false;
			homeTank.boomOver=false;
			homeTank=null;
			homeTankbullets.clear();
			enemyTank1bullets.clear();
			bossBullets.clear();
			trees.clear();
			boss.clear();
			enemyTank1.clear();
			iX=0;
			iY=0;
			home=null;
			this.dispose();
			beginUi.setVisible(true);
			beginUi.isRepaint=3;
			beginUi.repaint();
		}
	}

	/**
	 * 双缓冲
	 * 
	 * @param g 充当画笔；
	 *
	 */

	@Override
	public void update(Graphics g) {

		if (screenImage == null) {
			screenImage = this.createImage(4000, 4000);
		}

		 gOffScreen = screenImage.getGraphics();
		iX=homeTank.getX()-(Fram_width/2-homeTank.tankImgW);
		iY=homeTank.getY()-(Fram_length/2-homeTank.tankImgH);
		gOffScreen.drawImage(bg, -iX,-iY,4000, 4000, null);

		paint(gOffScreen);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("NewGame")) {
			printable = false;
			Object[] options = { "确定", "取消" };
			int response = JOptionPane.showOptionDialog(this, "您确认要开始新游戏！", "", JOptionPane.YES_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == 0) {

				printable = true;
				this.dispose();
				new TankClient();
			} else {
				printable = true;
			}
		}
	}
	/**
	 * 监听鼠标事件
	 */
	private class myMouseListener extends MouseAdapter  {

		@Override
		//鼠标移动
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseMoved(e);
			if (homeTank != null)
				homeTank.mouseMoved(e);
		}
		@Override
		//鼠标按下
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mousePressed(e);
			double tempRad=homeTank.getrad();
			if(e.getButton()==MouseEvent.BUTTON1)
			{
				try {
					fire1= new FileInputStream(new File("sound/buttleFire.wav"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AudioPlayer.player.start(fire1);
					homeTankbullets.add(new Bullet(homeTank.getX()+homeTank.tankImgW, homeTank.getY()+homeTank.tankImgH,tempRad,1,null ));
				
			}
			
			else if(e.getButton()==MouseEvent.BUTTON3){
				if(homeTankBullet2Cd>=8){
					homeTankBullet2Cd=0;
					
							try {
								fire2 = new FileInputStream(new File("sound/pdGo.wav"));
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						AudioPlayer.player.start(fire2);
					
					homeTankbullets.add(new Bullet(homeTank.getX()+homeTank.tankImgW, homeTank.getY()+homeTank.tankImgH,tempRad,0 ,null));
				}
				else{
					
				}
			}
				

		}

	}

	private class KeyMonitor extends KeyAdapter { // 键盘监听类；

		/**
		 * 键盘某个键按下后的事件
		 * 
		 */

		@Override
		public void keyPressed(KeyEvent e) {
			if (homeTank != null)
				homeTank.keyPressDown(e);
			if(e.getKeyCode()==KeyEvent.VK_B){
				homeTank.returnHome=true;
				try {
					returnHome= new FileInputStream(new File("sound/returnHome.wav"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AudioPlayer.player.start(returnHome);
				
			}
		}

		/**
		 * 键盘某个键抬起后事件
		 */

		// @Override
		public void keyReleased(KeyEvent e) {
			if (homeTank != null)
				homeTank.keyPressUp(e);
			
		}

		
	}
}
