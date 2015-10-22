package com.firstShape.astar;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;

public class AStarWithHash {
	public class Point{
		public int x, y;
		public Point parent;
		public long G, H, F;
	}
		
	List<Point> way;
	public int map[][];
	int n;
	
	public Point cell;
	
	/*Hashtable<String, Point> openList;
	Hashtable<String, Point> closeList;*/
	Hashtable<String, Point> openList;
	Hashtable<String, Point> closeList;
	
	public AStarWithHash(){
		openList = new Hashtable<String, Point>();
		
		closeList = new Hashtable<String, Point>();
				
		way=new ArrayList<Point>();	
		
		cell=new Point();
	}
		
	void createMap(int n){
		this.n=n;
		map=new int[n][n];
		Random rand=new Random();
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++) if(rand.nextInt(10)==0) map[i][j]=1;
	}
			
	public Point searchPath(int x0, int y0, int x, int y){//x0,y0 - финиш, x,y - старт
		boolean Complete=false;
		boolean isOpen;
		boolean isClosed;
		
		if(map[x][y]>0) return null;
		long Fmin;
		
		Point current, newPoint;
		
		newPoint=new Point();
		newPoint.parent=null;
		newPoint.G=0;
		newPoint.H=(long)(10*Math.sqrt(Math.pow(x-x0,2)+Math.pow(y-y0,2)));
		newPoint.F=newPoint.G+newPoint.H;
		newPoint.x=x0;
		newPoint.y=y0;
		
		String key=Integer.toString(newPoint.x)+Integer.toString(newPoint.y);
		
		openList.put(key,newPoint);
		
		while((openList.size()>0)&&(!Complete)){
			Fmin=77000;
			current=null;			
			
			//используем hashTable
			 for(Entry<String, Point> entry : openList.entrySet())
				if(entry.getValue().F<Fmin){
					Fmin=entry.getValue().F;
					current=entry.getValue();
				}
			
			key=Integer.toString(current.x)+Integer.toString(current.y);
			closeList.put(key,current);
			openList.remove(key);
				
			int minI, maxI, minJ, maxJ;
			if((current.x-1)<0) minI=0; else minI=current.x-1;
			if((current.y-1)<0) minJ=0; else minJ=current.y-1;
			if((current.x+2)>n) maxI=n; else maxI=current.x+2;
			if((current.y+2)>n) maxJ=n; else maxJ=current.y+2;
			
			for(int i=minI; i<maxI; i++)
				for(int j=minJ; j<maxJ; j++){
					if (map[i][j]>0) continue;
					if((i==current.x)&&(j==current.y)) continue;
					else if(!Complete) Complete = (i == x) && (j == y);
					else break;
					isClosed=false;
					
					//поиск в закрытом списке хэш таблицы
					String tmpKey=Integer.toString(i)+Integer.toString(j);
					if(closeList.get(tmpKey)!=null)isClosed=true;
					
					if(isClosed) continue;
					isOpen=false;
					//поиск в открытом списке хэш таблицы
					if(openList.get(tmpKey)!=null){
						isOpen=true;
						long h;
						Point temp=openList.get(tmpKey);
						if(Math.sqrt(Math.pow(current.x-i,2)+Math.pow(current.y-j,2))>1.1) h=14;else h=10;
						if(temp.G>(current.G+h)){
							temp.parent=current;
							temp.G=current.G+h;
							temp.F=temp.G+temp.H;
						}
					}
					if(!isOpen){
						newPoint=new Point();
						newPoint.parent=current;
						newPoint.x=i;
						newPoint.y=j;
						long h;
						if(Math.sqrt(Math.pow(current.x-i,2)+Math.pow(current.y-j,2))>1.1) h=14;else h=10;
						newPoint.G=current.G+h;
						newPoint.H=(long)(10*Math.sqrt(Math.pow(x-i,2)+Math.pow(y-j,2)));
						newPoint.F=newPoint.G+newPoint.H;
						openList.put(tmpKey,newPoint);
					}
				}
		}
			
		key=Integer.toString(x)+Integer.toString(y);
			/*if(openList.get(key)!=null){
				current=openList.get(key);
				while(current!=null){
					way.add(current);
					current=current.parent;
				}
			}*/
		/*openList.clear();
		closeList.clear();*/
		cell=openList.get(key).parent;
		return openList.get(key);// возвращает первую точку оптимального пути 
	}
	
	public Point search4Path(int x0, int y0, int x, int y){//x0,y0 - финиш, x,y - старт
		boolean Complete=false;
		boolean isOpen;
		boolean isClosed;
		
		if(map[x][y]!=0) return null;
		long Fmin;
		
		Point current, newPoint;
		
		newPoint=new Point();
		newPoint.parent=null;
		newPoint.G=0;
		newPoint.H=(long)(10*Math.sqrt(Math.pow(x-x0,2)+Math.pow(y-y0,2)));
		newPoint.F=newPoint.G+newPoint.H;
		newPoint.x=x0;
		newPoint.y=y0;
		
		String key=Integer.toString(newPoint.x)+Integer.toString(newPoint.y);
		
		openList.put(key,newPoint);
		
		while((openList.size()>0)&&(!Complete)){
			Fmin=77000;
			current=null;			
			
			//используем hashTable
			 for(Entry<String, Point> entry : openList.entrySet())
				if(entry.getValue().F<Fmin){
					Fmin=entry.getValue().F;
					current=entry.getValue();
				}
			
			key=Integer.toString(current.x)+Integer.toString(current.y);
			closeList.put(key,current);
			openList.remove(key);
				
			int minI, maxI, minJ, maxJ;
			if((current.x-1)<0) minI=0; else minI=current.x-1;
			if((current.y-1)<0) minJ=0; else minJ=current.y-1;
			if((current.x+2)>n) maxI=n; else maxI=current.x+2;
			if((current.y+2)>n) maxJ=n; else maxJ=current.y+2;
			
			for(int i=minI; i<maxI; i++)
				for(int j=minJ; j<maxJ; j++){
					if (map[i][j]>0) continue;
					if((i==current.x)&&(j==current.y)) continue;
					if((Math.abs(i-current.x)==1)&&(Math.abs(j-current.y)==1)) continue;
					else if(!Complete) Complete = (i == x) && (j == y);
					else break;
					isClosed=false;
					
					//поиск в закрытом списке хэш таблицы
					String tmpKey=Integer.toString(i)+Integer.toString(j);
					if(closeList.get(tmpKey)!=null)isClosed=true;
					
					if(isClosed) continue;
					isOpen=false;
					//поиск в открытом списке хэш таблицы
					if(openList.get(tmpKey)!=null){
						isOpen=true;
						long h;
						Point temp=openList.get(tmpKey);
						if(Math.sqrt(Math.pow(current.x-i,2)+Math.pow(current.y-j,2))>1.1) h=14;else h=10;
						if(temp.G>(current.G+h)){
							temp.parent=current;
							temp.G=current.G+h;
							temp.F=temp.G+temp.H;
						}
					}
					if(!isOpen){
						newPoint=new Point();
						newPoint.parent=current;
						newPoint.x=i;
						newPoint.y=j;
						long h;
						if(Math.sqrt(Math.pow(current.x-i,2)+Math.pow(current.y-j,2))>1.1) h=14;else h=10;
						newPoint.G=current.G+h;
						newPoint.H=(long)(10*Math.sqrt(Math.pow(x-i,2)+Math.pow(y-j,2)));
						newPoint.F=newPoint.G+newPoint.H;
						openList.put(tmpKey,newPoint);
					}
				}
		}
			
		key=Integer.toString(x)+Integer.toString(y);
			/*if(openList.get(key)!=null){
				current=openList.get(key);
				while(current!=null){
					way.add(current);
					current=current.parent;
				}
			}*/
		/*openList.clear();
		closeList.clear();*/
		cell=openList.get(key).parent;
		return openList.get(key);// возвращает первую точку оптимального пути 
	}
	
	public void clearWay(){
		way.clear();
		openList.clear();
		closeList.clear();
	/*	for(int i=0;i<n;i++)
			for(int j=0;j<n;j++) if (map[i][j]==2) map[i][j]=0;*/
	}
	
	public void clearMap(){
		for(int i=0; i<n;i++)
			for(int j=0; j<n;j++)map[i][j]=0;
	}
}
