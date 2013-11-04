package wiredup.http;

public class ResponsePair {
	private String jsonData;
	private int statusCode;
	
	public ResponsePair() {
		
	}
	
	public ResponsePair(String jsonData, int statusCode) {
		this.jsonData = jsonData;
		this.statusCode = statusCode;
	}
	
	public String getJsonData() {
		return this.jsonData;
	}
	
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public int getStatusCode() {
		return this.statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
