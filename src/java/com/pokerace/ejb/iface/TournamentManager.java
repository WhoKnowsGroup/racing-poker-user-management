package com.pokerace.ejb.iface;

import javax.ejb.Local;

import com.pokerace.ejb.model.Tournament;

@Local
public interface TournamentManager {
	public Tournament findTournament(String tournamentName);
	public boolean createTournament(Tournament tournament);
}
