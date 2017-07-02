package xywd.tawcraft;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    Button servers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Hello XylemWD
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Button servers = (Button) findViewById(R.id.serversButton);

        servers.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent i;
        i = new Intent(this,ServersActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }
}
