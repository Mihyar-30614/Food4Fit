package cs.dal.food4fit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mihyar on 11/30/2017.
 * Count Down Timer
 */

public class TimerActivity extends AppCompatActivity {

    Button timerStart;
    TextView hours, minutes, viewHours, viewMinutes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        // Initialize Page content
        timerStart = (Button) findViewById(R.id.timerStart);
        hours      = (TextView) findViewById(R.id.timer_hours);
        minutes    = (TextView) findViewById(R.id.timer_minutes);
        viewHours  = (TextView) findViewById(R.id.timerViewHours);
        viewMinutes = (TextView) findViewById(R.id.timerViewMinutes);

        // Set Timer to 00:00
        hours.setText("00");
        minutes.setText("00");
    }

    // Start Timer
    public void startTimer( View view){
        // Get the Values of the timer
        String timerHours   = hours.getText().toString();
        String timerMinutes = minutes.getText().toString();

        // Set the View Timer Values
        viewHours.setText(timerHours);
        viewMinutes.setText(timerMinutes);

        // Calculate the time in milliseconds
        int h = Integer.parseInt(timerHours);
        int m = Integer.parseInt(timerMinutes);
        final int timer  = (((h*60) + m)*60)*1000;

        // Set a listener
        timerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create the countdown timer
                new CountDownTimer(timer, 60000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        int min = Integer.parseInt(viewMinutes.getText().toString());
                        int hrs = Integer.parseInt(viewHours.getText().toString());
                        if (min - 1 < 0){
                            viewMinutes.setText("59");
                            if (hrs - 1 < 0){
                                viewHours.setText("00");
                            }else{
                                viewHours.setText(hrs - 1);
                            }
                        }else{
                            viewMinutes.setText(min - 1);
                        }
                    }

                    @Override
                    public void onFinish() {

                    }
                };
            }
        });
    }
}
