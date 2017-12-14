package abdulrahmanjavanrd.com.musicbook_demo2.utilities;

/**
 * Created by nfs05 on 13/12/2017.
 */

public class ConvertTime {

    public static String millisecond(long m){
        String totalTime = "";
        String totalSecond = "";

        // covert minute and second
        int minute = (int)(m %(1000*60*60))/(1000*60);
        int second =  (int)(m %(1000*60*60))%(1000*60)/(1000);

        if( second < 10){
            totalSecond = "0"+ second ;
        }else {
            totalSecond = ""+second ;
        }
        totalTime = totalTime + minute +":"+totalSecond ;
        return totalTime;
    }

}
