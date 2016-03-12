package exemplo;

import java.io.File;
import java.util.Map;

import org.beanio.BeanReader;
import org.beanio.BeanWriter;
import org.beanio.StreamFactory;

public class ExemploMain {

	public static void main(String[] args) {
        StreamFactory factory = StreamFactory.newInstance();
        // Carrega o arquivo de mapeamento
        factory.load("contatos-map.xml");
        
        // Cria o BeanReader para ler o arquivo "input.csv"
        BeanReader in = factory.createReader("contatos", new File("input.csv"));
        // Cria o BeanWriter para escrever o arquivo "output.csv"
        BeanWriter out = factory.createWriter("contatos", new File("output.csv"));        
        
        Object registro = null;
        
        // Lê os registros de "input.csv"
        while ((registro = in.read()) != null) {
        
            if ("cabecalho".equals(in.getRecordName())) {
                Map<String,Object> header = (Map<String,Object>) registro;
                System.out.println(header.get("dataArquivo"));
            }
            else if ("contato".equals(in.getRecordName())) {
                Contato contato = (Contato) registro;
                // processa o contato
            }
            else if ("rodape".equals(in.getRecordName())) {
                Integer quantidade = (Integer) registro;
                System.out.println(quantidade + " contatos processados");
            }
            
            // escreve o arquivo "output.csv"
            out.write(registro);
        }
        
        in.close();
        
        out.flush();
        out.close();

	}

}
