package com.javacodegeeks.snippets.enterprise;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;

public class OrderQueryResultsetInHibernateWithCriteria {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();

			Employee employee = new Employee();
			employee.setName("Jack");
			employee.setSurname("Thomson");
			employee.setTitle("QA Engineer");
			employee.setCreated(new Date());
			session.save(employee);
			
			employee = new Employee();
			employee.setName("Helen");
			employee.setSurname("Jasmin");
			employee.setTitle("Project Manager");
			employee.setCreated(new Date());
			session.save(employee);
			
			employee = new Employee();
			employee.setName("Tom");
			employee.setSurname("Markus");
			employee.setTitle("Software Developer");
			employee.setCreated(new Date());
			session.save(employee);

			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session = sessionFactory.getCurrentSession();

		try {
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Employee.class);
			criteria.addOrder(Order.asc("surname"));

			List<Employee> employees = (List<Employee>) criteria.list();
			if (employees != null) {
				System.out.println("Total Results:" + employees.size());
				for (Employee employee : employees) {
					System.out.println(employee.getId() + " - " + employee.getSurname());
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