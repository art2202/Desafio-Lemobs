package art2202.projetos.myapplication;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LivroService {

    @GET("products.json")
    Call<ArrayList<Livro>> buscarLivro();
}
