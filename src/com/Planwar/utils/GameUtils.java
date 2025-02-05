package com.Planwar.utils;

import com.Planwar.obj.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//这个类是游戏元素的父类
public class GameUtils {
	//获取背景图片
	public static Image bdImg= Toolkit.getDefaultToolkit().getImage("imgs/bg.jpg");
	//获取boss图片
	public static Image bossImg= Toolkit.getDefaultToolkit().getImage("imgs/boss.png");
	//获取爆炸图片
	public static Image explodeImg= Toolkit.getDefaultToolkit().getImage("imgs/explode/e6.gif");
	//获取我方飞机图片
	public static Image planeImg= Toolkit.getDefaultToolkit().getImage("imgs/plane.png");
	//获取我方飞机子弹的图片
	public static Image shellImg= Toolkit.getDefaultToolkit().getImage("imgs/shell.png");
	//获取敌方小飞机的图片
	public static Image enemy1Img= Toolkit.getDefaultToolkit().getImage("imgs/enemy1.png");
	//获取敌方大飞机的图片
	public static Image enemy2Img= Toolkit.getDefaultToolkit().getImage("imgs/enemy2.png");
	//获取敌方大飞机子弹的图片
	public static Image enemy2bulletImg= Toolkit.getDefaultToolkit().getImage("imgs/enemy2bullet.png");
	//获取敌方小boss1的图片
	public static Image littleboss1Img= Toolkit.getDefaultToolkit().getImage("imgs/littleboss1.png");
	//获取敌方小boss1的图片
	public static Image littleboss2Img= Toolkit.getDefaultToolkit().getImage("imgs/littleboss2.png");
	//获取敌方1号boss子弹的图片
	public static Image littleBoss1BulletImg= Toolkit.getDefaultToolkit().getImage("imgs/littleboss1bullet.png");
	//获取敌方2号boss子弹的图片
	public static Image littleBoss2BulletImg= Toolkit.getDefaultToolkit().getImage("imgs/littleboss2bullet.png");
	//获取补给的图片
	public static Image giftImg= Toolkit.getDefaultToolkit().getImage("imgs/gift.png");
	//获取二级子弹的图片
	public static Image doubleShellImg= Toolkit.getDefaultToolkit().getImage("imgs/doubleshell.png");
	//获取三级子弹的图片
	public static Image tripleShellImg= Toolkit.getDefaultToolkit().getImage("imgs/tripleshell.png");
	//获取boss子弹的图片
	public static Image bossBulletImg= Toolkit.getDefaultToolkit().getImage("imgs/bossbullet.png");
	//获取警示标志的图片
	public static Image warningImg= Toolkit.getDefaultToolkit().getImage("imgs/warning.gif");



	//创建我方飞机子弹的集合
	public static List<ShellObj> shellObjList=new ArrayList<>();
	//创建敌方小飞机的集合
	public static List<Enemy1Obj> enemy1ObjList=new ArrayList<>();
	//创建敌方大飞机的集合
	public static List<Enemy2Obj> enemy2ObjList=new ArrayList<>();
	//创建敌方大飞机子弹的集合
	public static List<Enemy2BulletObj> enemy2BulletObjList=new ArrayList<>();
	//所有元素集合
	public static List<GameObj> gameObjList=new ArrayList<>();
	//所有要移除的元素的集合
	public static List<GameObj> removeList=new ArrayList<>();
	//爆炸集合
	public static List<ExplodeObj> explodeObjList=new ArrayList<>();
	//1号boss子弹的集合
	public static List<LittleBoss1Bullet> littleBoss1BulletList=new ArrayList<>();
	//2号boss子弹的集合
	public static List<LittleBoss2Bullet> littleBoss2BulletList=new ArrayList<>();
	//补给的集合
	public static List<GiftObj> giftObjList=new ArrayList<>();
	//二级子弹的集合
	public static List<DoubleShellObj> doubleShellObjList=new ArrayList<>();
	//三级子弹的集合
	public static List<TripleShellObj> tripleShellObjList=new ArrayList<>();
	//boss子弹的集合
	public static List<BossBullet> bossBulletList=new ArrayList<>();

	//这个方法是用来绘制文字的
	public static void drawWord(Graphics gImage,String str,Color color,int size,int x,int y){
		gImage.setColor(color);
		gImage.setFont(new Font("仿宋",Font.BOLD,size));
		gImage.drawString(str,x,y);
	}


}
