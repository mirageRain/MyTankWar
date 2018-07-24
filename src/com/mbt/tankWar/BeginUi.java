package com.mbt.tankWar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import sun.audio.AudioPlayer;

/**
 * author：Mirage
 * version:1.0 下午3:09:52
 */
public class BeginUi extends JFrame implements ActionListener {

	public static int Fram_width = 1080; // 静态全局窗口大小
	public static int Fram_length = 720;
	Image offScreenImage = null;

	public static ImageIcon start1 = new ImageIcon("images/start1.png");
	public static ImageIcon map1 = new ImageIcon("images/map1.png");
	public static ImageIcon help1 = new ImageIcon("images/help1.png");
	public static ImageIcon exit1 = new ImageIcon("images/exit1.png");

	public static ImageIcon start2 = new ImageIcon("images/start2.png");
	public static ImageIcon map2 = new ImageIcon("images/map2.png");
	public static ImageIcon help2 = new ImageIcon("images/help2.png");
	public static ImageIcon exit2 = new ImageIcon("images/exit2.png");

	public static Image beginBg = new ImageIcon("images/beginUi.jpg").getImage();

	public static Image ui1 = new ImageIcon("images/ui1.gif").getImage();
	public static Image ui2 = new ImageIcon("images/ui2.gif").getImage();

	// private Timer t = new Timer();

	JTextArea aa = new JTextArea();
	JTextField bb = new JTextField();
	private JButton start = new JButton();
	private JButton map = new JButton();
	private JButton help = new JButton();
	private JButton exit = new JButton();
	private boolean startFlag = false;
	private boolean mapFlag = false;
	private boolean helpFlag = false;
	private boolean exitFlag = false;

	private InputStream changes = null;

	private AudioClip bgm = null;
	public int isRepaint = 1;
	private Timer timer = new Timer();

	public BeginUi() {
		timer.schedule(new TimerTask() { // bg1；

			@Override
			public void run() {
				isRepaint = 1;
			}

		}, 0);
		timer.schedule(new TimerTask() { // bg2；

			@Override
			public void run() {
				isRepaint = 2;
			}

		}, 3400);
		timer.schedule(new TimerTask() { // bg2；

			@Override
			public void run() {

				isRepaint = 3;
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				repaint();
				isRepaint = 0;

			}

		}, 6800);

		this.setLayout(null);
		this.setTitle("坦克大战之光明之战");
		this.setSize(Fram_width, Fram_length); // 设置界面大小
		this.setLocationRelativeTo(null); // 设置界面出现的位置,页面居中显示
		this.setResizable(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		try {
			bgm = Applet.newAudioClip(new File("sound/begin.wav").toURL());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		bgm.loop();
		start.addActionListener(this);
		map.addActionListener(this);
		help.addActionListener(this);
		exit.addActionListener(this);

		// drawButton();

		// this.setVisible(false);
		// this.setVisible(true);

		final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		this.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
					Fram_width = (int) screensize.getWidth(); // 静态全局窗口大小
					Fram_length = (int) screensize.getHeight();
				} else {
					Fram_width = 1080; // 静态全局窗口大小
					Fram_length = 720;

				}
			}
		});

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Object[] options = { "是", "否" };
				int i = JOptionPane.showOptionDialog(null, "您确定要退出游戏吗？", "将执行退出游戏操作", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);

				if (i == 0) {
					System.exit(0);
				}
			}

		});

		this.addMouseListener(new myMouseListener());// 设置键盘监听器
		this.addMouseMotionListener(new myMouseListener());
		new Thread(new refresh()).start();
	}

	public void drawButton() {
		start.setBounds((int) (Fram_width * 0.09), (int) (Fram_length * 0.8), start1.getIconWidth(),
				start1.getIconHeight());
		if (startFlag)
			start.setIcon(start2);
		else
			start.setIcon(start1);
		start.setBorderPainted(false);

		map.setBounds((int) (Fram_width * 0.31), (int) (Fram_length * 0.8), map1.getIconWidth(), map1.getIconHeight());
		if (mapFlag)
			map.setIcon(map2);
		else
			map.setIcon(map1);
		map.setBorderPainted(false);

		help.setBounds((int) (Fram_width * 0.53), (int) (Fram_length * 0.8), map1.getIconWidth(), map1.getIconHeight());
		if (helpFlag)
			help.setIcon(help2);
		else
			help.setIcon(help1);
		// help.setBorderPainted(false);

		exit.setBounds((int) (Fram_width * 0.75), (int) (Fram_length * 0.8), map1.getIconWidth(), map1.getIconHeight());
		if (exitFlag)
			exit.setIcon(exit2);
		else
			exit.setIcon(exit1);
		// exit.setBorderPainted(false);

		this.validate();
	}

	@Override
	public void paint(Graphics g) {
		if (isRepaint == 1)
			g.drawImage(ui1, 0, 0, Fram_width, Fram_length, null);
		else if (isRepaint == 2)
			g.drawImage(ui2, 0, 0, Fram_width, Fram_length, null);
		else if (isRepaint == 3) {
			// drawButton();

			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			g.drawImage(beginBg, 0, 0, Fram_width, Fram_length, null);
			add(start);
			add(map);
			add(help);
			add(exit);
			// drawButton();
			isRepaint = 0;
		} else {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			drawButton();
		}
	}

	@Override
	public void update(Graphics g) {

		if (offScreenImage == null) {
			offScreenImage = this.createImage(Fram_width, Fram_length);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		paint(gOffScreen);

		g.drawImage(offScreenImage, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(start)) {
			bgm.stop();
			try {
				// sound(ss);
				this.setVisible(false);
				Thread.sleep(500);

				new TankClient();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}

		if (e.getSource().equals(exit)) {

			Object[] options = { "是", "否" };
			int i = JOptionPane.showOptionDialog(null, "您确定要退出游戏吗？", "将执行退出游戏操作", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE, null, options, options[0]);

			if (i == 0) {
				System.exit(0);
			}

		}

		if (e.getSource().equals(help)) {
			String url = "http://www.mabotao.com/?p=50";
			java.net.URI uri = java.net.URI.create(url);
			java.awt.Desktop dp = java.awt.Desktop.getDesktop();
			if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
				try {
					dp.browse(uri);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // 获取系统默认浏览器打开链接
			}

		}

		if (e.getSource().equals(map)) {
			// new MapPaint().launchFrame();
		}
		// TODO Auto-generated method stub

	}

	private class myMouseListener extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseMoved(e);
			if ((e.getX() >= Fram_width * 0.08) && (e.getX() <= Fram_width * 0.09 + 190)
					&& (e.getY() >= Fram_length * 0.8) && (e.getY() <= Fram_length * 0.8 + 90)) {
				if (!startFlag) {
					try {
						changes = new FileInputStream(new File("sound/change.wav"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					AudioPlayer.player.start(changes);
				}
				start.setIcon(start2);
				startFlag = true;
			} else {
				startFlag = false;
				start.setIcon(start1);
			}

			if ((e.getX() >= Fram_width * 0.30) && (e.getX() <= Fram_width * 0.31 + 190)
					&& (e.getY() >= Fram_length * 0.8) && (e.getY() <= Fram_length * 0.8 + 90)) {
				if (!mapFlag) {
					try {
						changes = new FileInputStream(new File("sound/change.wav"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					AudioPlayer.player.start(changes);
				}
				mapFlag = true;
				map.setIcon(map2);
			} else {
				mapFlag = false;
				map.setIcon(map1);
			}

			if ((e.getX() >= Fram_width * 0.52) && (e.getX() <= Fram_width * 0.53 + 190)
					&& (e.getY() >= Fram_length * 0.8) && (e.getY() <= Fram_length * 0.8 + 90)) {
				if (!helpFlag) {
					try {
						changes = new FileInputStream(new File("sound/change.wav"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					AudioPlayer.player.start(changes);
				}
				helpFlag = true;
				help.setIcon(help2);
			} else {
				helpFlag = false;
				help.setIcon(help1);
			}

			if ((e.getX() >= Fram_width * 0.74) && (e.getX() <= Fram_width * 0.75 + 190)
					&& (e.getY() >= Fram_length * 0.8) && (e.getY() <= Fram_length * 0.8 + 90)) {
				if (!exitFlag) {
					try {
						changes = new FileInputStream(new File("sound/change.wav"));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					AudioPlayer.player.start(changes);
				}
				exitFlag = true;
				exit.setIcon(exit2);
			} else {
				exitFlag = false;
				exit.setIcon(exit1);
			}

		}
	}

	private class refresh implements Runnable {

		@Override
		public void run() {
			isRepaint = 1;

			while (isRepaint != 0) {
				repaint();

				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

}
