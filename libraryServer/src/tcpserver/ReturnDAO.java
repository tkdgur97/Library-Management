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

public class ReturnDAO {
	Connection con;

	public ReturnDAO() {
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

	// 반납 정보 가져오기
	public ArrayList<ReturnDTO> getReturnInfoAll() {
		String sql = "select * from bookreturn";
		PreparedStatement ps;
		ArrayList<ReturnDTO> info = null;
		try {
			ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			info = new ArrayList<>();
			while (rs.next()) {
				String id = rs.getString("id");
				String title = rs.getString("title");
				String applyDay = rs.getString("applyday");

				info.add(new ReturnDTO(id, title, applyDay));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	// 반납 확인
	public void returnBook(String[] returnCheck) {
		String sql = "delete from bookreturn where title = ?";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			for (int i = 0; i < returnCheck.length; i++) {
				ps.setString(1, returnCheck[i]);
				ps.executeUpdate();
				new BookDAO().resCancel(returnCheck[i]);
				new RentDAO().returnCheck(returnCheck[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 도서 반납
	public void returnBook(String id, String title) {
		String sql = "insert into bookreturn values(?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			// 날짜 더하기

			Calendar cal = Calendar.getInstance();

			String applyDay = df.format(cal.getTime());
			ps.setString(1, id);
			ps.setString(2, title);
			ps.setString(3, applyDay);
			ps.executeUpdate();
			new RentDAO().returnChange(title);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
