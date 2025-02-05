package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;

public class Enemy2Obj extends GameObj{
	//设置血量属性
	int health=3;

	public Enemy2Obj() {
		super();
	}

	public Enemy2Obj(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
	}

	public Enemy2Obj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		y+=speed;
		//一级子弹和敌方大飞机的碰撞检测
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
				this.x=-100;
				this.y=-100;
				GameUtils.removeList.add(this);
				GameWin.score+=2;
			}
		}
		//二级子弹和敌方大飞机的碰撞检测
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
				this.x=-100;
				this.y=-100;
				GameUtils.removeList.add(this);
				GameWin.score+=2;
			}
		}
		//三级子弹和敌方大飞机的碰撞检测
		for(TripleShellObj tripleshellObj: GameUtils.tripleShellObjList){
			if(this.getRec().intersects(tripleshellObj.getRec())&&health>0){
				tripleshellObj.setX(-100);
				tripleshellObj.setY(-100);
				GameUtils.removeList.add(tripleshellObj);
				health-=5;
			} else if (this.getRec().intersects(tripleshellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				tripleshellObj.setX(-100);
				tripleshellObj.setY(-100);
				GameUtils.removeList.add(tripleshellObj);
				this.x=-100;
				this.y=-100;
				GameUtils.removeList.add(this);
				GameWin.score+=2;
			}
		}
		//越界判断
		if(this.y>800){
			GameUtils.removeList.add(this);
		}
	}

	@Override
	public Rectangle getRec() {
		return super.getRec();
	}
}
