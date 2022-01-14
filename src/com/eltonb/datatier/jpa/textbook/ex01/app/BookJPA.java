/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eltonb.datatier.jpa.textbook.ex01.app;

import com.eltonb.datatier.jpa.textbook.ex01.model.Authors;
import com.eltonb.datatier.jpa.textbook.ex01.model.Titles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author elton.ballhysa
 */
public class BookJPA {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try {
            BookJPA bookjpa = new BookJPA();
            bookjpa.testRead();
            //bookjpa.testUpdate();
            //bookjpa.testInsert();
            //bookjpa.testDelete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private final EntityManagerFactory emf;

    public BookJPA() {
        emf = Persistence.createEntityManagerFactory("BookPU");
    }
    
    private void testRead() throws Exception {
        EntityManager em = emf.createEntityManager();
        Authors a = em.find(Authors.class, 1);
        System.out.println("a = " + a);
        List<Authors> authors = em.createNamedQuery("Authors.findAll", Authors.class).getResultList();
        authors.forEach(this::displayAuthor);
        /*
        TypedQuery<Authors> allAuthorsQuery = em.createQuery("select author from Authors as author", Authors.class);
        allAuthorsQuery.getResultList()
                .stream()
                .forEach(System.out::println);

        TypedQuery<Titles> allTitlesQuery = em.createQuery("select title from Titles as title", Titles.class);
        allTitlesQuery.getResultList()
                .stream()
                .forEach(this::displayTitle);
        */
    }
    
    private void displayAuthor(Authors author) {
        System.out.println(author);
        author.getTitlesList().forEach(t -> System.out.println("\t" + t));
    }
    
    private void testUpdate() throws Exception {
        EntityManager em = emf.createEntityManager();
        Authors pd = em.find(Authors.class, 1);
        pd.setFirstname("PAUL");
        em.getTransaction().begin();
        em.persist(pd);
        em.getTransaction().commit();
        
        System.out.println("after updating paul deitel");
        em.createNamedQuery("Authors.findAll")
            .getResultList()
            .forEach(System.out::println);

    }

    private void displayTitle(Titles t) {
        String titleLine = String.format("%s - %s ed. %d, author(s): %s", 
                t.getIsbn(), 
                t.getTitle(), 
                t.getEditionnumber(),
                t.authorNames());
        System.out.println(titleLine);
    }

    private void testInsert() {
        EntityManager em = emf.createEntityManager();
        Authors bengio = new Authors();
        bengio.setFirstname("Yoshua");
        bengio.setLastname("Bengio");

        Titles dl = new Titles();
        dl.setIsbn("B01MRVFGX4");
        dl.setTitle("Deep learning");
        dl.setEditionnumber(1);
        dl.setCopyright("2015");
        bengio.setTitlesList(new ArrayList<Titles>());
        bengio.getTitlesList().add(dl);

        em.getTransaction().begin();
        em.persist(bengio);
        em.getTransaction().commit();
        
        System.out.println("after bengio");
        
        em.createNamedQuery("Authors.findAll")
            .getResultList()
            .stream()
            .forEach(System.out::println);
    }

    private void testDelete() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT a FROM Authors a WHERE a.firstname = :fn and a.lastname = :ln");
        query.setParameter("fn", "Yoshua");
        query.setParameter("ln", "Bengio");
        Authors a = (Authors) query.getSingleResult();
        
        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
        
        System.out.println("After deleting bengio");
        
        em.createNamedQuery("Authors.findAll")
            .getResultList()
            .stream()
            .forEach(System.out::println);
    }
                    
}
