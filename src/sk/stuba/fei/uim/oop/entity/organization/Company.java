package sk.stuba.fei.uim.oop.entity.organization;
import sk.stuba.fei.uim.oop.utility.Constants;
import java.util.Set;
import java.util.HashSet;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.HashMap;

public class Company implements OrganizationInterface{
   private String name;
   private HashMap<PersonInterface,Integer> employeesToEmployment = new HashMap<>();
   private Set<ProjectInterface> projects = new HashSet<>();
   private int budget = Constants.COMPANY_INIT_OWN_RESOURCES;
   public String getName(){
    return this.name;
   }
   public void setName(String name){
    this.name=name;
   }
   public void addEmployee(PersonInterface p,int employment){
    employeesToEmployment.put(p,employment);
   }
   public Set<PersonInterface> getEmployees(){
        return this.employeesToEmployment.keySet();
   }
   public int getEmploymentForEmployee(PersonInterface p){
        return this.employeesToEmployment.get(p);
   }
   public void registerProjectInOrganization(ProjectInterface project){
        projects.add(project);
   }
   public Set<ProjectInterface> getRunningProjects(int year){
    Set<ProjectInterface> runningProjects = new HashSet<>();

        for (ProjectInterface project : projects) {
            if (project.getStartingYear() <= year && project.getEndingYear()>= year) {
                runningProjects.add(project);
            }
        }

        return runningProjects;
   }
   public Set<ProjectInterface> getAllProjects(){
    return this.projects;
   }
   public int getProjectBudget(ProjectInterface pi){
        return pi.getTotalBudget();
   }
   public int getBudgetForAllProjects(){
    int sum=0;    
        for(ProjectInterface project : projects){
            sum+=project.getTotalBudget();
        }
        return sum;
   }
   public int getBudget(){
    return this.budget;
   }
   public void setBudget(int budget){
    this.budget=budget;
   }
   public void projectBudgetUpdateNotification(ProjectInterface pi,int year,int budgetForYear){
        if(budgetForYear==0){
            pi.setBudgetForYear(year, budgetForYear);
        }else{
            if(this.getBudget()>=budgetForYear){
            pi.setBudgetForYear(year, budgetForYear*2);
            this.setBudget(this.getBudget()-budgetForYear);
            }else{
                pi.setBudgetForYear(year, budgetForYear+this.getBudget());
                this.setBudget(0);
            }
        }
   }
}
