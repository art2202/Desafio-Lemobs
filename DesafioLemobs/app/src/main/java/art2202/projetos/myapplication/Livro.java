package art2202.projetos.myapplication;

public class Livro implements Comparable {

    private String title;
    private int price;
    private String writer;
    private String thumbnailHd;
    private String date;
    private boolean comprado = false;
    private int favorito = 0;



    public boolean getComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }

    public Livro(String title, int price, String writer, String thumbnailHd, String date) {
        this.title = title;
        this.price = price;
        this.writer = writer;
        this.thumbnailHd = thumbnailHd;
        this.date = date;
    }
    public Livro(){

    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getWriter() {
        return writer;
    }

    public String getThumbnailHd() {
        return thumbnailHd;
    }

    public String getDate() {
        return date;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public int compareTo(Object book) {

        if( book instanceof Livro){
            Livro livro = (Livro) book;
            if(this.favorito < livro.favorito){
                return -1;
            }
            if (this.favorito > livro.favorito){
                return 1;
            }
            return 0;
        }
        else
            System.out.println("Erro: objeto de tipo diferente");
            return 0;

    }
}
