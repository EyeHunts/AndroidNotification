package in.eyehunt.androidnotification;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final int notification_one = 1;
    private MainUi mainUI;

    private NotificationHelper notificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationHelper = new NotificationHelper(this);
        mainUI = new MainUi(findViewById(R.id.activity_main));
    }

    //Postthe notifications
    public void postNotification(int id, String title) {
        Notification.Builder notificationBuilder = null;

        notificationBuilder = notificationHelper.getNotification1(title,
                getString(R.string.channel_one_body));

        if (notificationBuilder != null) {
            notificationHelper.notify(id, notificationBuilder);
        }
    }

    //Implement onClickListeners
    class MainUi implements View.OnClickListener {
        final EditText editTextOne;

        private MainUi(View root) {
            editTextOne = (EditText) root.findViewById(R.id.channel_one_text);
            ((Button) root.findViewById(R.id.post_to_channel_one)).setOnClickListener(this);
            ((Button) root.findViewById(R.id.channel_one_settings)).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.post_to_channel_one:
                    postNotification(notification_one, editTextOne.getText().toString());
                    break;

                case R.id.channel_one_settings:
                    goToNotificationSettings(NotificationHelper.CHANNEL_ONE_ID);
                    break;

            }
        }

        //settings screen for the selected notification channel
        public void goToNotificationSettings(String channel) {
            Intent i = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
            i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
            i.putExtra(Settings.EXTRA_CHANNEL_ID, channel);
            startActivity(i);
        }
    }
}