package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JTextField;

import tcpserver.MemberDTO;
import tcpserver.TCPClient;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/*
 * 회원가입 화면
 */

@SuppressWarnings("serial")
public class SignUpJFrame extends JFrame implements ActionListener {

	private Random ran = new Random();
	private JTextField idField;
	private JTextField telField;
	private JTextField rrnField;
	private JTextField addField;
	private JPasswordField pwField;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;

	private String checkId = "!@#$"; // 아이디 중복버튼을 눌렀는지 확인
	private String random = "";
	private JTextField nameField;
	private JTextField autoJoin;
	private JTextField rrnField_1;

	// 회원가입 프레임
	public SignUpJFrame() {

		// 프레임 설정
		setTitle("회원가입");
		setSize(450, 550);
		getContentPane().setLayout(null);

		// 패널 설정
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.LIGHT_GRAY);
		mainPanel.setBounds(12, 10, 410, 490);
		mainPanel.setLayout(null);
		getContentPane().add(mainPanel);

		// id 입력 설정
		JLabel idLab = new JLabel("ID");
		idLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 14));
		idLab.setBounds(32, 43, 34, 15);
		mainPanel.add(idLab);
		idField = new JTextField();
		idField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 8)
					ke.consume();
			}

		});
		idField.setBounds(119, 41, 170, 21);
		mainPanel.add(idField);
		idField.setColumns(10);

		// pw 입력 설정
		JLabel pwLab = new JLabel("PW");
		pwLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 14));
		pwLab.setBounds(32, 97, 34, 15);
		mainPanel.add(pwLab);
		pwField = new JPasswordField();
		pwField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 8)
					ke.consume();
			}

		});
		pwField.setBounds(119, 95, 170, 21);
		mainPanel.add(pwField);

		// 이름 입력 설정
		JLabel nameLab = new JLabel("NAME");
		nameLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 14));
		nameLab.setBounds(32, 152, 52, 15);
		mainPanel.add(nameLab);
		nameField = new JTextField();
		nameField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 10)
					ke.consume();
			}

		});
		nameField.setColumns(10);
		nameField.setBounds(119, 150, 170, 21);
		mainPanel.add(nameField);

		// 주민번호 입력 설정
		JLabel rrnLab = new JLabel("RRN");
		rrnLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 14));
		rrnLab.setBounds(32, 202, 34, 15);
		mainPanel.add(rrnLab);

		rrnField = new JTextField();
		rrnField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 6)
					ke.consume();
			}

		});
		rrnField.setToolTipText("주민번호 숫자만 입력하세요");
		rrnField.setColumns(10);
		rrnField.setBounds(119, 200, 74, 21);
		mainPanel.add(rrnField);

		JLabel lblNewLabel = new JLabel("-");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(172, 201, 62, 18);
		mainPanel.add(lblNewLabel);

		rrnField_1 = new JTextField();
		rrnField_1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 7)
					ke.consume();
			}

		});
		rrnField_1.setToolTipText("주민번호 숫자만 입력하세요");
		rrnField_1.setColumns(10);
		rrnField_1.setBounds(215, 200, 74, 21);
		mainPanel.add(rrnField_1);

		// 전화번호 입력 설정
		JLabel telLab = new JLabel("TEL");
		telLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 14));
		telLab.setBounds(32, 253, 34, 15);
		mainPanel.add(telLab);
		telField = new JTextField();
		telField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 11)
					ke.consume();
			}

		});
		telField.setColumns(10);
		telField.setBounds(119, 251, 170, 21);
		mainPanel.add(telField);

		// 주소 입력 설정
		JLabel addLab = new JLabel("ADDRESS");
		addLab.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 14));
		addLab.setBounds(32, 303, 81, 15);
		mainPanel.add(addLab);
		addField = new JTextField();
		addField.setColumns(10);
		addField.setBounds(119, 301, 170, 21);
		mainPanel.add(addField);

		// 회원가입 진행
		btn1 = new JButton("회원가입");
		btn1.setBounds(80, 419, 97, 23);
		btn1.addActionListener(this);
		mainPanel.add(btn1);

		// 회원가입 취소
		btn2 = new JButton("취소");
		btn2.setBounds(222, 419, 97, 23);
		btn2.addActionListener(this);
		mainPanel.add(btn2);

		// id 중복확인
		btn3 = new JButton("중복확인");
		btn3.setBackground(new Color(153, 204, 255));
		btn3.setBounds(301, 40, 97, 23);
		btn3.addActionListener(this);
		mainPanel.add(btn3);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(33, 353, 153, 44);
		panel_1.setLayout(null);
		mainPanel.add(panel_1);

		// 자동 가입 방지
		JLabel textLab = new JLabel();
		int RandomNum;
		for (int i = 0; i < 3; i++) {
			RandomNum = ran.nextInt(25) + 65;
			random += String.valueOf((char) RandomNum);
			RandomNum = ran.nextInt(9);
			random += String.valueOf(RandomNum);
		}

		textLab.setText(random);
		panel_1.setLayout(new GridLayout(0, 1));
		panel_1.add(textLab);

		// 자동가입 방지
		autoJoin = new JTextField();
		autoJoin.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				JTextField src = (JTextField) ke.getSource();
				if (src.getText().length() >= 6)
					ke.consume();
			}

		});
		autoJoin.setColumns(10);
		autoJoin.setBounds(203, 364, 170, 21);
		mainPanel.add(autoJoin);

		setVisible(true);
	} // default constructor end

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		String id = idField.getText();
		String pw = pwField.getText();
		String name = nameField.getText();
		String rrn = rrnField.getText() + rrnField_1.getText();
		String tel = telField.getText();
		String address = addField.getText();

		// ID중복방지 버튼
		if (e.getSource() == btn3) {
			String result = new TCPClient().idOverlapCheck(id);
			if (result.equals("false")) {
				checkId = idField.getText();
				JOptionPane.showMessageDialog(null, "사용가능 합니다.");
			} else {
				JOptionPane.showMessageDialog(null, "아이디가 존재합니다.");
			}

			// 취소 버튼
		} else if (e.getSource() == btn2) {
			setVisible(false);

			// 회원가입 버튼
		} else if (e.getSource() == btn1) {
			if (checkId.equals(idField.getText())) {
				if (autoJoin.getText().equals(random)) {
					MemberDTO dto = new MemberDTO();
					dto.setId(id);
					dto.setPw(pw);
					dto.setName(name);
					dto.setTel(tel);
					dto.setAddress(address);
					dto.setRrn(rrn);
					String result = new TCPClient().signUp(dto);
					if (result.equals("성공")) {
						JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다.");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
					}

				} else {
					JOptionPane.showMessageDialog(null, "자동가입방지 문자가 잘못되었습니다. 다시 입력해주세요");
				}

			} else {
				JOptionPane.showMessageDialog(null, "중복확인을 눌러주세요.");
			}
		}//else if (e.getSource() == btn1) end

	}// actionPerformed end

}// class end
