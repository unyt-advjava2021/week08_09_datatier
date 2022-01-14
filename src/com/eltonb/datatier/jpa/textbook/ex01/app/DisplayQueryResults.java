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
import java.util.Comparator;

/**
 *
 * @author elton.ballhysa
 */
public class DisplayQueryResults {
    
    public static void main(String[] args) {
        DisplayQueryResults dqr = new DisplayQueryResults();
        //dqr.displayAuthors();
        dqr.displayTitles();
    }

    private void displayAuthors() {
        System.out.println("Authors then Titles:");
        System.out.println("=============================:");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("books-PU");
        EntityManager em = emf.createEntityManager();
        em
                .createNamedQuery("Authors.findAll", Authors.class)
                .getResultList()
                .stream()
                .sorted(Comparator
                            .comparing(Authors::getLastname)
                            .thenComparing(Authors::getFirstname))
                .forEach(author -> {
                    System.out.println(author.fullName());
                    for (Titles title : author.getTitlesList())
                        System.out.println("\t" + title);
                });
    }

    private void displayTitles() {
        System.out.println("Titles then Authors:");
        System.out.println("=============================:");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BookPU");
        EntityManager em = emf.createEntityManager();
        em
                .createNamedQuery("Titles.findAll", Titles.class)
                .getResultList()
                .stream()
                .sorted(Comparator
                            .comparing(Titles::getTitle))
                .forEach(title -> {
                    System.out.println(title);
                    for (Authors author : title.getAuthorsList())
                        System.out.println("\t" + author.fullName());
                });
    }
    
}
