

/**
 *  JuggleFest problem for Yodle
 * 
 * 	@author Amit Ruparel / amitruparel91@gmail.com
 * 	@version 1.0
 * 
 */

import java.io.*;
import java.util.*;

/**
 * This class represents the Circuit objects
 * @author amit
 */
class Circuit
{
	private int h;
	private int e;
	private int p;
	private int id;
	private TreeSet<Juggler> ts;
	private int capacity;
	
	/**
	 * Constructs a new Circuit object with values of hand to eye coordination (H), endurance (E) and pizzazz (P)
	 * @param h Hand to eye coordination
	 * @param e  Endurance
	 * @param p Pizzazz
	 * @param id Circuit identification
	 */
	public Circuit(int h,int e,int p,int id)
	{
		this.h=h;
		this.e=e;
		this.p=p;
		this.id=id;
		this.ts=new TreeSet<Juggler>(new JugglerComparator(this));
		
	}
	
	/**
	 * Sets the max number of jugglers that can be assigned to the circuit
	 * @param size The maxsize
	 */
	public void setSize(int size)
	{
		capacity=size;
	}
	
	/**
	 * Returns the score of the match between this circuit and given juggler
	 * @param j1 Given juggler
	 * @return the score of the match
	 */
	public int matchScore(Juggler j1)
	{
		return h*j1.getH()+p*j1.getP()+e*j1.getE();
	}
	
	/**
	 * Get the Jugglers assigned to this circuit in an ArrayList
	 * @return ArrayList of Juggler objects
	 */
	public ArrayList<Juggler> getJugglerList()
	{
		return (new ArrayList<Juggler>(ts.descendingSet()));
	}
	
	
	/**
	 * Tries to assign a given juggler to this circuit and returns value accoring to result of the trial
	 * @param j Given juggler
	 * @return 0 if successful, value of juggler replaced if some juggler was replaced, -1 if attempt was unsuccessful
	 */
	public int attemptAdd(Juggler j)
	{
		if(this.ts.size()<capacity)
		{
			this.ts.add(j);
			return 0;
		}
		else if(matchScore(this.ts.first())<matchScore(j))
		{
			int removedId=this.ts.first().getid();
			this.ts.remove(this.ts.first());
			this.ts.add(j);
			return removedId;
		}
		else
			return -1;
		
	}
	
	/**
	 * Returns string represenation of the circuit
	 */
	public String toString()
	{
		return String.format("Circuit h=%d, e=%d, p=%d \n %s",h,e,p,ts.toString());
	}
	
	/**
	 * Check if this circuit can be assigned anymore jugglers
	 * @return true if another juggler can be assigned to this circuit
	 */
	public boolean isFull()
	{
		return ts.size()==capacity;
	}
	
	/**
	 * Print the juggler assignments for a given circuit in the required form
	 * @param clist ArrayList of Circuit objects
	 */
	public void printAsssignments(ArrayList<Circuit> clist)
	{
		ArrayList<Juggler> jugglerList=new ArrayList<Juggler>(ts.descendingSet());
		System.out.print("C"+id+" ");
		boolean first=true;
		for(Juggler j:jugglerList)
		{
			if(first)
			{
				first=false;
				System.out.print(j.prefString(clist));
			}
			else
				System.out.print(", "+j.prefString(clist));
		}
		System.out.println();
	}
	
	/**
	 * Get the id of this circuit
	 * @return id
	 */
	public int getId()
	{
		return id;
	}
	
}

class Juggler
{
	int id;
	private int h;
	private int e;
	private int p;
	private int pref[];
	
	/**
	 * Constructs a new Juggler object with values of hand to eye coordination (H), endurance (E) and pizzazz (P)
	 * @param h Hand to eye coordination
	 * @param e  Endurance
	 * @param p Pizzazz
	 * @param id Juggler identification number
	 * @param prefCapacity The number of circuit preferences that a juggler can have
	 */
	public Juggler(int h,int e,int p,int id,int prefCapacity)
	{
		this.h=h;
		this.e=e;
		this.p=p;
		this.id=id;
		pref=new int[prefCapacity];
	}
	
	/**
	 * Sets the preference array of this juggler
	 * @param pref the preference array to be set
	 * @return This same juggler object
	 */
	public Juggler setPref(int[] pref)
	{
		this.pref=pref;
		return this;
	}
	
	/**
	 * Get the value for Pizzaz
	 * @return Pizzaz value
	 */
	
	public int getP() { return p; }
	/**
	 * Get the value for Endurance
	 * @return Endurance value
	 */
	public int getE() { return e; }
	
	/**
	 * Get the value for Hand to Eye coordination
	 * @return Hand to eye coordination value
	 */
	public int getH() { return h; }
	
	/**
	 * Get the id number for the juggler
	 * @return Identification number
	 */
	public int getid() { return id; }
	
	
	/**
	 * Get the i'th preferred circuit of this juggler
	 * @param i Representing which circuit for this juggler we want
	 * @return i'th circuit
	 */
	public int getPref(int i)
	{
		return pref[i];
	}
	
	/**
	 * Gets the number of preferred circuits this juggler can have
	 * @return The number of preferred circuits
	 */
	public int getPrefSize()
	{
		return pref.length;
	}
	
	/**
	 * Retusnt the string representation
	 */
	public String toString()
	{
		return String.format("J%d",id);
	}
	
	/**
	 * Returns the string representation of the preferences of this juggler with their scores in required form
	 * @param clist The ArrayList containing all the Circuit objects
	 * @return String representation of preferred circuits with scores
	 */
	public String prefString(ArrayList<Circuit> clist)
	{
		String retString="J"+id;
		Circuit[] cArr=new Circuit[pref.length];
		for(int i=0;i<pref.length;i++)
		{
			cArr[i]=clist.get(pref[i]);
		}
		//Arrays.sort(cArr,Collections.reverseOrder(new CircuitComparator(this))); ///HEREEE
		for(int i=0;i<pref.length;i++)
		{
			retString+=" C"+cArr[i].getId()+":"+cArr[i].matchScore(this);
		}
		
		return retString;
	}
	
	@Override
	public boolean equals(Object jnew)
	{
		if(!(jnew instanceof Juggler))
			return false;
		return this.id==((Juggler)jnew).id;
		
	}
	
	@Override
	public int hashCode()
	{
		return this.id;
	}
}


/**
 * This class will be used to compare and arrange various Juggler objects, given a particular circuit
 * @author amit
 *
 */
class JugglerComparator implements Comparator<Juggler>
{
	private Circuit c;
	
	/**
	 * Creates a new JugglerComparator initialized with a given Circuit with whom the jugglers' skills must be matched to be compared
	 * @param c Circuit of reference for comparision of Jugglers
	 */
	public JugglerComparator(Circuit c)
	{
		this.c=c;
	}
	
	/**
	 Return 1 if Juggler j1 is a better match for circuit c, 0 if both are equal matches and -1 is j2 is a better match
	 */
	public int compare(Juggler j1, Juggler j2) 
	{
		int compareScore= (new Integer(c.matchScore(j1))).compareTo(new Integer(c.matchScore(j2)));
		
		if(compareScore==0)
		{
			return j1.getid()==j2.getid()?0:-1;
		}
		else
			return compareScore;
	}
	
}


/**
 * Main class that uses all the other classes
 * @author amit
 *
 */
public class YodleJuggle
{
	/**
	 * Main method that carries the execution
	 * @param arg Not used
	 */
	public static void main(String arg[])
	{
		try
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("input.txt")));
			
			ArrayList<Circuit> clist=new ArrayList<Circuit>();
			ArrayList<Juggler> jlist=new ArrayList<Juggler>();
			
			int circuitCount=loadCircuits(br,clist);
			int jugglerCount=loadJugglers(br,jlist);
			
			for(Circuit c:clist)
				c.setSize(jugglerCount/circuitCount);
			
			int[] whereplaced=matchMaker(clist,jlist);
			
			for(int i=clist.size()-1;i>=0;i--)
			{
				clist.get(i).printAsssignments(clist);
			}
		}
		catch(IOException e)
		{
			System.out.println("There was some problem with I/O stream. Details:"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Matches all the Juggler objects to appropriate Circuit objects as required
	 * @param clist ArrayList of Circuit objects
	 * @param jlist ArrayList of Juggler Objects
	 */
	/*public static void matchMaker(ArrayList<Circuit> clist,ArrayList<Juggler> jlist)
	{
		int currPositions[]=new int[jlist.size()];
		boolean settled[]=new boolean[jlist.size()];
		//Arrays.fill(currPositions,0);
		
		int i=0;
		int prefPointer,pref;
		while(i<jlist.size())
		{
			if(settled[i]==false)
			{
				prefPointer=currPositions[i];
				pref=jlist.get(i).getPref(prefPointer);
				
				
				int c=clist.get(pref).attemptAdd(jlist.get(i));
				switch(c)
				{
					case 0:
						settled[i]=true;
						i++;
						break;
					case -1:
						currPositions[i]++;
						if(currPositions[i]==jlist.get(i).getPrefSize())
						{
							currPositions[i]=0;
							//Add this to whatever circuit available you can
							for(int availableCircuit=0;availableCircuit<clist.size();++availableCircuit)
							{
								//if(!clist.get(availableCircuit).isFull())
								{
									int status=clist.get(availableCircuit).attemptAdd(jlist.get(i));
									if(status==0)
									{
										settled[i]=true;
										i++;
										break;
									}
									else if(status==-1)
									{
										continue;
									}
									else
									{
										settled[i]=true;
										settled[status]=false;
										currPositions[status]=0;
										i=status;
									}
								}
							}
						}
						break;
					default:
						settled[i]=true;
						settled[c]=false;
						currPositions[c]=0;
						i=c;					
				}
			}
			else
				i++;
		}
		
		
	}*/
	public static int[] matchMaker(ArrayList<Circuit> clist,ArrayList<Juggler> jlist)
	{
		int currPositions[]=new int[jlist.size()];
		int wherePlaced[]=new int[jlist.size()];
		boolean settled[]=new boolean[jlist.size()];
		boolean allsettled=false;
		
		while(!allsettled)
		{
			
			allsettled=true;
			int prefPointer,pref=0;
			for(int i=0;i<jlist.size();i++)
			{
				if(!settled[i])
				{
					
					prefPointer=currPositions[i];
					
					boolean placed=false;
					
					while(!placed && (prefPointer<jlist.get(i).getPrefSize()))
					{
						pref=jlist.get(i).getPref(prefPointer);
						int c=clist.get(pref).attemptAdd(jlist.get(i));
						switch(c)
						{
							case 0:
								settled[i]=true;
								
								wherePlaced[i]=pref;
								
								placed=true;
								break;
							
							case -1:
								currPositions[i]++;
								prefPointer=currPositions[i];
								
								break;
								
							default:
								settled[i]=true;
								settled[c]=false;
								placed=true;
								allsettled=false;
								
								wherePlaced[i]=pref;
								
								break;
						}
					}
					
					if(!placed)
					{
						for(int j=0;j<clist.size();j++)
						{
							int c=clist.get(j).attemptAdd(jlist.get(i));
							switch(c)
							{
								case 0:
									settled[i]=true;
									placed=true;
									
									wherePlaced[i]=pref;
									
									break;
									
								case -1:
									break;
									
								default:
									settled[i]=true;
									settled[c]=false;
									placed=true;
									allsettled=false;
									
									wherePlaced[i]=pref;
									
									break;
							}

							if(placed)
								break;
						}
						
					}
					
				}
			}
		}
		
		return wherePlaced;
		
	}
	
	/**
	 * Load all the Circuit objects from file
	 * @param br BufferedReader object that has been initialized to read from required file from required spot
	 * @param clist ArrayList of Circuit objects
	 * @return Count of total number of Circuit objects in the file
	 * @throws IOException
	 */
	public static int loadCircuits(BufferedReader br, ArrayList<Circuit> clist) throws IOException
	{
		String line=br.readLine();
		int[] skills=new int[3];
		int circuitCount=0;
		while(!line.equals(""))
		{
			line=line.substring(line.indexOf(":")+1);
			skills[0]=Integer.parseInt(line.substring(0,line.indexOf(" ")));
			
			line=line.substring(line.indexOf(":")+1);
			skills[1]=Integer.parseInt(line.substring(0,line.indexOf(" ")));
			
			line=line.substring(line.indexOf(":")+1);
			skills[2]=Integer.parseInt(line.substring(0));
			
			clist.add(new Circuit(skills[0],skills[1],skills[2],circuitCount));
			
			line=br.readLine();
			circuitCount++;
		}
		
		return circuitCount;
	}
	
	/**
	 * Load all the Juggler objects from file
	 * @param br BufferedReader object that has been initialized to read Juggler objects from required file from required spot
	 * @param jlist ArrayList of Juggler objects
	 * @return Count of total number of Juggler objects in the file
	 * @throws IOException
	 */
	public static int loadJugglers(BufferedReader br, ArrayList<Juggler> jlist) throws IOException
	{
		String line=br.readLine();
		int[] skills=new int[3];
		String[] prefs;
		int jugglerCount=0;
		while(line!=null)
		{
			
			line=line.substring(line.indexOf(":")+1);
			skills[0]=Integer.parseInt(line.substring(0,line.indexOf(" ")));
			
			line=line.substring(line.indexOf(":")+1);
			skills[1]=Integer.parseInt(line.substring(0,line.indexOf(" ")));
			
			line=line.substring(line.indexOf(":")+1);
			skills[2]=Integer.parseInt(line.substring(0,line.indexOf(" ")));
			
			line=line.substring(line.indexOf(" ")+1);
			prefs=line.split(",");
			int[] prefInt=new int[prefs.length];
			
			for(int i=0;i<prefs.length;i++)
			{
				prefInt[i]=Integer.parseInt(prefs[i].substring(1));
			}
			
			jlist.add((new Juggler(skills[0],skills[1],skills[2],jugglerCount,prefs.length)).setPref(prefInt));
			jugglerCount++;
			line=br.readLine();
		}
		return jugglerCount;
	}
}
