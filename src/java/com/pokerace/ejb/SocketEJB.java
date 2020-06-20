package com.pokerace.ejb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.ejb.Stateless;

import au.com.suncoastpc.conf.Configuration;

import com.pokerace.ejb.iface.SocketManager;

@Stateless
public class SocketEJB implements SocketManager {
	@Override
	public String connectSocket(String msg) {	//was connection_socket()
		try (Socket socket = new Socket(Configuration.getWebsocketTestHost(), Configuration.getWebsocketTestPort())) {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(msg);
	      
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			msg = reader.readLine();
		}
	    catch(Exception e) {
	    	System.out.println(e);
	    }
	    
		return msg;
	}
	
}
