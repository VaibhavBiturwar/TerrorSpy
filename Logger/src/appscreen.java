import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.PointerByReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vaibhav Biturwar
 */
public class appscreen implements Runnable {
    static int i=0;
     private static final int MAX_TITLE_LENGTH = 1024;
 
     public  void run() //throws Exception //run String[] args static
	 {
          String lastTitle = "none";
          String lastProcess = "none";
          long lastChange = System.currentTimeMillis();//aplcn start hote hi
 
          while (true)
          {
               String currentTitle = getActiveWindowTitle();
               String currentProcess = getActiveWindowProcess();
               if (!lastTitle.equals(currentTitle))
               {    
                   
                    long change = System.currentTimeMillis();
                    long time = (change - lastChange) / 1000;
                   
                   /* System.out.println("Change! Last title: " + lastTitle + " lastProcess: " + lastProcess + " time: " + time + " seconds");
                    System.out.println(change);
                    System.out.println(currentTitle);*/
                    
                    long Start,End,Duration;
                    String Title;
                    
                    End=change;
                    Start=lastChange;
                    Duration=time;
                    Title=lastTitle;
                    
           
                    /*System.out.println(Start);
                    System.out.println(End);
                    System.out.println(Duration);
                    System.out.println(Title);*/
                    if(!Title.equals("none"))
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
                        String qry="INSERT INTO active_window (start,end,duration,title) VALUES('"+Start+"','"+End+"','"+Duration+"','"+Title+"')";
                        stmt.executeUpdate(qry);// if no data is recived then use executeUpdate when data uis recieved executeQuery
                        System.out.println(qry);
                        //JOptionPane.showMessageDialog(this,"Data Saved","Status",JOptionPane.INFORMATION_MESSAGE);
                        screenshot(Title);
                        table2(Title,Start);
                     }
                    catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null,ex.getMessage(),"Status",JOptionPane.ERROR_MESSAGE);
                    }
                        }
                    
                    lastChange = change;
                    lastTitle = currentTitle;
                    lastProcess = currentProcess;
               }
               try
               {
                    Thread.sleep(1000);
               }
               catch (InterruptedException ex)
               {
                    // ignore
               }
          }
     }
 
     private static String getActiveWindowTitle()
     {
          char[] buffer = new char[MAX_TITLE_LENGTH * 2];
          HWND foregroundWindow = User32DLL.GetForegroundWindow();
          User32DLL.GetWindowTextW(foregroundWindow, buffer, MAX_TITLE_LENGTH);
          String title = Native.toString(buffer);
          return title;
     }
 
     private static String getActiveWindowProcess()
     {
          char[] buffer = new char[MAX_TITLE_LENGTH * 2];
          PointerByReference pointer = new PointerByReference();
          HWND foregroundWindow = User32DLL.GetForegroundWindow();
          User32DLL.GetWindowThreadProcessId(foregroundWindow, pointer);
          Pointer process = Kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pointer.getValue());
          Psapi.GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
          String processName = Native.toString(buffer);
          return processName;
     }
 
     static class Psapi
     {
          static
          {
               Native.register("psapi");
          }
 
          public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
   }
 
     static class Kernel32
     {
          static
          {
               Native.register("kernel32");
          }
 
          public static int PROCESS_QUERY_INFORMATION = 0x0400;
          public static int PROCESS_VM_READ = 0x0010;
 
          public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
     }
 
     static class User32DLL
     {
          static
          {
               Native.register("user32");
          }
 
          public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
          public static native HWND GetForegroundWindow();
          public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
     }
     
     
     
     
     public static void screenshot(String name) throws InterruptedException
    {
        
        //while(true){
        try
        {
            Robot robot=new Robot(); //to perform events on the screen
            String format="jpg";  //format of the screenshot
            String file=name+"."+format;//name of the screen shot file
            
            Rectangle size=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()); //specifies area ,size of the screen
            BufferedImage image=robot.createScreenCapture(size);
            ImageIO.write(image, format, new File("Screenshots",file));//file name and file type
            System.out.println("Saved");
           // JOptionPane.showMessageDialog(null,"Done","Status", JOptionPane.INFORMATION_MESSAGE);
           // TimeUnit.SECONDS.sleep(5);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
          //  JOptionPane.showMessageDialog(null,"error","Status", JOptionPane.ERROR_MESSAGE);
        }
        i=i+1;
        
        //}
    }
     
     
     
     
     
    public static void table2(String title,long time)
    {   
        String temp="";
        String out="";
        Object row[]=new Object[1];
        StringBuffer words=new StringBuffer();
        
        try
        {
            Connection con;
            Statement stmt;
            String uid="root";
            String pwd="";
            
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/problem",uid,pwd);
            stmt=con.createStatement();
            //String qry="SELECT char1 FROM insertion WHERE time1 BETWEEN "+start+" and "+end;
            String qry="SELECT Keytyped FROM log";
            ResultSet res=stmt.executeQuery(qry);
         //   System.out.println(qry);
            
            while(res.next())
            {
                row[0]=res.getString("Keytyped");
                // System.out.println(row[0]);
                 temp=row[0].toString();
                 words.append(temp);
            }
            
            
            
          System.out.println(words);
            out=words.toString();
            out=out.replace("Space", " ");
            String millstr=String.valueOf(time);
           String tim=timeret(millstr);
           String dat=dateret(millstr);
          String qry2="INSERT INTO word(title,words,date,time) VALUES ('"+title+"','"+out+"','"+dat+"','"+tim+"');";
           System.out.println(qry2);
          stmt.executeUpdate(qry2);
          
         String qrydel="DELETE FROM log";
          stmt.executeUpdate(qrydel);
         
        }
        catch(Exception ex)
       {
           JOptionPane.showMessageDialog(null,ex.getMessage(),"FUNCTION",JOptionPane.INFORMATION_MESSAGE);
       }
    }
    
    public static String dateret(String milli)
    {
        long tim=Long.parseLong(milli);
        Date dt=new Date(tim);
        DateFormat dat=new SimpleDateFormat("yyyy/MM/dd");
        String dateformat=dat.format(dt);
        return dateformat;
    }
    
    public static String timeret(String milli)
    {
        long tim=Long.parseLong(milli);
        Date dt=new Date(tim);
        DateFormat dat=new SimpleDateFormat("HH:mm:ss");
        String dateformat=dat.format(dt);
        return dateformat;
    }
    
}
