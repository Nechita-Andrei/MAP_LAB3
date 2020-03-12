package Utils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekCalculator {

    public long calculate(){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(2019, 8, 30);
        //end.set(2019, 9, 27);
        Date startDate = start.getTime();
        Date endDate = end.getTime();
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long diffTime = endTime - startTime;
        long diffDays = diffTime / (1000 * 60 * 60 * 24);
        //DateFormat dateFormat = DateFormat.getDateInstance();
//        System.out.println("The difference between "+
//                dateFormat.format(startDate)+" and "+
//                dateFormat.format(endDate)+" is "+
//                diffDays+" days.");
        return (diffDays/7) +1;
    }
}
