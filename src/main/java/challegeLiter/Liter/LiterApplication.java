package challegeLiter.Liter;


import challegeLiter.Liter.principal.Principal;
import challegeLiter.Liter.repository.AutoresRepository;
import challegeLiter.Liter.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterApplication implements CommandLineRunner {
	@Autowired
	private LibrosRepository repositoryLibro;
	@Autowired
	private AutoresRepository repositoryAutor;
	public static void main(String[] args) {

		SpringApplication.run(LiterApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal menu = new Principal(repositoryLibro, repositoryAutor);
		menu.menu();
	}

}
