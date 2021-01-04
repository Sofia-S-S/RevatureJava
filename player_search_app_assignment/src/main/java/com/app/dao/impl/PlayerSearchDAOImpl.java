package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.app.dao.PlayerSearchDAO;
import com.app.dao.dbutil.PostresqlConnection;
import com.app.exception.BusinessException;
import com.app.model.Player;

public class PlayerSearchDAOImpl implements PlayerSearchDAO {

	@Override
	public Player getPlayerById(int id) throws BusinessException {
		Player player = null;
		System.out.println("In DAO within getPlayerById() with id = "+id);
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql = "select name,age,gender,teamname,contact,dob from roc_revature.player where id=?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println("Query Executed");
			if (resultSet.next()) {
				System.out.println("If in DAO");
				player = new Player();
				player.setId(id);
				player.setName(resultSet.getString("name"));
				player.setAge(resultSet.getInt("age"));
				player.setGender(resultSet.getString("gender"));
				player.setContact(resultSet.getLong("contact"));
				player.setTeamname(resultSet.getString("teamname"));
				player.setDob(resultSet.getDate("dob"));
			} else {
				System.out.println("else in dao");
				throw new BusinessException("No Player found with Id " + id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("exception in DAO");
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return player;
	}

	@Override
	public Player getPlayerByContact(long contact) throws BusinessException {
		Player player = null;
		try (Connection connection=PostresqlConnection.getConnection()){	
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
		player.setTeamname(resultSet.getString("team_name"));
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
	public List<Player> getAllPlayers() throws BusinessException {
		List<Player> playersList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select id,name,age,gender,teamname,contact,dob from roc_revature.player";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Player player =new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(resultSet.getString("name"));
				player.setAge(resultSet.getInt("age"));
				player.setGender(resultSet.getString("gender"));
				player.setContact(resultSet.getLong("contact"));
				player.setTeamname(resultSet.getString("teamname"));
				player.setDob(resultSet.getDate("dob"));
				playersList.add(player);
			}
			if(playersList.size()==0)
			{
				throw new BusinessException("No Players in the DB so far");
			}
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return playersList;
	}

	@Override
	public List<Player> getPlayersByAge(int age) throws BusinessException {
		List<Player> playersList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select id,name,gender,teamname,contact,dob from roc_revature.player where age=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, age);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Player player =new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(resultSet.getString("name"));
				player.setAge(age);
				player.setGender(resultSet.getString("gender"));
				player.setContact(resultSet.getLong("contact"));
				player.setTeamname(resultSet.getString("teamname"));
				player.setDob(resultSet.getDate("dob"));
				playersList.add(player);
			}
			if(playersList.size()==0)
			{
				throw new BusinessException("No Players found with age "+age);
			}
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return playersList;
	}

	@Override
	public List<Player> getPlayersByGender(String gender) throws BusinessException {
		List<Player> playersList=new ArrayList<>();
		try (Connection connection=PostresqlConnection.getConnection()){	
		String sql="select id,name,age,gender,teamname,contact, dob from roc_revature.player where gender=?";
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
			player.setTeamname(resultSet.getString("teamname"));
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


	@Override
	public List<Player> getPlayersByTeamName(String teamname) throws BusinessException {
		List<Player> playersList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select id,name,age,gender,teamname,contact,dob from roc_revature.player where name=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, teamname);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Player player =new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(resultSet.getString("name"));
				player.setAge(resultSet.getInt("age"));
				player.setGender(resultSet.getString("gender"));
				player.setContact(resultSet.getLong("contact"));
				player.setTeamname(resultSet.getString("teamname"));
				player.setDob(resultSet.getDate("dob"));
				playersList.add(player);
			}
			if(playersList.size()==0)
			{
				throw new BusinessException("No Players found with teamname "+teamname);
			}
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return playersList;
	}

	@Override
	public List<Player> getPlayersByName(String name) throws BusinessException {
		List<Player> playersList=new ArrayList<>();
		try (Connection connection = PostresqlConnection.getConnection()) {
			String sql="select id,name,age,gender,teamname,contact,dob from roc_revature.player where name=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, name);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Player player =new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(name);
				player.setAge(resultSet.getInt("age"));
				player.setGender(resultSet.getString("gender"));
				player.setContact(resultSet.getLong("contact"));
				player.setTeamname(resultSet.getString("teamname"));
				player.setDob(resultSet.getDate("dob"));
				playersList.add(player);
			}
			if(playersList.size()==0)
			{
				throw new BusinessException("No Players found with teamname "+name);
			}
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return playersList;
	}

	@Override
	public List<Player> getPlayersByDob(Date dob) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}



}
