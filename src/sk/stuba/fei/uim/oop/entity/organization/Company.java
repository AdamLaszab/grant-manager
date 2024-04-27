package sk.stuba.fei.uim.oop.entity.organization;
import sk.stuba.fei.uim.oop.utility.Constants;
import java.util.Set;
import java.util.HashSet;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.HashMap;

public class Company extends Organization{
   private int budget = Constants.COMPANY_INIT_OWN_RESOURCES;
   public void projectBudgetUpdateNotification(ProjectInterface pi,int year,int budgetForYear){
       if(budgetForYear==0){
         pi.setBudgetForYear(year, budgetForYear);
       } else{
            if(this.budget >= budgetForYear){
                pi.setBudgetForYear(year, budgetForYear*2);
                this.budget=budget-budgetForYear;
            }else{
                pi.setBudgetForYear(year, budgetForYear+this.budget);
                this.budget=0;
            }
       }
   }
}
