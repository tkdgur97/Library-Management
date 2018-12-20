package user;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import tcpserver.MemberDTO;
import tcpserver.TCPClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class UserWithdrawalJFrame extends JFrame implements ActionListener {

	private JPasswordField PasswordField, PasswordCheckField;
	private JButton deleteButton;
	private String userId, userPw;

	public UserWithdrawalJFrame(MemberDTO dto) {

		this.userId = dto.getId();
		this.userPw = dto.getPw();

		// 비밀번호 라벨
		JLabel PasswordLabel = new JLabel("비밀번호");
		PasswordLabel.setBounds(12, 22, 57, 15);

		// 비밀번호 패스워드 필드
		PasswordField = new JPasswordField(10);
		PasswordField.setBounds(110, 19, 136, 21);

		// 비밀번호 확인 라벨
		JLabel PasswordCheckLabel = new JLabel("비밀번호 확인");
		PasswordCheckLabel.setBounds(12, 50, 99, 15);

		// 비밀번호 확인 패스워드 필드
		PasswordCheckField = new JPasswordField(10);
		PasswordCheckField.setBounds(110, 47, 136, 21);

		// 회원탈퇴 버튼
		deleteButton = new JButton("회원탈퇴");
		deleteButton.setBounds(79, 78, 97, 23);

		// JFrame에 추가 및 설정
		add(PasswordLabel);
		add(PasswordField);
		add(PasswordCheckLabel);
		add(PasswordCheckField);
		add(deleteButton);

		successButton = new JButton("성공");

		// 버튼 이벤트(회원 탈퇴)
		deleteButton.addActionListener(this);
		setTitle("회원 탈퇴");
		setLayout(null);
		setResizable(false);
		setBounds(300, 700, 281, 138);
		setVisible(true);

	} // default constructor end

	public JButton successButton;

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		// 회원 탈퇴
		if (e.getSource() == deleteButton) {
			String pw = PasswordField.getText();
			String pwCheck = PasswordCheckField.getText();

			// 비밀 번호 확인
			if (pw.equals(userPw) && pwCheck.equals(userPw)) {
				String result = new TCPClient().withdrawal(userId);
				if (result.equals("성공")) {
					JOptionPane.showMessageDialog(null, "회원탈퇴 성공");
					dispose();

					successButton.doClick();

				} else {
					JOptionPane.showMessageDialog(null, "비밀번호가 틀립니다.", "비밀번호 오류", JOptionPane.ERROR_MESSAGE);
				}

			}
		}
	} // actionPerformed end

} // class end
