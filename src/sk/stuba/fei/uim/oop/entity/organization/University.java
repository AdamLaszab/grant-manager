package sk.stuba.fei.uim.oop.entity.organization;

import java.util.Set;
import java.util.HashSet;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

import java.util.HashMap;

public class University implements OrganizationInterface{
   private String name;
   private HashMap<PersonInterface,Integer> employeesToEmployment = new HashMap<>();
   private Set<ProjectInterface> projects= new HashSet<ProjectInterface>();
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
    //NIE SOM SI ISTY 
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
   public void projectBudgetUpdateNotification(ProjectInterface pi,int year,int budgetForYear){
            pi.setBudgetForYear(year, budgetForYear);
   }
}
