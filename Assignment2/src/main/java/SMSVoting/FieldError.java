package SMSVoting;

public class FieldError {
	private String field;
    private String message;
    
    public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

 
    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
 
}