package com.javacodegeeks.snippets.enterprise;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PaginationInHibernateWithQueryResults {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			for (int i = 0; i < 100; i++) {
				Employee employee = new Employee();
				employee.setName("employe_"+i);
				employee.setSurname("surname_"+i);
				employee.setTitle("Engineer_"+i);
				employee.setCreated(new Date());
				session.save(employee);
			}
			
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
		session = sessionFactory.getCurrentSession();
		
		int pageNumber = 2;
		int pageSize = 10;

		try {
			session.beginTransaction();
			
			Query query = session.createQuery("from Employee");
			query.setFirstResult((pageNumber - 1) * pageSize);
			query.setMaxResults(pageSize);
			
			List<Employee> employees = (List<Employee>) query.list();
			if (employees!=null) {
				System.out.println("Total Results:" + employees.size());
				for (Employee employee : employees) {
					System.out.println(employee.getId() + " - " + employee.getName());
				}
			}
			
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}

}
