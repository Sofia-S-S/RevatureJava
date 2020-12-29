package com.app.main;

import java.util.Date;
import java.util.List;

import com.app.dao.PlayerCrudDAO;
import com.app.dao.PlayerSearchDAO;
import com.app.dao.impl.PlayerCrudDAOImpl;
import com.app.dao.impl.PlayerSearchDAOImpl;
import com.app.exeption.BusinessException;
import com.app.model.Player;



public class PlayerMain {

	public static void main(String[] args) {
		
		//8

		PlayerCrudDAO dao = new PlayerCrudDAOImpl();
		PlayerSearchDAO daos = new PlayerSearchDAOImpl();
		
		//Add player
		
		Player pl = new Player(108, "Eric", 24, 9001312321L, "M", "Blue", new Date());
		
		
		try {
			if(dao.createPlayer(pl)!=0) {
				System.out.println("Player created successfully");
			}
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			
		}
		
		// Get player by id
		
		try {
			Player player = dao.getPlayerById(108);
			if(player!=null) {
				System.out.println("\nDetails of player with id "+player.getId());
				System.out.println(player);
			}
		} catch (BusinessException e) {
			System.out.println(e);
		}
		
		// update contact of a player (by id)
		
		try {
			if(dao.updatePlayerContact(108, 1089425674L)!=0) {
				System.out.println("\nContact of the player updated successfully");

			}
		} catch (BusinessException e) {
			System.out.println(e);
		}
		
		// Get all players
		try {
			List<Player> playerList = dao.getAllPlayers();
			if(playerList!=null && playerList.size()!=0) {
			System.out.println("\n\nFound "+playerList.size()+" players in DB... Printing them all");	
			for(Player p:playerList) {
				System.out.println(p);
			}
			}
			
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		
		// Delete player with giving id
		try {
			if(dao.deletePlayer(108)!=0){
			System.out.println("\nPlayer deleted successfully");
			}
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		
		// Find player by contact
		try {
			Player player = daos.getPlayerByContact(1234567890L);
			if(player!=null) {
				System.out.println("\nDetails of player with contact "+player.getContact());
				System.out.println(player);
			}
		} catch (BusinessException e) {
			System.out.println(e);
		}
		
		// Get all players by gender
		try {
			List<Player> playerList = daos.getPlayersByGender("M");
			if(playerList!=null && playerList.size()!=0) {
			System.out.println("\n\nFound "+playerList.size()+" players in DB with giving gender... Printing them all");	
			for(Player p:playerList) {
				System.out.println(p);
			}
			}
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
/* 1)Try runnning the same app and get idea of what is going on and where
 * 2)Look into the recording and parallely try to recreate  the whole app by yourself from scratch
 * 3)Complete update and delete operations and complete PlayerSearchDAOImpl
 * 
*/
