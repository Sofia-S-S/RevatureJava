package com.app.dao;

import java.util.List;

import com.app.exeption.BusinessException;
import com.app.model.Player;

//2

public interface PlayerCrudDAO {
	
	public int createPlayer(Player player) throws BusinessException ;//6.2.throws BusinessException 
	public void deletePlayer(int id);
	public int updatePlayerContact(int id, long newContact) throws BusinessException;
	public Player getPlayerById(int id) throws BusinessException;
	public List<Player> getAllPlayers();

}
