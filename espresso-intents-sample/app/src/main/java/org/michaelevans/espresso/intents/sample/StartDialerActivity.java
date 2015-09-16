package org.michaelevans.espresso.intents.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class StartDialerActivity extends AppCompatActivity {

  private EditText phoneInput;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start_dialer);

    phoneInput = (EditText) findViewById(R.id.number_input);

    findViewById(R.id.dial_button).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (!TextUtils.isEmpty(phoneInput.getText())) {
          Intent dialer = new Intent(Intent.ACTION_DIAL);
          dialer.setData(Uri.parse("tel:" + phoneInput.getText().toString()));
          startActivity(dialer);
        }
      }
    });
  }
}
