package com.app.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.dao.PlayerCrudDAO;
import com.app.dao.dbutil.PostgresqlConnection;
import com.app.exeption.BusinessException;
import com.app.model.Player;

//3

public class PlayerCrudDAOImpl implements PlayerCrudDAO{

	@Override
	public int createPlayer(Player player) throws BusinessException {
	//5	  Java7+ STEP6 - Close connection
		int c = 0;
		try (Connection connection=PostgresqlConnection.getConnection()){ //add multi-catch clause to surrounding try
			
			//7   STEP3
			String sql="insert into sport.players(id,name,age,gender,team_name,contact,dob) values(?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, player.getId());//  number shows to which placeholder ? to pass value
			preparedStatement.setString(2, player.getName());
			preparedStatement.setInt(3, player.getAge());
			preparedStatement.setString(4, player.getGender());
			preparedStatement.setString(5, player.getTeam_name());
			preparedStatement.setLong(6, player.getContact());
			//convert util.Date to sql.date
			preparedStatement.setDate(7, new java.sql.Date(player.getDob().getTime())); 
			
			// STEP4  - Execute Query 
			c = preparedStatement.executeUpdate();
			
			} catch (ClassNotFoundException | SQLException e) {
				
				//before adding  class BusinessExeption:
				//System.out.println("Some internal error occured. Please contact admin");
				//after: 6.1
				System.out.println(e);//take off this lane when app is live
				throw new BusinessException("Some internal error occured. Please contact admin");
				// 6.2 add exception to our DAO
				
			}

		return c;
		}
		


	@Override
	public int deletePlayer(int id) throws BusinessException {
		// Task
		int d= 0;
		try (Connection connection=PostgresqlConnection.getConnection()){	
		String sql="delete from sport.players where id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		d = preparedStatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return d;
	
	}

	@Override
	public int updatePlayerContact(int id, long newContact) throws BusinessException {
		// Task
		int x = 0;
		try (Connection connection=PostgresqlConnection.getConnection()){	
		String sql="update sport.players set contact=? where id=?";
		PreparedStatement preparedStatement=connection.prepareStatement(sql);
		preparedStatement.setInt(2, id);
		preparedStatement.setLong(1, newContact);
		x = preparedStatement.executeUpdate();

		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
			
		}
		return x;
	}

	@Override
	public Player getPlayerById(int id) throws BusinessException {
		Player player = null;
		try (Connection connection=PostgresqlConnection.getConnection()){	
			String sql = "select name,age,gender,team_name,contact,dob from sport.players where id=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				//if we found a player -> instantiate player object and set values to it
				player = new Player();
				player.setId(id);
				player.setName(resultSet.getString("name"));//set name value from resultSet getString of column name
				player.setAge(resultSet.getInt("age"));
				player.setGender(resultSet.getString("age"));
				player.setTeam_name(resultSet.getString("team_name"));
				player.setContact(resultSet.getLong("contact"));
				player.setDob(resultSet.getDate("dob"));
			}else {
				throw new BusinessException("No player found with id "+id);
			}
		} catch (ClassNotFoundException | SQLException e) {
			
			System.out.println(e);//take off this lane when app is live
			throw new BusinessException("Some internal error occured. Please contact admin");
		
			
		}
		return player; //always start your code with fixing auto generated return
	}

	@Override
	public List<Player> getAllPlayers() throws BusinessException {
		List<Player> playersList=new ArrayList<>();
		try (Connection connection = PostgresqlConnection.getConnection()) {
			String sql="select id,name,age,gender,team_name,contact,dob from sport.players";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Player player =new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(resultSet.getString("name"));
				player.setAge(resultSet.getInt("age"));
				player.setGender(resultSet.getString("gender"));
				player.setContact(resultSet.getLong("contact"));
				player.setTeam_name(resultSet.getString("team_name"));
				player.setDob(resultSet.getDate("dob"));
				playersList.add(player);
			}
			if(playersList.size()==0)
			{
				throw new BusinessException("No Players in the DB yet");
			}
		}catch (ClassNotFoundException | SQLException e) {
			System.out.println(e); // Take off this line when app is live
			throw new BusinessException("Internal error occured contact SYSADMIN ");
		}
		return playersList;
	}

}