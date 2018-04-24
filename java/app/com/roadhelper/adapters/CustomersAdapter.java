package app.com.roadhelper.adapters;

/**
 * Created by HP on 2/24/2018.
 */


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import app.com.roadhelper.NotificationsActivity;
import app.com.roadhelper.R;
import app.com.roadhelper.helper.DatabaseHelper;

public class CustomersAdapter extends RecyclerView.Adapter<CustomersAdapter.MyViewHolder> {

    private List<AlarmItem> moviesList;
    private Activity C;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title , details , time;
        Button delete;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            details = (TextView) view.findViewById(R.id.details);
            time = (TextView) view.findViewById(R.id.time);
            delete = (Button)view.findViewById(R.id.delete);
        }
    }


    public CustomersAdapter(List<AlarmItem> moviesList , Activity c) {
        this.moviesList = moviesList;
        this.C = c;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
      final  AlarmItem r = moviesList.get(position);

        holder.title.setText(r.getId() + "-"+r.getTitle());
        holder.details.setText(r.getDetails());
        holder.time.setText(getDate(Long.parseLong(r.getTime()), "dd/MM/yyyy hh:mm:ss.SSS"));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             new DatabaseHelper(C).deleteData(r.getId());
                ((NotificationsActivity)C).init();
            }
        });



//        holder.pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(C, "", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });


    }//converting time to milliseconds
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    @Override
    public int getItemCount() {
        return moviesList.size();
    }

//
//    JSONObject answer;
//    public void change_val(final String target_id , final int amount  , final String shop_id,final int pos ) {
//        class JsonOpener extends AsyncTask<String, Void, String> {
//            ProgressDialog loading = new ProgressDialog(C);
//
//            @Override
//            protected void onPreExecute() {
//                loading.setTitle("يرجى الانتظار");
//                loading.show();;
//
//
//                super.onPreExecute();
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                try {
//                    Toast.makeText(C, answer.getString("body"), Toast.LENGTH_SHORT).show();
//                    moviesList.get(pos).setNum_points((Integer.parseInt(moviesList.get(pos).getNum_points())-amount)+"");
//                    CustomersAdapter.this.notifyDataSetChanged();
//                } catch (Exception e) {
//                    System.out.println("fetching cats error : " + e.getMessage());
//                }
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                //  BufferedReader bufferedReader = null;
//                try {
//                    HashMap<String, String> pars = new HashMap<String, String>();
//                    pars.put("service" , "updatecustomerpoints");
//                    pars.put("user_id" , SharedPrefManager.getInstance(C).getUser().getUser_id());
//                    pars.put("password" , SharedPrefManager.getInstance(C).getUser().getPassword());
//                    pars.put("shop_id" , shop_id);
//                    pars.put("target_id" , target_id);
//                    pars.put("amount" , amount+"");
//
//                    String result =
//                            ConnectionUtils.sendPostRequest(APIUrl.SERVER, pars);
//
//                    answer = new JSONObject(result);
//                } catch (Exception e) {
//                    System.out.println("fetching cats error : " + e.getMessage());
//                }
//
//                return "Ready";
//
//
//            }
//
//
//        }
//        JsonOpener ru = new JsonOpener();
//        ru.execute();
//    }



}