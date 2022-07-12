package im.tobe.sqliteandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import im.tobe.sqliteandroid.data.DBHandler;
import im.tobe.sqliteandroid.databinding.ActivityMainBinding;
import im.tobe.sqliteandroid.model.Contact;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        DBHandler db = new DBHandler(MainActivity.this);

        Contact contact1 = new Contact();
        contact1.setName("Jeremy");
        contact1.setPhoneNumber("12344555");

        Contact contact2 = new Contact();
        contact2.setName("Jason");
        contact2.setPhoneNumber("938844332");

//        db.addContact(contact2);

//        Contact contactFound = db.getContact(2);
//        Log.d("MainActivity", "onCreate: contactFound - "+ contactFound.getName() );
//
//        contactFound.setName("Tommy");
//        contactFound.setPhoneNumber("29499933");
//        int updatedRow = db.updateContact(contactFound);
//        Log.d("MainActivity", "onCreate: updatedRow - "+updatedRow);

//        db.deleteContact(contactFound);

        int count = db.getContactCount();
        Log.d("MainActivity", "onCreate: count - "+count);

        List<Contact> contactList = db.getAllContacts();
        for (Contact contact: contactList ) {
            Log.d("MainActivity", "onCreate: "+contact.getId()+" "+contact.getName());
        }
    }
}