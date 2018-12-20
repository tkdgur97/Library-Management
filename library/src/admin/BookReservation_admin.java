package admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import tcpserver.ReservationDTO;
import tcpserver.TCPClient;
import javax.swing.JButton;

/*
 * 책 예약 현황 표
 */

@SuppressWarnings("serial")
public class BookReservation_admin implements ActionListener {
	private JPanel mainPanel;
	private JScrollPane scrollPane;
	private JPanel tableMainPanel;
	public JTable infoTable;
	public DefaultTableModel dtm;
	private JButton refreshButton;

	public BookReservation_admin() {

		// 패널만들기
		mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBounds(0, 0, 582, 506);
		mainPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		// DB에서 대출예약정보 가져오기
		ArrayList<ReservationDTO> reserInfo = new TCPClient().getUserReservationAll();

		// 표 만들기
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		String day = df.format(cal.getTime());

		String[] column = { "Id", "Title", "Date" };
		int count = 0;
		for (int i = 0; i < reserInfo.size(); i++) {
			ReservationDTO dto = (ReservationDTO) reserInfo.get(i);
			if (dto.getDate().equals(day)) {
				count++;
			}
		}

		// row값에 데이터 입력
		String[][] row = new String[count][column.length];

		int count2 = 0;
		for (int i = 0; i < reserInfo.size(); i++) {
			ReservationDTO dto = (ReservationDTO) reserInfo.get(i);
			if (dto.getDate().equals(day)) {
				row[count2][0] = dto.getId();
				row[count2][1] = dto.getTitle();
				row[count2][2] = dto.getDate();
				count2++;
			}
		}

		// 테이블에 데이터 입력
		dtm = new DefaultTableModel(row, column) { // 셀 수정 못하게 하는 부분
			public boolean isCellEditable(int a, int column) {
				return false;
			}
		};
		infoTable = new JTable(dtm);

		// 표를 넣을 패널 설정
		tableMainPanel = new JPanel();
		tableMainPanel.setLayout(null);
		tableMainPanel.setBounds(12, 37, 461, 456);
		mainPanel.add(tableMainPanel);

		// 스크롤 pane에 테이블 추가
		scrollPane = new JScrollPane(infoTable);
		scrollPane.setBounds(0, 0, 461, 456);
		tableMainPanel.add(scrollPane);

		refreshButton = new JButton("새로고침");
		refreshButton.setBounds(376, 10, 97, 23);
		refreshButton.addActionListener(this);
		mainPanel.add(refreshButton);
		infoTable.getTableHeader().setReorderingAllowed(false); // 이동 불가

	} // default constructor end

	public JPanel getPanel() {

		return mainPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == refreshButton) {
			// DB에서 대출예약정보 가져오기
			ArrayList<ReservationDTO> reserInfo = null;

			reserInfo = new TCPClient().getUserReservationAll();

			// 표 만들기
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();

			String day = df.format(cal.getTime());

			String[] column = { "Id", "Title", "Date" };
			int count = 0;
			for (int i = 0; i < reserInfo.size(); i++) {
				ReservationDTO dto = (ReservationDTO) reserInfo.get(i);
				if (dto.getDate().equals(day)) {
					count++;
				}
			}

			// row값에 데이터 입력
			String[][] row = new String[count][column.length];

			int count2 = 0;
			for (int i = 0; i < reserInfo.size(); i++) {
				ReservationDTO dto = (ReservationDTO) reserInfo.get(i);
				if (dto.getDate().equals(day)) {
					row[count2][0] = dto.getId();
					row[count2][1] = dto.getTitle();
					row[count2][2] = dto.getDate();
					count2++;
				}
			}

			dtm.setDataVector(row, column);

		}

	}

} // class end
