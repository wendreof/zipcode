package wendreo.design.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import wendreo.design.R;
import wendreo.design.models.ZipCode;
import wendreo.design.retrofit.RetrofitConfig;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	
	private EditText cep;
	private TextView resposta;
	private LinearLayout linearLayout;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_main );
		
		findViewByIds ( );
	}
	
	private void findViewByIds ( ) {
		cep = findViewById ( R.id.etMain_cep );
		resposta = findViewById ( R.id.etMain_resposta );
		Button btnBuscarCep = findViewById ( R.id.btnMain_buscarCep );
		linearLayout = findViewById ( R.id.LinearLayout );
		btnBuscarCep.setOnClickListener ( this );
	}
	
	@Override
	public void onClick ( View view ) {
		String mcep = cep.getText ().toString ();
		
		if (mcep.equals("") || mcep.length () < 9)
		{
			cep.setError ( "Por favor, insira um CEP válido!" );
		}
		else
			{
			if ( view.getId ( ) == R.id.btnMain_buscarCep )
			{
				Call< ZipCode > call = new RetrofitConfig ( ).getCEPService ( ).buscarCEP ( cep.getText ( ).toString ( ) );
				call.enqueue ( new Callback< ZipCode > ( ) {
					@Override
					public void onResponse ( Call< ZipCode > call, Response< ZipCode > response ) {
						ZipCode cep = response.body ( );
						try {
							ShowMSG ( "Busca realizada com suceso...", 1);
							resposta.setText ( cep.toString ( ) );
						} catch ( Exception e ) {
							Log.e ("Erro CEP:", "Erro ao buscar o cep:" + e.getMessage ( ) );
						}
					}
					
					@Override
					public void onFailure ( Call< ZipCode > call, Throwable t ) {
						ShowMSG ( "Erro ao buscar o cep:" + t.getMessage ( ), 0);
					}
				} );
			}
		}
	}
	
	private void ShowMSG ( String msg, Integer time   )
	{
		if(time == 0)
		{
			Snackbar.make ( linearLayout, msg, Snackbar.LENGTH_LONG ).show ( );
		}
		else
		{
			Snackbar.make ( linearLayout, msg, Snackbar.LENGTH_SHORT ).show ( );
		}
	}
}