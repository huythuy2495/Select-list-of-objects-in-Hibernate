package com.javacodegeeks.snippets.enterprise;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

public class CountTotalRecordsInHibernateWithProjections {
	
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
		
		try {
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Employee.class);
			criteria.setProjection(Projections.rowCount());
			
			List employees = criteria.list();
			if (employees!=null) {
				Integer rowCount = (Integer) employees.get(0);
				System.out.println("Total Results:" + rowCount);
			}
			
			session.getTransaction().commit();
		}
		catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
		
	}

}