package com.firstShape.astar;


import java.util.List;

import com.firstShape.astar.AStarWithHash.Point;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	float[] x = new float[10];    
	float[] y = new float[10];    
	boolean[] touched = new boolean[10];
	
	float controlX, controlY, controlR;
	float atcX, atcY, atcR;
	
	Paint paint;
	Paint paintBlue;
	Paint paintGreen;
	private MainThread thread;
	
	public AStarWithHash A; 
	int n;
	Point path;
	
	environment env;
	
	public Bitmap bitmap;
	public Bitmap bitmapAtack;
	public Bitmap weapon;
	public Bitmap belt;
	public Bitmap tors;
	public Bitmap feet;
	public Bitmap hair;
	public Bitmap head;
	public Bitmap legs;
	public Bitmap hands;
	
	public Bitmap beltA;
	public Bitmap torsA;
	public Bitmap feetA;
	public Bitmap hairA;
	public Bitmap headA;
	public Bitmap legsA;
	public Bitmap handsA;
	
	public Bitmap free;
	
	public Bitmap land;
	public Bitmap bush;
	
	public Bitmap skeletonA;
	public Bitmap skeletonW;
	
	control gamer;
	
	List<AI> list;
	
	AI enemy[];
	
	public MainGamePanel(Context context) {
						
		super(context);
		getHolder().addCallback(this);
						
		// создаем поток для игрового цикла
		thread = new MainThread(getHolder(), this);
		setFocusable(true);
				
		this.paint=new Paint();
		this.paint.setColor(Color.RED);
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setAntiAlias(true);
		this.paint.setTextSize(18);
		
		this.paintBlue=new Paint();
		this.paintBlue.setColor(Color.BLUE);
		this.paintBlue.setStyle(Paint.Style.STROKE);
		this.paintBlue.setAntiAlias(true);
		this.paintBlue.setTextSize(20);
		
		this.paintGreen=new Paint();
		this.paintGreen.setColor(Color.GREEN);
		this.paintGreen.setStyle(Paint.Style.FILL);
		this.paintGreen.setAntiAlias(true);
		this.paintGreen.setTextSize(20);
		
		A=new AStarWithHash();
		A.createMap(160);	
		path=null;
		gamer=new control();
		
		this.bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.gamer_sprite);
		
		this.bitmapAtack=BitmapFactory.decodeResource(getResources(), R.drawable.gamer_atack);
		
		this.weapon=BitmapFactory.decodeResource(getResources(), R.drawable.gamer_sword);
		
		//одежда при хотьбе
		this.belt=BitmapFactory.decodeResource(getResources(), R.drawable.belt_gamer);
		
		this.tors=BitmapFactory.decodeResource(getResources(), R.drawable.tors_gamer);
		
		this.legs=BitmapFactory.decodeResource(getResources(), R.drawable.legs_gamer);
		
		this.hair=BitmapFactory.decodeResource(getResources(), R.drawable.hair_gamer);
		
		this.head=BitmapFactory.decodeResource(getResources(), R.drawable.head_gamer);
		
		this.feet=BitmapFactory.decodeResource(getResources(), R.drawable.feet_gamer);
		
		this.hands=BitmapFactory.decodeResource(getResources(), R.drawable.hands_gamer);
		
		//одежда при атаке
		this.beltA=BitmapFactory.decodeResource(getResources(), R.drawable.belt_gamer_atack);
		
		this.torsA=BitmapFactory.decodeResource(getResources(), R.drawable.tors_gamer_atack);
		
		this.legsA=BitmapFactory.decodeResource(getResources(), R.drawable.legs_gamer_atack);
		
		this.hairA=BitmapFactory.decodeResource(getResources(), R.drawable.hair_gamer_atack);
		
		this.headA=BitmapFactory.decodeResource(getResources(), R.drawable.head_gamer_atack);
		
		this.feetA=BitmapFactory.decodeResource(getResources(), R.drawable.feet_gamer_atack);
		
		this.handsA=BitmapFactory.decodeResource(getResources(), R.drawable.hands_gamer_atack);
		
		//пустое место если нет элемента одежды
		this.free=BitmapFactory.decodeResource(getResources(), R.drawable.free_atack);
		
		env=new environment();
		//загрузка рельефа
		this.land=BitmapFactory.decodeResource(getResources(), R.drawable.env_land);
		this.bush=BitmapFactory.decodeResource(getResources(), R.drawable.env_bush);
		
		//загрузка противников
		this.skeletonA=BitmapFactory.decodeResource(getResources(), R.drawable.skeleton_slash);
		this.skeletonW=BitmapFactory.decodeResource(getResources(), R.drawable.skeleton_walk);
		
		//создание списка противников
		/*list=new ArrayList<AI>();
		for(int i=0;i<20;i++){
			AI elem=new AI();
			list.add(elem);
		}*/
		enemy=new AI[10];
		for (int i=0;i<1;i++){
			enemy[i]=new AI();
			A.map[(int)((32+100*i)/64)][1]=0;
			enemy[i].get(32+100*i, 100);
		}
	}
			
	boolean drawable=false;
	boolean onCalc=false;
		
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 
		int action = event.getAction() & MotionEvent.ACTION_MASK;        
		 int pointerIndex = (event.getAction() &                            
							 MotionEvent.ACTION_POINTER_ID_MASK) >>
	     					 MotionEvent.ACTION_POINTER_ID_SHIFT;        
	     int pointerId = event.getPointerId(pointerIndex);
	     switch (action) {
	     case MotionEvent.ACTION_DOWN:        
	     case MotionEvent.ACTION_POINTER_DOWN:            
	    	 touched[pointerId] = true;            
	    	 x[pointerId] = (int)event.getX(pointerIndex);
	    	 y[pointerId] = (int)event.getY(pointerIndex);	     	 	    	  	
	    	 break;
	     case MotionEvent.ACTION_UP:        
	     case MotionEvent.ACTION_POINTER_UP:        
	     case MotionEvent.ACTION_CANCEL:            
	    	 touched[pointerId] = false;            
	    	 x[pointerId] = (int)event.getX(pointerIndex);            
	    	 y[pointerId] = (int)event.getY(pointerIndex);  	    	
	    	 break;
	     case MotionEvent.ACTION_MOVE:            
	    	 int pointerCount = event.getPointerCount();            
	    	 for (int i = 0; i < pointerCount; i++) {                
	    		 pointerIndex = i;                
	    		 pointerId = event.getPointerId(pointerIndex);                
	    		 x[pointerId] = (int)event.getX(pointerIndex);                
	    		 y[pointerId] = (int)event.getY(pointerIndex);   
	    		 }            
	    	 break;        
	     }	
	     
	     for (int i=0;i<10;i++){
	    		if(touched[i]){
	    	    	if ((x[i]>getWidth()-50)&&(y[i]<50)) {
	    				thread.setRunning(false);
	    				((Activity)getContext()).finish();
	    			  	}
	    		}
	    	 double rC=Math.sqrt(Math.pow(x[i]-controlX,2)+Math.pow(y[i]-controlY,2));
	    	 if(rC<1) continue;
	    	 if(rC<controlR) gamer.onMove=touched[i];else gamer.onMove=false;
	    	 	    	 
	    	 if(gamer.onMove){                                                                   
	    		 this.gamer.apgX=(x[i]-controlX)/rC;              
	    		 this.gamer.apgY=(y[i]-controlY)/rC;
	    		 break;
	    	 }
	     }
	    	 
	    	 for (int ii=0;ii<10;ii++){
		    	 double rA=Math.sqrt(Math.pow(x[ii]-atcX,2)+Math.pow(y[ii]-atcY,2));
		    	 if(rA<1) continue;
		    	 if(rA<controlR) gamer.onAtack=touched[ii];else gamer.onAtack=false; 
		    	 
		    	 if(gamer.onAtack){                                                                   
		    		 this.gamer.aX=(x[ii]-atcX)/rA;               
		    		 this.gamer.aY=(y[ii]-atcY)/rA;
		    		 		    		
		    		 break;
		    	 }
	    	 }
	     
	     return true;    
	     } 
	
					
	protected void update(){
		gamer.move(getWidth(),getHeight(),A.map);
		//расчет видимой части матрицы
		env.calc(gamer.matrixX, gamer.matrixY, getWidth(), getHeight());
		//движение противника
		for(int i=0;i<1;i++){
			enemy[i].action(gamer);
		if(enemy[i].onMove){
				
				A.searchPath((int)(gamer.matrixX/64),
							 (int)(gamer.matrixY/64),
							 (int)(enemy[i].matrixX/64),
							 (int)(enemy[i].matrixY/64));
				A.clearWay();			
				
				
				if(A.cell!=null) enemy[i].calc(A.cell.x*64+32,A.cell.y*64+32, A.map);						
		
		}
		enemy[i].move(getWidth(),getHeight(),A.map,env);
		}
	
	}
	
	@Override
	public void onDraw(Canvas canvas){
		//прорисовка окружения 
		env.drawLand(canvas, land, A.map, getWidth(), getHeight());
		env.drawObj(canvas, bush, A.map, getWidth(), getHeight());	
								 				
		// рисуем противников
		 
		 for(int i=0;i<1;i++)if (enemy[i].onAtack) {
							 enemy[i].drawAtack(canvas,skeletonA,
									 				free,
									 				free,
									 				free,
									 				free,
									 				free,
									 				free,
									 				free,
									 				free,free,free,
									 				6);	
			 
			 } else enemy[i].draw(canvas,skeletonW,
			 			    	   	  free,
			 			    	   	  free,
			 			    	   	  free,
			 			    	   	  free,
			 			    	   	  free,
			 			    	   	  free,
			 			    	   	  free,free,free);	 
		 
		/* for (int i=0;i<list.size();i++) 
			 if (list.get(i).onAtack) {
			 if(list.get(i).onMove){
				 list.get(i).draw(canvas,skeletonW,
						 				free,
			 			    			free,
			 			    			free,
			 			    			free,
			 			    			free,
			 			    			free,
			 			    			free,
			 			    			free,free);	
				 }else 
			list.get(i).drawAtack(canvas,skeletonA,
					 			    	 free,
					 			    	 free,
					 			    	 free,
					 			    	 free,
					 			    	 free,
					 			    	 free,
					 			    	 free,
					 			    	 free,free,free,
					 			    	 6);		 
			 
			 } else list.get(i).draw(canvas,skeletonW,
		 									free,
		 									free,
		 									free,
		 									free,
		 									free,
		 									free,
		 									free,
		 									free,free);		
		 */
		 
		//прорисовываем персонажа игрока	 		 		 
		 if (gamer.onAtack) {
			 if(gamer.onMove){
				 gamer.draw(canvas,bitmap,
			 			    			legs,
			 			    			tors,
			 			    			belt,
			 			    			feet,
			 			    			hair,
			 			    			head,
			 			    			hands,
			 			    			free,free);	
				 gamer.drawAtack(canvas,free,
						 				free,
						 				free,
						 				free,
						 				free,
						 				free,
						 				free,
						 				free,
						 			    free,weapon,free,
						 			    6);	
			 }else 
			 gamer.drawAtack(canvas,bitmapAtack,
					 			    legsA,
					 			    torsA,
					 			    beltA,
					 			    feetA,
					 			    hairA,
					 			    headA,
					 			    handsA,
					 			    free,weapon,free,
					 			    6);		 
			 
			 } else gamer.draw(canvas,bitmap,
			 			    	   legs,
			 			    	   tors,
			 			    	   belt,
			 			    	   feet,
			 			    	   hair,
			 			    	   head,
			 			    	   hands,free,free);
		 
		 
		//рисуем элементы управления
		 
		 canvas.drawCircle(controlX,controlY,controlR,paintBlue);
		 canvas.drawCircle(controlX,controlY,controlR/4,paintGreen);
		 
		 canvas.drawCircle(atcX,atcY,atcR,paintBlue);
		 canvas.drawCircle(atcX,atcY,atcR/4,paintGreen);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		if(width>height) {
			controlR=height/6;
			controlX=height/6;			
		}else{
			controlR=width/6;
			controlX=width/6;
		}
		atcR=controlR;
		controlY=height-controlR;
		atcY=controlY;
		atcX=width-atcR;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		//посылаем потоку команду на закрытие и дожидаемся, 
		//пока поток не будет закрыт.
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// пытаемся снова остановить поток thread
			}
		}
	}
	
}