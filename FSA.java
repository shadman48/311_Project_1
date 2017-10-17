import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ListIterator;
import java.util.TreeMap;

public class FSA {

	public static void main(String args[]) throws IOException
	{
		
		String fileName = "Machines.txt";
		String stringStart = "******";
		String stringEnd = "&&&&&&";
		int numberOfStates = 0;
		String[] finalStates = null;
		
		String[] alphabet = null;
		char[] alpha = null;
		boolean exit = false;
		
		
		while(exit != true)
		{
			try
			{
				FileReader fileReader = new FileReader(fileName);
		        BufferedReader bufferedReader = new BufferedReader(fileReader);
		        String[][] transistions = null;
		        ArrayList<String> lines = new ArrayList<String>();
		        String line = null;
		        String nextSymbol; 
		        int count = 0;
		        boolean store = false;
		         
		        while ((line = bufferedReader.readLine()) != null) 
			    {
		        	if(line.isEmpty())
		        		count = -1;
		        	//Takes care of the top 3 conditions.
			        if(count == 0)
			        	numberOfStates = Integer.parseInt(line);
			        
					if(count == 1)
			        	finalStates = line.toString().split(" ");
			        if(count == 2)
			        {
			        	alphabet = line.toString().split(" ");
			        	transistions = new String[alphabet.length * numberOfStates][3];
			        }
			        if(line.contains("-"))
			        {
		        		transistions[count-3] = line.toString().trim().replaceAll("\\-", "").split(" ");
			        }
			        
			        
			        else if(line.contains(stringEnd))
			        {
			        	store = false;
			        	//calls methods to run
			        	processMachine(lines, alphabet, transistions, finalStates);
			        }
			        if(store == true)
			        {
			        	lines.add(line);
			        }
			        else if(line.contains(stringStart))
			        {
			        	store = true;
			        }
			        
			        count++;
			    }      
		        
		        
				bufferedReader.close();
				 
				return;
			}
			catch(IOException e)
			{
				System.out.println("Error Caught.");
			}
		}
	}
	
	
	
	private static void processMachine(ArrayList<String> list, String[] alphabet, String[][] transistions, String[] finalStates)
	{
		
		String[] alpha = alphabet;
		String initial_state = "0";
		String state = initial_state;
		String symbol = "";
		String str = "";
		boolean accepted = false;
		boolean exit = false;
		
		//Checks if list of strings are in the alphabet.
		for(int i = 0; i < list.size(); i++)
		{
			str = list.get(i);
			
			//Checks is string is in alphabet.
			for(int c = 0; c < str.length(); c++)
			{
				symbol = getNextSymbol(str, c);
				
				if(symbol != "end")
				{
					//Checks if symbol is in alphabet.
					for(int j = 0; j < alpha.length; j++)
				  	{
				  		if(symbol.equals(alpha[j]))
				  		{
				  			state = getNextState(state, symbol, transistions, transistions.length);
				  			
				  			//End of string
				  			if(str.length()-1 == c)
				  				exit = true;
				  		}
				  	}
					
				}
			}
//			must also reset exit
			if(exit == true)
			{
				//If last symbol is not a final state reject string.
				for(int f = 0; f < finalStates.length; f++)
				{
					if(finalStates[f].equals(state))
					{
						System.out.println("Accepted String: " + str);
						accepted = true;
						break;
					}
				}
					
				if(!accepted)
					{
						System.out.println("Rejected String: " + str);
					}
				state = initial_state;
				accepted = false;
				exit = false;
			}
		}
	}



	private static String getNextState(String state, String inputSymbol, String[][] transistions, int alpha) 
	{
		String nextState = "";
//		int nxtState = (state * length) + alpha;
		// curState inVale nxtState
		for(int i = 0; i < alpha; i++)
	  	{
			if(transistions[i][0].equals(state))
			{
				if(transistions[i][1].equals(inputSymbol))
				{
					nextState = transistions[i][2];
					return nextState;
				}
				
			}
	  	}
		return null;
	}



	private static String getNextSymbol(String str, int counter) 
	{
		String[] array = str.split("");
		
		if(array[counter] != null)
			return array[counter];
		else
			return "end";
	}
	
	
	
	public static void lookUpTable(String[] state, String inputSymbol, ArrayList<String> transistions)
	{
		String str = transistions.toString();
		str = str.trim().replaceAll("\\-", "");
		String[] array = str.trim().split("");
		int x = (array.length) / 3;
//		String[][] states = new String[x][x];
//		for(int i = 0; i < array.length; i = i+3)
//		{
//			states[i] = array[i];
//			for(int j = 0; j < array.length; j = j+3)
//			{
//				for(int k = 0; k < array.length; k = k+3)
//				{
//					
//				}
//			}	
//		}
		
	}
	
	
	
	
	
	
	
}



