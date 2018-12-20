package admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.JButton;
import java.awt.SystemColor;

import javax.swing.border.BevelBorder;

import tcpserver.BookDTO;
import tcpserver.TCPClient;

/*
 * 도서 등록
 */

public class BookRegister_admin implements ActionListener {

	private JTextField numberField;
	private JTextField nameField;
	private JTextField authorField;
	private JTextField yearField;
	private JTextField publisherField;
	private JTextField billField;
	private JButton registButton;
	private JPanel mainPanel;

	public BookRegister_admin() {

		// 패널 설정
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 10, 502, 242);
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mainPanel.setBackground(SystemColor.control);

		// 책 등록 라벨
		JLabel label = new JLabel("책 등록");
		label.setBounds(219, 10, 57, 15);
		mainPanel.add(label);

		// 도서 일련번호 라벨
		JLabel bookNumber = new JLabel("도서 일련번호");
		bookNumber.setBounds(12, 51, 84, 15);
		mainPanel.add(bookNumber);

		// 도서 일련번호 입력 텍스트필드
		numberField = new JTextField();
		numberField.setColumns(10);
		numberField.setBounds(108, 48, 381, 21);
		mainPanel.add(numberField);

		// 도서 제목 라벨
		JLabel bookTitle = new JLabel("도서 제목");
		bookTitle.setBounds(12, 76, 84, 15);
		mainPanel.add(bookTitle);

		// 도서 제목 입력 텍스트필드
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(108, 73, 381, 21);
		mainPanel.add(nameField);

		// 도서 청구기호 라벨
		JLabel bookBill = new JLabel("도서 청구기호");
		bookBill.setBounds(12, 176, 84, 15);
		mainPanel.add(bookBill);

		// 도서 청구기호 텍스트필드
		billField = new JTextField();
		billField.setColumns(10);
		billField.setBounds(108, 173, 381, 21);
		mainPanel.add(billField);

		// 도서 저자 라벨
		JLabel bookAuthor = new JLabel("도서 저자");
		bookAuthor.setBounds(12, 101, 84, 15);
		mainPanel.add(bookAuthor);

		// 도서 저자 텍스트 필드
		authorField = new JTextField();
		authorField.setColumns(10);
		authorField.setBounds(108, 98, 381, 21);
		mainPanel.add(authorField);

		// 도서 출판년도 라벨
		JLabel bookYear = new JLabel("도서 발행 년도");
		bookYear.setBounds(12, 151, 84, 15);
		mainPanel.add(bookYear);

		// 도서 출판년도 텍스트필드
		yearField = new JTextField();
		yearField.setColumns(10);
		yearField.setBounds(108, 148, 381, 21);
		mainPanel.add(yearField);

		// 도서 발행처 라벨
		JLabel bookPublisher = new JLabel("발행처");
		bookPublisher.setBounds(12, 126, 57, 15);
		mainPanel.add(bookPublisher);

		// 도서 발행처 텍스트 필드
		publisherField = new JTextField();
		publisherField.setColumns(10);
		publisherField.setBounds(108, 123, 381, 21);
		mainPanel.add(publisherField);

		// 등록하기 버튼
		registButton = new JButton("도서 등록하기");
		registButton.setBounds(369, 206, 121, 23);
		registButton.addActionListener(this);
		mainPanel.add(registButton);

	} // default constructor end

	// 패널 반환
	public JPanel getPanel() {
		return mainPanel;
	}

	public void actionPerformed(ActionEvent e) {

		// 등록버튼을 눌렀을 때
		if (e.getSource() == registButton) {
			ArrayList<BookDTO> list = new TCPClient().getBookInfo();
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				BookDTO dto = list.get(i);
				if (dto.getNumber().equals(numberField.getText())) {
					JOptionPane.showMessageDialog(null, "해당 일련번호가 존재합니다.");
					count++;
					break;
				} else if (dto.getTitle().equals(nameField.getText())) {
					JOptionPane.showMessageDialog(null, "제목이 존재합니다.");
					count++;
					break;
				}
			}
			if (count == 0) {
				String info = numberField.getText() + "\n" + nameField.getText() + "\n" + authorField.getText() + "\n"
						+ publisherField.getText() + "\n" + yearField.getText() + "\n" + billField.getText();

				new TCPClient().bookRegist(info);

				numberField.setText("");
				nameField.setText("");
				authorField.setText("");
				publisherField.setText("");
				yearField.setText("");
				billField.setText("");
				JOptionPane.showMessageDialog(null, "등록완료");

			}
		}

	} // default constructor end
}// class end
