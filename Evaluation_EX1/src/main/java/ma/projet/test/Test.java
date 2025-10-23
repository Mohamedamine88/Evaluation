package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.service.ProduitService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.util.HibernateConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneService = context.getBean(LigneCommandeService.class);


        Categorie cat1 = new Categorie();
        cat1.setCode("C1");
        cat1.setLibelle("Catégorie 1");

        Categorie cat2 = new Categorie();
        cat2.setCode("C2");
        cat2.setLibelle("Catégorie 2");


        Produit p1 = new Produit();
        p1.setReference("AFG387");
        p1.setPrix(500f);
        p1.setCategorie(cat1);

        Produit p2 = new Produit();
        p2.setReference("KTM5734");
        p2.setPrix(230f);
        p2.setCategorie(cat1);

        Produit p3 = new Produit();
        p3.setReference("CCF904");
        p3.setPrix(140f);
        p3.setCategorie(cat2);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);


        Commande c1 = new Commande();
        c1.setDate(LocalDate.of(2025, 10, 3));
        commandeService.create(c1);

        Commande c2 = new Commande();
        c2.setDate(LocalDate.of(2025, 11, 5));
        commandeService.create(c2);


        LigneCommandeProduit l1 = new LigneCommandeProduit();
        l1.setProduit(p1);
        l1.setCommande(c1);
        l1.setQuantite(19);

        LigneCommandeProduit l2 = new LigneCommandeProduit();
        l2.setProduit(p2);
        l2.setCommande(c1);
        l2.setQuantite(7);

        LigneCommandeProduit l3 = new LigneCommandeProduit();
        l3.setProduit(p3);
        l3.setCommande(c2);
        l3.setQuantite(29);

        ligneService.create(l1);
        ligneService.create(l2);
        ligneService.create(l3);



        System.out.println("\n--- Produits de la catégorie C1 ---");
        List<Produit> produitsCat1 = produitService.findByCategorie(cat1);
        for (Produit p : produitsCat1) {
            System.out.printf("Référence : %-5s Prix : %-6.2f Catégorie : %s%n",
                    p.getReference(), p.getPrix(), p.getCategorie().getLibelle());
        }

        System.out.println("\n--- Produits de la commande C1 ---");
        produitService.findByCommande(c1.getId());

        System.out.println("\n--- Produits commandés entre Janvier - 2025 et Janvier -  2026 ---");
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 1);
        produitService.findByDateCommande(start, end);

        System.out.println("\n--- Produits avec un prix > 55 MAD ---");
        produitService.findProduitsPrixSuperieur(55f);

        context.close();
    }
}
