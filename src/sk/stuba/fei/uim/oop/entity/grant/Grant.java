package sk.stuba.fei.uim.oop.entity.grant;
import java.util.Set;

import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.utility.Constants;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;
public class Grant implements GrantInterface {
    private String identifier;
    private int year;
    private AgencyInterface agency;
    private int budget;
    private int remainingBudget;
    private GrantState state;
    private Set<ProjectInterface> projects;
    private HashMap<ProjectInterface,Integer> evaluated;
    
    public Grant(){
        this.projects = new LinkedHashSet<ProjectInterface>();
        this.evaluated = new HashMap<ProjectInterface,Integer>();
    }
    public String getIdentifier(){
        return this.identifier;
    }
    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }
    public int getYear(){
        return this.year;
    }
    public void setYear(int year){
        if(year>=1000 && year<=9999){
        this.year=year;
        }
    }
    public AgencyInterface getAgency(){
        return this.agency;
    }
    public void setAgency(AgencyInterface agency){
        this.agency =agency;
    }
    public int getBudget(){
        return this.budget;
    }
    public void setBudget(int budget){
        this.budget=budget; 
        this.remainingBudget=budget;
    }
    public int getRemainingBudget(){
        return this.remainingBudget;
    }
    public GrantState getState(){
        return this.state;
    }
    public void callForProjects(){
        if(this.state == null){
        this.state=GrantState.STARTED;
        }
    }
    public boolean registerProject(ProjectInterface project){
        if(this.state==GrantState.STARTED){
            if(project.getStartingYear()==this.year){
                if(!project.getAllParticipants().isEmpty()){
                    this.projects.add(project);
                    return true;
                }
            }
        }
        return false;
    }
    public Set<ProjectInterface> getRegisteredProjects(){
        return this.projects;
    }
    public int getBudgetForProject(ProjectInterface project){
        return this.evaluated.get(project);
    }
    public void evaluateProjects(){
        if(this.state == GrantState.STARTED){
       this.state = GrantState.EVALUATING;
       Set<ProjectInterface> passed = new LinkedHashSet<>();
       Set<ProjectInterface> failed = new LinkedHashSet<>();
        HashSet<GrantInterface> allGrants = new HashSet<>(); 
       allGrants.addAll(this.agency.getAllGrants());
       HashSet<GrantInterface> runningGrants = new HashSet<>();
       for(GrantInterface grant : allGrants){
            if(grant.getState()==GrantState.CLOSED){
                runningGrants.add(grant);
            }
       }
       HashSet<ProjectInterface> runningProjects = new HashSet<>();
       for(GrantInterface grant: runningGrants){
            for(ProjectInterface project : grant.getRegisteredProjects()){
                if(project.getBudgetForYear(this.year)>0 || project.getBudgetForYear((this.year)+(Constants.PROJECT_DURATION_IN_YEARS-1))>0){
                    runningProjects.add(project);
                }
            }            
       }
       HashMap<PersonInterface,ArrayList<Integer>> overlapingPeople = new HashMap<>();
       for(ProjectInterface project : runningProjects){
        for(int i=0;i<Constants.PROJECT_DURATION_IN_YEARS;i++){
            for(PersonInterface person : project.getAllParticipants()){
            if(project.getBudgetForYear(this.year+i)>0){
                if(overlapingPeople.containsKey(person) == false){
                    ArrayList<Integer> newList = new ArrayList<>();
                    for (int j = 0; j < Constants.PROJECT_DURATION_IN_YEARS; j++) {
                        newList.add(0);
                    }
                    newList.set(i,newList.get(i)+project.getApplicant().getEmploymentForEmployee(person));
                    overlapingPeople.put(person,newList);
                }else{
                    overlapingPeople.get(person).set(i,overlapingPeople.get(person).get(i)+project.getApplicant().getEmploymentForEmployee(person));
                }
            }else{
               if(overlapingPeople.containsKey(person)==false){
                ArrayList<Integer> newList = new ArrayList<>();
                for (int j = 0; j < Constants.PROJECT_DURATION_IN_YEARS; j++) {
                        newList.add(0);
                }
                overlapingPeople.put(person,newList);
               }
            }
        }
         }
       }
       for(ProjectInterface project : this.projects){
        passed.add(project);
        for(PersonInterface employee : project.getAllParticipants()){
            if(overlapingPeople.containsKey(employee)){
                 for(int i=0;i<Constants.PROJECT_DURATION_IN_YEARS;i++){
                        if((overlapingPeople.get(employee).get(i)+project.getApplicant().getEmploymentForEmployee(employee))>Constants.MAX_EMPLOYMENT_PER_AGENCY){
                            passed.remove(project);
                            failed.add(project);
                            break;
                        }
                 }
            }
        }
       }
       for(ProjectInterface project : failed){
            for(int i=0;i<Constants.PROJECT_DURATION_IN_YEARS;i++){
                evaluated.put(project,0);
            }
        }
        int numberOfFunded;
        int moneyPerFunded;
        if(passed.size()==1){
            numberOfFunded = passed.size();
        }else{      
        numberOfFunded = passed.size()/2;
        }
        if(passed.size()!=0){
        moneyPerFunded = budget/numberOfFunded;
        }else{
            moneyPerFunded=0;
        }
        for(ProjectInterface project : passed){
            if(numberOfFunded>0){
                evaluated.put(project,moneyPerFunded);
                remainingBudget-=moneyPerFunded;
                numberOfFunded--;
            }else{
                evaluated.put(project,0);
            }
        }
    }
    }

    public void closeGrant(){
        if(this.state == GrantState.EVALUATING){
        this.state=GrantState.CLOSED;
        for(ProjectInterface project : evaluated.keySet()){
            int assigned = evaluated.get(project);
            int perYear = assigned/Constants.PROJECT_DURATION_IN_YEARS;
            for(int i=0;i<Constants.PROJECT_DURATION_IN_YEARS;i++){
                project.setBudgetForYear(this.year+i, perYear);
            }
        }
    }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grant grant = (Grant) obj;
        return Objects.equals(identifier, grant.identifier);
    }
    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
