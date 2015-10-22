package com.firstShape.astar;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

	 
	private boolean running;//флаг, указывающий на то, что игра запущена.
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
 		
    // желательный fps
 	private final static int     MAX_FPS = 30;
 	// максимальное число кадров, которые можно пропустить
 	private final static int    MAX_FRAME_SKIPS = 10;
 	// период, который занимает кадр(последовательность обновление-рисование)
 	private final static int    FRAME_PERIOD = 1000 / MAX_FPS;    
 	 
 	@Override
 	public void run() {
 	    Canvas canvas;
 	    Log.d(TAG, "Starting game loop");
 	 
 	    long beginTime;        // время начала цикла
 	    long timeDiff;        // время выполнения шага цикла
 	    int sleepTime;        // сколько мс можно спать (<0 если выполнение опаздывает)
 	    int framesSkipped;    // число кадров у которых не выполнялась операция вывода графики на экран
 	 
 	    sleepTime = 0;
 	  		 
 	    while (running) {
 	        canvas = null;
 	       // пытаемся заблокировать canvas 
 	       // для изменение картинки на поверхности
 	        try {
 	            canvas = this.surfaceHolder.lockCanvas();
 	            synchronized (surfaceHolder) {
 	                beginTime = System.currentTimeMillis();
 	                framesSkipped = 0;    // обнуляем счетчик пропущенных кадров
 	                // обновляем состояние игры
 	                this.gamePanel.update();
 	            
 	                // формируем новый кадр
 	                
					this.gamePanel.onDraw(canvas); //Вызываем метод для рисования
 	                // вычисляем время, которое прошло с момента запуска цикла
 	                timeDiff = System.currentTimeMillis() - beginTime;
 	                // вычисляем время, которое можно спать
 	                sleepTime = (int)(FRAME_PERIOD - timeDiff);
 	 
 	                if (sleepTime > 0) {
 	                    // если sleepTime > 0 все хорошо, мы идем с опережением
 	                    try {
 	                        // отправляем поток в сон на период sleepTime
 	                        // такой ход экономит к тому же заряд батареи
 	                        Thread.sleep(sleepTime);
 	                    } catch (InterruptedException e) {}
 	                }
 	 
 	                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
 	                    // если sleepTime < 0 нам нужно обновлять игровую
 	                    // ситуацию и не тратить время на вывод кадра
 	                    this.gamePanel.update();
 	                    // добавляем смещение FRAME_PERIOD, чтобы иметь
 	                    // время границы следующего кадра
 	                    sleepTime += FRAME_PERIOD;
 	                    framesSkipped++;
 	                }
 	            }
 	        } finally {
 	        // в случае ошибки, плоскость не перешла в 
 	        //требуемое состояние
 	            if (canvas != null) {
 	                surfaceHolder.unlockCanvasAndPost(canvas);
 	            }
 	        }
 	    }
 	}
 		 
}
