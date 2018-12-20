package admin;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import tcpserver.MemberDTO;
import tcpserver.TCPClient;

import javax.swing.JButton;

/*
 * 	회원정보 수정 화면
 */

@SuppressWarnings("serial")
public class UserUpdateJFrame_admin extends JFrame implements ActionListener {
	private JTextField idField;
	private JTextField nameField;
	private JTextField telField;
	private JTextField addrField;
	private JTextField pwField;
	JButton btnUpdateCom, btnCancle, btnCheck;

	String userId, userPw; // 로그인한 유저 아이디와 비밀번호 정보를 저장할 변수 선언

	public UserUpdateJFrame_admin(MemberDTO dto) {
		setTitle("회원 정보 수정(관리자)");
		setSize(450, 500);
		getContentPane().setLayout(null);

		// 아이디 라벨 및 입력 필드
		JLabel idLab = new JLabel("ID");
		idLab.setFont(new Font("굴림", Font.PLAIN, 15));
		idLab.setBounds(41, 61, 57, 15);
		getContentPane().add(idLab);

		idField = new JTextField();
		idField.setText(dto.getId());
		userId = dto.getId(); // userId에 로그인한 유저 아이디를 저장.
		idField.setEditable(false); // 아이디를 수정 못하도록 설정
		idField.setBounds(161, 58, 142, 21);
		getContentPane().add(idField);
		idField.setColumns(10);

		// 비밀번호 라벨 및 입력 필드
		JLabel pwLab = new JLabel("PW");
		pwLab.setFont(new Font("굴림", Font.PLAIN, 15));
		pwLab.setBounds(41, 120, 57, 15);
		getContentPane().add(pwLab);

		pwField = new JTextField();
		pwField.setText(dto.getPw());
		userPw = dto.getPw(); // userPw에 로그인한 유저 비밀번호를 저장.
		pwField.setBounds(161, 117, 142, 21);
		getContentPane().add(pwField);

		// 이름 라벨 및 입력 필드
		JLabel nameLab = new JLabel("NAME");
		nameLab.setFont(new Font("굴림", Font.PLAIN, 15));
		nameLab.setBounds(41, 175, 57, 15);
		getContentPane().add(nameLab);

		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setText(dto.getName());
		nameField.setBounds(161, 172, 142, 21);
		getContentPane().add(nameField);

		// 전화번호 라벨 및 입력 필드
		JLabel telLab = new JLabel("TEL");
		telLab.setFont(new Font("굴림", Font.PLAIN, 15));
		telLab.setBounds(41, 238, 57, 15);
		getContentPane().add(telLab);

		telField = new JTextField();
		telField.setColumns(10);
		telField.setText(dto.getTel());
		telField.setBounds(161, 235, 142, 21);
		getContentPane().add(telField);

		// 주소 라벨 및 입력 필드
		JLabel addrLab = new JLabel("ADDRESS");
		addrLab.setFont(new Font("굴림", Font.PLAIN, 15));
		addrLab.setBounds(41, 306, 76, 15);
		getContentPane().add(addrLab);

		addrField = new JTextField();
		addrField.setColumns(10);
		addrField.setText(dto.getAddress());
		addrField.setBounds(161, 303, 142, 21);
		getContentPane().add(addrField);

		// 수정 완료(버튼)
		btnUpdateCom = new JButton("수정 완료");
		btnUpdateCom.setBounds(98, 373, 107, 40);
		getContentPane().add(btnUpdateCom);

		// 취소(버튼)
		btnCancle = new JButton("취소");
		btnCancle.setBounds(240, 373, 107, 40);
		getContentPane().add(btnCancle);

		// 버튼 이벤트
		btnUpdateCom.addActionListener(this);
		btnCancle.addActionListener(this);
		btnCheck = new JButton("check");
		btnCheck.addActionListener(this);
		setVisible(true);
	} // default constructor end

	@Override
	public void actionPerformed(ActionEvent e) {
		// 회원정보 수정
		if (e.getSource() == btnUpdateCom) {
			String name = nameField.getText();
			String pw = pwField.getText();
			String tel = telField.getText();
			String add = addrField.getText();

			new TCPClient().userInfoChange_admin(userId + "\n" + pw + "\n" + name + "\n" + tel + "\n" + add);
			String[] buttons = { "확인" };
			int c = JOptionPane.showOptionDialog(null, "아이디 : " + name, "회원 정보 수정", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, buttons, "두번째값");
			if (c == 0) {
				btnCheck.doClick();

			}
			dispose();

			// 취소 버튼 클릭시
		} else if (e.getSource() == btnCancle) {
			dispose();
		}
	} // actionPerformed end
}// class end
