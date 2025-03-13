package com.Planwar.obj;

import java.awt.*;

public class ExplodeObj extends GameObj{
	static Image[] explodePic=new Image[16];
	int explodeCount=0;
	static {
		for(int i=0;i< explodePic.length;i++){
			explodePic[i]=Toolkit.getDefaultToolkit().getImage("imgs/explode/e"+(i+1)+".gif");
		}
	}

	@Override
	public void paintSelf(Graphics g) {
		super.paintSelf(g);
		if(explodeCount<16){
			super.img=explodePic[explodeCount];
			super.paintSelf(g);
			explodeCount++;
		}
	}

	public ExplodeObj(int x, int y) {
		super(x, y);
	}
}
