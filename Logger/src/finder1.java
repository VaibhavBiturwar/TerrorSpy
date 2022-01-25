
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
public class finder1 {
   
//     public class finder1{
public  void send()
//    public static void main(String [] args)
         {
    //   String [] offensive ={"bomb","blood","murder"};
       // while(true)  
       {
        Object row[]=new Object[1];
        Object rowof[]=new Object[1];
       
        
        try
        {
            Connection con;
            Statement stmt,stmt1;
            String uid="root";
            String pwd="";
            
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/problem",uid,pwd);
            stmt=con.createStatement();
            stmt1=con.createStatement();
            //String qry="SELECT char1 FROM insertion WHERE time1 BETWEEN "+start+" and "+end;
            String qry="SELECT words FROM word";
            ResultSet res=stmt.executeQuery(qry);
            
         //   System.out.println(qry);
            String temp="";
          String qry1;
          ResultSet res1;
         //   while(true)  
            while(res.next())
            {
                qry1="SELECT words FROM offense";
                res1=stmt1.executeQuery(qry1);
                while(res1.next())
                {
                    if(res.getString("words").contains(res1.getString("words")))
                    {
                        System.out.println("offensive found "+res1.getString("words"));
                        mail();
                        
                    }
                }
                
            }
         
        }
        catch(Exception ex)
       {
           JOptionPane.showMessageDialog(null,ex.getMessage(),"FUNCTION",JOptionPane.INFORMATION_MESSAGE);
       }  
        
       }
    }
     
    public static  void mail ()
    {

            String Msg;
        
        //sender
        final String username = "xyz41947@gmail.com";
        final String password = "abxy123456";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.setProperty("mail.smtp.tls.trust", "smtp.gmail.com");//ssl

        Session session;
        session = Session.getInstance(props, 
                new javax.mail.Authenticator() {
            
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {

            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));//ur email
            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse("vaibhavbiturwar@gmail.com"));//u will send to   reciever
            message.setSubject("Suspecious Activity Detected");
            message.setText("Suspecious Activity Detected");
            Transport.send(message);
         //   Msg="true";
            System.out.println("sent");
    	   // return Msg;

        } catch (Exception e) {
        //  System.out.print("*************************************************************************************************************************************************************************************************");
        //    JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
 
        }
        }
    } 
     
    
    

