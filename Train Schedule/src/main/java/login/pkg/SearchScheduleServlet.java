package login.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchScheduleServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // SESSION CHECK
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("customerId") == null) {
            response.sendRedirect("logout.jsp");
            return;
        }

        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String travelDate = request.getParameter("travelDate");

        List<TrainSchedule> schedules = new ArrayList<>();

        try {
            ApplicationDB db = new ApplicationDB();
            Connection conn = db.getConnection();

            String sql = "SELECT * FROM schedules WHERE origin=? AND destination=? AND travel_date=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, origin);
            ps.setString(2, destination);
            ps.setString(3, travelDate);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TrainSchedule ts = new TrainSchedule();
                ts.setId(rs.getInt("id"));
                ts.setOrigin(rs.getString("origin"));
                ts.setDestination(rs.getString("destination"));
                ts.setTravelDate(rs.getString("travel_date"));
                ts.setDepartureTime(rs.getString("departure_time"));
                ts.setArrivalTime(rs.getString("arrival_time"));
                ts.setFare(rs.getDouble("fare"));
                schedules.add(ts);
            }

            db.closeConnection(conn);

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("results.jsp").forward(request, response);
    }
}
