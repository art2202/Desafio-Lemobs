package art2202.projetos.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitConfig {

    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Felcks/desafio-mobile-lemobs/master/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public LivroService getLivroService() {
        return this.retrofit.create(LivroService.class);
    }
}
