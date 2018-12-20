package admin;

import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JTabbedPane;

import javax.swing.JButton;
import javax.swing.JLabel;

import main.LoginJFrame;
import tcpserver.TCPClient;

/*	관리자 화면
	
	로그인 창에서 관리자 계정으로 로그인하게 되면 나오게 되는 화면이다.
	도서별 차트, 회원 나이차트, 회원 성별차트가 나옴
	도서 예약 현황 및 대출로 전환, 도서 반환 처리 가능
	도서 등록, 검색 가능
	회원 정보 수정 및 탈퇴 처리 가능

*/
@SuppressWarnings("serial")
public class AdminJFrame_admin extends JFrame implements ActionListener, MouseListener {
	private JButton loginButton;
	private BookReservation_admin reservationPanel;
	private BookRent_admin rentPanel;
	private JButton rentButton;
	private UserAgeGraph_admin ageChart;
	private String[] value;
	private int row = -1;

	public AdminJFrame_admin() {

		getContentPane().setBackground(Color.LIGHT_GRAY);
		setTitle("도서 관리 프로그램");
		setSize(1263, 1029);
		getContentPane().setLayout(null);

		// 탭 추가
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 39, 1223, 941);
		getContentPane().add(tabbedPane);

		// 홈 탭 설정
		JPanel homeTab = new JPanel();
		homeTab.setLayout(null);
		homeTab.setBounds(10, 10, 380, 530);
		tabbedPane.add("홈", homeTab);

		// 대출 탭 설정
		JPanel rentTab = new JPanel();
		rentTab.setLayout(null);
		rentTab.setBounds(10, 10, 380, 530);
		tabbedPane.add("대출 관리", rentTab);

		// 반납 관리 탭 설정
		JPanel returnTab = new JPanel();
		returnTab.setLayout(null);
		returnTab.setBounds(10, 10, 380, 530);
		tabbedPane.add("반납 관리", returnTab);

		// 책 관리 탭 설정
		JPanel bookManageTab = new JPanel();
		bookManageTab.setLayout(null);
		bookManageTab.setBounds(10, 10, 380, 530);
		tabbedPane.add("책 관리", bookManageTab);

		// 회원관리 탭 설정
		JPanel memberManageTab = new JPanel();
		memberManageTab.setLayout(null);
		memberManageTab.setBounds(10, 10, 380, 530);
		tabbedPane.add("회원관리", memberManageTab);

		// 대출 탭에 예약 현황 패널 불러오기
		JPanel reservationBasicPanel = new JPanel();
		reservationBasicPanel.setBounds(12, 59, 488, 522);
		reservationBasicPanel.setLayout(null);
		rentTab.add(reservationBasicPanel);
		reservationPanel = new BookReservation_admin();
		reservationBasicPanel.add(reservationPanel.getPanel());
		reservationPanel.infoTable.addMouseListener(this);

		// 대출 탭에 대출 현황 패널 불러오기
		JPanel rentBasicPanel = new JPanel();
		rentBasicPanel.setLayout(null);
		rentBasicPanel.setBounds(569, 59, 496, 522);
		rentTab.add(rentBasicPanel);
		rentPanel = new BookRent_admin();
		rentBasicPanel.add(rentPanel.getPanel());

		// 대출 탭 라벨
		JLabel rent_label = new JLabel("도서 예약 현황");
		rent_label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rent_label.setBounds(12, 10, 349, 52);
		rentTab.add(rent_label);

		JLabel rent_label2 = new JLabel("도서 대출 현황");
		rent_label2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rent_label2.setBounds(569, 10, 349, 52);
		rentTab.add(rent_label2);

		// 대출 탭 ▶버튼 추가
		rentButton = new JButton("▶");
		rentButton.addActionListener(this);
		rentButton.setBounds(505, 223, 52, 52);
		rentTab.add(rentButton);

		// 반납 탭에 반납 신청 목록 갖고오기
		JPanel returnBasicPanel = new JPanel();
		returnBasicPanel.setLayout(null);
		returnBasicPanel.setBounds(12, 72, 488, 635);
		returnBasicPanel.add(new BookReturn_admin().getPanel());
		returnTab.add(returnBasicPanel);

		// 반납 탭 라벨
		JLabel label_3 = new JLabel("도서 반납 신청 현황");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_3.setBounds(12, 10, 349, 52);
		returnTab.add(label_3);

		// 책 관리 탭에 책 등록패널 붙이기
		JPanel bookRegistBasicPanel = new JPanel();
		bookRegistBasicPanel.setLayout(null);
		bookRegistBasicPanel.setBounds(12, 10, 522, 522);
		bookRegistBasicPanel.add(new BookRegister_admin().getPanel());
		bookManageTab.add(bookRegistBasicPanel);

		// 책 관리 탭에 책 검색패널 붙이기
		JPanel bookSearchBasicPanel = new JPanel();
		bookSearchBasicPanel.setLayout(null);
		bookSearchBasicPanel.setBounds(546, 10, 645, 670);
		bookSearchBasicPanel.add(new BookSearch_admin().getPanel());
		bookManageTab.add(bookSearchBasicPanel);

		// 회원 관리 탭에 회원정보패널 붙이기
		JPanel memberManageBasicPanel = new JPanel();
		memberManageBasicPanel.setLayout(null);
		memberManageBasicPanel.setBounds(32, 31, 1146, 518);
		memberManageBasicPanel.add(new UserManagement_admin().getPanel());
		memberManageTab.add(memberManageBasicPanel);

		// 홈 탭에 책 종류 원형차트 추가
		JPanel bookKindChartBasicPanel = new JPanel();
		bookKindChartBasicPanel.setBounds(32, 50, 501, 406);
		bookKindChartBasicPanel.add(new BookChart_admin().getChart());
		homeTab.add(bookKindChartBasicPanel);

		// 홈 탭에 회원 나이차트 추가
		JPanel memberAgeChartBasicPanel = new JPanel();
		memberAgeChartBasicPanel.setLayout(null);
		memberAgeChartBasicPanel.setBounds(32, 514, 460, 270);
		ageChart = new UserAgeGraph_admin();
		memberAgeChartBasicPanel.add(ageChart.getPanel());
		homeTab.add(memberAgeChartBasicPanel);

		// 홈 탭에 회원 성별차트 추가
		JPanel memberGenderChartBasicPanel = new JPanel();
		memberGenderChartBasicPanel.setLayout(null);
		memberGenderChartBasicPanel.setBounds(564, 514, 274, 258);
		memberGenderChartBasicPanel.add(new UserGenderGraph_admin().getPanel());
		homeTab.add(memberGenderChartBasicPanel);

		// 기초 프레임 화면에 로그인 화면으로 버튼 추가
		loginButton = new JButton("로그인 화면으로");
		loginButton.setBounds(12, 10, 142, 23);
		loginButton.addActionListener(this);
		getContentPane().add(loginButton);

		// 홈 탭 라벨
		JLabel home_label = new JLabel("도서 현황");
		home_label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		home_label.setBounds(32, 10, 349, 52);
		homeTab.add(home_label);

		JLabel home_label2 = new JLabel("도서관 회원 비율");
		home_label2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		home_label2.setBounds(32, 466, 349, 52);
		homeTab.add(home_label2);

		// 프레임 설정
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	} // default constructor end

	@Override
	public void actionPerformed(ActionEvent e) {

		// 로그인 버튼을 눌렀을 때
		if (e.getSource() == loginButton) {
			setVisible(false);
			ageChart.browser.dispose();

			new LoginJFrame();

			// ▶ 대출 버튼을 눌렀을 때
		} else if (e.getSource() == rentButton) {

			// 예약 현황테이블에 값이 선택 되었을 경우
			if (row >= 0) {

				new TCPClient().rentBook(value[0], value[1], value[2]);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = df.parse(value[2]);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				Calendar cal = Calendar.getInstance();
				cal.setTime(date); // cal에 받아온 date값을 입력
				cal.add(Calendar.DATE, 7); // cal 일자에 7일을 추가
				String returnDay = df.format(cal.getTime()); // 7일을 더한 날을 받아오기
				value[3] = returnDay; // 테이블 업데이트
				reservationPanel.dtm.removeRow(row);
				rentPanel.dtm.addRow(value);
				row = -1; // 선택된 값 초기화

			}

		}
	} // actionPerformed end

	// 예약 테이블에 값을 선택 했을 경우
	@Override
	public void mouseClicked(MouseEvent arg0) {
		row = reservationPanel.infoTable.getSelectedRow();
		value = new String[4];

		// value에 해당 row에 값을 넣는다.
		for (int i = 0; i < 3; i++) {
			value[i] = reservationPanel.infoTable.getValueAt(row, i).toString();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}// class end
