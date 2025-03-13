package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;

public class Enemy1Obj extends GameObj{
	public Enemy1Obj() {
		super();
	}

	public Enemy1Obj(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
	}

	public Enemy1Obj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		y+=speed;
		//一级子弹和敌方小飞机的碰撞
		for(ShellObj shellObj: GameUtils.shellObjList){
			if(this.getRec().intersects(shellObj.getRec())){
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				shellObj.setX(-100);
				shellObj.setY(-100);
				this.setX(-200);
				this.setY(-200);
				GameUtils.removeList.add(shellObj);
				GameUtils.removeList.add(this);
				GameWin.score+=1;
			}
		}
		//二级子弹和敌方小飞机的碰撞
		for(DoubleShellObj doubleshellObj: GameUtils.doubleShellObjList){
			if(this.getRec().intersects(doubleshellObj.getRec())){
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				doubleshellObj.setX(-100);
				doubleshellObj.setY(-100);
				this.setX(-200);
				this.setY(-200);
				GameUtils.removeList.add(doubleshellObj);
				GameUtils.removeList.add(this);
				GameWin.score+=1;
			}
		}
		//三级子弹和敌方小飞机的碰撞
		for(TripleShellObj tripleshellObj: GameUtils.tripleShellObjList){
			if(this.getRec().intersects(tripleshellObj.getRec())){
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				tripleshellObj.setX(-100);
				tripleshellObj.setY(-100);
				this.setX(-200);
				this.setY(-200);
				GameUtils.removeList.add(tripleshellObj);
				GameUtils.removeList.add(this);
				GameWin.score+=1;
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
