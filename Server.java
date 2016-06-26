
import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.plaf.OptionPaneUI;

public class Server
{
	ServerSocket ss = null;
	Socket cs = null;
	DataInputStream dis = null;
    DataOutputStream dos =null;
    
    
	public Server(){
		Socket cs = null;
		try {
			ss = new ServerSocket(2005);
		} catch (IOException e) {
			System.out.println(" ERROARE LA ServerSOCKET"+ e.getMessage());
			try {
				cs = new Socket("localhost", 2005);
				
			} catch (UnknownHostException ee) {
				System.out.println(" ERROARE LA hostSOCKET"+ e.getMessage());
				e.printStackTrace();
			} catch (IOException ee) {
				System.out.println(" ERROARE LA ioSOCKET"+ e.getMessage());
				e.printStackTrace();}
		}
		
        if(ss!=null){
            JOptionPane.showOptionDialog(null, "Se asteapta un client",
                    "Info", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new Object[] {},
                    null);
            try {
				cs = ss.accept();
			} catch (IOException e) {
				System.out.println(" ERROARE LA SOCKET"+ e.getMessage());
				e.printStackTrace();
			}
        } 
        try {
			dis = new DataInputStream(cs.getInputStream());
		} catch (IOException e) {
			System.out.println(" ERROARE LA disSOCKET"+ e.getMessage());
			e.printStackTrace();
		}
        try {
			dos = new DataOutputStream(cs.getOutputStream());
		} catch (IOException e) {
			System.out.println(" ERROARE LA dosSOCKET"+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void sendMove(String move){
		try {
                    System.out.println("trimit "+move);
			dos.writeUTF(move);
		} catch (IOException e) {
			System.out.println("Erroare la trimitere mesaj");
		}		
    }
	
	public String getMove(){
			try {
                            
				String move = dis.readUTF();
                                System.out.println("primesc "+move);
                                return move;
			} catch (IOException e) {
				System.out.println("Erroare la primire mesaj");
				return "0000";
			}
    }
}
