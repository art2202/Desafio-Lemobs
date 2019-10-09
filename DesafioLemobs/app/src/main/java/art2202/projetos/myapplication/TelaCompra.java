package art2202.projetos.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TelaCompra extends AppCompatActivity {

    private Button comprar;
    private Button meusLivros;
    private Button resetarSaldo;
    private Button favoritarLivro;
    private TextView textSaldo;
    private static int saldo = 100;
    private  RecyclerView recyclerView;
    private Adapter adapter;
    private static ArrayList<Livro> livros = new ArrayList<>();
    private static boolean primeiraVez = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferencias = getSharedPreferences("Preferencias", 0);

        //verifica se é a primeira vez que abre a tela de compra, para realizar a request apenas uma vez
        if (primeiraVez) {
            primeiraVez = false;
            request();
            saldo = pegarSaldo(preferencias);
            iniciarElementosTelaCompra();
            voltarTela(preferencias);
        }
        else{
            iniciarElementosTelaCompra();
            iniciarAdapter();
            voltarTela(preferencias);
        }
        resetaPreferencia(preferencias);
    }

    public void iniciarElementosTelaCompra(){

        setContentView(R.layout.activity_tela_compra);
        meusLivros = findViewById(R.id.meusLivros);
        textSaldo = findViewById(R.id.saldoTelaCompra);
        comprar = findViewById(R.id.botao_comprar);
        resetarSaldo = findViewById(R.id.button_reseta_saldo);
        iniciarRecyclerView();
        textSaldo.setText(String.valueOf(saldo));
        favoritarLivro = findViewById(R.id.botao_favoritar);
    }

    public void iniciarRecyclerView(){

        recyclerView = findViewById(R.id.recycler_viewCompra);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void iniciarAdapter(){

        adapter = new Adapter(this, livros);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener(){



            /*verifica se o botao de compra foi selecionado e faz os procedimentos para compra ser concluida, atualiza saldo
            e manda a lista atualizada pro adapter mudar o texto do botao comprar*/
            @Override
            public void onComprarClick(int position, Button bt) {

                if(!livros.get(position).getComprado()) {

                    if(!MainActivity.livrosComprados.contains(livros.get(position))) {

                        if (saldo>= livros.get(position).getPrice()) {
                            livros.get(position).setComprado(true);
                            MainActivity.livrosComprados.add(livros.get(position));
                            atualizaSaldo(livros.get(position));
                            iniciarAdapter();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "saldo insuficiente", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "livro ja comprado", Toast.LENGTH_SHORT).show();
                }

            }


            /*verifica se o botao de favoritar foi selecionado e atualiza a lista com o livro favoritado e passa pro adapter
            a lista atualizada */
            @Override
            public void onFavoritarCLick(int position, Button btFavoritar) {

                if (livros.get(position).getFavorito() == -1){
                    Toast.makeText(getApplicationContext(), "ja foi favoritado", Toast.LENGTH_SHORT).show();
                }
                else{
                    livros.get(position).setFavorito(-1);
                    Collections.sort(livros);
                    iniciarAdapter();
                }

            }
        });
    }

    public void atualizaSaldo(Livro livro){

        saldo-= livro.getPrice();
        textSaldo.setText(String.valueOf(saldo) );

    }

    //realiza a request utilizando o retrofit
    public void request(){

        Call<ArrayList<Livro>> call = new RetrofitConfig().getLivroService().buscarLivro();
        call.enqueue(new Callback<ArrayList<Livro>>() {
            @Override
            public void onResponse(Call<ArrayList<Livro>> call, Response<ArrayList<Livro>> response) {
                livros = response.body();
                iniciarAdapter();
            }

            @Override
            public void onFailure(Call<ArrayList<Livro>> call, Throwable t) {
                Log.e("LivroService","Erro ao buscar o livro " + t.getMessage());
            }
        });
    }

    public void voltarTela(final SharedPreferences preferencias){

        meusLivros.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                iniciaRecyclerMain();
                salvarSaldo(saldo, preferencias);
                MainActivity.saldo.setText(String.valueOf(TelaCompra.saldo));
                onBackPressed();
            }
        });
    }


    public void resetaPreferencia(final SharedPreferences preferencias){
        resetarSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = preferencias.edit();
                editor.clear();
                editor.commit();
                textSaldo.setText("100");
                saldo= 100;

            }
        });
    }

    public void iniciaRecyclerMain(){

        MainActivity.adapterMain = new AdapterMain(this, MainActivity.livrosComprados);
        MainActivity.recyclerView.setAdapter(MainActivity.adapterMain);
    }

    public void salvarSaldo(int valor, SharedPreferences preferencias){

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("saldo", valor);
        editor.commit();

    }

    public int pegarSaldo(SharedPreferences preferencias){

        if (preferencias.contains("saldo")){
            return preferencias.getInt("saldo", 100);
        }
        else{
            return 100;
        }
    }
}
