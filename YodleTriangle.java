//package yodle;

/**
 * Triangle Problem for Yodle
 * 
 * 	@author Amit Ruparel / amitruparel91@gmail.com
 * 	@version 1.0
 */


import java.io.*;
import java.util.*;

/**
 * 
 * This is the only class of the problem. The main method does the needed job
 * 
 * @author amit
 *
 */
public class YodleTriangle
{
	/**
	 * Main method that reads the file and passes data structure formed to the method that computes max path
	 * 
	 * @param aa Not used
	 * @throws IOException
	 */
	public static void main(String aa[]) throws IOException
	{
		ArrayList<ArrayList<Integer>> li=new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> temp=new ArrayList<Integer>();
		
		//Start reading into data structures
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("triangle.txt")));
		String s=br.readLine();
		String[] integersString;
		while(s!=null)
		{
			integersString=s.trim().split(" ");
			for(String integer:integersString)
			{
				temp.add(Integer.parseInt(integer));
			}
			
			li.add((ArrayList)temp.clone());
			temp.clear();
			s=br.readLine();
		}
		
		
		System.out.println(maximumTotal(li));
	}
	
	/**
	 * 
	 * This method calculates the max path
	 * 
	 * @param triangle ArrayList representing the triangle
	 * @return Maximum path from top to bottom
	 */
	 public static int maximumTotal(ArrayList<ArrayList<Integer>> triangle) {
        if(triangle==null)
			return 0;
        int size=triangle.size();
        if(size==0)
			return 0;
		if(size==1)
			return triangle.get(0).get(0);
			
        int[] res=new int[size];
        int[] temp=new int[size];
        res[0]=triangle.get(0).get(0);
        ArrayList<Integer> currList;
        for(int i=1;i<size;i++)
        {
			currList=triangle.get(i);
			temp[0]=currList.get(0)+res[0];
			for(int j=1;j<currList.size()-1;j++)
			{
				temp[j]=Math.max(res[j]+currList.get(j),res[j-1]+currList.get(j));
				
			}
			temp[currList.size()-1]=res[i-1]+currList.get(currList.size()-1);
			System.arraycopy(temp,0,res,0,temp.length);
		}
        
        return findMax(res);
    }
    
	
	/**
	 * Calculates the Maximum element in an array
	 * @param arr Any given array
	 * @return Maximum element in arr
	 */
    public static int findMax(int[] arr)
    {
		int max=Integer.MIN_VALUE;
		for(Integer i:arr)
			max=max<i?i:max;
			
		return max;
	}
}
