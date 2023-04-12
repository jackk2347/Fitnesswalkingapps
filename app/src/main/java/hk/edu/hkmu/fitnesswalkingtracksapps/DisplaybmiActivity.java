package hk.edu.hkmu.fitnesswalkingtracksapps;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplaybmiActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText height ,weight;
    private Button cal , reset , back ;
    private TextView bmi_results , feedback ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_bmi);

        height = findViewById(R.id.height);
        weight= findViewById(R.id.weight);
        cal = findViewById(R.id.cal);
        reset = findViewById(R.id.reset);
        bmi_results = findViewById(R.id.bmi_results);
        feedback = findViewById(R.id.feedback);
        back = findViewById(R.id.back) ;

        cal.setOnClickListener(this);
        reset.setOnClickListener(this);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cal:
                cal();
                break ;
            case R.id.reset :
                reset();
                break ;
            case R.id.back:
                onBackPressed();
                break ;
            default:
                break ;
        }
    }




        public void cal(){
            String user_height = height.getText().toString();
            String user_weight = weight.getText().toString();

            if (user_height.length() > 0 && user_weight.length() > 0 && user_height != null && user_weight != null) {

                double bmi = Double.parseDouble(user_weight) / ((Double.parseDouble(user_height) / 100) * (Double.parseDouble(user_height) / 100));
                bmi_results.setText("Your BMI : " + String.format("%.1f", bmi));


                if (bmi < 18.5) {
                    feedback.setText("你的BMI範圍是 : 體重過輕\nYour BMI range : underweight");
                } else if (bmi >= 18.5 && bmi < 25) {
                    feedback.setText("你的BMI範圍是 : 正常範圍\nYour BMI range : Healthy Weight");
                } else if (bmi >= 25 && bmi < 29.9) {
                    feedback.setText("你的BMI範圍是 : 過重\nYour BMI range : overweight ");
                } else if (bmi >= 30) {
                    feedback.setText("你的BMI範圍是 : 重度肥胖\nYour BMI range : obese");
                }
            }

        }

        public void reset(){
            height.setText("");
            weight.setText("");
            feedback.setText("");
            bmi_results.setText("Your BMI : ?");
        }


}
