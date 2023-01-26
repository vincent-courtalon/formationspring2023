package com.formation.exercice3;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.formation.exercice3.metier.Cours;
import com.formation.exercice3.metier.Etudiant;
import com.formation.exercice3.metier.Matiere;
import com.formation.exercice3.metier.Professeur;
import com.formation.exercice3.metier.dto.CoursStat;
import com.formation.exercice3.repositories.CoursRepository;
import com.formation.exercice3.repositories.EtudiantRepository;
import com.formation.exercice3.repositories.MatiereRepository;
import com.formation.exercice3.repositories.ProfesseurRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Configuration
@Slf4j
public class Exercice3Application implements CommandLineRunner{

	@Autowired
	private CoursRepository coursRepository;

	@Autowired
	private EtudiantRepository etudiantRepository;

	@Autowired
	private MatiereRepository matiereRepository;

	@Autowired
	private ProfesseurRepository professeurRepository;

	public static void main(String[] args) {
		SpringApplication.run(Exercice3Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner reader = new Scanner(System.in);
		prepareData();
		log.info("preparation données finie");
		reader.nextLine();
		testRequete(reader);
		log.info("requette testée");
		reader.nextLine();
	}

	private void prepareData() {
		Professeur[] profs = new Professeur[3];
		profs[0] = professeurRepository.save(new Professeur(0, "bob", "bob@gmail.com", null));
		profs[1] = professeurRepository.save(new Professeur(0, "patrick", "patrick@gmail.com", null));
		profs[2] = professeurRepository.save(new Professeur(0, "sandy", "sandy@gmail.com", null));
		
		Matiere[] matieres = new Matiere[4];
		matieres[0] = matiereRepository.save(new Matiere(0, "java", null));
		matieres[1] = matiereRepository.save(new Matiere(0, "cuisine", null));
		matieres[2] = matiereRepository.save(new Matiere(0, "peche", null));
		matieres[3] = matiereRepository.save(new Matiere(0, "opera", null));

		LocalDate[] dates = new LocalDate[4];
		dates[0] = LocalDate.of(2023, 2, 10);
		dates[1] = LocalDate.of(2023, 3, 10);
		dates[2] = LocalDate.of(2023, 4, 10);
		dates[3] = LocalDate.of(2023, 5, 10);

		Etudiant[] etudiants = new Etudiant[100];
		for (int i = 0; i < etudiants.length; i++) {
			etudiants[i] = etudiantRepository.save(new Etudiant(0, "joe "+ i, "joe@gmail.com", null));
		}

		Random rd = new Random();
		for (int i = 1; i <= 10; i++) {
			LocalDate ldt = dates[rd.nextInt(dates.length)];
			Cours c = new Cours(0, ldt, ldt.plusDays(5), 
							"cours " + i, rd.nextInt(10) + 5, 
							null, 
							profs[rd.nextInt(profs.length)],
							matieres[rd.nextInt(matieres.length)]);
			for (int j = 0; j < etudiants.length; j++) {
				if (rd.nextDouble() > 0.8) {
					c.addEtudiant(etudiants[j]);
				}
			}
			coursRepository.save(c);
		}
	}

	private void testRequete(Scanner reader) {

		Cours c = coursRepository.findById(1).get();
		List<Etudiant> etudiants = etudiantRepository.findAllByCoursesId(1);
		System.out.println("pour cours : " + c.getTitre() + " sur " + c.getMatiere().getLibelle());
		etudiants.forEach(et -> System.out.println(et.getNom()));
		System.out.println("----------------------------------------------------------");
		etudiants = etudiantRepository.findparticipantCoursAfter(LocalDate.of(2023, 3, 11));
		etudiants.forEach(et -> System.out.println(et.getNom()));
		System.out.println("----------------------------------------------------------");
		etudiants = etudiantRepository.findparticipantCoursProf(1);
		etudiants.forEach(et -> System.out.println(et.getNom()));
		System.out.println("----------------------------------------------------------");

		List<CoursStat> stats = coursRepository.listeStatsCours();
		stats.forEach(st ->  System.out.println(st));
	}

}
