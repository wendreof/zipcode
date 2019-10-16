package wendreo.design.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import wendreo.design.models.ZipCode;

public interface ZipCodeService {
	@GET ("cep/find/{cep}/json")
	Call< ZipCode > buscarCEP( @Path ("cep") String cep);
}