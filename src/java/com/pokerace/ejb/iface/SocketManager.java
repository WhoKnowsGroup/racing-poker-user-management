package com.pokerace.ejb.iface;

import javax.ejb.Local;

@Local
public interface SocketManager {
	public String connectSocket(String msg);
}
