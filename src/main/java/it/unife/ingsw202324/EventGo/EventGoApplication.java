package it.unife.ingsw202324.EventGo;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Classe principale dell'applicazione EventGo.
 * Configura e avvia un'applicazione Spring Boot.
 *
 * Annotata con @SpringBootApplication, che indica che questa è una classe di configurazione
 * e utilizza la ricerca automatica di componenti nel pacchetto corrente e nei sottopacchetti.
 */
@SpringBootApplication
public class EventGoApplication {

	/**
	 * Metodo principale che avvia l'applicazione Spring Boot.
	 *
	 * @param args Argomenti della linea di comando passati al programma.
	 */
	public static void main(String[] args) {
		SpringApplication.run(EventGoApplication.class, args);
	}

	/**
	 * Bean di configurazione che definisce un CommandLineRunner per eseguire codice all'avvio dell'applicazione.
	 *
	 * Questo metodo specifico stampa i nomi di tutti i bean creati da Spring Boot, fornendo una panoramica
	 * dei componenti disponibili nell'ApplicationContext di Spring.
	 *
	 * @param ctx ApplicationContext di Spring, che fornisce l'accesso ai bean definiti nell'applicazione.
	 * @return Un CommandLineRunner che esegue l'azione specificata quando l'applicazione viene avviata.
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
}