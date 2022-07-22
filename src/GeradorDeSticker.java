import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;

public class GeradorDeSticker {

    public void Criar(InputStream inputStream, String NomeArquivo) throws Exception {

        // Leitura da imagem
        
        BufferedImage ImagemOriginal = ImageIO.read(inputStream);
        
        
        // Criar nova imagem original pra novo imagem (em memoria)
        int Largura = ImagemOriginal.getWidth(); // LARGURA
        int Altura = ImagemOriginal.getHeight(); // ALTURA
        int NovaAltura = Altura + 200;
    //    BufferedImage NovaImagem = new BufferedImage(Largura, NovaAltura, BufferedImage.TRANSLUCENT);
    BufferedImage NovaImagem = new BufferedImage(600, 1000, BufferedImage.TRANSLUCENT);
        // Copiar a imagem original pra novo imagem (em memoria)
    //    Graphics2D graphics = (Graphics2D) NovaImagem.getGraphics();
        Graphics2D graphics = (Graphics2D) NovaImagem.createGraphics();
        graphics.drawImage(ImagemOriginal, 0, 0,600,800, null);
        // Configurando a cor
        graphics.setColor(Color.CYAN);
        // Configurando a Fonte
        Font fonte = new Font(Font.SANS_SERIF, Font.BOLD, 90);
        graphics.setFont(fonte);
        // Escrever uma frase na nova imagem
        graphics.drawString("TopDemais", 60, 900);
        // Escever a nova imagem em um arquivo
        ImageIO.write(NovaImagem, "png", new File(NomeArquivo));

    }

}
