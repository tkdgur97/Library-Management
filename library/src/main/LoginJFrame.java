package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JTextField;

import admin.AdminJFrame_admin;
import tcpserver.TCPClient;
import user.UserJFrame;

import javax.swing.JPasswordField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.imageio.ImageIO;

import javax.swing.ImageIcon;

/*
 * 로그인 화면 
 */

@SuppressWarnings("serial")
public class LoginJFrame extends JFrame implements ActionListener {

	JTextField idField;
	JPasswordField pwField;
	JButton loginButton, joinButton, exitButton;
	private JButton idPwFindButton;

	// main 프레임 설정
	public LoginJFrame() {
		getContentPane().setBackground(Color.WHITE);

		// main 프레임 설정
		setTitle("도서관 관리 프로그램");
		setSize(500, 450);
		getContentPane().setLayout(null);

		// image 라벨
		JLabel imgLab = new JLabel("");

		URL url;
		boolean isAlive = false;
		try {
			url = new URL("http://35.243.191.233/main123123.jpg");
			InetAddress pingcheck = InetAddress.getByName("35.243.191.233");
			isAlive = pingcheck.isReachable(1000);
			if (isAlive) {
				Image image = ImageIO.read(url);
				ImageIcon icon = new ImageIcon(image);
				imgLab.setIcon(icon);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		imgLab.setBackground(Color.DARK_GRAY);
		imgLab.setBounds(12, 21, 460, 230);
		getContentPane().add(imgLab);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(12, 276, 460, 125);
		panel.setLayout(null);
		getContentPane().add(panel);

		// 버튼(로그인)
		loginButton = new JButton("로그인");
		loginButton.setBounds(291, 66, 97, 23);
		panel.add(loginButton);

		// 버튼(회원가입)
		joinButton = new JButton("회원가입");
		joinButton.setBounds(40, 10, 97, 23);
		panel.add(joinButton);

		// 버튼(종료)
		exitButton = new JButton("종료");
		exitButton.setBounds(332, 10, 97, 23);
		panel.add(exitButton);

		// 버튼(아이디/비밀번호 찾기)
		idPwFindButton = new JButton("아이디/비밀번호 찾기");
		idPwFindButton.setBounds(149, 10, 162, 23);
		panel.add(idPwFindButton);

		// id 텍스트 필드
		idField = new JTextField();
		idField.setBounds(144, 49, 115, 20);
		panel.add(idField);
		idField.setToolTipText("8글자 이내 입력");
		idField.setColumns(10);

		// id 라벨
		JLabel idLab = new JLabel("ID");
		idLab.setBounds(108, 52, 24, 15);
		panel.add(idLab);

		// pw 라벨
		JLabel pwLab = new JLabel("PW");
		pwLab.setBounds(108, 86, 24, 15);
		panel.add(pwLab);

		// pw 텍스트 필드
		pwField = new JPasswordField();
		pwField.setBounds(144, 84, 115, 20);
		panel.add(pwField);
		pwField.setToolTipText("8글자 이내 입력");

		loginButton.addActionListener(this);
		joinButton.addActionListener(this);
		exitButton.addActionListener(this);
		idPwFindButton.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// 로그인
		if (e.getSource() == loginButton) {

			String id = idField.getText();
			String pw = pwField.getText();
			// 관리자 계정 로그인
			if (id.equals("admin") && pw.equals("1234")) {
				dispose();

				new AdminJFrame_admin();

				// 일반 회원 로그인
			} else {
				String result = null;

				if (id.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요", "아이디 입력", JOptionPane.INFORMATION_MESSAGE);
				} else {
					result = new TCPClient().loginCheck(id + "\n" + pw);
					if (result.equals("NOID")) {
						JOptionPane.showMessageDialog(null, "아이디가 없습니다.");
					} else if (result.equals("PASS")) {
						dispose();
						new UserJFrame(id);
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다.");
					}
				}

			}
		}
		// 회원가입
		else if (e.getSource() == joinButton) {
			// 회원가입 프레임
			new SignUpJFrame();
		}
		// 아이디/비밀번호 찾기
		else if (e.getSource() == idPwFindButton) {
			new IdPwFindJFrame();
		} else { // 종료
			JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
}
