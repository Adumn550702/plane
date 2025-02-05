package com.Planwar.obj;


import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;

public class BossObj extends GameObj{
	int health=30;
	public BossObj() {
		super();
	}

	public BossObj(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
	}

	public BossObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	public BossObj(int x, int y) {
		super(x, y);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		if(y<40){
			y+=speed;
		}else {
			x+=speed;
			if(x<0||x>360){
				speed=-speed;
			}
		}
		//首先是我方一级子弹和boss进行碰撞检测
		for(ShellObj shellObj: GameUtils.shellObjList){
			if(this.getRec().intersects(shellObj.getRec())&&health>0){
				shellObj.setX(-100);
				shellObj.setY(-100);
				GameUtils.removeList.add(shellObj);
				health--;
			} else if (this.getRec().intersects(shellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				shellObj.setX(-100);
				shellObj.setY(-100);
				GameUtils.removeList.add(shellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.state=4;
				GameWin.score+=10;
			}
		}
		//首先是我方二级子弹和boss进行碰撞检测
		for(DoubleShellObj doubleshellObj: GameUtils.doubleShellObjList){
			if(this.getRec().intersects(doubleshellObj.getRec())&&health>0){
				doubleshellObj.setX(-100);
				doubleshellObj.setY(-100);
				GameUtils.removeList.add(doubleshellObj);
				health-=3;
			} else if (this.getRec().intersects(doubleshellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				doubleshellObj.setX(-100);
				doubleshellObj.setY(-100);
				GameUtils.removeList.add(doubleshellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.state=4;
				GameWin.score+=10;
			}
		}
		//首先是我方三级子弹和boss进行碰撞检测
		for(TripleShellObj tripleshellObj: GameUtils.tripleShellObjList){
			if(this.getRec().intersects(tripleshellObj.getRec())&&health>0){
				tripleshellObj.setX(-100);
				tripleshellObj.setY(-100);
				GameUtils.removeList.add(tripleshellObj);
				health-=4;
			} else if (this.getRec().intersects(tripleshellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				tripleshellObj.setX(-100);
				tripleshellObj.setY(-100);
				GameUtils.removeList.add(tripleshellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.state=4;
				GameWin.score+=10;
			}
		}
		//白色矩形
		g.setColor(Color.WHITE);
		g.fillRect(200,40,200,10);
		//红色矩形
		g.setColor(Color.RED);
		g.fillRect(200,40,health*200/30,10);
	}

	@Override
	public Rectangle getRec() {
		return super.getRec();
	}
}
