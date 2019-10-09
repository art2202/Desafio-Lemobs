package art2202.projetos.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button comprarLivro;
    public static TextView saldo;
    public static RecyclerView recyclerView;
    public static ArrayList<Livro> livrosComprados = new ArrayList<>();
    public static AdapterMain adapterMain;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniciarElementos();
        trocarTela();
        iniciarRecyclerViewUsuario();
    }

    public void iniciarElementos(){
        setContentView(R.layout.activity_main);
        saldo = findViewById(R.id.saldo);
        comprarLivro = findViewById(R.id.ComprarLivro);

    }

    public void iniciarRecyclerViewUsuario(){

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void trocarTela(){

        comprarLivro.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TelaCompra.class);

                intent.putExtra("saldo", 100);
                startActivity(intent);
            }
        });
    }


}
