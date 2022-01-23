package ooga.data.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Class to represent FireBase DataBase.
 */
public class FireBase {

  private static final String DATABASE_URL = "https://ooga-6eac1-default-rtdb.firebaseio.com";
  private FirebaseDatabase firebaseDatabase;

  /**
   * Constructor to initialize database and scores.
   */
  public FireBase() {

    try {
      FileInputStream serviceAccount =
          new FileInputStream("ooga-6eac1-firebase-adminsdk-youru-305a55a180.json");

      FirebaseOptions options = new FirebaseOptions.Builder()
          .setCredentials(GoogleCredentials.fromStream(serviceAccount))
          .setDatabaseUrl(DATABASE_URL)
          .build();

      if (FirebaseApp.getApps().size() == 0) {
        FirebaseApp.initializeApp(options);
      }
      firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_URL);
    } catch (IOException ioe) {
    }

  }

  /**
   * Retrieves data based off the key.
   *
   * @param key key for data
   * @return data
   */
  public Object retrieve(String key) {
    Object[] ret = {null};
    if (FirebaseApp.getApps().size() == 0) {
      return false;
    }
    try {
      DatabaseReference ref = firebaseDatabase.getReference(key);
      CountDownLatch latch = new CountDownLatch(1);
      ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          ret[0] = dataSnapshot.getValue(Object.class);
          latch.countDown();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
          latch.countDown();
        }
      });
      latch.await();
    } catch (InterruptedException ignored) {
      return false;
    }
    return ret[0];
  }

  /**
   * Saves data to database based on value key pair.
   *
   * @param value data
   * @param key   key
   */
  public Boolean update(Object value, String key) {
    final Boolean[] ret = {false};
    if (FirebaseApp.getApps().size() == 0 || value == null) {
      return false;
    }
    try {
      DatabaseReference ref = firebaseDatabase.getReference(key);
      final CountDownLatch latch = new CountDownLatch(1);
      ref.setValue(value, (databaseError, databaseReference) -> {
        if (databaseError == null) {
          ret[0] = true;
        }
        latch.countDown();
      });
      latch.await();
    } catch (InterruptedException ignored) {
      return false;
    }
    return ret[0];
  }

}
