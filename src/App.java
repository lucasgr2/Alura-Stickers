import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        // fazer conexao http e buscar o top filmes
        String URL = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";

        URI endereco = URI.create(URL);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // (parsear os dados) extrair os dados interessantes e separados: titulo poster
        // e classificação
        // TESTAR DEPOIS COM A BIBLIOTECA JACKSON
        JsonParser Parser = new JsonParser();
        List<Map<String, String>> ListaFilmes = Parser.parse(body);
        System.out.println(ListaFilmes.get(0));
        // exibir e manipular os dados
        GeradorDeSticker geradorDeSticker = new GeradorDeSticker();

        for (Map<String, String> filme : ListaFilmes) {

            String urlimagem = filme.get("image");
            String tituloimagem = filme.get("title");
            // Erro do : e fazendo vira imagem grande
            String nomeArquivo = tituloimagem.replace(":", "-");
            urlimagem = urlimagem.replaceAll("(@+)(.*).jpg$", "$1.jpg");
            // TRY CHAT ERRO CASO IMAGEM NAO FOR ENCONTRADA
            try {
                InputStream inputStream = new URL(urlimagem).openStream();

                String titulo = "Imagem/" + nomeArquivo + ".png";
                // TRY CAHT PRA CORRIGIR SE A IMAGEM NAO FOR ACEITA PELO IMAGEIO
                try {
                    System.out.println(tituloimagem);
                    System.out.println();

                    geradorDeSticker.Criar(inputStream, titulo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (java.io.FileNotFoundException err) {
                System.out.println("Imagem não encontrada ou link inválido");
            }
        }
    }

    // Tentativa de formatar com o replace
    public static String formataUrl(String url) {
        String padrao = "._V1";
        if (url.indexOf(padrao) == -1) {
            return url;
        }

        return url.substring(0, url.indexOf(padrao)) + url.substring(url.length() - 4);
    }
}
