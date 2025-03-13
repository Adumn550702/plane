package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class PlaneObj extends GameObj {
	// 记录我方飞机碰撞补给的次数
	public static int times = 0;
	LittleBoss1 littleBoss1 = new LittleBoss1();
	LittleBoss2 littleBoss2 = new LittleBoss2();

	public PlaneObj() {
		super();
	}

	public PlaneObj(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
		// 添加鼠标的移动事件
		this.frame.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				PlaneObj.super.x = e.getX() - 19;
				PlaneObj.super.y = e.getY() - 20;
			}
		});
	}

	public PlaneObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);

		// 检测我方飞机与敌方小飞机的碰撞
		for (Enemy1Obj enemy1Obj : GameUtils.enemy1ObjList) {
			if (this.getRec().intersects(enemy1Obj.getRec())) {
				handleCollision(enemy1Obj);
			}
		}

		// 检测我方飞机与敌方大飞机的碰撞
		for (Enemy2Obj enemy2Obj : GameUtils.enemy2ObjList) {
			if (this.getRec().intersects(enemy2Obj.getRec())) {
				handleCollision(enemy2Obj);
			}
		}

		// 检测我方飞机与敌方子弹的碰撞
		for (Enemy2BulletObj enemy2BulletObj : GameUtils.enemy2BulletObjList) {
			if (this.getRec().intersects(enemy2BulletObj.getRec())) {
				handleCollision(enemy2BulletObj);
			}
		}

		// 检测我方飞机与敌方 1 号 Boss 的碰撞
		if (this.getRec().intersects(littleBoss1.getRec())) {
			handleCollision(littleBoss1);
		}

		// 检测我方飞机与敌方 2 号 Boss 的碰撞
		if (this.getRec().intersects(littleBoss2.getRec())) {
			handleCollision(littleBoss2);
		}

		// 检测我方飞机与敌方 1 号 Boss 子弹的碰撞
		for (LittleBoss1Bullet littleBoss1Bullet : GameUtils.littleBoss1BulletList) {
			if (this.getRec().intersects(littleBoss1Bullet.getRec())) {
				handleCollision(littleBoss1Bullet);
			}
		}

		// 检测我方飞机与敌方 2 号 Boss 子弹的碰撞
		for (LittleBoss2Bullet littleBoss2Bullet : GameUtils.littleBoss2BulletList) {
			if (this.getRec().intersects(littleBoss2Bullet.getRec())) {
				handleCollision(littleBoss2Bullet);
			}
		}

		// 检测我方飞机与 Boss 子弹的碰撞
		for (BossBullet bossBullet : GameUtils.bossBulletList) {
			if (this.getRec().intersects(bossBullet.getRec())) {
				handleCollision(bossBullet);
			}
		}

		// 检测我方飞机与补给的碰撞
		for (GiftObj giftObj : GameUtils.giftObjList) {
			if (this.getRec().intersects(giftObj.getRec())) {
				giftObj.setX(-100);
				giftObj.setY(-100);
				GameUtils.removeList.add(giftObj);
				times++;
			}
		}
	}

	// 处理碰撞逻辑
	private void handleCollision(GameObj obj) {
		// 绘制爆炸效果
		ExplodeObj explodeObj = new ExplodeObj(x, y);
		GameUtils.explodeObjList.add(explodeObj);
		GameUtils.removeList.add(explodeObj);

		// 移除碰撞对象
		obj.setX(-100);
		obj.setY(-100);
		GameUtils.removeList.add(obj);

		// 减少玩家生命值
		GameWin.playerLives--;

		// 检查玩家生命值是否耗尽
		if (GameWin.playerLives <= 0) {
			GameWin.state = 3; // 游戏失败状态
		}
	}

	@Override
	public Rectangle getRec() {
		return super.getRec();
	}
}
