package com.maistasinamus.kebabai;

import android.content.Intent;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class NewEntryActivity extends AppCompatActivity {

    public static final String INSERT_URL = "https://kebaba2.000webhostapp.com/mobile/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        Button sendEntry=findViewById(R.id.newentry_submit);

        sendEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*---------------------------------------------*/
                Spinner Spinner_pavadinimas = (Spinner) findViewById(R.id.newentry_name);
                String SpinnerValue_pavadinimas=String.valueOf(Spinner_pavadinimas.getSelectedItem());
                /*---------------------------------------------*/
                RadioGroup radioGroup_Dydis = (RadioGroup) findViewById(R.id.newentry_sizes);
                int selectedId = radioGroup_Dydis.getCheckedRadioButtonId();
                RadioButton radioButton_Dydis = (RadioButton) findViewById(selectedId);
                String radiotButtonValue_Dydis=radioButton_Dydis.getText().toString();
                /*---------------------------------------------*/
                CheckBox chechBox_sauce_mix = (CheckBox) findViewById(R.id.newentry_sauce_mix);
                CheckBox chechBox_sauce_garlic = (CheckBox) findViewById(R.id.newentry_sauce_garlic);
                CheckBox chechBox_sauce_hot = (CheckBox) findViewById(R.id.newentry_sauce_hot);
                CheckBox chechBox_sauce_picantic = (CheckBox) findViewById(R.id.newentry_sauce_picantic);

                String sauce="";

                if(chechBox_sauce_hot.isChecked())
                {
                    sauce+="Astrus:";
                }
                if(chechBox_sauce_mix.isChecked())
                {
                    sauce+="Miksas:";
                }
                if(chechBox_sauce_garlic.isChecked())
                {
                    sauce+="Cesnakinis:";
                }
                if(chechBox_sauce_picantic.isChecked())
                {
                    sauce+="Pikantiskas:";
                }

                /*---------------------------------------------*/
                RadioGroup radioGroup_mesa = (RadioGroup) findViewById(R.id.newentry_types);
                int selectedId_mesa = radioGroup_mesa.getCheckedRadioButtonId();
                RadioButton radioButton_mesa = (RadioButton) findViewById(selectedId_mesa);
                String radiotButtonValue_mesa=radioButton_mesa.getText().toString();
                /*---------------------------------------------*/
                final EditText textvalue_kaina =findViewById(R.id.newentry_price);
                double kaina=0;
                    if (!textvalue_kaina.getText().toString().isEmpty())
                    {
                        kaina = Double.parseDouble(textvalue_kaina.getText().toString());
                    }

                /*---------------------------------------------*/

                NewEntry newentry = new NewEntry(SpinnerValue_pavadinimas,sauce,radiotButtonValue_Dydis,radiotButtonValue_mesa,kaina);
                if(!newentry.getPadazas().isEmpty() && newentry.getKaina()!=0 )
                {
                    //Toast.makeText(NewEntryActivity.this,
                            //"Gauti duomenys : \n"+newentry.getPavadinimas()+"\n"+newentry.getDydis()+"\n"+newentry.getMesa()+"\n"+newentry.getPadazas()+"\n"+newentry.getKaina(),
                           // Toast.LENGTH_SHORT).show();
                    insertToDB(newentry);
                }
                else if (newentry.getPadazas().isEmpty()) {
                    Toast.makeText(NewEntryActivity.this,
                            "Be padazo tipo ?? nesvaik ",
                            Toast.LENGTH_SHORT).show();
                }
                else if (newentry.getKaina()==0)
                {
                    Toast.makeText(NewEntryActivity.this,
                            "Nurodyk kiek noresi moketi",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void insertToDB(NewEntry newentry) {
        class SendEntry extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            DB db = new DB();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(NewEntryActivity.this,
                        getResources().getString(R.string.entryDatabaseInfo),
                        null, true, true);
            }

            @Override
            protected String doInBackground(String... strings) {
                // Pirmas string yra raktas, antras - reikšmė.
                HashMap<String, String> kebabData = new HashMap<String, String>();
                kebabData.put("pavadinimas", strings[0]);
                kebabData.put("dydis", strings[1]);
                kebabData.put("padazas", strings[2]);
                kebabData.put("mesa", strings[3]);
                kebabData.put("kaina", strings[4]);

                kebabData.put("veiksmas","kurti" );

                String result = db.sendPostRequest(INSERT_URL, kebabData);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(NewEntryActivity.this, s, Toast.LENGTH_SHORT).show();
                Intent goToSearchActivity = new Intent(NewEntryActivity.this, SearchActivity.class);
                startActivity(goToSearchActivity);
            }
        }

        SendEntry sendentry = new SendEntry();
        sendentry.execute(newentry.getPavadinimas(),
                newentry.getDydis(),
                newentry.getPadazas(),
                newentry.getMesa(),
                Double.toString(newentry.getKaina()));
    }
}
