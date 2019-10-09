package art2202.projetos.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private  Context contexto;
    private  ArrayList<Livro> arrayLivro;
    private OnItemClickListener mListener;

    public Adapter(Context contexto, ArrayList<Livro> arrayLivro){
        this.contexto = contexto;
        this.arrayLivro = arrayLivro;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onComprarClick(int position, Button btComprar);
        void onFavoritarCLick(int position, Button btFavoritar);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(contexto).inflate(R.layout.item_livro, parent, false);
        return new ViewHolder(v, (OnItemClickListener) mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Livro book = arrayLivro.get(position);

        holder.textTitulo.setText(book.getTitle());
        holder.textEscritor.setText(book.getWriter());
        holder.textPreco.setText("R$" +book.getPrice());
        holder.textData.setText(book.getDate());
        if (arrayLivro.get(position).getComprado()){
            holder.botaoComprar.setText("comprado");
        }
        else
            holder.botaoComprar.setText("comprar");

        if (arrayLivro.get(position).getFavorito() == -1){
            holder.botaoFavoritar.setText("favorito");
        }
        else {
            holder.botaoFavoritar.setText("favoritar");
        }
        Picasso.with(contexto).load(book.getThumbnailHd()).fit().centerInside().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayLivro.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textTitulo;
        private TextView textEscritor;
        private TextView textPreco;
        private TextView textData;
        private Button botaoComprar;
        private Button botaoFavoritar;


        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem_layout);
            textTitulo = itemView.findViewById((R.id.text_titulo));
            textEscritor = itemView.findViewById(R.id.text_escritor);
            textPreco = itemView.findViewById(R.id.text_preco);
            textData = itemView.findViewById(R.id.text_data);
            botaoComprar = itemView.findViewById(R.id.botao_comprar);
            botaoFavoritar = itemView.findViewById(R.id.botao_favoritar);


            botaoComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onComprarClick(position, botaoComprar);
                        }
                    }
                }
            });

            botaoFavoritar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onFavoritarCLick(position, botaoFavoritar);
                        }
                    }
                }
            });
        }
    }
}
