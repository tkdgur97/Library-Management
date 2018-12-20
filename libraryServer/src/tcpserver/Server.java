package tcpserver;

import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import java.util.TreeMap;

import javax.imageio.ImageIO;

public class Server {

	Socket socket;
	BufferedReader input;

	@SuppressWarnings("resource")
	public Server() throws Exception {
		ServerSocket serverSocket = new ServerSocket(9400);
		while (true) {
			socket = serverSocket.accept();
			checkRequest();
			socket.close();
		}
	}

	// 어떤 요청인지 판별하기
	public void checkRequest() throws Exception {
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String info = input.readLine();

		// 책 등록하기
		if (info.equals("bookRegist")) {
			bookRegist();
			// 전체 회원정보 가져오기
		} else if (info.equals("getUserInfoAll")) {
			getUserInfoAll();
			// 회원 개인정보 가져오기
		} else if (info.equals("getUserInfo")) {
			getUserInfo();
			// 책정보 가져오기
		} else if (info.equals("getBookInfo")) {
			getBookInfo();
			// 책종류 가져오기
		} else if (info.equals("getTypeOfBook")) {
			getTypeOfBook();
			// 유저 이미지 올리기
		} else if (info.equals("uploadUserImage")) {
			uploadUserImage();
			// 회원가입하기
		} else if (info.equals("signUp")) {
			signUp();
			// 회원가입 ID중복 확인
		} else if (info.equals("idOverlapCheck")) {
			idOverlapCheck();
			// 로그인 확인
		} else if (info.equals("loginCheck")) {
			loginCheck();
			// 개인정보 수정
		} else if (info.equals("userInfoChange")) {
			userInfoChange();
			// 개인정보 비밀번호 변경
		} else if (info.equals("pwChange")) {
			pwChange();
			// 회원 탈퇴
		} else if (info.equals("withdrawal")) {
			withdrawal();
			// 아이디 찾기
		} else if (info.equals("idFind")) {
			idFind();
			// 비밀번호 찾기
		} else if (info.equals("pwFind")) {
			pwFind();
			// 관리자 유저 정보 변경
		} else if (info.equals("userInfoChange_admin")) {
			userInfoChange_admin();
			// 예약 정보
		} else if (info.equals("getUserReservationAll")) {
			getUserReservationAll();
			// 개인 대출 정보
		} else if (info.equals("getUserRent")) {
			getUserRent();
			// 대출 하기
		} else if (info.equals("rentBook")) {
			rentBook();
			// 예약 하기
		} else if (info.equals("makeReservation")) {
			makeReservation();
			// 대출 정보 가져오기
		} else if (info.equals("getRentInfoAll")) {
			getRentInfoAll();
			// 개인 예약 정보 가져오기
		} else if (info.equals("getUserReservation")) {
			getUserReservation();
			// 예약 취소하기
		} else if (info.equals("reservationCancel")) {
			reservationCancel();
			// 반납 정보가져오기
		} else if (info.equals("getReturnInfoAll")) {
			getReturnInfoAll();
			// 책 반납
		} else if (info.equals("returnBook_admin")) {
			returnBook_admin();
			// 연장 하기
		} else if (info.equals("bookExtension")) {
			bookExtension();
			// 책 반납 신청하기
		} else if (info.equals("returnBook")) {
			returnBook();
		}
	}

	// 책 반납 신청
	public void returnBook() throws Exception {
		String id = input.readLine();
		String title = input.readLine();
		new ReturnDAO().returnBook(id, title);

	}

	// 연장 하기
	public void bookExtension() throws Exception {
		String title = input.readLine();
		String returnDay = input.readLine();
		String extensionDay = input.readLine();
		new RentDAO().bookExtension(title, returnDay, extensionDay);
	}

	// 반납 확인
	public void returnBook_admin() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		String[] returnCheck = (String[]) ois.readObject();
		new ReturnDAO().returnBook(returnCheck);
	}

	// 반납 정보 가져오기
	public void getReturnInfoAll() throws Exception {
		ArrayList<ReturnDTO> list = new ReturnDAO().getReturnInfoAll();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();

	}

	// 예약 취소하기
	public void reservationCancel() throws Exception {
		String id = input.readLine();
		String title = input.readLine();
		new ReservationDAO().reservationCancel(id, title);

	}

	// 개인 예약정보
	public void getUserReservation() throws Exception {
		String id = input.readLine();
		ArrayList<ReservationDTO> list = new ReservationDAO().getUserReservation(id);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();

	}

	// 대출 정보 가져오기
	public void getRentInfoAll() throws Exception {
		ArrayList<RentDTO> list = new RentDAO().getRentInfoAll();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();

	}

	// 예약하기
	public void makeReservation() throws Exception {
		String id = input.readLine();
		String title = input.readLine();
		String resDate = input.readLine();
		String result = new ReservationDAO().makeReservation(id, title, resDate);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();
	}

	// 대출하기
	public void rentBook() throws Exception {
		String id = input.readLine();
		String title = input.readLine();
		String date = input.readLine();
		String result = new RentDAO().rentBook(id, title, date);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();
	}

	// 개인 대출 정보
	public void getUserRent() throws Exception {
		String id = input.readLine();
		ArrayList<RentDTO> list = new RentDAO().getUserRent(id);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();

	}

	// 예약정보 가져오기
	public void getUserReservationAll() throws Exception {
		ArrayList<ReservationDTO> list = new ReservationDAO().getUserReservationAll();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();

	}

	// 유저 이미지 변경
	public void uploadUserImage() throws Exception {
		String id = input.readLine();
		InputStream inputStream = socket.getInputStream();
		byte[] sizeAr = new byte[4];
		inputStream.read(sizeAr);
		int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
		byte[] imageAr = new byte[size];
		inputStream.read(imageAr);
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
		String IMAGE_PATH = "/var/www/html/" + id + ".jpg"; // 파일 포맷 일치 필요
		ImageIO.write(image, "jpg", new File(IMAGE_PATH)); // 파일 포맷 일치 필요

	}

	// 관리자 유저 정보 변경
	public void userInfoChange_admin() throws Exception {
		String id = input.readLine();
		String pw = input.readLine();
		String name = input.readLine();
		String tel = input.readLine();
		String address = input.readLine();
		String result = new MemberDAO().userInfoChange_admin(id, pw, name, tel, address);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();

	}

	// 비밀번호 찾기
	public void pwFind() throws Exception {
		String id = input.readLine();
		String name = input.readLine();
		String result = new MemberDAO().pwFind(id, name);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();

	}

	// 아이디 찾기
	public void idFind() throws Exception {
		String name = input.readLine();
		String tel = input.readLine();
		String result = new MemberDAO().idFind(name, tel);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();
	}

	// 회원 탈퇴
	public void withdrawal() throws Exception {
		String id = input.readLine();
		String result = new MemberDAO().withdrawal(id);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();

	}

	// 개인정보 비밀번호 변경하기
	public void pwChange() throws Exception {
		String id = input.readLine();
		String pw = input.readLine();
		String result = new MemberDAO().pwChange(id, pw);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();
	}

	// 개인정보 수정하기
	public void userInfoChange() throws Exception {
		String id = input.readLine();
		String name = input.readLine();
		String tel = input.readLine();
		String address = input.readLine();
		String result = new MemberDAO().userInfoChange(id, name, tel, address);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();
	}

	// 개인정보 가져오기
	public void getUserInfo() throws Exception {
		String id = input.readLine();
		MemberDTO dto = new MemberDAO().getUserInfo(id);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(dto);
		oos.flush();
		oos.close();
	}

	// 회원가입 id 중복 확인하기
	public void idOverlapCheck() throws Exception {
		String id = input.readLine();
		String result = new MemberDAO().idOverlapCheck(id);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();
	}

	// 회원가입
	public void signUp() throws Exception {
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		MemberDTO dto = (MemberDTO) ois.readObject();
		String result = new MemberDAO().signUp(dto);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		ois.close();
		out.close();
	}

	// 로그인 확인
	public void loginCheck() throws Exception {
		String id = input.readLine();
		String pw = input.readLine();
		String result = new MemberDAO().loginCheck(id, pw);

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(result);
		out.close();

	}

	// 책 종류 가져오기
	public void getTypeOfBook() throws Exception {
		// TODO Auto-generated method stub
		TreeMap<String, Integer> bookKind = new BookDAO().getTypeOfBook();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(bookKind);
		oos.flush();
		oos.close();
	}

	// 책 정보 가져오기
	public void getBookInfo() throws Exception {
		ArrayList<BookDTO> list = new BookDAO().getBookInfo();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();

	}

	// 회원정보 가져오기
	public void getUserInfoAll() throws Exception {
		ArrayList<MemberDTO> list = new MemberDAO().getUserInfoAll();
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

		oos.writeObject(list);
		oos.flush();
		oos.close();
	}

	// 책 등록하기
	public void bookRegist() throws Exception {
		String number = input.readLine();
		String title = input.readLine();
		String author = input.readLine();
		String publisher = input.readLine();
		String year = input.readLine();
		String bill = input.readLine();
		new BookDAO().bookRegist(number, title, author, publisher, year, bill);

	}

	public static void main(String[] args) throws Exception {
		new Server();
	}
}
