package bochynski.karol.bmi;

/**
 * Created by Karol on 2017-03-23.
 */

public class AnalyzerBMI {

    /**
     * @return String - description for given BMI result
     */
    public static int getDescriptionResource(float BMIresult){
        int description;
        if(BMIresult < 18.5){
            description = R.string.bmi_result_underweight;
        }
        else if(BMIresult < 25){
            description = R.string.bmi_result_healthy;
        }
        else if(BMIresult < 30){
            description = R.string.bmi_result_overweight;
        }
        else if(BMIresult < 40){
            description = R.string.bmi_result_severely_obese;
        }
        else{
            description = R.string.bmi_result_morbidly_obese;
        }
        return description;
    }

    /**
     * @return int - resource of color for given bmi result
     */
    public static int getColorResourceForResult(float BMIresult){
        int resultColor;
        if(BMIresult < 18.5){
            resultColor = R.color.bmi_result_underweight;
        }
        else if(BMIresult < 25){
            resultColor = R.color.bmi_result_healthy;
        }
        else if(BMIresult < 30){
            resultColor = R.color.bmi_result_overweight;
        }
        else if(BMIresult < 40){
            resultColor = R.color.bmi_result_severely_obese;
        }
        else{
            resultColor = R.color.bmi_result_morbidly_obese;
        }
        return resultColor;
    }
}
