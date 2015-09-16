package org.michaelevans.espresso.intents.sample;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ContactPickerActivity extends AppCompatActivity {

  private final static int CONTACT_REQUEST_CODE = 1;

  private TextView phoneNumber;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contact_picker);

    phoneNumber = (TextView) findViewById(R.id.phone_number);
    findViewById(R.id.pick_contact).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent =
            new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, CONTACT_REQUEST_CODE);
      }
    });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == CONTACT_REQUEST_CODE && resultCode == RESULT_OK) {
      Uri contactUri = data.getData();
      phoneNumber.setText(contactUri.toString());
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
}
