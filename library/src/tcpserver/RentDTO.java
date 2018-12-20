package tcpserver;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RentDTO implements Serializable {
	private String id;
	private String title;
	private String rentDay;
	private String returnDay;
	private String extensionDay;
	private String returnCheck;

	public RentDTO(String id, String title, String rentDay, String returnDay, String extensionDay, String returnCheck) {
		super();
		this.id = id;
		this.title = title;
		this.rentDay = rentDay;
		this.returnDay = returnDay;
		this.extensionDay = extensionDay;
		this.returnCheck = returnCheck;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRentDay() {
		return rentDay;
	}

	public void setRentDay(String rentDay) {
		this.rentDay = rentDay;
	}

	public String getReturnDay() {
		return returnDay;
	}

	public void setReturnDay(String returnDay) {
		this.returnDay = returnDay;
	}

	public String getExtensionDay() {
		return extensionDay;
	}

	public void setExtensionDay(String extensionDay) {
		this.extensionDay = extensionDay;
	}

	public String getReturnCheck() {
		return returnCheck;
	}

	public void setReturnCheck(String returnCheck) {
		this.returnCheck = returnCheck;
	}

}
