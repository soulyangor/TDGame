package com.firstShape.astar;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	 
	private boolean running;//����, ����������� �� ��, ��� ���� ��������.
	private SurfaceHolder surfaceHolder;
 	private MainGamePanel gamePanel;

 	private static final String TAG = MainThread.class.getSimpleName();
 	  	 
 	public void setRunning(boolean running) {
 	  this.running = running;
 	 }
 	
 	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
 		super();
 		this.surfaceHolder = surfaceHolder;
 		this.gamePanel = gamePanel;
 	}
 		
    // ����������� fps
 	private final static int     MAX_FPS = 30;
 	// ������������ ����� ������, ������� ����� ����������
 	private final static int    MAX_FRAME_SKIPS = 10;
 	// ������, ������� �������� ����(������������������ ����������-���������)
 	private final static int    FRAME_PERIOD = 1000 / MAX_FPS;    
 	 
 	@Override
 	public void run() {
 	    Canvas canvas;
 	    Log.d(TAG, "Starting game loop");
 	 
 	    long beginTime;        // ����� ������ �����
 	    long timeDiff;        // ����� ���������� ���� �����
 	    int sleepTime;        // ������� �� ����� ����� (<0 ���� ���������� ����������)
 	    int framesSkipped;    // ����� ������ � ������� �� ����������� �������� ������ ������� �� �����
 	 
 	    sleepTime = 0;
 	  		 
 	    while (running) {
 	        canvas = null;
 	       // �������� ������������� canvas 
 	       // ��� ��������� �������� �� �����������
 	        try {
 	            canvas = this.surfaceHolder.lockCanvas();
 	            synchronized (surfaceHolder) {
 	                beginTime = System.currentTimeMillis();
 	                framesSkipped = 0;    // �������� ������� ����������� ������
 	                // ��������� ��������� ����
 	                this.gamePanel.update();
 	            
 	                // ��������� ����� ����
 	                
					this.gamePanel.onDraw(canvas); //�������� ����� ��� ���������
 	                // ��������� �����, ������� ������ � ������� ������� �����
 	                timeDiff = System.currentTimeMillis() - beginTime;
 	                // ��������� �����, ������� ����� �����
 	                sleepTime = (int)(FRAME_PERIOD - timeDiff);
 	 
 	                if (sleepTime > 0) {
 	                    // ���� sleepTime > 0 ��� ������, �� ���� � �����������
 	                    try {
 	                        // ���������� ����� � ��� �� ������ sleepTime
 	                        // ����� ��� �������� � ���� �� ����� �������
 	                        Thread.sleep(sleepTime);
 	                    } catch (InterruptedException e) {}
 	                }
 	 
 	                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
 	                    // ���� sleepTime < 0 ��� ����� ��������� �������
 	                    // �������� � �� ������� ����� �� ����� �����
 	                    this.gamePanel.update();
 	                    // ��������� �������� FRAME_PERIOD, ����� �����
 	                    // ����� ������� ���������� �����
 	                    sleepTime += FRAME_PERIOD;
 	                    framesSkipped++;
 	                }
 	            }
 	        } finally {
 	        // � ������ ������, ��������� �� ������� � 
 	        //��������� ���������
 	            if (canvas != null) {
 	                surfaceHolder.unlockCanvasAndPost(canvas);
 	            }
 	        }
 	    }
 	}
 		 
}
