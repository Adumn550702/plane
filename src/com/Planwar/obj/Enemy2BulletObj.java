package com.Planwar.obj;

import com.Planwar.GameWin;
import com.Planwar.utils.GameUtils;

import java.awt.*;

public class Enemy2BulletObj extends GameObj{
	public Enemy2BulletObj() {
		super();
	}

	public Enemy2BulletObj(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		super(img, width, height, x, y, speed, frame);
	}

	public Enemy2BulletObj(Image img, int x, int y, double speed) {
		super(img, x, y, speed);
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		y+=speed;
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
