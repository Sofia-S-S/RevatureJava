package com.app.service.impl;


import java.util.List;

import com.app.dao.PlayerSearchDAO;
import com.app.dao.impl.PlayerSearchDAOImpl;
import com.app.exception.BusinessException;
import com.app.model.Player;
import com.app.service.PlayerSearchService;

public class PlayerSearchServiceImpl implements PlayerSearchService {

	private PlayerSearchDAO playerSearchDAO = new PlayerSearchDAOImpl();

	@Override
	public Player getPlayerById(int id) throws BusinessException {
		Player player = null;
		System.out.println("PlayerService within getPlayerById() with id = "+id);
		if (id > 99 && id < 1000) {
			// code here for DAO
			System.out.println("In if before DAO call");
			player = playerSearchDAO.getPlayerById(id);
		
		} else {
			System.out.println("In else before throw");
			throw new BusinessException("Entered Player Id " + id + " is INVALID......");
		}
		return player;
	}

	@Override
	public Player getPlayerByContact(long contact) throws BusinessException {
		Player player=null;
		if(contact > 1000000000L && contact < 9999999999L) {
			player=playerSearchDAO.getPlayerByContact(contact);
		}else {
			throw new BusinessException("Entered contact "+contact+" is INVALID");
		}
		return player;
	}

	@Override
	public List<Player> getAllPlayers() throws BusinessException {
		List<Player> allPlayersList = null;
		allPlayersList = playerSearchDAO.getAllPlayers();
		return allPlayersList;
	}

	@Override
	public List<Player> getPlayersByAge(int age) throws BusinessException {
		List<Player> agePlayersList = null;
		if (age > 19 && age < 41) {
			// code here for DAO
			agePlayersList = playerSearchDAO.getPlayersByAge(age);
		} else {
			throw new BusinessException("Entered Player Age " + age + " is INVALID......");
		}
		return agePlayersList;
	}

	@Override
	public List<Player> getPlayersByGender(String gender) throws BusinessException {
		List<Player> playersList=null;
		System.out.println("PlayerService within getPlayersByGender() with gender "+gender);
		if (gender!=null && gender.matches("[M,F]{1}")) {
			// code here for DAO
			System.out.println("Listing all players with "+gender+" gender");
			playersList = playerSearchDAO.getPlayersByGender(gender);
		
		} 
		else {
			throw new BusinessException("Entered Player gender " + gender + " is INVALID. Choose M or F");
		}
		return playersList;		
	}

	@Override
	public List<Player> getPlayersByTeamName(String teamname) throws BusinessException {
		List<Player> teamPlayersList=null;
		if(teamname!=null && teamname.matches("[a-zA-Z]{5,20}")) {
			teamPlayersList=playerSearchDAO.getPlayersByTeamName(teamname);
		}else {
			throw new BusinessException("Entered TeamName "+teamname+" is INVALID");
		}
		return teamPlayersList;
	}

	@Override
	public List<Player> getPlayersByName(String name) throws BusinessException {
		List<Player> teamPlayersList=null;
		if(name!=null && name.matches("[a-zA-Z]{2,20}")) {
			teamPlayersList=playerSearchDAO.getPlayersByTeamName(name);
		}else {
			throw new BusinessException("Entered TeamName "+name+" is INVALID");
		}
		return teamPlayersList;
	}

	@Override
	public List<Player> getPlayersByDob(String dob) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
