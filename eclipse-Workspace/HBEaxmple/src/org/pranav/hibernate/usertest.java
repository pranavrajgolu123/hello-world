package org.pranav.hibernate;

import java.util.Date;

import javax.transaction.Transaction;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.pranav.hb.Address;
import org.pranav.hb.CarFeatures;
import org.pranav.hb.Vechile;
import org.pranav.hb.userdetail;

import com.mchange.rmi.Cardable;  
public class usertest {

	public static void main(String arg[]) {
		
		userdetail usr=new userdetail();
		usr.setFirstName("sggg");
		usr.setLastName("trtrt");
		usr.setPhoneNo("87567654656");
		usr.setJoindate(new Date());
		
		Address ad=new Address();
		ad.setHomeAdress("hajipur");
		ad.setState("Bihar");
		
		Address ad2=new Address();
		ad2.setHomeAdress("Patna");
		ad2.setState("Bihar");
		
		usr.getListAdresss().add(ad);
		usr.getListAdresss().add(ad2);
		
		Vechile vc=new Vechile();
		vc.setCarModel("BS4");
		vc.setCarName("Polo");
		
		Vechile vc2=new Vechile();
		vc2.setCarModel("Zeta");
		vc2.setCarName("Baleno");
		
		CarFeatures carFeu = new CarFeatures();
		carFeu.setCarName("Swift");
		carFeu.setCarAc("Yes");
		carFeu.setWheelDetail("15.5 r");
		
		usr.getListVechile().add(vc);
		usr.getListVechile().add(vc2);
		
		vc.getListManyVechile().add(usr);
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
        
		   Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();  
		  
		SessionFactory factory = meta.getSessionFactoryBuilder().build();  
		Session session = factory.openSession();  
		session.beginTransaction();  		        
		/*session.save(usr); 
		session.save(vc);
		session.save(vc2);
		session.save(carFeu);*/
		
		/*userdetail vec= (userdetail) session.get(userdetail.class, 25);
		vec.setFirstName("pranav");
		session.update(vec);*/
		
		//Vechile vec= (Vechile) session.get(Vechile.class, 32);
		
		session.getTransaction().commit(); 
		System.out.println("successfully saved");    
		session.close();    
		
/*		session = factory.openSession();  
		session.beginTransaction(); 
		//vec.setCarModel("BS4");
		session.update(vec);
		session.getTransaction().commit(); 
		session.close();
      */
		
	}
	
}
