package com.Planwar;

import com.Planwar.obj.*;
import com.Planwar.utils.GameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameWin extends JFrame {
	//0未开始 1游戏中，2暂停，3失败，4通关
	public static int state = 0;
	//背景图对象
	BgObj bgObj = new BgObj(GameUtils.bdImg, 0, -1800, 2);
	//定义一个图片变量
	Image offScreenImage = null;
	//我方飞机的对象
	public PlaneObj planeObj = new PlaneObj(GameUtils.planeImg, 37, 41, 290, 550, 0, this);
	//记录游戏绘制的次数
	int count = 1;
	//获取littleboss1的对象
	LittleBoss1 littleBoss1 = new LittleBoss1(GameUtils.littleboss1Img, 172, 112, -200, 350, 3, this);
	//获取littleboss2的对象
	LittleBoss2 littleBoss2 = new LittleBoss2(GameUtils.littleboss2Img, 172, 112, 300, -150, 2, this);
	//获取敌方boss对象
	BossObj bossObj = new BossObj(GameUtils.bossImg, 240, 174, 180, -180, 3, this);
	//获取警示标志的对象
	WaringObj waringObj = new WaringObj(GameUtils.warningImg, 599, 90, 0, 350, 0, this);
	//定义一个变量来记录我方飞机的索引
	public static int planeindex = 0;

	//定义一个变量记录游戏得分
	public static int score = 0;
	//定义玩家生命值变量
	public static int playerLives = 5; // 初始生命值为 5

	// 跟踪上一次生成生命道具的分数
	private int lastLifeScore = 0;
	// 生命道具是否已在游戏中
	private boolean lifeBuffActive = false;

	public void launch() {
		//窗口是否可见
		this.setVisible(true);
		//窗口的大小
		this.setSize(600, 800);
		//窗口的位置
		this.setLocationRelativeTo(null);
		//窗口的标题
		this.setTitle("Plan War");
		//关闭窗口会自动结束进程
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//将所有要绘制的游戏物体全部放入所有元素集合中进行绘制
		GameUtils.gameObjList.add(bgObj);
		GameUtils.gameObjList.add(planeObj);
		planeindex = GameUtils.gameObjList.indexOf(planeObj);//这里拿到了我方飞机的索引值

		//鼠标的点击事件
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == 1) {
					// 游戏未开始状态，点击开始游戏
					if (state == 0) {
						state = 1;//游戏开始状态
					}
					// 游戏失败或通关状态，点击重新开始游戏
					else if (state == 3 || state == 4) {
						restartGame();
					}
				}
			}
		});
		//添加键盘监听事件
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// 空格键暂停/继续游戏
				if (e.getKeyCode() == 32) {
					if (state == 1) {
						state = 2;
					} else if (state == 2) {
						state = 1;
					}
				}
				// R键重新开始游戏（在任何状态下都可用）
				else if (e.getKeyCode() == KeyEvent.VK_R) {
					if (state == 3 || state == 4) {
						restartGame();
					}
				}
				// 用于测试的L键 - 直接生成一个生命值道具
				else if (e.getKeyCode() == KeyEvent.VK_L) {
					if (state == 1 && !lifeBuffActive) {
						createLifeBuff();
					}
				}
			}
		});

		while (true) {
			createObj();
			repaint();
			try {
				Thread.sleep(25);//25毫秒
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 创建生命值道具的方法
	private void createLifeBuff() {
		// 在飞机上方一点的位置创建生命值道具
		int x = planeObj.getX();
		int y = planeObj.getY() - 100;

		// 确保生命道具在屏幕内
		if (x < 0) x = 10;
		if (x > 570) x = 570;
		if (y < 0) y = 10;

		// 创建生命值对象
		LifeBuff lifeBuff = new LifeBuff(GameUtils.lifeImg, 30, 30, x, y, 2, this);
		GameUtils.gameObjList.add(lifeBuff);
		lifeBuffActive = true;
	}

	// 重新开始游戏的方法
	private void restartGame() {
		// 清空所有游戏对象列表
		GameUtils.gameObjList.clear();
		GameUtils.shellObjList.clear();
		GameUtils.doubleShellObjList.clear();
		GameUtils.tripleShellObjList.clear();
		GameUtils.enemy1ObjList.clear();
		GameUtils.enemy2ObjList.clear();
		GameUtils.enemy2BulletObjList.clear();
		GameUtils.littleBoss1BulletList.clear();
		GameUtils.littleBoss2BulletList.clear();
		GameUtils.bossBulletList.clear();
		GameUtils.explodeObjList.clear();
		GameUtils.giftObjList.clear();
		GameUtils.removeList.clear();

		// 重置游戏变量
		score = 0;
		playerLives = 5;
		count = 1;
		lastLifeScore = 0;
		lifeBuffActive = false;

		// 重新初始化游戏对象
		bgObj = new BgObj(GameUtils.bdImg, 0, -1800, 2);
		planeObj = new PlaneObj(GameUtils.planeImg, 37, 41, 290, 550, 0, this);
		// 确保重置飞机的子弹形态为初始状态
		planeObj.times = 0;  // 重置子弹等级为初始值(1级子弹)

		littleBoss1 = new LittleBoss1(GameUtils.littleboss1Img, 172, 112, -200, 350, 3, this);
		littleBoss2 = new LittleBoss2(GameUtils.littleboss2Img, 172, 112, 300, -150, 2, this);
		bossObj = new BossObj(GameUtils.bossImg, 240, 174, 180, -180, 3, this);
		waringObj = new WaringObj(GameUtils.warningImg, 599, 90, 0, 350, 0, this);

		// 添加基本游戏对象到列表
		GameUtils.gameObjList.add(bgObj);
		GameUtils.gameObjList.add(planeObj);
		planeindex = GameUtils.gameObjList.indexOf(planeObj);

		// 重置游戏状态为开始
		state = 1;
	}

	@Override
	public void paint(Graphics g) {
		//初始化双缓存图片对象
		if (offScreenImage == null) {
			offScreenImage = createImage(600, 800);//大小要和游戏窗口大小相同
		}
		//获取双缓存图片对象的画笔
		Graphics gImage = offScreenImage.getGraphics();
		gImage.fillRect(0, 0, 600, 800);

		if (state == 0) {
			gImage.drawImage(GameUtils.bdImg, 0, 0, null);
			gImage.drawImage(GameUtils.explodeImg, 270, 350, null);
			gImage.drawImage(GameUtils.planeImg, 280, 470, null);
			gImage.drawImage(GameUtils.bossImg, 190, 70, null);
			// Draw the game start interface text
			gImage.setColor(Color.BLUE);
			gImage.setFont(new Font("Times New Roman", Font.BOLD, 30));
			gImage.drawString("Start Game", 250, 300);
		}
		if (state == 1) {
			// Add explosion collection to all elements collection
			GameUtils.gameObjList.addAll(GameUtils.explodeObjList);

			// No longer draw individual game elements, all elements are now in the collection and are drawn from there
			for (int i = 0; i < GameUtils.gameObjList.size(); i++) {
				GameUtils.gameObjList.get(i).paintSelf(gImage);
			}
			// Remove the elements in the deletion collection from all elements collection
			GameUtils.gameObjList.removeAll(GameUtils.removeList);
			count++;
		}
		if (state == 2) {
			gImage.drawImage(GameUtils.bdImg, 0, 0, null);
			GameUtils.drawWord(gImage, "Game Paused", Color.YELLOW, 30, 220, 300);
		}
		if (state == 3) {
			gImage.drawImage(GameUtils.bdImg, 0, 0, null);
			GameUtils.drawWord(gImage, "Game Over", Color.RED, 30, 220, 300);
			GameUtils.drawWord(gImage, "Click or press R to restart", Color.WHITE, 20, 190, 350);
		}
		if (state == 4) {
			gImage.drawImage(GameUtils.bdImg, 0, 0, null);
			GameUtils.drawWord(gImage, "Game Cleared", Color.GREEN, 30, 220, 300);
			GameUtils.drawWord(gImage, "Click or press R to restart", Color.WHITE, 20, 190, 350);
		}
		// Draw the game score and life panel
		GameUtils.drawWord(gImage, score + " points", Color.GREEN, 40, 30, 100);
		GameUtils.drawWord(gImage, "Lives: " + playerLives, Color.RED, 30, 30, 150);
		// Draw the double-buffered image onto the game window
		g.drawImage(offScreenImage, 0, 0, null);

		System.out.println(GameUtils.gameObjList.size());
	}

	//整个方法是用来批量创建物体
	void createObj() {
		// 当积分每增加30分且当前没有生命道具时，生成一个生命值buff
		if (score >= 30 && score - lastLifeScore >= 30 && !lifeBuffActive) {
			createLifeBuff();
			lastLifeScore = score;
		}

		if (count % 15 == 0) {//这里控制子弹产生的速度
			if (planeObj.times == 0) {//这里使用的是1级子弹
				GameUtils.shellObjList.add(new ShellObj(GameUtils.shellImg, 14, 29, planeObj.getX() + 12, planeObj.getY() - 20, 5, this));
				GameUtils.gameObjList.add(GameUtils.shellObjList.get(GameUtils.shellObjList.size() - 1));//添加到所有元素集合中的对象，是新new出来的子弹对象，并不是整个子弹集合
			}
			if (planeObj.times == 1) {//这里使用的是二级子弹
				GameUtils.doubleShellObjList.add(new DoubleShellObj(GameUtils.doubleShellImg, 32, 64, planeObj.getX() + 5, planeObj.getY() - 20, 8, this));
				GameUtils.gameObjList.add(GameUtils.doubleShellObjList.get(GameUtils.doubleShellObjList.size() - 1));
			}
			if (planeObj.times == 2) {//这里使用的是三级子弹
				GameUtils.tripleShellObjList.add(new TripleShellObj(GameUtils.tripleShellImg, 64, 182, planeObj.getX() - 5, planeObj.getY() - 100, 15, this));
				GameUtils.gameObjList.add(GameUtils.tripleShellObjList.get(GameUtils.tripleShellObjList.size() - 1));
			}
		}
		//两种敌方飞机
		if (count % 15 == 0) {//控制敌方小飞机的产生速度
			GameUtils.enemy1ObjList.add(new Enemy1Obj(GameUtils.enemy1Img, 32, 24, (int) ((Math.random() * 10) * 60), 0, 5, this));
			GameUtils.gameObjList.add(GameUtils.enemy1ObjList.get(GameUtils.enemy1ObjList.size() - 1));
		}
		if (count % 20 == 0) {
			if (count % 100 == 0) {
				GameUtils.enemy2ObjList.add(new Enemy2Obj(GameUtils.enemy2Img, 44, 67, (int) ((Math.random() * 10) * 60), 0, 2, this));
				GameUtils.gameObjList.add(GameUtils.enemy2ObjList.get(GameUtils.enemy2ObjList.size() - 1));
			}
			if (GameUtils.enemy2ObjList.size() > 0) {
				int x = (GameUtils.enemy2ObjList.get(GameUtils.enemy2ObjList.size() - 1)).getX();
				int y = (GameUtils.enemy2ObjList.get(GameUtils.enemy2ObjList.size() - 1)).getY();
				GameUtils.enemy2BulletObjList.add(new Enemy2BulletObj(GameUtils.enemy2bulletImg, 14, 25, x + 17, y + 55, 5, this));
				GameUtils.gameObjList.add(GameUtils.enemy2BulletObjList.get(GameUtils.enemy2BulletObjList.size() - 1));
			}
		}
		if (count == 600 && (!GameUtils.gameObjList.contains(littleBoss2))) {
			GameUtils.gameObjList.add(littleBoss2);
		}
		if (count == 800 && (!GameUtils.gameObjList.contains(littleBoss1))) {
			GameUtils.gameObjList.add(littleBoss1);
		}
		if (count % 15 == 0) {
			if (GameUtils.gameObjList.contains(littleBoss1)) {
				GameUtils.littleBoss1BulletList.add(new LittleBoss1Bullet(GameUtils.littleBoss1BulletImg, 42, 42, littleBoss1.getX() + 75, littleBoss1.getY() + 100, 4, this));
				GameUtils.gameObjList.add(GameUtils.littleBoss1BulletList.get(GameUtils.littleBoss1BulletList.size() - 1));
			}
		}
		if (count % 40 == 0) {
			if (GameUtils.gameObjList.contains(littleBoss2)) {
				GameUtils.littleBoss2BulletList.add(new LittleBoss2Bullet(GameUtils.littleBoss2BulletImg, 21, 59, littleBoss2.getX() + 78, littleBoss2.getY() + 100, 8, this));
				GameUtils.gameObjList.add(GameUtils.littleBoss2BulletList.get(GameUtils.littleBoss2BulletList.size() - 1));
			}
		}
		if (count == 1300 && (!GameUtils.gameObjList.contains(bossObj))) {
			GameUtils.gameObjList.add(bossObj);
		}

		if (count % 20 == 0) {
			if (GameUtils.gameObjList.contains(bossObj)) {
				//敌方1号boss子弹
				GameUtils.littleBoss1BulletList.add(new LittleBoss1Bullet(GameUtils.littleBoss1BulletImg, 42, 42, bossObj.getX() + 10, bossObj.getY() + 130, 6, this));
				GameUtils.gameObjList.add(GameUtils.littleBoss1BulletList.get(GameUtils.littleBoss1BulletList.size() - 1));
				//敌方2号boss的子弹
				if (count % 40 == 0) {
					GameUtils.littleBoss2BulletList.add(new LittleBoss2Bullet(GameUtils.littleBoss2BulletImg, 21, 59, bossObj.getX() + 220, bossObj.getY() + 130, 10, this));
					GameUtils.gameObjList.add(GameUtils.littleBoss2BulletList.get(GameUtils.littleBoss2BulletList.size() - 1));
				}
				//boss子弹
				GameUtils.bossBulletList.add(new BossBullet(GameUtils.bossBulletImg, 51, 72, bossObj.getX() + 70, bossObj.getY() + 100, 9, this));
				GameUtils.gameObjList.add(GameUtils.bossBulletList.get(GameUtils.bossBulletList.size() - 1));
			}
		}
		if (count == 1250 && (!GameUtils.gameObjList.contains(waringObj))) {
			GameUtils.gameObjList.add(waringObj);
		}
		if (count == 1290) {
			GameUtils.removeList.add(waringObj);
		}
	}

	public static void main(String[] args) {
		GameWin gameWin = new GameWin();
		gameWin.launch();
	}

	// 在GameWin类内部定义生命值buff类
	private class LifeBuff extends GameObj {
		// 内部使用的移动速度
		private double moveSpeed;

		public LifeBuff(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
			super(img, width, height, x, y, speed, frame);
			this.moveSpeed = speed;
		}

		@Override
		public void paintSelf(Graphics gImage) {
			super.paintSelf(gImage);
			// 物体向下移动 - 使用setY方法而不是直接访问y
			setY(getY() + (int)moveSpeed);

			// 检测是否与飞机碰撞
			if (this.getRec().intersects(planeObj.getRec())) {
				// 增加一条生命值
				playerLives++;
				// 将此对象添加到删除列表中
				GameUtils.removeList.add(this);
				// 标记生命道具不再活跃
				lifeBuffActive = false;
			}

			// 如果移出屏幕底部则删除
			if (getY() > 800) {
				GameUtils.removeList.add(this);
				// 标记生命道具不再活跃
				lifeBuffActive = false;
			}
		}

		@Override
		public Rectangle getRec() {
			return new Rectangle(getX(), getY(), getWidth(), getHeight());
		}
	}
}