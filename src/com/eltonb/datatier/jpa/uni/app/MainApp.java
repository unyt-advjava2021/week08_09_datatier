package com.eltonb.datatier.jpa.uni.app;

import com.eltonb.datatier.jpa.uni.entities.Department;
import com.eltonb.datatier.jpa.uni.entities.Instructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MainApp {

    private final EntityManagerFactory emf;
    private final EntityManager em;

    public static void main(String[] args) {
        MainApp app = new MainApp();
        app.go();
    }

    public MainApp() {
        this.emf = Persistence.createEntityManagerFactory("uni-PU");
        this.em = emf.createEntityManager();
    }

    private void doRead() {
        Department csd = em.find(Department.class, "CS");
        Instructor csChair = csd.getChair();
        System.out.println(csd);
        System.out.println(csChair);
        System.out.println("----CS instructors------");
        csd.getInstructors().forEach(System.out::println);
        System.out.println("----CS students------");
        csd.getStudents().forEach(System.out::println);
        List<Department> engDepartments = em.createNamedQuery("Department.findAll").getResultList();
        engDepartments.forEach(System.out::println);
    }

    private void doCreateInstructor() {
        Instructor i = new Instructor();
        i.setName("Nutty");
        i.setSurname("Professor");
        i.setId(5);
        Department xxd = em.find(Department.class, "XX");
        i.setDepartment(xxd);
        xxd.setChair(i);
        em.getTransaction().begin();
        em.persist(i);
        em.persist(xxd);
        em.getTransaction().commit();
    }

    private void doCreateDepartment() {
        Department d = new Department();
        d.setCode("XX");
        d.setFacultyCode("YY");
        d.setName("Dummy");
        em.getTransaction().begin();
        em.persist(d);
        em.getTransaction().commit();
    }

    private void doDeleteWrong() {
        Department xxd = em.find(Department.class, "XX");
        //Map map = em.unwrap(UnitOfWorkImpl.class).getCloneMapping();
        xxd.setChair(null);
        Instructor nutty = em.find(Instructor.class, 5);
        em.getTransaction().begin();
        em.persist(xxd);
        em.remove(nutty);  // delete from instructors where id = ?
        em.getTransaction().commit();
    }

    private void doDelete2() {
        Department xxd = em.find(Department.class, "XX");
        int chairId = xxd.getChair().getId();
        xxd.setChair(null);
        em.getTransaction().begin();
        em.persist(xxd);
        em.createQuery("delete from Instructor i where i.id = :id").setParameter("id", chairId).executeUpdate();
        em.getTransaction().commit();
    }

    private void doDelete1() {
        Department xxd = em.find(Department.class, "XX");
        xxd.setChair(null);
        em.getTransaction().begin();
        em.persist(xxd);
        em.flush();
        em.clear();
        Instructor nutty = em.find(Instructor.class, 5);
        em.remove(nutty);
        xxd = em.find(Department.class, "XX");
        em.remove(xxd);
        em.getTransaction().commit();
    }

    private void doDeleteDepartment() {
        Department xxd = em.find(Department.class, "XX");
        em.getTransaction().begin();
        em.remove(xxd);
        em.getTransaction().commit();
    }

    private void go() {
        doDelete1();
        //doDeleteDepartment();
        //doCreateDepartment();
        //doCreateInstructor();
        //doRead();
    }
}
