package com.hefsp.empmanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hefsp.empmanagement.dao.UserDAO;
import com.hefsp.empmanagement.model.User;

//This code handles HTTP requests and responses for managing user data in an employee.

@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Here create user DAO Object;
	private UserDAO userDAO;

    // Here created constructor to initialize object.
    public UserServlet() {
    	this.userDAO=new UserDAO();

    }

    //This used for post REquest and REsponse
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	//This used for Get and perform operation and send  REquest and REsponse
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getServletPath();

		// Here created switch cases to handle request and supported actions
		switch (action)
		{
		case "/new"    :
			showNewForm(request,response);
			break;
		case "/insert" :
			try {
				insertEmp(request,response);
			} catch (ServletException | IOException | SQLException e1) {
				e1.printStackTrace();
			}
			break;
		case "/delete" :
			try {
				deleteEmp(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/edit"   :
			try {
				editForm(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/update" :
			try {
				updateEmp(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
			break;

		default:
			try {
				listUser(request,response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	//This method retrieves all users from the data source
	private void listUser(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, SQLException
	{
		List<User> listuser=userDAO.selectAllEmp();
		request.setAttribute("listUser", listuser);
		RequestDispatcher dispatcher=request.getRequestDispatcher("emp-list.jsp");
		dispatcher.forward(request, response);

	}
	//This method retrieves the updated user information from the request parameters.

	private void updateEmp(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, SQLException
	{
		int id=Integer.parseInt(request.getParameter("id"));
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String contactno=request.getParameter("contactno");
		String address = request.getParameter("address");

		User user=new User(firstname, lastname, contactno, address);
		userDAO.UpdateEmp(user);
		response.sendRedirect("list");
	}

	//This method retrieves the user's ID from the request parameters

	private void editForm(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, SQLException
	{
		int id=Integer.parseInt(request.getParameter("id"));
		User existUser=userDAO.selectEmp(id);
		RequestDispatcher dispatcher=request.getRequestDispatcher("emp-form.jsp");
		request.setAttribute("user", existUser);
		dispatcher.forward(request, response);

	}
	// This  method retrieves the ID of the user to be deleted from the request parameters
	private void deleteEmp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		int id=Integer.parseInt(request.getParameter("id"));
		userDAO.deleteEmp(id);
		response.sendRedirect("list");

	}
	// This method used to forward the link
	private void showNewForm(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
	{
		RequestDispatcher dispatcher=request.getRequestDispatcher("emp-form.jsp");
		dispatcher.forward(request, response);
	}

	// This method used for insert data into database
	private void insertEmp(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException, SQLException
	{
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String contactno=request.getParameter("contactno");
		String address = request.getParameter("address");

		User newUser =new User(firstname, lastname, contactno, address);

		userDAO.insertEmp(newUser);
		response.sendRedirect("list");
	}

}
