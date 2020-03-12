package Validation;

public class ValidationException extends Exception {
    public ValidationException(String message){
        super(message);
    }
    public ValidationException(){};
    public String getMesaj(){
        return super.getMessage();
    }
}
