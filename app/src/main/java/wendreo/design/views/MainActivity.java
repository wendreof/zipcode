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

public class MainActivity extends AppCompatActivity
		implements View.OnClickListener {
	
	private EditText editTextZipCode;
	private TextView textViewResponse;
	private LinearLayout LinearLayout;
	
	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_main );
		
		findViewByIds ( );
	}
	
	private void findViewByIds ( ) {
		editTextZipCode = findViewById ( R.id.editTextZipCode );
		textViewResponse = findViewById ( R.id.textViewResponse );
		Button btnSearch = findViewById ( R.id.btnSearch );
		LinearLayout = findViewById ( R.id.LinearLayout );
		btnSearch.setOnClickListener ( this );
	}
	
	@Override
	public void onClick ( View view )
	{
		String zip = editTextZipCode.getText ( ).toString ( );
		
		if ( zip.equals ( "" ) || zip.length ( ) < 9 )
		{
			editTextZipCode.setError ( getString( R.string.type_valid_zipcode) );
		}
		else
		{
		if ( view.getId ( ) == R.id.btnSearch )
		{
			Call< ZipCode > call = new RetrofitConfig ( ).getCEPService ( )
					.buscarCEP ( zip );
			call.enqueue ( new Callback< ZipCode > ( )
			{
				@Override
				public void onResponse ( Call< ZipCode > call,
				                         Response< ZipCode > response )
				{
					ZipCode zip = response.body ( );
					try
					{
						if( zip.getCep ( ) == null ){
							textViewResponse.setText ( getString( R.string.no_zip) );
						} else {
							ShowMSG ( getString( R.string.success), 1 );
							textViewResponse.setText ( zip.toString ( ) );
						}

					}
					catch ( Exception e )
					{
						Log.e ( getString( R.string.error1),
								getString( R.string.error2) + e.getMessage ( ) );
					}
				}
				
				@Override
				public void onFailure ( Call< ZipCode > call, Throwable t )
				{
					ShowMSG ( getString( R.string.error2) + t.getMessage ( ),
							0 );
				}
			} );
		}
	}
	}
	
	private void ShowMSG ( String msg, Integer time )
	{
		if ( time == 0 )
		{
			Snackbar.make ( LinearLayout, msg, Snackbar.LENGTH_LONG ).show ( );
		}
		else
		{
			Snackbar.make ( LinearLayout, msg, Snackbar.LENGTH_SHORT ).show ( );
		}
	}
}