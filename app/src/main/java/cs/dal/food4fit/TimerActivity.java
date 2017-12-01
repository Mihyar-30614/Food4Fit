package cs.dal.food4fit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Mihyar on 11/30/2017.
 * Count Down Timer
 */

public class TimerActivity extends AppCompatActivity {

    Button timerStart;
    TextView hours, minutes, viewHours, viewMinutes;
    ProgressBar progressBar;
    int percent = 0;

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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    // Start Timer
    public void startTimer( View view){
        // Set Timer to 00:00
        if (hours.getText().toString().equals("")){
            hours.setText("00");
        }
        if (minutes.getText().toString().equals("")){
            minutes.setText("00");
        }
        progressBar.setProgress(0);

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

        if (h != 0 || m != 0){
            timerStart.setText("Start");

            // Set a listener
            timerStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerStart.setEnabled(false);
                    // Create the countdown timer
                    new CountDownTimer(timer, 60000){

                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Set the percentage
                            percent += 60000;
                            int progress = 100/(timer/percent);
                            progressBar.setProgress(progress);

                            int min = Integer.parseInt(viewMinutes.getText().toString());
                            int hrs = Integer.parseInt(viewHours.getText().toString());
                            if (min - 1 < 0){
                                viewMinutes.setText("59");
                                if (hrs - 1 < 0){
                                    viewHours.setText("00");
                                }else{
                                    viewHours.setText(Integer.toString(hrs - 1));
                                }
                            }else{
                                viewMinutes.setText(Integer.toString(min - 1));
                            }
                        }

                        @Override
                        public void onFinish() {
                            // Reset everything
                            timerStart.setEnabled(true);
                            timerStart.setText("Set");
                            viewMinutes.setText("00");
                            minutes.setText("00");
                            progressBar.setProgress(100);

                            // Notification, Vibrate, sound
                            peep();
                        }
                    }.start();
                }
            });
        }
    }

    private void peep() {
        android.support.v4.app.NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.timer)
                        .setContentTitle("Cooking Timer")
                        .setContentText("Your meal is ready");
        Intent notificationIntent = new Intent(this, TimerActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        // Color of the notification LED
        builder.setLights(Color.GREEN, 500, 500);
        // Vibrate with these patterns
        long[] pattern = {500,500,500,500,500,500,500};
        builder.setVibrate(pattern);
        builder.setStyle(new NotificationCompat.InboxStyle());
        // Add the sound
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(notificationSound);
        // Push the Notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
