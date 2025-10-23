package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        FemmeService femmeService = context.getBean(FemmeService.class);
        HommeService hommeService = context.getBean(HommeService.class);
        MariageService mariageService = context.getBean(MariageService.class);


        Femme f1 = new Femme(); f1.setCin("EE276590"); f1.setNom("ELIDRISSI"); f1.setPrenom("Fatima"); f1.setDateNaissance(LocalDate.of(1965, 3, 12));
        Femme f2 = new Femme(); f2.setCin("AA772467"); f2.setNom("OUISSA"); f2.setPrenom("Nadia"); f2.setDateNaissance(LocalDate.of(1970, 7, 5));
        Femme f3 = new Femme(); f3.setCin("GG227744"); f3.setNom("AMARAY"); f3.setPrenom("Hiba"); f3.setDateNaissance(LocalDate.of(1980, 11, 4));
        Femme f4 = new Femme(); f4.setCin("HH127635"); f4.setNom("KHATAB"); f4.setPrenom("Aicha"); f4.setDateNaissance(LocalDate.of(1968, 9, 3));
        Femme f5 = new Femme(); f5.setCin("RD675234"); f5.setNom("LASRI"); f5.setPrenom("Kenza"); f5.setDateNaissance(LocalDate.of(1975, 6, 10));

        Arrays.asList(f1, f2, f3, f4, f5).forEach(femmeService::create);


        Homme h1 = new Homme(); h1.setNom("ELIDRISSI"); h1.setPrenom("Mohamed Amine");
        Homme h2 = new Homme(); h2.setNom("KHATAB"); h2.setPrenom("Ali");
        Homme h3 = new Homme(); h3.setNom("LASRRI"); h3.setPrenom("Youssef");

        Arrays.asList(h1, h2, h3).forEach(hommeService::create);


        Mariage m1 = new Mariage(); m1.setHomme(h1); m1.setFemme(f1); m1.setDateDebut(LocalDate.of(1990,9,3)); m1.setNbrEnfant(3);
        Mariage m2 = new Mariage(); m2.setHomme(h1); m2.setFemme(f2); m2.setDateDebut(LocalDate.of(1995,9,3)); m2.setNbrEnfant(4);
        Mariage m3 = new Mariage(); m3.setHomme(h1); m3.setFemme(f3); m3.setDateDebut(LocalDate.of(2000,11,4)); m3.setNbrEnfant(2);
        Mariage m4 = new Mariage(); m4.setHomme(h1); m4.setFemme(f4); m4.setDateDebut(LocalDate.of(1989,9,3)); m4.setDateFin(LocalDate.of(1990,9,3)); m4.setNbrEnfant(3);


        Mariage m5 = new Mariage(); m5.setHomme(h2); m5.setFemme(f1); m5.setDateDebut(LocalDate.of(1992,5,10)); m5.setNbrEnfant(2);
        Mariage m6 = new Mariage(); m6.setHomme(h2); m6.setFemme(f2); m6.setDateDebut(LocalDate.of(1996,7,12)); m6.setNbrEnfant(0);
        Mariage m7 = new Mariage(); m7.setHomme(h2); m7.setFemme(f3); m7.setDateDebut(LocalDate.of(2000,3,5)); m7.setNbrEnfant(3);
        Mariage m8 = new Mariage(); m8.setHomme(h2); m8.setFemme(f4); m8.setDateDebut(LocalDate.of(2003,1,20)); m8.setNbrEnfant(5);


        Mariage m9 = new Mariage(); m9.setHomme(h3); m9.setFemme(f1); m9.setDateDebut(LocalDate.of(2010,6,1)); m9.setNbrEnfant(3);
        Mariage m10 = new Mariage(); m10.setHomme(h3); m10.setFemme(f2); m10.setDateDebut(LocalDate.of(2012,8,15)); m10.setNbrEnfant(3);

        Arrays.asList(m1,m2,m3,m4,m5,m6,m7,m8,m9,m10).forEach(mariageService::create);


        System.out.println("\n--- Liste des Femmes ---");
        femmeService.findAll().forEach(f -> System.out.printf("%-5s %-25s %-25s %-12s%n",
                f.getCin(), f.getNom(), f.getPrenom(), f.getDateNaissance()));


        Femme plusAgee = femmeService.findAll().stream()
                .min((fA, fB) -> fA.getDateNaissance().compareTo(fB.getDateNaissance()))
                .orElse(null);
        System.out.println("\nLa femme la plus âgée : " + (plusAgee != null ? plusAgee.getCin() + " " + plusAgee.getNom() + " " + plusAgee.getPrenom() : "N/A"));


        System.out.println("\n--- Les épouses de l'homme " + h1.getNom() + " ---");
        hommeService.afficherEpousesEntreDates(h1.getId(), LocalDate.of(1980,1,1), LocalDate.of(2025,12,31));


        System.out.println("\n--- Le nombre d'enfants de " + f1.getNom() + " entre 1965 et 2025 ---");
        int nbEnfants = femmeService.nombreEnfantsEntreDates(f1.getId(), LocalDate.of(1965,1,1), LocalDate.of(2025,12,31));
        System.out.println("Le nombre d'enfants est : " + nbEnfants);


        System.out.println("\n--- Les femmes mariées deux fois ou plus ---");
        femmeService.femmesMarieesAuMoinsDeuxFois().forEach(f -> System.out.printf("%-5s %-25s %-25s%n", f.getCin(), f.getNom(), f.getPrenom()));


        System.out.println("\n--- Les hommes mariés à 4 femmes entre 1965 et 2025 ---");
        hommeService.afficherNombreHommesAvecQuatreFemmes(LocalDate.of(1965,1,1), LocalDate.of(2025,12,31));


        System.out.println("\n--- Les mariages de l'homme " + h1.getNom() + " en détails ---");
        mariageService.afficherMariagesAvecDetails(h1.getId());

        context.close();
    }
}
