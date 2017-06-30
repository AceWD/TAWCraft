package xywd.tawcraft;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ServersActivity extends Activity implements View.OnClickListener{


    TextView survival;
    TextView survivalSwitch;

    TextView direwolf20;
    TextView direwolf20Switch;

    String html = "";
    String error = "timed out";

    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);

        survival = (TextView) findViewById(R.id.textSurvival);
        survivalSwitch = (TextView) findViewById(R.id.textSurvivalOn);

        direwolf20 = (TextView) findViewById(R.id.textDireWolf20);
        direwolf20Switch = (TextView) findViewById(R.id.textDireWolf20On);

        refresh = (Button) findViewById(R.id.refreshButton);

        refresh.setOnClickListener(this);





    }




    @Override
    public void onClick(View view)
    {
        new SetContentUrl().execute("https://api.minetools.eu/ping/mc.taw.net/25568");
        new SetContentUrl().execute("https://api.minetools.eu/ping/mc.taw.net/25584");
    }


    //Handling The background thread
    private class SetContentUrl extends AsyncTask<String, Void, String>
    {

        //This function in the "background"(different thread) will get the content of the html page. This function can't access the main thread
        @Override
        protected String doInBackground(String... strings) {
            try
            {
                html = getUrlSource(strings[0]);
                System.out.println("Finished!");


            }
            catch (Exception e)
            {
                System.out.println("Problem!");
                System.out.println(e);
            }

            return "Executed";
        }

        //This function will be called after doInBackground is finished. This function is responsible for things that i want to do with the main thread
        @Override
        protected void onPostExecute(String result)
        {
            System.out.println(html);

            //if (html.toLowerCase().indexOf(error.toLowerCase()) != -1)
            // .*timed out.* means 0 ore more of any character matches
            if(html.matches(".*timed out.*"))
            {
                survivalSwitch.setText("Off");
                Toast.makeText(getApplicationContext(), "Off", Toast.LENGTH_SHORT);
            }
            else
            {
                survivalSwitch.setText("On");
                Toast.makeText(getApplicationContext(), "On", Toast.LENGTH_SHORT);
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

}
