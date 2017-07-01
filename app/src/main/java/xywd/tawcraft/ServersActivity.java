package xywd.tawcraft;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class ServersActivity extends Activity implements View.OnClickListener{


    TextView survival;
    String survivalText;


    TextView creative;
    String creativeText;


    TextView direwolf20;
    String direwolf20Text;

    TextView timer;

    String error = "timed out";

    Boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);

        timer = (TextView) findViewById(R.id.textTimer);

        survival = (TextView) findViewById(R.id.textSurvival);
        survivalText = survival.getText().toString();

        creative = (TextView) findViewById(R.id.textCreative);
        creativeText = creative.getText().toString();


        direwolf20 = (TextView) findViewById(R.id.textDireWolf20);
        direwolf20Text = direwolf20.getText().toString();

        //refresh = (Button) findViewById(R.id.refreshButton);


        //schedual();
        System.out.println("Starting Timer!");


        Timer time = new Timer();
        TimerTask minuteTask = new TimerTask() {
            @Override
            public void run() {

                if(isRunning == false)
                {
                    count.cancel();
                    count.start();
                }

                isRunning = true;

            }

        };

        time.schedule(minuteTask, 0l, 1000 * 1 * 30);






    }

    CountDownTimer count = new CountDownTimer(30000, 1000) {

        @Override
        public void onTick(long l) {
            timer.setText("Seconds Remaining: " + l / 1000);
        }

        @Override
        public void onFinish() {
            timer.setText("Refresh in progress...");
            AsyncTask survivalTask = new SetContentUrl().execute("https://api.minetools.eu/ping/mc.taw.net/25568","1");
            AsyncTask direwolf20Task = new SetContentUrl().execute("https://api.minetools.eu/ping/mc.taw.net/2584","2");
            AsyncTask creativeTask = new SetContentUrl().execute("https://api.minetools.eu/ping/mc.taw.net/25574","3");
            isRunning = false;
        }
    };






    @Override
    public void onClick(View view)
    {



    }



    /*
        Make the requests at the same time to optimize the app. idea: use arrays
     */

    //Handling The background thread
    private class SetContentUrl extends AsyncTask<String, Void, String[]>
    {

        //This function in the "background"(different thread) will get the content of the html page. This function can't access the main thread

        @Override
        protected String[] doInBackground(String... strings)
        {
            String arr [] = new String [2];
            String html = "";

            try
            {
                html = getUrlSource(strings[0]);
                System.out.println("Finished!");
                arr[0] = strings[1];
                arr[1] = html;
                return arr;


            }
            catch (Exception e)
            {
                System.out.println("Problem!");
                System.out.println(e);
            }

            return arr;

        }

        //This function will be called after doInBackground is finished. This function is responsible for things that i want to do with the main thread

        @Override
        protected void onPostExecute(String[] content)
        {
            if(content[0] == "1")
            {
                updateText(content[1], survival, survivalText);
            }
            else if(content[0] == "2")
            {
                updateText(content[1], creative, creativeText);
            }
            else if(content[0] == "3")
            {
                updateText(content[1], direwolf20, direwolf20Text);
                Toast.makeText(getApplicationContext(), "Refresh Complete!", Toast.LENGTH_SHORT).show();
            }

        }

    }


    private static String getUrlSource(String url) throws IOException {

        URL web = new URL(url);
        URLConnection page = web.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                page.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder a = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            a.append(inputLine);
        in.close();

        return a.toString();

    }

    //For updating the TextViews on the screen
    public void updateText(String content, TextView view, String origin)
    {
        System.out.println(content);


        //if (html.toLowerCase().indexOf(error.toLowerCase()) != -1)
        // .*timed out.* means 0 ore more of any character matches
        if(content.matches(".*timed out.*"))
        {
            view.setTextColor(Color.RED);
            view.setText(origin + "  Offline");
            //Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT);
        }
        else
        {
            view.setTextColor(Color.GREEN);
            view.setText(origin + "  Online");
            //Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT);
        }

    }

}
