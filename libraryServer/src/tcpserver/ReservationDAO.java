package tcpserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationDAO {
	Connection con;

	public ReservationDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/library";
			String user = "root";
			String password = "1234";
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 예약 정보 가져오기
	public ArrayList<ReservationDTO> getUserReservationAll() {
		String sql = "select * from reservation";
		PreparedStatement ps;
		ArrayList<ReservationDTO> info = null;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			info = new ArrayList<>();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String date = rs.getString("date");
				info.add(new ReservationDTO(id, title, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	public String makeReservation(String id, String title, String resDate) {

		String sql = "insert into reservation values (?,?,?)";
		PreparedStatement ps;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, title);
			ps.setString(3, resDate);

			result = ps.executeUpdate();
			new BookDAO().rentBook(title);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (result == 1) {
			return "성공";
		} else {
			return "실패";
		}
	}

	// 유저 예약정보 가져오기
	public ArrayList<ReservationDTO> getUserReservation(String id) {
		String sql = "select * from reservation where id = ?";
		PreparedStatement ps;
		ArrayList<ReservationDTO> list = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			list = new ArrayList<>();
			while (rs.next()) {
				id = rs.getString("id");
				String title = rs.getString("title");
				String date = rs.getString("date");
				list.add(new ReservationDTO(id, title, date));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	// 예약 취소하기
	public void reservationCancel(String id, String title) {
		String sql = "delete from reservation where id =? and title =?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, title);
			ps.executeUpdate();
			new BookDAO().resCancel(title);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
