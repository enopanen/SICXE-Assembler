package project4enopanen;


public class HashArray {
	

public static InputLine[] hashTable;

    public HashArray()
    {
	    hashTable = new InputLine[100];
    }//end constructor

    public int hashFunction(String nameKey)
    {
	    long hashValue = 0;
	    int letter, key;

	    int length = nameKey.length();

	    for(int i = 0;  i <= length - 1; i++)
	    {
		    letter = (int)nameKey.charAt(i) - 31;
		    hashValue += letter * 103;
	    }//end for

	    key = (int)(hashValue % 100); //hashTable size is 100.

	    return key;

    }//end hashFunction()

    public void hashInsert(InputLine line)
    {
	    String name = line.label;
	    int location = hashFunction(name);

	    while(hashTable[location] != null && !hashTable[location].label.equals(name))
	    {
			    location  = (location + 1) % 100;  //hashTable size = 100 
	    }//end else
	    if(hashTable[location] == null)
	    {
		    hashTable[location] = line;

	    }//end if
	    else
	    {
		    System.out.println("********" + name + " is a duplicate********");
	    }//end if 

    }//end hashInsert()

    public int hashRetrieve(String name)
    {
	    int location = hashFunction(name);
	    
	    wLoop:
	    while(hashTable[location] != null)
	    {
		    if(hashTable[location].label.equals(name))
		    {

			    break wLoop;
		    }
		    location = (location + 1) % 100;
	    }//end while 
	    
	    if(hashTable[location] == null)
	    {
		    location = -1;
	    }//end if
	    
	    return location;
	    //return hashTable[location].address;
	    
    }//end hashRetrieve()
    
    public void printTable()
    {
	    String tmp = String.format("%-22s%-10s%-10s", "Table Location", "Label", "Address");
	    System.out.println("\n" + tmp);
	    
	    for(int i = 0; i < 100; i++)
	    {
		    if(hashTable[i] != null)
		    {
			    tmp = String.format("%-22s%-10s%-10s", i, hashTable[i].label, hashTable[i].address);
			    System.out.println(tmp);
		    }//end if
		    
	    }//end foreach
	   
    }//end printTable()
	
	


}//end class HashArray
