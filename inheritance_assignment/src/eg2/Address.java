package eg2;

public class Address {

	private int adId;
	private int streetNum;
	private String streetName;
	private String cityName;
	private String state;
	private int zipCode;
	
	public Address() {
		// TODO Auto-generated constructor stub
	}
	
	// Source -> Generate constructor using fields
	
	public Address(int adId, int streetNum, String streetName, String cityName, String state, int zipCode) {
		super();
		this.adId = adId;
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.cityName = cityName;
		this.state = state;
		this.zipCode = zipCode;
	}
	
	// Source -> Generate Getter and Setter
	
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	public int getStreetNum() {
		return streetNum;
	}
	public void setStreetNum(int streetNum) {
		this.streetNum = streetNum;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "Address [adId=" + adId + ", streetNum=" + streetNum + ", streetName=" + streetName + ", cityName="
				+ cityName + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
	
	
	
}
