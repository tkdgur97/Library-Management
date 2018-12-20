package tcpserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.Socket;
import java.util.ArrayList;

import java.util.TreeMap;

@SuppressWarnings("unchecked")
public class TCPClient {
	Socket socket;

	public TCPClient() {
		try {

//			socket = new Socket("35.243.191.233", 9400);
			socket = new Socket("localhost", 9400);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 회원가입 id 중복방지 확인
	public String idOverlapCheck(String id) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("idOverlapCheck\n" + id);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			input.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 회원가입 정보 전송
	public String signUp(MemberDTO dto) {

		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("signUp");
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(dto);
			oos.flush();
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			oos.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 로그인 확인
	public String loginCheck(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("loginCheck\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 회원 개인정보 가져오기
	public MemberDTO getUserInfo(String id) {
		PrintWriter out;
		MemberDTO dto = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getUserInfo\n" + id);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			dto = (MemberDTO) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	// 아이디 찾기
	public String idFind(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("idFind\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 비밀번호 찾기
	public String pwFind(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("pwFind\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 책 등록하기
	public void bookRegist(String info) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("bookRegist\n" + info);
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 전체회원 정보 얻어오기
	public ArrayList<MemberDTO> getUserInfoAll() {
		PrintWriter out;
		ArrayList<MemberDTO> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getUserInfoAll");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<MemberDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	// 도서 정보 얻어오기
	public ArrayList<BookDTO> getBookInfo() {

		PrintWriter out;
		ArrayList<BookDTO> list = null;
		;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getBookInfo");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<BookDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 책 종류 갖고오기
	public TreeMap<String, Integer> getTypeOfBook() {
		PrintWriter out;
		TreeMap<String, Integer> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getTypeOfBook");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

			list = (TreeMap<String, Integer>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	// 유저 정보 변경
	public String userInfoChange(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("userInfoChange\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 유저 이미지 올리기
	public String uploadUserImage(String id, String path) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("uploadUserImage\n" + id);
			byte buffer[] = new byte[2048];
			File imgfile = new File(path);
			String flen = String.valueOf(imgfile.length());
			String header = "0000000000".substring(0, 10 - flen.length()) + flen;

			FileInputStream fis = new FileInputStream(imgfile);
			OutputStream os = socket.getOutputStream();
			os.write(header.getBytes());
			while (fis.available() > 0) {
				int readsz = fis.read(buffer);
				os.write(buffer, 0, readsz);
			}
			out.close();
			fis.close();
			os.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	// 개인정보 수정 비밀번호 변경
	public String pwChange(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("pwChange\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 회원탈퇴
	public String withdrawal(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("withdrawal\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	// 유저 정보 변경
	public String userInfoChange_admin(String info) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("userInfoChange_admin\n" + info);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 개인 예약 정보 가져오기
	public ArrayList<ReservationDTO> getUserReservation(String id) {
		PrintWriter out;
		ArrayList<ReservationDTO> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getUserReservation\n" + id);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<ReservationDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	// 예약 정보 가져오기
	public ArrayList<ReservationDTO> getUserReservationAll() {
		PrintWriter out;
		ArrayList<ReservationDTO> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getUserReservationAll");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<ReservationDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;

	}

	// 개인 대출 정보 가져오기
	public ArrayList<RentDTO> getUserRent(String id) {
		PrintWriter out;
		ArrayList<RentDTO> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getUserRent\n" + id);
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<RentDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 대출 하기
	public String rentBook(String id, String title, String date) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("rentBook\n" + id + "\n" + title + "\n" + date);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 예약하기
	public String makeReservation(String id, String title, String resDate) {
		PrintWriter out;
		String result = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("makeReservation\n" + id + "\n" + title + "\n" + resDate);
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			result = input.readLine();
			out.close();
			input.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 대출 정보 가져오기
	public ArrayList<RentDTO> getRentInfoAll() {
		PrintWriter out;
		ArrayList<RentDTO> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getRentInfoAll\n");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<RentDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 예약 취소
	public void reservationCancel(String info) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("reservationCancel\n" + info);
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 반납 정보 가져오기
	public ArrayList<ReturnDTO> getReturnInfoAll() {
		PrintWriter out;
		ArrayList<ReturnDTO> list = null;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("getReturnInfoAll\n");
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (ArrayList<ReturnDTO>) ois.readObject();
			out.close();
			ois.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	// 반납 처리
	public void returnBook_admin(String[] bookReturn) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("returnBook_admin\n");
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(bookReturn);
			oos.flush();
			out.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 책 연장하기
	public void bookExtension(String title, String returnDay, int extensionDay) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("bookExtension\n" + title + "\n" + returnDay + "\n" + extensionDay);
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 책 반납하기
	public void returnBook(String id, String title) {
		PrintWriter out;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("returnBook\n" + id + "\n" + title);
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
