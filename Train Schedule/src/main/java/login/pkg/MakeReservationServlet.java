package login.pkg;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MakeReservationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validate session FIRST
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int customerId = (int) session.getAttribute("customerId");

        // Validate scheduleId
        String scheduleIdStr = request.getParameter("scheduleId");
        if (scheduleIdStr == null || scheduleIdStr.isEmpty()) {
            response.getWriter().println("Error: scheduleId is missing.");
            return;
        }

        int scheduleId = Integer.parseInt(scheduleIdStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cs336", "root", "password");

            String sqlInsert = "INSERT INTO reservations (user_id, schedule_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sqlInsert);
            ps.setInt(1, customerId);
            ps.setInt(2, scheduleId);

            ps.executeUpdate();
            conn.close();

            response.sendRedirect("viewReservationsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error making reservation.");
        }
    }
}
