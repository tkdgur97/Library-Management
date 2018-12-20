package tcpserver;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MemberDTO implements Serializable {
	private String id;
	private String pw;
	private String name;
	private String tel;
	private String address;
	private String bookrentcurrent;
	private String bookrentcumlative;
	private String booklate;
	private String rrn;
	private String image;

	public MemberDTO() {
	}

	public MemberDTO(String id, String pw, String name, String tel, String address, String bookrentcurrent,
			String bookrentcumlative, String booklate, String rrn) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.tel = tel;
		this.address = address;
		this.bookrentcurrent = bookrentcurrent;
		this.bookrentcumlative = bookrentcumlative;
		this.booklate = booklate;
		this.rrn = rrn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBookrentcurrent() {
		return bookrentcurrent;
	}

	public void setBookrentcurrent(String bookrentcurrent) {
		this.bookrentcurrent = bookrentcurrent;
	}

	public String getBookrentcumlative() {
		return bookrentcumlative;
	}

	public void setBookrentcumlative(String bookrentcumlative) {
		this.bookrentcumlative = bookrentcumlative;
	}

	public String getBooklate() {
		return booklate;
	}

	public void setBooklate(String booklate) {
		this.booklate = booklate;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
