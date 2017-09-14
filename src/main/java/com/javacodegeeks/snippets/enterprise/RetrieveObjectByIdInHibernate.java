package com.javacodegeeks.snippets.enterprise;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class RetrieveObjectByIdInHibernate {
	
public static void main(String[] args) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		
		Session session = sessionFactory.getCurrentSession();

		Employee employee = new Employee();
		employee.setName("employee_name");
		employee.setSurname("employee_surname");
		employee.setTitle("employee_title");
		employee.setCreated(new Date());
		
		try {
			session.beginTransaction();
			session.save(employee);
			session.getTransaction().commit();
			System.out.println("Employee saved with ID: " + employee.getId());
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		long id = employee.getId();
		
		session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			Employee dbEmployee = (Employee) session.get(Employee.class, id);
			System.out.println(dbEmployee.getId() + " - " + dbEmployee.getName());
			
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}

}