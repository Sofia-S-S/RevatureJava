package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.dao.PlayerSearchDAO;
import com.app.dao.dbutil.PostgresqlConnection;
import com.app.exeption.BusinessException;
import com.app.model.Player;

public class PlayerSearchDAOImpl implements PlayerSearchDAO {

	@Override
	public Player getPlayerByContact(long contact) throws BusinessException{
		
		Player player = null;
		try (Connection connection=PostgresqlConnection.getConnection()){	
		String sql="select id,name,age,gender,team_name,dob from sport.players where contact=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setLong(1, contact);
		ResultSet resultSet=preparedStatement.executeQuery();
		if(resultSet.next()){
		player =new Player();
		player.setContact(contact);
		player.setId(resultSet.getInt("id"));
		player.setName(resultSet.getString("name"));
		player.setAge(resultSet.getInt("age"));
		player.setGender(resultSet.getString("age"));
		player.setTeam_name(resultSet.getString("team_name"));
		player.setDob(resultSet.getDate("dob"));
		}else {
			throw new BusinessException("No player found with contact "+contact);
		}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BusinessException("Some internal error occured. Please contact admin");
		}
		return player;
	}

	@Override
	public List<Player> getPlayersByGender(String gender) throws BusinessException {

		List<Player> playersList=new ArrayList<>();
		try (Connection connection=PostgresqlConnection.getConnection()){	
		String sql="select id,name,age,gender,team_name,contact, dob from sport.players where gender=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setString(1, gender);
		ResultSet resultSet=preparedStatement.executeQuery();
		while(resultSet.next()) {
			Player player =new Player();
			player.setId(resultSet.getInt("id"));
			player.setName(resultSet.getString("name"));
			player.setAge(resultSet.getInt("age"));
			player.setGender(gender);
			player.setContact(resultSet.getLong("contact"));
			player.setTeam_name(resultSet.getString("team_name"));
			player.setDob(resultSet.getDate("dob"));
			playersList.add(player);
		}
		if(playersList.size()==0)
		{
			throw new BusinessException("No Players this with gender in the DB yet");
		}
	}catch (ClassNotFoundException | SQLException e) {
		System.out.println(e); // Take off this line when app is live
		throw new BusinessException("Internal error occured contact SYSADMIN ");
	}
	return playersList;
	}

}