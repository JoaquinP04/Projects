package login.pkg;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class viewReservationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer customerId = (Integer) session.getAttribute("customerId");

        if (customerId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<resDetails> current = new ArrayList<>();
        List<resDetails> past = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/cs336", "root", "password");

            // CURRENT reservations
            String sqlCurrent =
                "SELECT r.id AS reservation_id, r.reservation_date, " +
                "s.origin, s.destination, s.departure_time, s.arrival_time, " +
                "s.travel_date, s.fare " +
                "FROM reservations r " +
                "JOIN schedules s ON r.schedule_id = s.id " +
                "WHERE r.user_id = ? AND s.travel_date >= CURDATE()";

            PreparedStatement psCurrent = conn.prepareStatement(sqlCurrent);
            psCurrent.setInt(1, customerId);
            ResultSet rsCurrent = psCurrent.executeQuery();

            while (rsCurrent.next()) {
                current.add(new resDetails(
                    rsCurrent.getInt("reservation_id"),
                    rsCurrent.getString("origin"),
                    rsCurrent.getString("destination"),
                    rsCurrent.getString("departure_time"),
                    rsCurrent.getString("arrival_time"),
                    rsCurrent.getString("travel_date"),
                    rsCurrent.getDouble("fare")
                ));
            }

            // PAST reservations
            String sqlPast =
                "SELECT r.id AS reservation_id, r.reservation_date, " +
                "s.origin, s.destination, s.departure_time, s.arrival_time, " +
                "s.travel_date, s.fare " +
                "FROM reservations r " +
                "JOIN schedules s ON r.schedule_id = s.id " +
                "WHERE r.user_id = ? AND s.travel_date < CURDATE()";

            PreparedStatement psPast = conn.prepareStatement(sqlPast);
            psPast.setInt(1, customerId);
            ResultSet rsPast = psPast.executeQuery();

            while (rsPast.next()) {
                past.add(new resDetails(
                    rsPast.getInt("reservation_id"),
                    rsPast.getString("origin"),
                    rsPast.getString("destination"),
                    rsPast.getString("departure_time"),
                    rsPast.getString("arrival_time"),
                    rsPast.getString("travel_date"),
                    rsPast.getDouble("fare")
                ));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("currentReservations", current);
        request.setAttribute("pastReservations", past);

        request.getRequestDispatcher("viewReservation.jsp").forward(request, response);
    }
}
