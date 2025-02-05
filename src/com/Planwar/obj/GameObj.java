package com.Planwar.obj;

import com.Planwar.GameWin;

import java.awt.*;

//所有游戏元素的父类
public class GameObj {
	//元素的图片
	Image img;
	//游戏元素的大小
	int width;
	int height;
	//游戏元素的位置
	int x;
	int y;
	//元素的运动速度
	double speed;
	//窗口类
	GameWin frame;

	//set和get方法
	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public GameWin getFrame() {
		return frame;
	}

	public void setFrame(GameWin frame) {
		this.frame = frame;
	}
    //构造方法
	public GameObj() {
	}

	public GameObj(Image img, int width, int height, int x, int y, double speed, GameWin frame) {
		this.img = img;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.frame = frame;
	}

	public GameObj(Image img, int x, int y, double speed) {
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public GameObj(int x, int y) {
		this.x = x;
		this.y = y;
	}

	//绘制元素自身的方法
	public void paintSelf(Graphics g){
		g.drawImage(img,x,y,null);
	}
	//获取自身矩形的方法，用来进行碰撞检测
	public Rectangle getRec(){
		return new Rectangle(x,y,width,height);
	}

}
