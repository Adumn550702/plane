package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;

public class LittleBoss2 extends GameObj{
	int health=10;
	public LittleBoss2() {
		super();
	}

	public LittleBoss2(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
	}

	public LittleBoss2(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	public LittleBoss2(int x, int y) {
		super(x, y);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		if(y<150){
			y+=2;
		}else{
			x+=speed;
			if(x>400||x<10){
				speed=-speed;
			}
		}

		//敌方1号boss和我方子弹碰撞之后，我方子弹消失，当1号boss血量为0的时候，1号boss也会消失，否则不会消失
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
				//当敌方小boss被击毁的时候才会出现补给品
				GiftObj giftObj=new GiftObj(this.x,this.y);
				GameUtils.giftObjList.add(giftObj);
				GameUtils.gameObjList.addAll(GameUtils.giftObjList);

				shellObj.setX(-100);
				shellObj.setY(-100);
				GameUtils.removeList.add(shellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.score+=5;
			}
		}
		//敌方2号boss和我方二级子弹碰撞之后，我方子弹消失，当1号boss血量为0的时候，1号boss也会消失，否则不会消失
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
				//当敌方小boss被击毁的时候才会出现补给品
				GiftObj giftObj=new GiftObj(this.x,this.y);
				GameUtils.giftObjList.add(giftObj);
				GameUtils.gameObjList.addAll(GameUtils.giftObjList);
				doubleshellObj.setX(-100);
				doubleshellObj.setY(-100);
				GameUtils.removeList.add(doubleshellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.score+=5;
			}
		}
		//敌方2号boss和我方三级子弹碰撞之后，我方子弹消失，当1号boss血量为0的时候，1号boss也会消失，否则不会消失
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
				//当敌方小boss被击毁的时候才会出现补给品
				GiftObj giftObj=new GiftObj(this.x,this.y);
				GameUtils.giftObjList.add(giftObj);
				GameUtils.gameObjList.addAll(GameUtils.giftObjList);
				tripleshellObj.setX(-100);
				tripleshellObj.setY(-100);
				GameUtils.removeList.add(tripleshellObj);
				this.x=-200;
				this.y=-200;
				GameUtils.removeList.add(this);
				GameWin.score+=5;
			}
		}
	}

	@Override
	public Rectangle getRec() {
		return super.getRec();
	}
}
