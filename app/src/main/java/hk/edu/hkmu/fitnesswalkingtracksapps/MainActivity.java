package hk.edu.hkmu.fitnesswalkingtracksapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button lan_1 ,lan_2 ,lan_3,bmi_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lan_1 = findViewById(R.id.lan_1);
        lan_2 = findViewById(R.id.lan_2);
        lan_3 = findViewById(R.id.lan_3);
        bmi_btn = findViewById(R.id.bmi_btn);


        lan_1.setOnClickListener(this);
        lan_2.setOnClickListener(this);
        lan_3.setOnClickListener(this);
        bmi_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent ;
        switch(v.getId()){
            case R.id.lan_1 :
                intent = new Intent(this,DisplayActivity.class);
                intent.putExtra("language",0);
                startActivity(intent);
                break ;
            case R.id.lan_2:
                intent = new Intent(this,DisplayActivity.class);
                intent.putExtra("language",1);
                startActivity(intent);
                break ;
            case R.id.lan_3 :
                intent = new Intent(this,DisplayActivity.class);
                intent.putExtra("language",2);
                startActivity(intent);
                break ;
            case R.id.bmi_btn :
                intent = new Intent(this,DisplaybmiActivity.class);
                startActivity(intent);
            default:
                break ;
        }

    }
}