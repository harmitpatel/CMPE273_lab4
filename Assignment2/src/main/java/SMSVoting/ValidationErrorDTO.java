package SMSVoting;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorDTO {
	 
    private List<FieldError> fieldErrors = new ArrayList<>();
 
    public ValidationErrorDTO() {
 
    }
 
    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
 
}