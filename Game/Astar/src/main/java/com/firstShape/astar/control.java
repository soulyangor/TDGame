package com.firstShape.astar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class control {
	double x,y,z; // координаты на экране
    
	double matrixX, matrixY; //координаты в мире
	    
    int delayAtack=3; //задержка между атаками
    boolean onAtack;  //флаг движения
    double hp;    //здоровье
    double apgX, aX;//направление по x для движения и атаки
    double apgY, aY;//направление по y для движения и атаки
    boolean onMove; //флаг атаки
    boolean onDmg;//флаг нанесения урона
    double speed;//скорость
    
    
    int rightFrame, rAframe;
    int leftFrame, lAframe;
    int topFrame, tAframe;
    int downFrame, dAframe;       
    
    double xC,yC;
    control(){
    	this.delayAtack=5; 
    	this.onAtack=false; 
    	this.apgX=0;
    	this.apgY=0;
    	this.onMove=false;
     	this.hp=100;
     	this.speed=2.4;
    	    	
    	this.x=200;
    	this.y=400;
    	
    	this.matrixX=200;
    	this.matrixY=400;
    	
    	this.rightFrame=0;
    	this.leftFrame=0;
    	this.topFrame=0;
    	this.downFrame=0;
    	
    	rAframe=0;
    	lAframe=0;
    	tAframe=0;
    	dAframe=0;
    }
    
    public void draw(Canvas canvas,Bitmap body,
								   Bitmap legs,
								   Bitmap tors,
								   Bitmap belt,
								   Bitmap feet,
								   Bitmap hair,
								   Bitmap head,
								   Bitmap hands,
								   Bitmap shield,
								   Bitmap box)  {
    	int srcX = 0;
        int srcY = 0;
        
    	if((apgX>0)&&(apgX>Math.abs(apgY))){
    		srcX = this.rightFrame * body.getWidth() / 9;
    		srcY = 3 * body.getHeight() / 4;
    		if (onMove) this.rightFrame++;
    		if (this.rightFrame==9) this.rightFrame=0;
         }
		
		if((apgY<0)&&(Math.abs(apgY)>Math.abs(apgX))){
			srcX = this.topFrame * body.getWidth() / 9;
			srcY = 0 * body.getHeight() / 4;
			if (onMove) this.topFrame++;
    		if (this.topFrame==9) this.topFrame=0;
    		}
		
		if((apgX<0)&&(Math.abs(apgX)>Math.abs(apgY))){
			srcX = this.leftFrame * body.getWidth() / 9;
			srcY = 1 * body.getHeight() / 4;
			if (onMove) this.leftFrame++;
    		if (this.leftFrame==9) this.leftFrame=0;
			}
		
		if((apgY>0)&&(apgY>Math.abs(apgX))){
			srcX = this.downFrame * body.getWidth() / 9;
			srcY = 2 * body.getHeight() / 4;
			if (onMove) this.downFrame++;
    		if (this.downFrame==9) this.downFrame=0;
			}
    	    	
        Rect src = new Rect(srcX, srcY, srcX + body.getWidth() / 9, srcY + body.getHeight() / 4);
        
    	Rect dst = new Rect((int)this.x - (body.getWidth() / 18), (int)this.y - (body.getHeight() / 8),
        		(int)this.x + (body.getWidth() / 18), (int)this.y + (body.getHeight() / 8));
    	
    	canvas.drawBitmap(body, src, dst, null);
    	canvas.drawBitmap(legs, src, dst, null);
    	canvas.drawBitmap(tors, src, dst, null);
    	canvas.drawBitmap(belt, src, dst, null);
    	canvas.drawBitmap(feet, src, dst, null);
    	canvas.drawBitmap(hair, src, dst, null);
    	canvas.drawBitmap(head, src, dst, null);
    	canvas.drawBitmap(hands, src, dst, null);
    	canvas.drawBitmap(shield, src, dst, null);
    	canvas.drawBitmap(box, src, dst, null);
    	
    }
    int k=0;
    public void drawAtack(Canvas canvas,Bitmap body,
    									Bitmap legs,
    									Bitmap tors,
    									Bitmap belt,
    									Bitmap feet,
    									Bitmap hair,
    									Bitmap head,
    									Bitmap hands,
    									Bitmap weapon1,
    									Bitmap weapon2,
    									Bitmap box,
    									int colFrame) {
   
    	int srcX = 0;
        int srcY = 0;   
                
       	if((aX>0)&&(aX>Math.abs(aY))){
    		srcX = this.rAframe * body.getWidth() / colFrame;
    		srcY = 3 * body.getHeight() / 4;
    		if ((onAtack)&&(k==0)) this.rAframe++;
    		if (this.rAframe==6) {
    			this.rAframe=0;
    			onDmg=true;
    		}
    		onDmg=false;
         }
		
		if((aY<0)&&(Math.abs(aY)>Math.abs(aX))){
			srcX = this.tAframe * body.getWidth() / colFrame;
			srcY = 0 * body.getHeight() / 4;
			if ((onAtack)&&(k==0)) this.tAframe++;
    		if (this.tAframe==6) {
    			this.tAframe=0;
    			onDmg=true;
    		}
    		onDmg=false;
    		}
		
		if((aX<0)&&(Math.abs(aX)>Math.abs(aY))){
			srcX = this.lAframe * body.getWidth() / colFrame;
			srcY = 1 * body.getHeight() / 4;
			if ((onAtack)&&(k==0)) this.lAframe++;
    		if (this.lAframe==6) {
    			this.lAframe=0;
    			onDmg=true;
    		}
    		onDmg=false;
			}
		
		if((aY>0)&&(aY>Math.abs(aX))){
			srcX = this.dAframe * body.getWidth() / colFrame;
			srcY = 2 * body.getHeight() / 4;
			if ((onAtack)&&(k==0)) this.dAframe++;
    		if (this.dAframe==6) {
    			this.dAframe=0;
    			onDmg=true;
    		}
    		onDmg=false;
			}
		k++;
		if(k==delayAtack) k=0;
				    	
        Rect src = new Rect(srcX, srcY, srcX + body.getWidth() / colFrame, srcY + body.getHeight() / 4);
        
    	Rect dst = new Rect((int)this.x - (body.getWidth() / (colFrame*2)), (int)this.y - (body.getHeight() / 8),
        		(int)this.x + (body.getWidth() / (colFrame*2)), (int)this.y + (body.getHeight() / 8));
    	
    	canvas.drawBitmap(body, src, dst, null);
    	canvas.drawBitmap(legs, src, dst, null);
    	canvas.drawBitmap(tors, src, dst, null);
    	canvas.drawBitmap(belt, src, dst, null);
    	canvas.drawBitmap(feet, src, dst, null);
    	canvas.drawBitmap(hair, src, dst, null);
    	canvas.drawBitmap(head, src, dst, null);
    	canvas.drawBitmap(hands, src, dst, null);
    	canvas.drawBitmap(box, src, dst, null);
    	canvas.drawBitmap(weapon1, src, dst, null);
    	
    	if(weapon2.getWidth()>body.getWidth()){
    		src = new Rect(3*srcX, 3*srcY, 3*srcX + weapon2.getWidth() / colFrame, 3*srcY + weapon2.getHeight() / 4);
         
    		dst = new Rect((int)this.x - (weapon2.getWidth() / (colFrame*2)), (int)this.y - (weapon2.getHeight() / 8),
         		(int)this.x + (weapon2.getWidth() / (colFrame*2)), (int)this.y + (weapon2.getHeight() / 8));
    	}
    	canvas.drawBitmap(weapon2, src, dst, null);
    }
           
           
    public void move(double xDispl, double yDispl, int map[][]){
    	if(onMove) {
    		map[(int)(matrixX/64)][(int)(matrixY/64)]=0;
    		
    		int i,j;
    		
    		boolean onWalk=true;
    		
    		i=(int)(matrixX/64);
    		j=(int)(matrixY/64);
    		
    		double tmpX, tmpY;
    		if(apgX>0){
    			i=(int)((matrixX+speed*apgX+22)/64);
    			tmpX=matrixX+speed*apgX+22;
    			if(map[i][j]!=0)onWalk=false;
    		}else{
    			i=(int)((matrixX+speed*apgX-22)/64);
    			tmpX=matrixX+speed*apgX-22;
    			if(map[i][j]!=0)onWalk=false;
    		}
    		
    		i=(int)(matrixX/64);
    		
    		if(apgY>0){
    			j=(int)((matrixY+speed*apgY+22)/64);
    			tmpY=matrixY+speed*apgY+22;
    			if(map[i][j]!=0)onWalk=false;
    		}else{
    			j=(int)((matrixY+speed*apgY-22)/64);
    			tmpY=matrixY+speed*apgY-22;
    			if(map[i][j]!=0)onWalk=false;
    		}
    		i=(int)(tmpX/64);
    		j=(int)(tmpY/64);
    		
    		if(map[i][j]!=0)onWalk=false;
    		
    		if (onWalk){
    			matrixX+=speed*apgX;
    			matrixY+=speed*apgY;
    		}
    		x=matrixX-xDispl*((int)(matrixX/xDispl));
    		y=matrixY-yDispl*((int)(matrixY/yDispl));
    	}
    	map[(int)(matrixX/64)][(int)(matrixY/64)]=-1;
    }
           
    void Control(){
    }
}
