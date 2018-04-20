package app.com.roadhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import app.com.roadhelper.helper.SharedPrefManager;

public class RootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
//        MenuItem refresh = menu.getItem(R.id.menu_refresh);
//        refresh.setEnabled(true);
        return true;
    }

    /**
     * the menu layout has the 'add/new' menu item
     */

    /**
     * react to the user tapping/selecting an options menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO put your code here to respond to the button tap
                finish();
                return true;

            case R.id.menu_home:
                // TODO put your code here to respond to the button tap
                startActivity(new Intent(this , HomeActivity.class));
                finish();
                return true;

            case R.id.menu_search:
                // TODO put your code here to respond to the button tap
                startActivity(new Intent(this , MapsActivity.class));
                return true;
            case R.id.menu_settings:
                // TODO put your code here to respond to the button tap
                startActivity(new Intent(this , UserSettings.class));
                return true;

//            case R.id.menu_nots:
//                // TODO put your code here to respond to the button tap
//                startActivity(new Intent(this , NotificationsActivity.class));
//                return true;
            case R.id.menu_logout:
                // TODO put your code here to respond to the button tap
                SharedPrefManager.getInstance(this).Logout();
Intent intent = new Intent(this , LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
                return true;

//            case R.id.menu_exit:
//                // TODO put your code here to respond to the button tap
//               System.exit(0);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
