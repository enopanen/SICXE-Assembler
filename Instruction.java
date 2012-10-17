package project4enopanen;

import java.util.StringTokenizer;


public class Instruction 	
{
	
public String mnemonic, opcode;
    public int fmt1, fmt2;
    
    public Instruction(String line)
    {   
        StringTokenizer sTok = new StringTokenizer(line);
        
        this.mnemonic = sTok.nextToken();
        this.opcode = sTok.nextToken();
        this.fmt1 = Integer.parseInt(sTok.nextToken());
        this.fmt2 = Integer.parseInt(sTok.nextToken());
        
    }//end constructor()
    
    public String mnemonic()
    {
        return mnemonic;
    }//end mnemonic()
    
    public String opcode()
    {
        return opcode;
    }//end opcode()
    
    public int fmt1()
    {
        return fmt1;
    }//end fmt1()
    
    public int fmt2()
    {
        return fmt2;
    }//end fmt2()
    
    @Override
    public String toString()
    {
        return mnemonic + " " + opcode + " " + fmt1 + " " + fmt2;
    }//end toString()




}//end class Instruction
