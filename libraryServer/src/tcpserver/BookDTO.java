package tcpserver;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BookDTO implements Serializable {
	private String number;
	private String title;
	private String author;
	private String publisher;
	private String year;
	private String bill;
	private String rent;

	public BookDTO(String number, String title, String author, String publisher, String year, String bill,
			String rent) {
		super();
		this.number = number;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.year = year;
		this.bill = bill;
		this.rent = rent;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

}
