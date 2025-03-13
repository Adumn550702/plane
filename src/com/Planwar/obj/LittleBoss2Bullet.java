package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;

public class LittleBoss2Bullet extends GameObj{
	int health=2;
	public LittleBoss2Bullet() {
		super();
	}

	public LittleBoss2Bullet(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
	}

	public LittleBoss2Bullet(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	public LittleBoss2Bullet(int x, int y) {
		super(x, y);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		//实现追踪功能
		this.y+=speed;
		this.x-=(this.x-GameUtils.gameObjList.get(GameWin.planeindex).getX())/30;

		//敌方2号boss子弹和我方子弹碰撞之后，我方子弹消失，当1号boss血量为0的时候，1号boss也会消失，否则不会消失
		for(ShellObj shellObj: GameUtils.shellObjList){
			if(this.getRec().intersects(shellObj.getRec())&&health>0){
				shellObj.setX(-100);
				shellObj.setX(-100);
				GameUtils.removeList.add(shellObj);
				health--;
			} else if (this.getRec().intersects(shellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				shellObj.setX(-100);
				shellObj.setX(-100);
				GameUtils.removeList.add(shellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.score+=3;
			}
		}
		//敌方2号boss子弹和我方二级子弹碰撞之后，我方子弹消失，当1号boss血量为0的时候，1号boss也会消失，否则不会消失
		for(DoubleShellObj doubleshellObj: GameUtils.doubleShellObjList){
			if(this.getRec().intersects(doubleshellObj.getRec())&&health>0){
				doubleshellObj.setX(-100);
				doubleshellObj.setX(-100);
				GameUtils.removeList.add(doubleshellObj);
				health-=3;
			} else if (this.getRec().intersects(doubleshellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				doubleshellObj.setX(-100);
				doubleshellObj.setX(-100);
				GameUtils.removeList.add(doubleshellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.score+=3;
			}
		}
		//敌方2号boss子弹和我方三级子弹碰撞之后，我方子弹消失，当1号boss血量为0的时候，1号boss也会消失，否则不会消失
		for(TripleShellObj tripleshellObj: GameUtils.tripleShellObjList){
			if(this.getRec().intersects(tripleshellObj.getRec())&&health>0){
				tripleshellObj.setX(-100);
				tripleshellObj.setX(-100);
				GameUtils.removeList.add(tripleshellObj);
				health-=5;
			} else if (this.getRec().intersects(tripleshellObj.getRec())&&health<=0) {
				//绘制爆炸
				ExplodeObj explodeObj=new ExplodeObj(x,y);
				GameUtils.explodeObjList.add(explodeObj);
				GameUtils.removeList.add(explodeObj);
				tripleshellObj.setX(-100);
				tripleshellObj.setX(-100);
				GameUtils.removeList.add(tripleshellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.score+=3;
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
