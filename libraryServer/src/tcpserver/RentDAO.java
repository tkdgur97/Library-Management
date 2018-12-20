package tcpserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RentDAO {
	Connection con;

	public RentDAO() {
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

	// 개인 대출 정보 가져오기
	public ArrayList<RentDTO> getUserRent(String id) {
		String sql = "select * from rent where id = ?";
		PreparedStatement ps;
		ArrayList<RentDTO> info = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			info = new ArrayList<>();
			while (rs.next()) {
				id = rs.getString("id");
				String title = rs.getString("title");
				String rentDay = rs.getString("rentDay");
				String returnDay = rs.getString("returnDay");
				String extensionDay = rs.getString("extensionDay");
				String returnCheck = rs.getString("returnCheck");
				info.add(new RentDTO(id, title, rentDay, returnDay, extensionDay, returnCheck));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	// 대출 하기
	public String rentBook(String id, String title, String rentDay) {
		String sql = "insert into rent values (?,?,?,?,?,?)";

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		Date date;
		int result = 0;
		try {
			date = df.parse(rentDay);

			// 날짜 더하기

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, 7);
//			cal.add(Calendar.MONTH, 2);
			String returnDay = df.format(cal.getTime());
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, title);
			ps.setString(3, rentDay);
			ps.setString(4, returnDay);
			ps.setString(5, "0");
			ps.setString(6, "N");

			result = ps.executeUpdate();
			new ReservationDAO().reservationCancel(id, title);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result == 1) {
			if (new BookDAO().rentBook(title) == 1) {
				return "성공";

			} else {
				return "실패";
			}
		} else {
			return "실패";
		}

	}

	public ArrayList<RentDTO> getRentInfoAll() {
		String sql = "select * from rent";
		PreparedStatement ps;
		ArrayList<RentDTO> info = null;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			info = new ArrayList<>();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String rentDay = rs.getString("rentday");
				String returnDay = rs.getString("returnday");
				String extensionDay = rs.getString("extensionDay");
				String returnCheck = rs.getString("returnCheck");
				info.add(new RentDTO(id, title, rentDay, returnDay, extensionDay, returnCheck));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	public void bookExtension(String title, String returnDay, String extensionDay) {
		String sql = "update rent set returnday = ?,extensionday = ? where title = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, returnDay);
			ps.setString(2, extensionDay);
			ps.setString(3, title);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 반납 확인으로 대출 목록에서 삭제
	public void returnCheck(String title) {
		String sql = "delete from rent where title = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, title);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//유저가 반납 신청시 반납신청 값 변경
	public void returnChange(String title) {
		String sql = "update rent set returnCheck = ? where title = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, "Y");
			ps.setString(2, title);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
