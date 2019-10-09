package art2202.projetos.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {

    private Context contexto;
    private ArrayList<Livro> arrayLivroComprado;

    public AdapterMain(Context contexto, ArrayList<Livro> arrayLivroComprado){
        this.contexto = contexto;
        this.arrayLivroComprado = arrayLivroComprado;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contexto).inflate(R.layout.item_livro_usuario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Livro book = arrayLivroComprado.get(position);

        holder.textTitulo.setText(book.getTitle());
        holder.textEscritor.setText(book.getWriter());
        holder.textData.setText(book.getDate());
        Picasso.with(contexto).load(book.getThumbnailHd()).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayLivroComprado.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textTitulo;
        private TextView textEscritor;
        private TextView textData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem_layout_comprado);
            textTitulo = itemView.findViewById(R.id.text_titulo_comprado);
            textEscritor = itemView.findViewById(R.id.text_escritor_comprado);
            textData = itemView.findViewById(R.id.text_data_comprado);
        }
    }
}
