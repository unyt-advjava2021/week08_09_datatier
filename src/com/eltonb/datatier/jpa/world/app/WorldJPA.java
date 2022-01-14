/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eltonb.datatier.jpa.world.app;

import com.eltonb.datatier.jpa.world.entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.*;

/**
 *
 * @author elton.ballhysa
 */
public class WorldJPA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            WorldJPA app = new WorldJPA();
            app.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void go() throws Exception {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("World-PU");
        EntityManager em = emf.createEntityManager();
        
        //displayCountry(em, "ALB");
        //queryCountriesByContinent(em, "Europe");
        
        List<Pair<String, Double>> langPopPairs = em
                .createNamedQuery("Country.findByContinent", Country.class)
                .setParameter("continent", "Europe")
                .getResultList()
                .stream()
                .flatMap(c -> c.getCountrylanguageList().stream())
                .map(cl -> Pair.of(cl.getCountrylanguagePK().getLanguage(), cl.speakingPopulation()))
                .collect(toList());
        
        Map<String, Double> languagePopulationTotals = 
        langPopPairs
                .stream()
                .collect(groupingBy(Pair::first, summingDouble(Pair::second)));
        languagePopulationTotals
                .entrySet()
                .stream()
                .sorted(comparingDouble(Map.Entry<String, Double>::getValue).reversed())
                .limit(10)
                .forEach(e -> System.out.printf("%10s: %10.0f\n", e.getKey(), e.getValue()));

        Map.Entry<String, Double> mostSpokenEntry = languagePopulationTotals
                .entrySet()
                .stream()
                .max(Comparator.comparingDouble(e -> e.getValue()))
                .get();
        
        System.out.printf("most spoken language: %s with %.0f\n", mostSpokenEntry.getKey(), mostSpokenEntry.getValue());
        //insertKosovo(em);
        //displayCountry(em, "KOS");        
        //updateAlbaniaPopulation(em);
        //displayCountry(em, "ALB");        
    }
    
    private void queryCountriesByContinent(EntityManager em, String continent) throws Exception {
        em.createNamedQuery("Country.findByContinent")
                .setParameter("continent", continent)
                .setMaxResults(10)
                .getResultList()
                .forEach(System.out::println);
    }
    
    private void displayCountry(EntityManager em, String countryCode) throws Exception {
        Country country = em.find(Country.class, countryCode);
        System.out.println("country = " + country);  
        List<City> cities = new ArrayList<>(country.getCityList());
        cities.forEach(System.out::println);
    }
    
    private void insertKosovo(EntityManager em) throws Exception {
        Country kosovo = new Country();
        kosovo.setCode("KOS");
        kosovo.setName("Kosovo");
        kosovo.setContinent("Europe");
        kosovo.setRegion("Southern Europe");
        kosovo.setSurfaceArea(18_000);
        kosovo.setPopulation(2_000_000);
        kosovo.setLocalName("Kosove");
        kosovo.setGovernmentForm("Republic");
        kosovo.setHeadOfState("Hashim Thaci");
        kosovo.setCode2("KS");
        em.getTransaction().begin();
        em.persist(kosovo);
        em.getTransaction().commit();
    }

    private void updateAlbaniaPopulation(EntityManager em) {
        Country alb = em.find(Country.class, "ALB");
        alb.setPopulation(3_000_000);
        em.getTransaction().begin();
        em.persist(alb);
        em.getTransaction().commit();
    }
    
}
