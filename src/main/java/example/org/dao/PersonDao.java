package example.org.dao;

import example.org.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDao {

    private final EntityManager entityManager;

    @Autowired
    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public void testNPlus() {
        Session session = entityManager.unwrap(Session.class);

//        // 1 request
//        List<Person> people = session.createQuery("select p from Person p", Person.class).
//                getResultList();
//
//        //N requests to DB
//        for (Person person : people) {
//            System.out.println("Person " + person.getName() + " has: " + person.getItems());
//        }

        Set<Person> people = new HashSet<Person>(session.createQuery(
                "select p from Person p LEFT JOIN FETCH p.items").getResultList());

        for (Person person : people) {
            System.out.println("Person " + person.getName() + " has: " + person.getItems());
        }
    }
}
