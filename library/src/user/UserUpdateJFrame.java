package user;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import tcpserver.MemberDTO;
import tcpserver.TCPClient;

import javax.swing.JPasswordField;
import javax.swing.JButton;

/*
 *  회원 정보 수정
 */

@SuppressWarnings("serial")
public class UserUpdateJFrame extends JFrame implements ActionListener {
	private JTextField idField;
	private JTextField nameField;
	private JTextField telField;
	private JTextField addrField;
	private JPasswordField pwField;
	private JButton pwUpdateButton, updateComButton, cancleButton;
	private String userId, userPw;

	public UserUpdateJFrame(MemberDTO dto) {

		// 메인 프레임 설정
		setTitle("회원 정보 수정");
		setSize(450, 500);
		getContentPane().setLayout(null);

		// 아이디 라벨
		JLabel idLab = new JLabel("ID");
		idLab.setFont(new Font("굴림", Font.PLAIN, 15));
		idLab.setBounds(41, 61, 57, 15);
		getContentPane().add(idLab);

		// 아이디 텍스트 필드
		idField = new JTextField();
		idField.setText(dto.getId());
		userId = dto.getId(); // userId에 로그인한 유저 아이디를 저장.
		idField.setEditable(false); // 아이디를 수정 못하도록 설정
		idField.setBounds(161, 58, 142, 21);
		getContentPane().add(idField);
		idField.setColumns(10);

		// 비밀번호 라벨
		JLabel pwLab = new JLabel("PW");
		pwLab.setFont(new Font("굴림", Font.PLAIN, 15));
		pwLab.setBounds(41, 120, 57, 15);
		getContentPane().add(pwLab);

		// 비밀번호 패스워드 필드
		pwField = new JPasswordField();
		pwField.setText(dto.getPw());
		userPw = dto.getPw(); // userPw에 로그인한 유저 비밀번호를 저장.
		pwField.setEditable(false);
		pwField.setBounds(161, 117, 142, 21);
		getContentPane().add(pwField);

		// 이름 라벨
		JLabel nameLab = new JLabel("NAME");
		nameLab.setFont(new Font("굴림", Font.PLAIN, 15));
		nameLab.setBounds(41, 175, 57, 15);
		getContentPane().add(nameLab);

		// 이름 텍스트 필드
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setText(dto.getName());
		nameField.setBounds(161, 172, 142, 21);
		getContentPane().add(nameField);

		// 전화번호 라벨
		JLabel telLab = new JLabel("TEL");
		telLab.setFont(new Font("굴림", Font.PLAIN, 15));
		telLab.setBounds(41, 238, 57, 15);
		getContentPane().add(telLab);

		// 전화번호 텍스트 필드
		telField = new JTextField();
		telField.setColumns(10);
		telField.setText(dto.getTel());
		telField.setBounds(161, 235, 142, 21);
		getContentPane().add(telField);

		// 주소 라벨
		JLabel addrLab = new JLabel("ADDRESS");
		addrLab.setFont(new Font("굴림", Font.PLAIN, 15));
		addrLab.setBounds(41, 306, 76, 15);
		getContentPane().add(addrLab);

		// 주소 텍스트 필드
		addrField = new JTextField();
		addrField.setColumns(10);
		addrField.setText(dto.getAddress());
		addrField.setBounds(161, 303, 142, 21);
		getContentPane().add(addrField);

		// 비밀번호 변경(버튼)
		pwUpdateButton = new JButton("비밀번호 변경");
		pwUpdateButton.setFont(new Font("굴림", Font.PLAIN, 11));
		pwUpdateButton.setBounds(315, 116, 107, 23);
		getContentPane().add(pwUpdateButton);

		// 수정 완료(버튼)
		updateComButton = new JButton("수정 완료");
		updateComButton.setBounds(98, 373, 107, 40);
		getContentPane().add(updateComButton);

		// 취소(버튼)
		cancleButton = new JButton("취소");
		cancleButton.setBounds(240, 373, 107, 40);
		getContentPane().add(cancleButton);

		// 버튼 이벤트
		pwUpdateButton.addActionListener(this);
		updateComButton.addActionListener(this);
		cancleButton.addActionListener(this);

		setVisible(true);
	} // default constructor end

	@Override
	public void actionPerformed(ActionEvent e) {

		// 비밀번호 변경
		if (e.getSource() == pwUpdateButton) {
			new UserChangePasswordJFrame(userId, userPw);
		}

		// 회원정보 수정
		else if (e.getSource() == updateComButton) {

			String name = nameField.getText();
			String tel = telField.getText();
			String add = addrField.getText();
			String result;

			result = new TCPClient().userInfoChange(userId + "\n" + name + "\n" + tel + "\n" + add);
			if (result.equals("성공")) {
				dispose();
				JOptionPane.showMessageDialog(null, "개인정보 수정 완료");
			} else {
				JOptionPane.showMessageDialog(null, "정보 수정 실패");
			}

		} else if (e.getSource() == cancleButton) {// 취소
			dispose();
		}

	} // actionPerformed end

} // class end
