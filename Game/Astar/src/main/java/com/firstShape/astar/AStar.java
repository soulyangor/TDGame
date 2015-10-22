package com.firstShape.astar;

import java.util.ArrayList;
import java.util.List;

public class AStar {
	public class Point{
		public int x, y;
		public Point parent;
		public long G, H, F;
	}
	
	List<Point> openList;
	List<Point> closeList;
	List<Point> way;
	public int map[][];
	int n;
	
	public AStar(){
		openList=new ArrayList<Point>();
		closeList=new ArrayList<Point>();
		way=new ArrayList<Point>();			
	}
		
	void createMap(int n){
		this.n=n;
		map=new int[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++) map[i][j]=0;
	}
	
	public void searchPath(int x0, int y0, int x, int y){
		boolean Complete=false;
		boolean isOpen;
		boolean isClosed;
		
		if(map[x][y]!=0) return;
		long Fmin;
		
		Point current, newPoint;
		
		newPoint=new Point();
		newPoint.parent=null;
		newPoint.G=0;
		newPoint.H=(long)(10*Math.sqrt(Math.pow(x-x0,2)+Math.pow(y-y0,2)));
		newPoint.F=newPoint.G+newPoint.H;
		newPoint.x=x0;
		newPoint.y=y0;
		
		openList.add(newPoint);
		
		while((openList.size()>0)&&(!Complete)){
			Fmin=77000;
			current=null;
			
			for (Point i:openList ) 
				if(i.F<Fmin){
					Fmin=i.F;
					current=i;
				}
			
			closeList.add(current);
			openList.remove(current);
				
			int minI, maxI, minJ, maxJ;
			if((current.x-1)<0) minI=0; else minI=current.x-1;
			if((current.y-1)<0) minJ=0; else minJ=current.y-1;
			if((current.x+2)>n) maxI=n; else maxI=current.x+2;
			if((current.y+2)>n) maxJ=n; else maxJ=current.y+2;
			
			for(int i=minI; i<maxI; i++)
				for(int j=minJ; j<maxJ; j++){
					if (map[i][j]!=0) continue;
					if((i==current.x)&&(j==current.y)) continue;
					else if(!Complete) Complete = (i == x) && (j == y);
					else break;
					isClosed=false;
					
					//поиск в закрытом списке можно использовать хэш таблицы
					for(int k=closeList.size()-1; k>=0;k--)
						if((closeList.get(k).x==i)&&(closeList.get(k).y==j)){
							isClosed=true;
							break;
						}
					if(isClosed) continue;
					isOpen=false;
					//поиск в открытом списке можно использовать хэш таблицы
					for(int k=openList.size()-1; k>=0;k--) 
						if((openList.get(k).x==i)&&(openList.get(k).y==j)){
							isOpen=true;
							long h;
							if(Math.sqrt(Math.pow(current.x-i,2)+Math.pow(current.y-j,2))>1.1) h=14;else h=10;
							if(openList.get(k).G>(current.G+h)){
								openList.get(k).parent=current;
								openList.get(k).G=current.G+h;
								openList.get(k).F=openList.get(k).G+openList.get(k).H;
							}
							break;
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
						openList.add(newPoint);
					}
				}
		}
		for(int i=openList.size()-1;i>=0;i--)
			if((openList.get(i).x==x)&&(openList.get(i).y==y)){
				current=openList.get(i);
				while(current!=null){
					way.add(current);
					current=current.parent;
				}
			}
		openList.clear();
		closeList.clear();
	}
	public void clearWay(){
		way.clear();
	}
	
	public void clearMap(){
		for(int i=0; i<n;i++)
			for(int j=0; j<n;j++)map[i][j]=0;
	}
}
