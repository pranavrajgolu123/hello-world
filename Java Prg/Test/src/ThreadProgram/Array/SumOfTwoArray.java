package ThreadProgram.Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SumOfTwoArray {

	int arr[][];
	int r,c;
	public SumOfTwoArray(int r, int c) {
		super();
		this.r = r;
		this.c = c;
		arr=new int[r][c];
	}
	
	int[][] getMatrix() throws IOException {
		/*Scanner input=new Scanner(System.in);*/
		BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		for(int i=0;i<r;i++) {
			String str=input.readLine();
			st=new StringTokenizer(str, " ");
			for(int j=0;j<c;j++)   
			arr[i][j]=Integer.parseInt(st.nextToken());
			
		}
		return arr;
	}
	
	int[][] findSum(int a[][], int b[][]){
		
		int temp[][]=new int[r][c];
		for(int i=0;i<r;i++) {
			for(int j=0;j<c;j++) {
				temp[i][j]=a[i][j] + b[i][j];
			}
		}
		
		return temp;
	}
	
	void displayMatrix(int res[][]) {
		
		for(int i=0;i<r;i++) {
			for(int j=0;j<c;j++) {
				System.out.print(res[i][j]+"\t");
			}
			
			System.out.println();
		}
	}
	
}

class MatrixSum{
	public static void main(String arg[]) throws IOException {
		
		SumOfTwoArray obj1=new SumOfTwoArray(3, 3);
		SumOfTwoArray obj2=new SumOfTwoArray(3, 3);
		
		int x[][],y[][],z[][];
		
		x=obj1.getMatrix();
		y=obj2.getMatrix();
		z=obj1.findSum(x, y);
		obj2.displayMatrix(z);
	}
}
