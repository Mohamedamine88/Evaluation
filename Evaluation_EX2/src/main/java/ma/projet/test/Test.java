package ma.projet.test;

import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        EmployeService employeService = context.getBean(EmployeService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);


        Employe emp1 = new Employe();
        emp1.setNom("ELIDRISSI");
        emp1.setPrenom("Mohamed Amine");
        employeService.create(emp1);

        Employe emp2 = new Employe();
        emp2.setNom("LASRI");
        emp2.setPrenom("Khalid");
        employeService.create(emp2);


        Projet proj1 = new Projet();
        proj1.setNom("Projet 1");
        proj1.setChef(emp1);
        proj1.setDateDebut(LocalDate.of(2021, 5, 27));
        proj1.setDateFin(LocalDate.of(2022, 3, 17));
        projetService.create(proj1);

        Projet proj2 = new Projet();
        proj2.setNom("Projet 2");
        proj2.setChef(emp2);
        proj2.setDateDebut(LocalDate.of(2026, 10, 1));
        proj2.setDateFin(LocalDate.of(2027, 12, 26));
        projetService.create(proj2);


        Tache t1 = new Tache();
        t1.setNom("Tâche planifiée 1");
        t1.setProjet(proj1);
        t1.setPrix(2800f);
        t1.setDateDebut(LocalDate.of(2026, 2, 10));
        t1.setDateFin(null);
        tacheService.create(t1);

        Tache t2 = new Tache();
        t2.setNom("Tâche planifiée 2");
        t2.setProjet(proj2);
        t2.setPrix(1340f);
        t2.setDateDebut(LocalDate.of(2022, 1, 20));
        t2.setDateFin(null);
        tacheService.create(t2);


        Tache t3 = new Tache();
        t3.setNom("Tâche réalisée 1");
        t3.setProjet(proj1);
        t3.setPrix(3400f);
        t3.setDateDebut(LocalDate.of(2021, 7, 14));
        t3.setDateFin(LocalDate.of(2021, 7, 20));
        tacheService.create(t3);

        Tache t4 = new Tache();
        t4.setNom("Tâche réalisée 2");
        t4.setProjet(proj1);
        t4.setPrix(8573f);
        t4.setDateDebut(LocalDate.of(2026, 5, 24));
        t4.setDateFin(LocalDate.of(2026, 6, 12));
        tacheService.create(t4);


        EmployeTache et1 = new EmployeTache();
        et1.setEmploye(emp1);
        et1.setTache(t1);
        employeTacheService.create(et1);

        EmployeTache et2 = new EmployeTache();
        et2.setEmploye(emp1);
        et2.setTache(t3);
        employeTacheService.create(et2);

        EmployeTache et3 = new EmployeTache();
        et3.setEmploye(emp2);
        et3.setTache(t2);
        employeTacheService.create(et3);

        EmployeTache et4 = new EmployeTache();
        et4.setEmploye(emp1);
        et4.setTache(t4);
        employeTacheService.create(et4);


        System.out.println("\n--- Les tâches d'un employé ---");
        employeService.afficherTachesParEmploye(emp1.getId());

        System.out.println("\n--- Les pojets gérés par l'employé ---");
        employeService.afficherProjetsGeresParEmploye(emp1.getId());

        System.out.println("\n--- Les tâches planifiées pour le projet ---");
        projetService.afficherTachesPlanifieesParProjet(proj1.getId());

        System.out.println("\n--- Les tâches réalisées pour le projet ---");
        projetService.afficherTachesRealiseesParProjet(proj1.getId());

        System.out.println("\n--- Les tâches avec un prix > 1000 ---");
        tacheService.afficherTachesPrixSuperieurA1000();

        System.out.println("\n--- Les tâches réalisées entre deux dates ---");
        LocalDate debut = LocalDate.of(2021, 1, 1);
        LocalDate fin = LocalDate.of(2028, 1, 1);
        tacheService.afficherTachesEntreDates(debut, fin);

        context.close();
    }
}
