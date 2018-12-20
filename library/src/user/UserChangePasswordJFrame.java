package user;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import tcpserver.TCPClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/*
 *  비밀번호 변경
 */

@SuppressWarnings("serial")
public class UserChangePasswordJFrame extends JFrame implements ActionListener {

	private String userId;
	private String userPw;
	private JButton changeButton;
	private JPasswordField currentField, changeField;

	public UserChangePasswordJFrame(String userId, String userPw) {

		this.userId = userId;
		this.userPw = userPw;

		// 현재 비밀번호
		JLabel CurrentLabel = new JLabel("현재 비밀번호");
		CurrentLabel.setBounds(8, 8, 93, 15);
		currentField = new JPasswordField(10);
		currentField.setBounds(113, 5, 116, 21);

		// 변경 비밀번호
		JLabel ChangeLabel = new JLabel("변경할 비밀번호");
		ChangeLabel.setBounds(8, 34, 104, 18);
		changeField = new JPasswordField(10);
		changeField.setBounds(113, 31, 116, 21);

		// 변경하기 버튼
		changeButton = new JButton("변경하기");
		changeButton.setBounds(8, 62, 221, 21);

		// 버튼 액션
		changeButton.addActionListener(this);

		// JFrame 설정 및 추가
		add(CurrentLabel);
		add(currentField);
		add(ChangeLabel);
		add(changeField);
		add(changeButton);
		setLayout(null);
		setTitle("비밀번호 변경");
		setResizable(false);
		setBounds(300, 700, 247, 126);
		setVisible(true);

	} // default constructor end

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {

		// 변경하기 버튼을 눌렀을 경우
		if (e.getSource() == changeButton) {

			// 사용자 비밀번호 일치
			if (currentField.getText().equals(userPw)) {

				// 비밀번호 변경
				if (!changeField.getText().equals("")) {

					String result = new TCPClient().pwChange(userId + "\n" + changeField.getText());
					if (result.equals("성공")) {
						JOptionPane.showMessageDialog(null, "비밀번호 변경 성공");
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호 변경 실패");
					}

					// 비밀번호 변경이 성공하면 프로그램 종료
				}

				// 사용자 비밀번호 불일치
			} else {
				JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "비밀번호 불일치", JOptionPane.ERROR_MESSAGE);
			}
		} // if end

	} // actionPerformed end

} // class end
