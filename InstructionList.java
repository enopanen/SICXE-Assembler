
package project4enopanen;

import java.util.ArrayList;



public class InstructionList 
{
	
	private static ArrayList<Instruction> list = new ArrayList<Instruction>();

    	public InstructionList()
    	{
    	}//end constructor
    
    	public void insert(Instruction i)
    	{
      	      list.add(i);   
    	}//end insert()
   
    	public static Instruction get(String key)
    	{
      	      	Instruction instruction = null;
	    
	      	for(Instruction i : list)
	   	{
			if(i.mnemonic.equals(key))
		   	{
				   instruction = i;
		   	}//end if
	   	}//end foreach
	    

	   return instruction;

	 }//end get()	

}//end class InstructionList
