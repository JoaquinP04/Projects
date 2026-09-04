package login.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ApplicationDB db = new ApplicationDB();
        Connection conn = db.getConnection();

        try {
        	String query = "SELECT id, username FROM users WHERE username=? AND password=?";
        	PreparedStatement ps = conn.prepareStatement(query);
        	ps.setString(1, username);
        	ps.setString(2, password);

        	ResultSet rs = ps.executeQuery();

        	if (rs.next()) {
        	    int customerId = rs.getInt("id");
        	    String dbUsername = rs.getString("username");

        	    HttpSession session = request.getSession();
        	    session.setAttribute("customerId", customerId);
        	    session.setAttribute("username", dbUsername);

        	    response.sendRedirect("welcome.jsp");
        	} else {
        	    response.getWriter().println("Login failed. Incorrect username or password.");
        	}

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(conn);
        }
    }
}
