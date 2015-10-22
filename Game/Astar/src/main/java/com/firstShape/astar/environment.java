package com.firstShape.astar;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class environment {
	public int x,y; //координаты вывода для матрицы
	private int correctX,correctY;
	
	public class cell{
		int x;
		int y;
		public cell(int x, int y){
			this.x=x;
			this.y=y;
		}
	}
	
	List<cell> tops;
	
	public environment()
	{
		tops=new ArrayList<cell>();
	}
	public void calc(double x, double y, double xDispl, double yDispl){//расчет видимой части матрицы, x,y-координаты игрока, xDispl, yDispl-размер дисплея
	    double tmpX, tmpY;
		tmpX=xDispl*((int)(x/xDispl));
		tmpY=yDispl*((int)(y/yDispl));
		
		correctX=(int)/*Math.floor*/( ((int)(x/xDispl)) * (xDispl-64*( (int)(xDispl/64) ) ) );
		correctY=(int)/*Math.floor*/( ((int)(y/yDispl)) * (yDispl-64*( (int)(yDispl/64) ) ) );
		  
		this.y=(int)(tmpY/64);
		this.x=(int)(tmpX/64);
		/*if(this.x!=0)this.x++;
		if(this.y!=0)this.y++;*/
	}
	
	public void drawLand(Canvas canvas, Bitmap land, int map[][], double xDispl, double yDispl ){
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++){
				Rect dst = new Rect((i*64-correctX), j*64-correctY, (i+1)*64-correctX, (j+1)*64-correctY);
			    Rect src = new Rect(0,0,64,64);
			    canvas.drawBitmap(land, src, dst, null);
			}
	}
	
	public void drawObj(Canvas canvas, Bitmap obj, int map[][], double xDispl, double yDispl ){//отрисовка окружения
				
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++){
				Rect dst = new Rect((i*64-correctX), j*64-correctY, (i+1)*64-correctX, (j+1)*64-correctY);
			    Rect src = new Rect(0,0,64,64);
			    if(map[i+x][j+y]==1)canvas.drawBitmap(obj, src, dst, null);
			}
		
	}
}
