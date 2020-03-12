package Validation;



public class DataValidator extends AbstractValidator {
    private DataValidator(){
        super();
    }
    private static DataValidator instance=new DataValidator();
    public static DataValidator getInstance(){
        return instance;
    }
}
