package org.Hql.hb;

import java.util.List;

import javax.persistence.NamedQuery;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class HQLTest {

	public static void main(String arg[]) {
		
		Studentdata st=new Studentdata();
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
        
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
		  
		SessionFactory factory = meta.getSessionFactoryBuilder().build();  
		Session session = factory.openSession();  
		session.beginTransaction(); 
		
		/*//fetch the data from Db		
		Query query= session.createQuery("from Studentdata");
		List studentInfo = query.list();*/
		
		/*//fetch the data using Where Clause
		Query query1= session.createQuery("from Studentdata where studentId > 5");
		List studentInfo1 = query1.list();*/
		
/*		//fetch the data using Where Clause
		Query query2= session.createQuery("from Studentdata");
		//print some limited data
		query2.setFirstResult(5);
		query2.setMaxResults(4);		
		List<Studentdata> studentInfo2 = (List<Studentdata>)query2.list();
		*/
		
/*		//fetch only studentName Data		
		Query query3= session.createQuery("select studentName from Studentdata where studentId >5");	
		List<String> studentInfo3 = (List<String>)query3.list();
		*/
		
/*		//fetch only studentName Data using where or and clause
		String id="5";
		String studentAddress="Address 10";
		Query query4= session.createQuery("from Studentdata where studentId > :studentId and studentAddress = :studentAddress");	
		query4.setInteger("studentId", Integer.parseInt(id));
		query4.setString("studentAddress", studentAddress);
		List<Studentdata> studentInfo4 = (List<Studentdata>)query4.list();*/
		
/*		//fetch the data using NamedQuery 
		Query query5= session.getNamedQuery("studentId");
		query5.setInteger("studentId", 2);
		List <Studentdata>studentInfo5= (List<Studentdata>)query5.list();*/
		
/*		// fetch the data using NamedNativeQuery
		Query query6= session.getNamedQuery("studentInfo.byName");
		query6.setString("studentName", "Student 10");
		List <Studentdata>studentInfo6= (List<Studentdata>)query6.list();*/
		
/*		Criteria criteria=session.createCriteria(Studentdata.class);
		criteria.add(Restrictions.eq("studentName", "Student 9"));
		List <Studentdata>studentInfo7= (List<Studentdata>)criteria.list();*/
		
/*		Criteria criteria2=session.createCriteria(Studentdata.class);
		criteria2.add(Restrictions.like("studentName", "%Student%"))
				 .add(Restrictions.between("studentId", 5, 50));
		List <Studentdata> studentInfo7= (List<Studentdata>)criteria2.list();*/
		
/*		
		Criteria criteria3=session.createCriteria(Studentdata.class);
		criteria3.add(Restrictions.or(Restrictions.between("studentId", 1, 3), 
				      Restrictions.between("studentId", 6, 10)));
		List <Studentdata> studentInfo8= (List<Studentdata>)criteria3.list();*/
		
		//projection example
/*		Criteria criteria4=session.createCriteria(Studentdata.class)
				.addOrder(Order.desc("studentName"));
		List <Studentdata> studentInfo9= (List<Studentdata>)criteria4.list();*/
		
		
/*		Criteria criteria4=session.createCriteria(Studentdata.class)
				.setProjection(Projections.count("studentId"));
		System.out.println("Criteria:   "+criteria4);*/
		
		
		//Query by Example 
		//st.setStudentId(5);
		st.setStudentName("Student 1%");		
		Example example = Example.create(st).enableLike();
		Criteria criteria6=session.createCriteria(Studentdata.class)
				 .add(example);
		List <Studentdata> studentInfo10= (List<Studentdata>)criteria6.list();
		
		
		session.getTransaction().commit();		
		System.out.println("successfully saved");    
		session.close(); 		

		
		//print all the fetch Data
		for(Studentdata stdata: studentInfo10) {
			System.out.println(stdata.getStudentName());
		}
		
		
/*		//print olny StudentName filed
		for(String stdata: studentInfo3) {
			System.out.println(stdata);
		}*/
		
		
		
/*		// fetch all the data with using Hql
		session = factory.openSession();  
		session.beginTransaction(); 		
		for(int i =1; i<=studentInfo.size();i++) {			
		st=(Studentdata) session.get(Studentdata.class, i);
		System.out.println("Table data " + st.getStudentName() + "  "+ st.getStudentAddress() ); 
		}
		session.getTransaction().commit(); 
		session.close(); */
	}
}
