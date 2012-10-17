package project4enopanen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Eric Nopanen 
 */
public class Main {

	public static void main(String[] args) throws IOException 
	{


		int c = 0;

		for(String s : args)
		{
			c++;
		}//end for 

		if(c != 2)
		{
			System.out.println("This program takes 1 input file as a command line argument.");
		}//end if
		else
		{

        		InstructionList iList = new InstructionList();
        
       			FileReader fr1 = new FileReader(args[0]);

			FileReader fr1 = new FileReader("instructions.txt");
        		BufferedReader br1 = new BufferedReader (fr1);       
        		String line = br1.readLine();
        
        		while(line != null)
        		{
            			Instruction i = new Instruction(line);
            			iList.insert(i);
     
    	        		line = br1.readLine();
        		}//end while
        
        		br1.close();
        
        		FileReader fr2 = new FileReader(args[1]);
			FileReader fr2 = new FileReader("2");
        		BufferedReader br2 = new BufferedReader(fr2);
        		line = br2.readLine();
        
			ArrayList<InputLine> q = new ArrayList<InputLine>();
			ArrayList<InputLine> list = new ArrayList<InputLine>();
			HashArray hashTable = new HashArray();


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start Pass 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~



	        	while(line != null)
        		{    
	
			    if(!line.trim().equals(""))
			    {
				line = line.toUpperCase();
				InputLine i = new InputLine(line, 0);
				list.add(i);
		
				if(!i.label.equals(""))
				{
					hashTable.hashInsert(i);
				}//end if
		
				if(i.AM.equals("="))
				{
					q.add(i);
				}//end if

				if(i.mnemonic.equals("LTORG"))
				{
					for(int j = q.size() - 1; j >= 0; j--)
					{
						InputLine tmp = q.get(j);
						i = new InputLine(tmp.operand, 1);	
						list.add(i);
						hashTable.hashInsert(i);
					}//end for

					q.clear();

				}//end if
		
			    }//end if
	
			    line = br2.readLine();
                    
	       		 }//end while
    
    		    	br2.close();
	
			for(int j = q.size() - 1; j >= 0; j--)
			{
				InputLine tmp = q.get(j);
				tmp = new InputLine(tmp.operand, 1);	
				list.add(tmp);
				hashTable.hashInsert(tmp);
			}//end for 

			q.clear();
			


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ End Pass 1 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~






//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Start Pass 2 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~




			
			DecimalFormat df3 = new DecimalFormat( "000" );
			DecimalFormat df5 = new DecimalFormat( "00000" );
			int lineCounter = 1;	
			
	
			for(InputLine i : list)
			{
				int tableLoc = -2;
			        String opString = "";	
				String rString = "";

				Instruction instruction = InstructionList.get(i.mnemonic);

				if(instruction != null && instruction.fmt1 == 2)
				{
					i.opcode = instruction.opcode;

				}//end if
				else
				{
					if(i.isImmediate)
					{
						i.isPC = false;
						if(instruction != null)	
						{
							opString = addIntToHex(instruction.opcode, 1);	
							i.opcode = opString;
						}//end if
					}//end if 	
					else if(i.isIndirect)
					{
						if(instruction != null)	
						{
							opString = addIntToHex(instruction.opcode, 2);	
							i.opcode = opString;
						}//end if
					}//end else if
					else 
					{
						if(instruction != null)	
						{
							opString = addIntToHex(instruction.opcode, 3);	
							i.opcode = opString;
						}//end if
					}//end else if


					if(i.mnemonic.equals("WORD") || i.mnemonic.equals("BYTE")) 
					{
						i.opcode = "00";
						i.isPC = false;
					}//end if

				}//end else

				if(i.opcode.length() == 1)
				{
					i.opcode = "0"	+ i.opcode;
				}//end if
					

				
			
				if(!i.operand.equals("") && !i.mnemonic.endsWith("R"))
				{	
					try
					{
						Integer.parseInt(i.operand);
					}//end try
					catch(NumberFormatException e)
					{
						if(i.AM.equals("="))
						{
							tableLoc = hashTable.hashRetrieve("=" 
								  + i.operand);
						}//end if
						else if(!i.label.equals("=" + i.operand))
						{
							tableLoc = hashTable.hashRetrieve(i.operand);
						}//end else
					}//end catch
				}//end if 
				if(i.mnemonic.endsWith("R"))	
				{
					i.isPC = false;
					char ac[] = new char[2];
					ac[0] = i.operand.charAt(0);	
					ac [1] = i.operand.charAt(2);	
					char first = 0;

					for(char c: ac)
					{
						switch(c){
						case 'A':
							first = '0';
							break;
						case 'X':
							first = '1';
							break;
						case 'L':
							first = '2';
							break;
						case 'B':
							first = '3';
							break;
						case 'S':
							first = '4';
							break;
						case 'T':
							first = '5';
							break;
						default:
							break;
						}//end switch	

						i.opcode = i.opcode + first;
						
					}//end for each
				}//end if

				opString = "";
				
				if(tableLoc >= 0)
				{
					if(i.isExtended)	
					{ 
						if(!i.isIndexed)
						{
							String disp = HashArray.hashTable[tableLoc].address;
								
							
						}//ende if	
						else
						{
							i.opcode = i.opcode + "1";
						}//end else
					
					}//end if

					if(i.isBase)
					{ 
						if(i.isIndexed)
						{

						}//ende if	
						else
						{
							i.opcode = i.opcode + "4";
						}//end else

					}//end else if

					if(i.isPC)	
					{
						if(i.isIndexed)
						{
						}//end if 
						else	
						{
							i.opcode = i.opcode + "2";
						}//end else
					}//end else

					

					rString = i.toString();

				}//end if
				else if(tableLoc == -1)
				{
					rString = "********** ERROR: Operand not found in symbol table [#5 on line " + lineCounter + "]";
					
				}//end else if
				else
				{
					
				}//end else
				
				System.out.println(df3.format(lineCounter) + " " + rString);

				lineCounter++;

			}//end foreach 
			
			hashTable.printTable();

	     	}//end else (executed only with proper # of command line args)

	}//end Main()


	public static String addIntToHex(String hex, int num)
	{

		int b = Integer.decode("#" + hex);

		b += num;

	        hex = Integer.toHexString(b);	
		
		return hex;

	}//end addIntToHex()

	

}//end Class Main

