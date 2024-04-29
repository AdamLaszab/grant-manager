package sk.stuba.fei.uim.oop.entity.organization;
import sk.stuba.fei.uim.oop.utility.Constants;


import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;

import java.util.HashMap;
import java.util.ArrayList;
public class Company extends Organization{
   private int budget;
   private HashMap<ProjectInterface,ArrayList<Integer>> projectToList;
   public Company(){
     super();
     this.budget= Constants.COMPANY_INIT_OWN_RESOURCES;
     this.projectToList = new HashMap<ProjectInterface,ArrayList<Integer>>();
   }
   public void projectBudgetUpdateNotification(ProjectInterface pi,int year,int budgetForYear){
        if(projectToList.containsKey(pi)==false){
            ArrayList<Integer> newList = new ArrayList<>();
            for (int i = 0; i<=(pi.getEndingYear()-pi.getStartingYear()); i++) {
            newList.add(0);
            }
            projectToList.put(pi,newList);
        } 
        int index = year-pi.getStartingYear(); 
       if(budgetForYear==0){
         projectToList.get(pi).set(index,budgetForYear);
       } else{
            if(this.budget >= budgetForYear){
                projectToList.get(pi).set(index,budgetForYear);
                this.budget=budget-budgetForYear;
            }else{
                projectToList.get(pi).set(index,this.budget);
                this.budget=0;
            }
       }
   }
   @Override
   public int getProjectBudget(ProjectInterface pi){
    if(super.getAllProjects().contains(pi)==true){
        int sum = pi.getTotalBudget();
        for(int number : projectToList.get(pi)){
            sum+=number;
        }
        return sum;
    }else{
        return 0;
    }
   }
   @Override
   public int getBudgetForAllProjects(){
        int sum=0;
        for(ProjectInterface project : super.getAllProjects()){
            sum+=project.getTotalBudget();
            if(projectToList.containsKey(project)==true){
              for(int number: projectToList.get(project)){
                sum+=number;
              }
            }
        }
        return sum;
   }
}
