package tcpserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MemberDAO {
	Connection conn;

	public MemberDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/library";
			String user = "root";
			String password = "1234";
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 회원가입하기
	public String signUp(MemberDTO dto) {
		String sql = "insert into members values (?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps;
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPw());
			ps.setString(3, dto.getName());
			ps.setString(4, dto.getTel());
			ps.setString(5, dto.getAddress());
			ps.setString(6, "0");
			ps.setString(7, "0");
			ps.setString(8, "0");
			ps.setString(9, dto.getRrn());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (result == 1) {
			return "성공";
		} else {
			return "실패";
		}

	}

//	 전체 회원정보 가져오기
	public ArrayList<MemberDTO> getUserInfoAll() {
		String sql = "select * from members";
		PreparedStatement ps;
		ArrayList<MemberDTO> info = null;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			info = new ArrayList<MemberDTO>();
			while (rs.next()) {
				String id = rs.getString(1);
				String pw = rs.getString(2);
				String name = rs.getString(3);
				String tel = rs.getString(4);
				String address = rs.getString(5);
				String bookrentcurrent = rs.getString(6);
				String bookrentcumlative = rs.getString(7);
				String booklate = rs.getString(8);
				String rrn = rs.getString(9);
				info.add(new MemberDTO(id, pw, name, tel, address, bookrentcurrent, bookrentcumlative, booklate, rrn));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return info;

	}

	public MemberDTO userCheck(String id, String pw) {

		// 로그인한 회원 정보 검색
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			// SQL문 실행(Select)
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				dto = new MemberDTO();
				if (id.equals(rs.getString(1))) {
					if (pw.equals(rs.getString(2))) {
						String userId = rs.getString(1);
						String userPw = rs.getString(2);
						String userName = rs.getString(3);
						String usersSN = rs.getString(4);
						String userTel = rs.getString(5);
						String userAdd = rs.getString(6);
						String userIma = rs.getString(7);

						dto.setId(userId);
						dto.setPw(userPw);
						dto.setName(userName);
						dto.setRrn(usersSN);
						dto.setTel(userTel);
						dto.setAddress(userAdd);
						dto.setImage(userIma);

						// 로그인 성공 메시지 전달
						JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
						return dto;
					} else if (!(pw.equals(rs.getString("pw")))) { // 비밀번호 오류
						dto.setId("pwEr");
						return dto;
					}
				} else if (!(id.equals(rs.getString(1)))) {
					dto.setId("idEr");
					return dto;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 비밀 번호 변경
	public void userPwChange(String id, String pw) {
		// 로그인한 회원정보 검색
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			// SQL문 실행(Select)
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (id.equals(rs.getString(1))) {

					// 회원정보 수정(비밀번호 변경)
					String sql1 = "update members set pw = ? where id = ?";
					PreparedStatement ps1 = conn.prepareStatement(sql1);
					ps1.setString(1, pw);
					ps1.setString(2, id);
					// SQL문 실행(Update)
					ps1.executeUpdate();
					// 비밀번호 성공 메시지 전달
					JOptionPane.showMessageDialog(null, "비밀번호 변경이 완료되었습니다. \n" + "다시 로그인을 진행해 주세요", "비밀번호 변경 성공",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 회원 정보 변경
	public MemberDTO userInfoChange(String[] userInfo) {
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userInfo[0]); // userInfo --> id
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				dto = new MemberDTO();

				if (userInfo[0].equals(rs.getString(1))) {
					String sql1 = "update members set name = ?, tel = ?, address = ? where id = ?";
					PreparedStatement ps1 = conn.prepareStatement(sql1);
					ps1.setString(1, userInfo[1]); // userInfo[1] --> name
					ps1.setString(2, userInfo[2]); // userInfo[2] --> tel
					ps1.setString(3, userInfo[3]); // userInfo[3] --> addr
					ps1.setString(4, userInfo[0]); // userInfo[0] --> id
					ps1.executeUpdate();

					String userId = rs.getString(1);
					String userPw = rs.getString(2);
					String userName = rs.getString(3);
					String usersSN = rs.getString(4);
					String userTel = rs.getString(5);
					String userAdd = rs.getString(6);
					String userIma = rs.getString(7);

					dto.setId(userId);
					dto.setPw(userPw);
					dto.setName(userName);
					dto.setRrn(usersSN);
					dto.setTel(userTel);
					dto.setAddress(userAdd);
					dto.setImage(userIma);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 회원 탈퇴 처리
	public void userDelete(String id) {
		// 로그인한 회원정보 검색
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			// SQL문 실행(Select)
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				if (id.equals(rs.getString(1))) {
					String sql1 = "delete from members where id = ?";
					PreparedStatement ps1 = conn.prepareStatement(sql1);
					ps1.setString(1, id);
					ps1.executeUpdate();
					// 회원 탈퇴 메시지
					JOptionPane.showMessageDialog(null, "회원탈퇴를 완료하였습니다.", "회원탈퇴 완료", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 회원가입 아이디 중복확인
	public String idOverlapCheck(String id) {
		// 로그인한 회원정보 검색
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		String result = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			// SQL문 실행(Select)
			ResultSet rs = ps.executeQuery();
			result = String.valueOf(rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		} // 검색
		return result;
	}

	// 로그인 아이디 확인 & 비밀번호 확인
	public String loginCheck(String id, String pw) {
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		String npw = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			// SQL문 실행(Select)
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				npw = rs.getString("pw");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} // 검색

		// 아이디가 없을 경우
		if (npw == null) {
			return "NOID";

			// 비밀번호가 맞을 경우
		} else if (pw.equals(npw)) {
			return "PASS";

			// 아이디만 맞을 경우
		} else {
			return "NOPW";
		}

	}

	// 개인정보 가져오기
	public MemberDTO getUserInfo(String id) {
		String sql = "select * from members where id = ?";
		PreparedStatement ps;
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String pw = rs.getString(2);
				String name = rs.getString(3);
				String tel = rs.getString(4);
				String address = rs.getString(5);
				String bookrentcurrent = rs.getString(6);
				String bookrentcumlative = rs.getString(7);
				String booklate = rs.getString(8);
				String rrn = rs.getString(9);
				dto = new MemberDTO(id, pw, name, tel, address, bookrentcurrent, bookrentcumlative, booklate, rrn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dto;
	}

	// 개인정보 수정
	public String userInfoChange(String id, String name, String tel, String address) {
		String sql = "update members set name=?, tel=?,address=? where id=?";
		PreparedStatement ps;
		String result = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, tel);
			ps.setString(3, address);
			ps.setString(4, id);
			if (ps.executeUpdate() == 1) {
				result = "성공";
			} else {
				result = "실패";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	// 개인정보 비밀번호 변경
	public String pwChange(String id, String pw) {
		String sql = "update members set pw = ? where id=?";
		PreparedStatement ps;
		String result = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, pw);
			ps.setString(2, id);
			if (ps.executeUpdate() == 1) {
				result = "성공";
			} else {
				result = "실패";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	// 회원 탈퇴
	public String withdrawal(String id) {
		String sql = "delete from members where id = ?";
		PreparedStatement ps;
		String result = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			if (ps.executeUpdate() == 1) {
				result = "성공";
			} else {
				result = "실패";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	// 아이디 찾기
	public String idFind(String name, String tel) {
		String sql = "select id from members where name = ? and tel = ?";
		PreparedStatement ps;
		String id = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, tel);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	// 비밀번호 찾기
	public String pwFind(String id, String name) {
		String sql = "select pw from members where id = ? and name = ?";
		PreparedStatement ps;
		String pw = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pw = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pw;
	}

	// 관리자 유저 정보 변경
	public String userInfoChange_admin(String id, String pw, String name, String tel, String address) {
		String sql = "update members set name=?,pw=?, tel=?,address=? where id=?";
		PreparedStatement ps;
		String result = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, pw);
			ps.setString(3, tel);
			ps.setString(4, address);
			ps.setString(5, id);
			if (ps.executeUpdate() == 1) {
				result = "성공";
			} else {
				result = "실패";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
}
