package com.app.main;

import java.util.Date;

import com.app.dao.PlayerCrudDAO;
import com.app.dao.impl.PlayerCrudDAOImpl;
import com.app.exeption.BusinessException;
import com.app.model.Player;



public class PlayerMain {

	public static void main(String[] args) {
		
		//8

		PlayerCrudDAO dao = new PlayerCrudDAOImpl();
		
		//Add player
		
//		Player pl = new Player(108, "Eric", 24, 9001312321L, "M", "Blue", new Date());
//		
//		
//		try {
//			if(dao.createPlayer(pl)!=0) {
//				System.out.println("Player created successfully");
//			}
//		} catch (BusinessException e) {
//			System.out.println(e.getMessage());
//			
//		}
		
		// Get player by id
		
		try {
			Player player = dao.getPlayerById(108);
			if(player!=null) {
				System.out.println("Details of player with id "+player.getId());
				System.out.println(player);
			}
		} catch (BusinessException e) {
			System.out.println(e);
		}
		
		// update contact of a player (by id)
		
		try {
			if(dao.updatePlayerContact(108, 1089425674L)!=0) {
				System.out.println("Contact of the player updated successfully");

			}
		} catch (BusinessException e) {
			System.out.println(e);
		}
	}

}
