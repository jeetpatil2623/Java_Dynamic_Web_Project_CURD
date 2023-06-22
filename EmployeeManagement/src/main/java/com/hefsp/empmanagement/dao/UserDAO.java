package com.hefsp.empmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hefsp.empmanagement.model.User;

// This code is of Data Access Object (DAO). It is used to perform CURD operations.
public class UserDAO {

	// It code is for connecting to MYSQL Database.

	private String jdbcURL="jdbc:mysql://localhost:3306/empdata?useSSL=false";
	private String jdbcUsername="root";
	private String jdbcPassword="root";
	private String jdbc="com.mysql.jdbc.Driver";

	//Here are some queries of CURD which are stored in String variables.

	private static final String INSERT_EMP_SQL="INSERT INTO employee"+"(firstname,lastname,contactno,address) values"+"(?,?,?,?);";

	private static final String SELECT_EMP_BY_ID="select id,firstname,lastname,contactno,address where id=?";

	private static final String SELECT_ALL_EMP="SELECT * from employee";

	private static final String DELETE_EMP="DELETE FROM employee where id=?;";

	private static final String UPDATE_EMP="UPDATE employee set firstname=?,lastname=?,contactno=?,address=? where id=?;";

	// This method connects database.
	protected Connection getConnection()
	{
		Connection connection=null;
		try {
			Class.forName(jdbc);
			connection=DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	//This method is Used to insert data in database table.
	public void insertEmp(User user) throws SQLException
	{
		try(Connection connection=getConnection();
				PreparedStatement preparedStatement= connection.prepareStatement(INSERT_EMP_SQL))
		{
			preparedStatement.setString(1, user.getFirstname());
			preparedStatement.setString(2, user.getLastname());
			preparedStatement.setString(3, user.getContactno());
			preparedStatement.setString(4, user.getAddress());
			preparedStatement.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	// This method is used to update data in table.
	public boolean UpdateEmp(User user) throws SQLException
	{
		boolean update;
		try(Connection connection=getConnection();
				PreparedStatement statement= connection.prepareStatement(UPDATE_EMP))
		{
			statement.setString(1, user.getFirstname());
			statement.setString(2, user.getLastname());
			statement.setString(3, user.getContactno());
			statement.setString(4, user.getAddress());
			statement.setInt(5, user.getId());

			update=statement.executeUpdate()>0;
		}
		return update;
	}

	// This method is used to single row from table.
	public User selectEmp(int id)
	{
		User user=null;

		try(Connection connection=getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(SELECT_EMP_BY_ID);)
				{
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			ResultSet rs=preparedStatement.executeQuery();

			while(rs.next())
			{
				String firstname=rs.getString("firstname");
				String lastname=rs.getString("lastname");
				String contactno=rs.getString("contactno");
				String address=rs.getString("address");

				user=new User(firstname, lastname, contactno, address);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	// This Method return ALL Registered employees
	public List<User> selectAllEmp()
	{
		List<User> users=new ArrayList<>();

		try(Connection connection=getConnection();
				PreparedStatement preparedStatement=connection.prepareStatement(SELECT_ALL_EMP);)
				{

			System.out.println(preparedStatement);
			ResultSet rs=preparedStatement.executeQuery();

			while(rs.next())
			{
				int id=rs.getInt("id");
				String firstname=rs.getString("firstname");
				String lastname=rs.getString("lastname");
				String contactno=rs.getString("contactno");
				String address=rs.getString("address");

				users.add(new User(id,firstname, lastname, contactno, address));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	// This method is used to delete row from database.
	public boolean deleteEmp(int id) throws SQLException
	{
		boolean rowDelete;
		try(Connection connection =getConnection();
				PreparedStatement statement=connection.prepareStatement(DELETE_EMP);)
		{
			statement.setInt(1, id);
			rowDelete=statement.executeUpdate()>0;
		}
		return rowDelete;
	}
}
