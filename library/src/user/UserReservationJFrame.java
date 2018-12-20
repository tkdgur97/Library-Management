package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.toedter.calendar.JDateChooser;

import tcpserver.ReservationDTO;
import tcpserver.TCPClient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

/*
 *  도서 예약
 */

@SuppressWarnings({ "serial", "deprecation" })
public class UserReservationJFrame extends JFrame implements ActionListener {

	private JDateChooser dateChooser;
	private JButton reservationButton, cancelButton;
	private Date dateMin, dateMax;
	private DefaultTableModel model;
	private JTable infoTable;
	private JScrollPane scrollPane;
	private String id, title;
	private Object[][] content;
	private ArrayList<ReservationDTO> list;
	private int date, year, mon, count;
	public JButton checkButton, reserCancelButton;

	public UserReservationJFrame(String id, String title) {

		this.id = id;
		this.title = title;

		// 메인 프레임 설정
		setTitle("도서 예약");
		setSize(526, 500);
		getContentPane().setLayout(null);

		// DateChooser 설정
		dateChooser = new JDateChooser();
		dateChooser.setBounds(158, 288, 190, 23);
		getContentPane().add(dateChooser);

		// 도서 예약 버튼 설정
		reservationButton = new JButton("도서 예약");
		reservationButton.setBounds(383, 288, 97, 23);
		getContentPane().add(reservationButton);

		// 예약 날짜 선택 라벨
		JLabel resLab = new JLabel("예약 날짜 선택");
		resLab.setFont(new Font("굴림", Font.BOLD, 15));
		resLab.setBounds(34, 288, 112, 21);
		getContentPane().add(resLab);

		// 메인 테이블 패널 설정
		JPanel talbeMainPanel = new JPanel();
		talbeMainPanel.setLayout(new BorderLayout());
		talbeMainPanel.setBounds(12, 10, 483, 253);
		getContentPane().add(talbeMainPanel);

		// 로그인한 일반 사용자의 도서 예약 목록 정보를 가져옴
		list = new TCPClient().getUserReservation(id);

		// 테이블 설정
		String[] header = { "id", "title", "date" };
		content = new Object[list.size()][header.length];

		// 로그인한 일반 사용자의 도서 에약 목록 출력
		for (int i = 0; i < content.length; i++) {
			ReservationDTO dto = list.get(i);
			content[i][0] = dto.getId();
			content[i][1] = dto.getTitle();
			content[i][2] = dto.getDate();
		}
		model = new DefaultTableModel(content, header);
		infoTable = new JTable(model) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane = new JScrollPane(infoTable);
		talbeMainPanel.add(scrollPane);

		// 예약 취소 버튼 설정
		cancelButton = new JButton("예약 취소");
		cancelButton.setBounds(383, 336, 97, 23);
		getContentPane().add(cancelButton);

		// 선택한 도서명 출력 라벨
		JLabel label = new JLabel("선택 하신 도서명 : " + title);
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setBounds(34, 340, 314, 21);
		getContentPane().add(label);

		// 예약 가능 날짜 선택 범위 제한(현재 날짜(예약 불가) 이후를 기준으로 7일 이내 예약 가능)
		dateMin = new Date();
		dateMin.setDate(dateMin.getDate());
		dateMax = new Date();
		dateMax.setDate(dateMin.getDate() + 7);
		dateChooser.setSelectableDateRange(dateMin, dateMax);

		// 이벤트 설정
		reservationButton.addActionListener(this);
		cancelButton.addActionListener(this);
		checkButton = new JButton("check");
		reserCancelButton = new JButton("cancel");
		setVisible(true);

	} // default constructor end

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {

		// 도서 예약
		if (e.getSource() == reservationButton) {
			if (model.getRowCount() < 3) {
				if (dateChooser.getDate() == null) {
					JOptionPane.showMessageDialog(null, "날짜를 선택해 주세요");
				} else {

					// 사용자가 선택한 날짜 정보 가져오기(년도, 달, 일)
					year = dateChooser.getDate().getYear() + 1900;
					mon = dateChooser.getDate().getMonth() + 1;
					date = dateChooser.getDate().getDate();

					String resDate = year + "-" + mon + "-" + date;

					// 예약한 도서 기존 테이블 항목에 추가

					ArrayList<ReservationDTO> result = new TCPClient().getUserReservationAll();
					for (int i = 0; i < result.size(); i++) {
						if (result.get(i).getTitle().equals(title)) {
							count++;
						}
					}

					if (count != 0) {
						JOptionPane.showMessageDialog(null, "이미 예약 된 도서 입니다.", "예약 불가", JOptionPane.CANCEL_OPTION);
					} else {
						
						Object[] addContent = { id, title, resDate };
						model.addRow(addContent);
						new TCPClient().makeReservation(id, title, resDate);
						
						checkButton.doClick();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "예약 권 수가 초과되었습니다.");
			}

		} // if end

		// 예약 도서 취소
		else if (e.getSource() == cancelButton) {

			// 선택한 예약 도서 정보 값 가져오기
			int row = infoTable.getSelectedRow();
			int column = 0; // 선택한 행의 일련 번호
			String title2 = (String) infoTable.getValueAt(row, 1);
			column = 2; // 선택한 행의 예약 날자

			String info = id + "\n" + title2;

			new TCPClient().reservationCancel(info);
			// 예약 취소 완료 후 테이블에서 해당 예약 항목 제거
			model.removeRow(row);
			if(title2.equals(title)) {
				count--;
			}
			reserCancelButton.setToolTipText(title2);
			reserCancelButton.doClick();

		} // else if end

	} // actionPerformed end

} // class end
