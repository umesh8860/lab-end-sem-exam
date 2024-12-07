package com.klef.jfsd.exam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        // Initialize SessionFactory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
        // Insert records into the database
        insertCustomer(session);

        // Apply restrictions using Criteria
        applyCriteriaRestrictions(session);

        // Close the session
        session.close();
        sessionFactory.close();
    }

    private static void insertCustomer(Session session) {
        Transaction transaction = session.beginTransaction();
        
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setAge(25);
        customer.setLocation("New York");

        session.save(customer);  // Save customer to DB
        transaction.commit();
    }

    private static void applyCriteriaRestrictions(Session session) {
        Criteria criteria = session.createCriteria(Customer.class);
        
        // Example: Apply 'equal' restriction for age = 25
        criteria.add(Restrictions.eq("age", 25));
        
        // Example: Apply 'greater than' restriction for age > 20
        criteria.add(Restrictions.gt("age", 20));
        
        // Example: Apply 'like' restriction for name starting with 'John'
        criteria.add(Restrictions.like("name", "John%"));

        List<Customer> customers = criteria.list();
        
        for (Customer customer : customers) {
            System.out.println("Customer ID: " + customer.getId() + ", Name: " + customer.getName());
        }
    }
}