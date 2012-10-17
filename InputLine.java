
package project4enopanen;



public class InputLine 
{

    public String label = "";  
    public String mnemonic = ""; 
    public String AM = "";  //address mode
    public String operand = "";
    public String comment = "";
    public String address = "";
    public String opcode = "";
    public String opAddress = "";
    public boolean isPC = true; 
    public boolean isExtended = false;
    public boolean isBase = false;
    public boolean isIndexed = false;
    public boolean isImmediate = false;
    public boolean isIndirect = false;
    public static String nextAddress; //implement previous address like linked list.
    
    public InputLine(String line, int n)  // n == 0 for normal and n == 1 for literal 
    {
        if(n == 0)
	{
		if(line.trim().substring(0,1).compareTo(".") == 0)
		{
			this.comment = line;
		}//end if
		else
		{
			int l = line.length();

			if(l > 29)
			{
				try
				{
					this.label = line.substring(0, 8).trim().toUpperCase();
				}//end try
				catch(Exception e){}
				try
				{
					this.mnemonic = line.substring(9, 17).trim().toUpperCase();
				}//end try
				catch(Exception e){}
				try
				{
					this.AM = line.substring(18, 19).trim();
				}//end try
				catch(Exception e){}
				try
				{
					this.operand = line.substring(19, 29).trim();
				}//end try
				catch(Exception e){}
				
				this.comment = line.substring(29).trim();
			}//end if    
			else
			{
				try
				{
					this.label = line.substring(0, 8).trim().toUpperCase();
				}//end try
				catch(Exception e){}
				try
				{
					this.mnemonic = line.substring(9, 17).trim().toUpperCase();
				}//end try
				catch(Exception e){}
				try
				{
					this.AM = line.substring(18, 19).trim();
				}//end try
				catch(Exception e){}
				try
				{
					this.operand = line.substring(19).trim();
				}//end try
				catch(Exception e){}
			}//end else
			
			if(mnemonic.compareToIgnoreCase("START") == 0)
			{
				this.address = operand;
				nextAddress = operand;
			}//end if
			else if(mnemonic.compareToIgnoreCase("LTORG") == 0 ||
				mnemonic.compareToIgnoreCase("BASE") == 0 ||
				mnemonic.compareToIgnoreCase("END") == 0)
			{
				this.address = nextAddress;
			}//end else if
			else if(mnemonic.compareToIgnoreCase("RESW") == 0)
			{
				this.address = nextAddress;

				int a = Integer.parseInt(operand);
				int b = Integer.decode("#" + nextAddress.toString());

				b = a * 3 + b;
				nextAddress = Integer.toHexString(b);
			}//end if
			else if(mnemonic.compareToIgnoreCase("RESB") == 0)
			{
				this.address = nextAddress;
				int a = Integer.parseInt(operand);
				int b = Integer.decode("#" + nextAddress.toString());

				b += a;
				nextAddress = Integer.toHexString(b);
			}//end else if
			else if(mnemonic.compareToIgnoreCase("WORD") == 0)
			{
				this.address = nextAddress;
				int a = Integer.decode("#" + nextAddress.toString());

				a += 3;
				nextAddress = Integer.toHexString(a);
			}//end else if
			else
			{
				Instruction i = InstructionList.get(mnemonic);

				if(i == null)
				{
					System.out.println(mnemonic + "********Invalid Mnemonic********");
				}//end if
				else
				{
					this.address = nextAddress;
					int a = Integer.decode("#" + nextAddress.toString());

					a += i.fmt1;
					nextAddress = Integer.toHexString(a);   
				}//end else


				if(this.mnemonic.substring(0,1).equals("+"))
				{
					this.isExtended = true;
					this.isPC = false;
				}//end if


			}//end else

		}//end else (not comment)
		
	}//end if(n == 0)
	else if(n == 1) //for hex literal
	{
		this.label = "=" + line;
		this.operand = line;
		this.mnemonic = "BYTE";
		
		int l = line.length();
		
		if(line.charAt(0) == 'X')
		{
			l = (l - 4)/2;
		}//end if
		else
		{
			l = l - 3;
		}//end else
	
		this.address = nextAddress;
		int a = Integer.decode("#" + nextAddress.toString());

		a += l;
		nextAddress = Integer.toHexString(a); 
		
	}//end else

	if(this.operand.endsWith(",X") && this.operand.length() > 3)
	{
		this.operand = this.operand.substring(0, this.operand.length() - 2);
		this.isIndexed = true;
	}//end if  

	if(this.AM.equals("#"))
	{
		this.isImmediate = true;
	}//end if 

	if(this.AM.equals("@"))
	{
		this.isIndirect = true;	
	}//end if 
	

	if(this.address.length() < 5)
	{
		int nz = 5 - this.address.length();

		for(int i = 0; i < nz; i++)
		{
			this.address = "0" + this.address;		
		}//end for
		
	}//end if
	
	
	
    }//end InputLine()

    @Override
    public String toString()
    {
	    String rLine;
	    if(!mnemonic.equals(""))
	    {
		    rLine = String.format("%-12s%-10s%-10s%-15s%10s",
			    		  address + "  " + opcode, label, mnemonic, 
					  AM + operand + ((isIndexed)?",X":""), comment);
				   
	    }//end if
	    else
	    {
		    rLine = comment;
	    }//end else
	    
         return rLine;  
                
    }//end toString()


	
}//end class InputLine
