package user;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.URL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import tcpserver.MemberDTO;
import tcpserver.RentDTO;
import tcpserver.TCPClient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import main.LoginJFrame;

/*	일반 사용자 화면
 
 	로그인 창에서 일반 사용자 계정으로 로그인하게 되면 나오게 되는 화면이다.
 	개인 정보, 대출 도서 정보, 도서 검색 화면이 나옴
 	개인 정보 수정, 반납 신청, 반납 예정일 연장, 도서 예약 및 취소 가능
 	도서관 정보 확인 및 위치 확인 가능
   
 */

@SuppressWarnings("serial")
public class UserJFrame extends JFrame implements ActionListener {

	private JTable table1;
	private JFileChooser fileOpen;
	private FileNameExtensionFilter fileName;
	private JButton returnButton, extensionButton;
	private JButton registration = new JButton();
	private JButton updateButton, deleteButton, logoutButton;
	private int row, col;
	private MemberDTO dto;
	private RentDTO dto1;
	private int offset = registration.getInsets().left;
	private UserBookSearch search;
	private DefaultTableModel model;
	JButton refresh;

	@SuppressWarnings("static-access")
	public UserJFrame(String id) {

		// 회원정보 가져오기
		dto = new TCPClient().getUserInfo(id);

		// 프레임 설정
		setTitle(dto.getName() + "회원님 도서 이용 정보");
		setSize(1350, 800);
		getContentPane().setLayout(null);

		// 탭 추가
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 15, 1310, 730);
		getContentPane().add(tabbedPane);

		// 회원정보 탭 설정
		JPanel memberInfo = new JPanel();
		memberInfo.setBackground(Color.LIGHT_GRAY);
		memberInfo.setBounds(12, 10, 1460, 741);
		memberInfo.setLayout(null);
		tabbedPane.add("회원 정보", memberInfo);

		// 도서관 정보 탭 설정
		JPanel libraryInfo = new JPanel();
		libraryInfo.setBackground(Color.LIGHT_GRAY);
		libraryInfo.setBounds(12, 10, 1460, 741);
		libraryInfo.setLayout(null);
		tabbedPane.add("도서관 정보", libraryInfo);

		// 도서관 정보 설정
		JPanel libraryInfoPanel = new JPanel();
		libraryInfoPanel.setBounds(12, 10, 638, 681);
		libraryInfoPanel.setLayout(null);
		libraryInfo.add(libraryInfoPanel);

		// 도서관 정보 설정(도서관 사진 이미지)
		URL url;
		boolean isAlive = false;
		JLabel libraryImage = new JLabel();
		try {
			url = new URL("http://35.243.191.233/library123.jpg");
			InetAddress pingcheck = InetAddress.getByName("35.243.191.233");
			isAlive = pingcheck.isReachable(1000);
			if (isAlive) {
				Image image = ImageIO.read(url);

				ImageIcon icon = new ImageIcon(image);

				libraryImage
						.setIcon(imageSize(icon, registration.getWidth() - offset, registration.getHeight() - offset));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		libraryImage.setBounds(12, 10, 614, 350);
		libraryInfoPanel.add(libraryImage);

		// 설립일 라벨
		JLabel setUpDate = new JLabel("설  립  일 : 2018. 12. 20");
		setUpDate.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		setUpDate.setBounds(38, 400, 295, 27);
		libraryInfoPanel.add(setUpDate);

		// 설립일 출력 라벨
		JLabel setUpDateLab = new JLabel("");
		setUpDateLab.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		setUpDateLab.setBounds(142, 400, 90, 27);

		// 소재지 라벨
		JLabel stead = new JLabel("소  재  지 : 서울특별시 중구 세종대로 110 서울특별시청");
		stead.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		stead.setBounds(38, 450, 431, 27);
		libraryInfoPanel.add(stead);

		// 소재지 출력 라벨
		JLabel steadLab = new JLabel("");
		steadLab.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		steadLab.setBounds(142, 450, 90, 27);
		libraryInfoPanel.add(steadLab);

		// 운영시간 라벨
		JLabel operatingHours = new JLabel("운영 시간 : 평일 09:00 ~ 21:00 주말 09:00~18:00");
		operatingHours.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		operatingHours.setBounds(38, 500, 588, 27);
		libraryInfoPanel.add(operatingHours);

		// 운영시간 출력 라벨
		JLabel operatingHoursLab = new JLabel("");
		operatingHoursLab.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		operatingHoursLab.setBounds(142, 500, 90, 27);
		libraryInfoPanel.add(operatingHoursLab);

		// 휴관일 라벨
		JLabel close = new JLabel("휴  관  일 : 매주 월요일 휴무");
		close.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		close.setBounds(38, 550, 474, 27);
		libraryInfoPanel.add(close);

		// 휴관일 출력 라벨
		JLabel closeLab = new JLabel("");
		closeLab.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		closeLab.setBounds(142, 550, 90, 27);
		libraryInfoPanel.add(closeLab);

		// 전화번호 라벨
		JLabel tel = new JLabel("전화 번호 : 02-967-2058");
		tel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		tel.setBounds(38, 600, 431, 27);
		libraryInfoPanel.add(tel);

		// 전화번호 출력 라벨
		JLabel libraryTelLab = new JLabel("");
		libraryTelLab.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		libraryTelLab.setBounds(142, 600, 90, 27);
		libraryInfoPanel.add(libraryTelLab);

		// 도서관 정보 설정
		JPanel userMapPanel = new JPanel();
		userMapPanel.setBounds(655, 10, 638, 681);
		userMapPanel.add(new UserMap().getPanel());
		new UserMap().getPanel();
		userMapPanel.setLayout(null);
		libraryInfo.add(userMapPanel);

		// 회원 대출 목록 테이블
		JPanel rentPanel = new JPanel(new BorderLayout());
		rentPanel.setBackground(new Color(255, 255, 224));
		rentPanel.setBounds(12, 502, 496, 185);
		memberInfo.add(rentPanel);

		// 일반 사용자 대출 정보를 가져옴
		ArrayList<RentDTO> list = new TCPClient().getUserRent(dto.getId());
		String[] columnNames = { "도서 이름", "대출일", "반납 예정일", "연장일(최대 7일)", "반납 신청 여부" };
		String[][] rowData = new String[list.size()][columnNames.length];

		// 일반 사용자 대출 정보 출력
		for (int i = 0; i < rowData.length; i++) {
			dto1 = list.get(i);
			rowData[i][0] = dto1.getTitle(); // 대출 중인 책 이름
			rowData[i][1] = dto1.getRentDay(); // 대출일
			rowData[i][2] = dto1.getReturnDay(); // 반납 예정일
			rowData[i][3] = dto1.getExtensionDay(); // 반납 연장일
			rowData[i][4] = dto1.getReturnCheck(); // 반납 여부
		}

		model = new DefaultTableModel(rowData, columnNames);
		table1 = new JTable(model) {
			// cell 값 수정을 못하도록 설정
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane(table1);
		rentPanel.add(scrollPane);

		// 검색 판넬
		JPanel searchPanel = new JPanel(null);
//		panel_2.setBackground(new Color(255, 255, 224));
		searchPanel.setBounds(683, 33, 628, 698);
		search = new UserBookSearch();
		search.id = dto.getId();
		searchPanel.add(search.getPanel());
		memberInfo.add(searchPanel);

		// 회원 사진 등록 버튼
		registration.setBounds(12, 33, 200, 280);
		memberInfo.add(registration);
		registration.setToolTipText("사진을 등록합니다.");

		// ID 라벨
		JLabel idLab = new JLabel("ID");
		idLab.setBounds(271, 45, 39, 15);
		memberInfo.add(idLab);
		idLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 아이디 출력
		JLabel idPrint = new JLabel(dto.getId());
		idPrint.setBounds(271, 70, 226, 15);
		memberInfo.add(idPrint);
		idPrint.setFont(new Font("굴림", Font.PLAIN, 15));

		// 나이 및 성별 계산
		String rrn = dto.getRrn();
		String userAge = age(rrn);
		String userGender = gender(rrn);

		// 로그인한 계정에 사진 정보가 있을 경우 사진 출력
//		URL url;
//		boolean isAlive = false;
		try {
			url = new URL("http://35.243.191.233/" + dto.getId() + ".jpg");
			InetAddress pingcheck = InetAddress.getByName("35.243.191.233");
			isAlive = pingcheck.isReachable(1000);
			if (isAlive) {
				Image image = ImageIO.read(url);

				ImageIcon icon = new ImageIcon(image);

				registration
						.setIcon(imageSize(icon, registration.getWidth() - offset, registration.getHeight() - offset));

			} else {
				registration.setText("사진 등록");
			}
		} catch (Exception e) {
		}

		// 버튼(회원 정보 수정, 회원탈퇴, 로그아웃, 반납 신청, 반납 연장)
		logoutButton = new JButton("로그아웃");
		logoutButton.setBounds(12, 442, 200, 50);
		memberInfo.add(logoutButton);

		deleteButton = new JButton("회원 탈퇴");
		deleteButton.setBounds(12, 383, 201, 50);
		memberInfo.add(deleteButton);

		updateButton = new JButton("회원 정보 수정");
		updateButton.setBounds(12, 323, 200, 50);
		memberInfo.add(updateButton);

		returnButton = new JButton("반납 신청");
		returnButton.setBounds(522, 580, 125, 32);
		returnButton.addActionListener(this);
		memberInfo.add(returnButton);

		extensionButton = new JButton("반납 연장");
		extensionButton.setBounds(520, 650, 125, 32);
		extensionButton.addActionListener(this);
		memberInfo.add(extensionButton);

		// 회원 정보 판넬
		JPanel memberInfoPanel = new JPanel();
		memberInfoPanel.setLayout(null);
		memberInfoPanel.setBackground(new Color(255, 255, 224));
		memberInfoPanel.setBounds(224, 33, 421, 459);
		memberInfo.add(memberInfoPanel);

		// NAME 라벨
		JLabel nameLab = new JLabel("NAME");
		nameLab.setBounds(46, 80, 60, 15);
		memberInfoPanel.add(nameLab);
		nameLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 이름 출력
		JLabel namePrint = new JLabel(dto.getName());
		namePrint.setBounds(46, 105, 226, 15);
		memberInfoPanel.add(namePrint);
		namePrint.setFont(new Font("굴림", Font.PLAIN, 15));

		// AGE 라벨
		JLabel ageLab = new JLabel("AGE");
		ageLab.setBounds(46, 145, 77, 15);
		memberInfoPanel.add(ageLab);
		ageLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 나이 출력
		JLabel agePrint = new JLabel(userAge);
		agePrint.setBounds(46, 170, 226, 15);
		memberInfoPanel.add(agePrint);
		agePrint.setFont(new Font("굴림", Font.PLAIN, 15));

		// GENDER 라벨
		JLabel genderLab = new JLabel("GENDER");
		genderLab.setBounds(46, 212, 77, 15);
		memberInfoPanel.add(genderLab);
		genderLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 성별 출력
		JLabel lblDdd = new JLabel(userGender);
		lblDdd.setBounds(46, 237, 226, 15);
		memberInfoPanel.add(lblDdd);
		lblDdd.setFont(new Font("굴림", Font.PLAIN, 15));

		// TEL 라벨
		JLabel telLab = new JLabel("TEL");
		telLab.setBounds(46, 275, 97, 15);
		memberInfoPanel.add(telLab);
		telLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 전화번호 출력
		JLabel TelPrint = new JLabel(dto.getTel());
		TelPrint.setBounds(46, 300, 226, 15);
		memberInfoPanel.add(TelPrint);
		TelPrint.setFont(new Font("굴림", Font.PLAIN, 15));

		// ADDRESS 라벨
		JLabel addLab = new JLabel("ADDRESS");
		addLab.setBounds(46, 341, 97, 15);
		memberInfoPanel.add(addLab);
		addLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 주소 출력
		JLabel addPrint = new JLabel(dto.getAddress());
		addPrint.setBounds(46, 366, 226, 15);
		memberInfoPanel.add(addPrint);
		addPrint.setFont(new Font("굴림", Font.PLAIN, 15));

		// GRADE 라벨
		JLabel gradeLab = new JLabel("GRADE");
		gradeLab.setBounds(46, 409, 77, 15);
		memberInfoPanel.add(gradeLab);
		gradeLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 15));

		// 로그인한 회원 등급 출력
		JLabel gradePrint = new JLabel("일반 회원");
		gradePrint.setBounds(46, 434, 83, 15);
		memberInfoPanel.add(gradePrint);
		gradePrint.setFont(new Font("굴림", Font.PLAIN, 15));

		refresh = new JButton("새로고침");
		refresh.setBounds(522, 518, 125, 32);
		memberInfo.add(refresh);

		// 버튼 액션(사진 등록, 회원 정보 수정, 회원 탈퇴, 로그 아웃)
		registration.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		logoutButton.addActionListener(this);
		refresh.addActionListener(this);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	} // default constructor end

	// 입력받은 주민번호를 계산하여 나이를 반환하는 메소드
	public String age(String userAge) {

		// 주민번호의 앞자리 2글자를 저장
		Calendar cal = Calendar.getInstance();
		int age = Integer.parseInt(userAge.substring(0, 2));

		// 년도 계산
		if (age > 18 && age < 100) {
			age += 1900;
		} else if (age <= 18) {
			age += 2000;
		}

		// 현재 년도를 저장
		int year = cal.get(Calendar.YEAR);

		// 나이 결과값을 String 타입의 result에 저장
		String result = String.valueOf(year - age + 1);
		return result;

	} // age method end

	// 입력받은 주민번호를 계산하여 성별을 반환하는 메소드
	public String gender(String userAge) {
		// 주민번호 뒷자리 저장
		String gender = userAge.substring(6, 7);
		String result = null;

		if (gender.equals("1") || gender.equals("3")) {
			result = "남성";
		} else if (gender.equals("2") || gender.equals("4")) {
			result = "여성";
		}

		return result;

	} // gender method end

	// 회원 사진 크기 조절
	public Icon imageSize(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);

	} // imageSize method end

	@Override
	public void actionPerformed(ActionEvent e) {
		UserWithdrawalJFrame withdrawal = null;
		if (e.getSource() == registration) {

			// 사진 등록을 위한 JFileChooser 객체 생성
			fileOpen = new JFileChooser();
			// 필터링 확장자 지정
			fileName = new FileNameExtensionFilter("jpg", "jpg");
			fileOpen.setMultiSelectionEnabled(false);// 다중 선택 불가
			// 확장자 추가
			fileOpen.addChoosableFileFilter(fileName);
//			fileOpen.showOpenDialog(btn1);
			// 회원 사진 저장
			if (fileOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				ImageIcon userIma = new ImageIcon(fileOpen.getSelectedFile().toString());
				String imageName = fileOpen.getSelectedFile().toString();

				int offset = registration.getInsets().left;
				Icon icon = imageSize(userIma, registration.getWidth() - offset, registration.getHeight() - offset);
				userIma = (ImageIcon) icon;
				registration.setIcon(userIma);

				new TCPClient().uploadUserImage(dto.getId(), imageName);

			}

		} // if end

		// 회원 정보 수정
		else if (e.getSource() == updateButton) {
			new UserUpdateJFrame(dto);

			// 회원 탈퇴
		} else if (e.getSource() == deleteButton) {
			withdrawal = new UserWithdrawalJFrame(dto);
			withdrawal.successButton.addActionListener(this);

			// 로그 아웃
		} else if (e.getSource() == logoutButton) {

			new LoginJFrame();
			dispose();

			// 아이디 삭제 성공
		} else if (e.getActionCommand().equals("성공")) {
			dispose();

			new LoginJFrame();

			// 반납 신청 이벤트
		} else if (e.getSource() == returnButton) {

			if (table1.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "반납 신청할 도서를 선택해 주세요");
			} else {
				row = table1.getSelectedRow();
				if (model.getValueAt(row, 4).toString().equals("Y")) {
					JOptionPane.showMessageDialog(null, "이미 반납 신청이 되었습니다.");
				} else {
					col = 0; // cell의 어디를 선택하더라도 도서 제목 열로 고정
					new TCPClient().returnBook(dto.getId(), table1.getValueAt(row, col).toString());
					model.setValueAt("Y", row, 4);
				}
			}

			// 반납 연장 버튼 이벤트
		} else if (e.getSource() == extensionButton) {

			if (table1.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(null, "반납 연장할 도서를 선택해 주세요");
			} else {
				int extensionDay = Integer.parseInt(JOptionPane.showInputDialog(null, "추가로 연장할 날을 입력해주세요 (7일 이내)"));
				if (extensionDay > 7) {
					JOptionPane.showMessageDialog(null, "7일 이내로 입력해주세요");
				} else {
					if (Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 3).toString()) + extensionDay > 7) {
						JOptionPane.showMessageDialog(null, "연장 일수가 초과하였습니다.");
					} else {

						model.setValueAt(Integer.parseInt(table1.getValueAt(table1.getSelectedRow(), 3).toString())
								+ extensionDay, table1.getSelectedRow(), 3);

						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

						Date date = null;

						try {
							date = df.parse(table1.getValueAt(table1.getSelectedRow(), 2).toString());
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.DATE, extensionDay);

						String returnDay = df.format(cal.getTime());
						model.setValueAt(returnDay, table1.getSelectedRow(), 2);
						new TCPClient().bookExtension(table1.getValueAt(table1.getSelectedRow(), 0).toString(),
								returnDay, extensionDay);

					}
				} // else end
			}

		} // else if end(반납 연장 이벤트)
		else if (e.getSource() == refresh) {
			// 일반 사용자 대출 정보를 가져옴
			ArrayList<RentDTO> list = null;

			list = new TCPClient().getUserRent(dto.getId());

			String[] columnNames = { "도서 이름", "대출일", "반납 예정일", "연장일(최대 7일)", "반납 신청 여부" };
			String[][] rowData = new String[list.size()][columnNames.length];

			// 일반 사용자 대출 정보 출력
			for (int i = 0; i < rowData.length; i++) {
				dto1 = list.get(i);
				rowData[i][0] = dto1.getTitle(); // 대출 중인 책 이름
				rowData[i][1] = dto1.getRentDay(); // 대출일
				rowData[i][2] = dto1.getReturnDay(); // 반납 예정일
				rowData[i][3] = dto1.getExtensionDay(); // 반납 예정일
				rowData[i][4] = dto1.getReturnCheck(); // 반납 여부
			}

			model.setDataVector(rowData, columnNames);
		}

	} // //actionPerformed end
}// class end
