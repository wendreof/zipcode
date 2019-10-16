package wendreo.design.retrofit;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import wendreo.design.service.ZipCodeService;

public class RetrofitConfig {
	
	private final Retrofit retrofit;
	
	public RetrofitConfig() {
		this.retrofit = new Retrofit.Builder()
				.baseUrl("http://ws.matheuscastiglioni.com.br/ws/")
				.addConverterFactory( JacksonConverterFactory.create())
				.build();
	}
	
	public ZipCodeService getCEPService() {
		return this.retrofit.create(ZipCodeService.class);
	}
}
