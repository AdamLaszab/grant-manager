package sk.stuba.fei.uim.oop.entity.grant;
import java.util.Set;

import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.utility.Constants;
import java.util.LinkedHashSet;
import java.util.HashMap;
import java.util.HashSet;
public class Grant implements GrantInterface {
    private String identifier;
    private int year;
    private AgencyInterface agency;
    private int budget;
    private int remainingBudget;
    private GrantState state;
    private Set<ProjectInterface> projects=new LinkedHashSet<ProjectInterface>();
    private Set<ProjectInterface> passed = new LinkedHashSet<>();
    private Set<ProjectInterface> failed = new LinkedHashSet<>();
    private HashMap<ProjectInterface,Integer> evaluated = new HashMap<>();
    
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
        this.year=year;
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
    public void setRemainingBudget(int budget){
        this.remainingBudget=budget;
    }
    public GrantState getState(){
        return this.state;
    }
    public void callForProjects(){
        this.state=GrantState.STARTED;
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
       this.state = GrantState.EVALUATING;
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
                if(project.getBudgetForYear(grant.getYear())>0){
                    runningProjects.add(project);
                }
            }            
       }
       HashMap<PersonInterface,Integer> overlapingPeople = new HashMap<>();
       for(ProjectInterface project : runningProjects){
            if(project.getBudgetForYear(this.year)>0){
                for(PersonInterface person : project.getAllParticipants()){
                overlapingPeople.put(person,project.getApplicant().getEmploymentForEmployee(person));
                }
            }
       }
       
       for(ProjectInterface project : this.projects){
        passed.add(project);
        for(PersonInterface employee : project.getAllParticipants()){
            if(overlapingPeople.containsKey(employee)){
                if(project.getApplicant().getEmploymentForEmployee(employee)+overlapingPeople.get(employee)>Constants.MAX_EMPLOYMENT_PER_AGENCY){
                   passed.remove(project); 
                   failed.add(project);
                }
            }else{

            }
        }
       }
       for(ProjectInterface project : failed){
            for(int i=0;i<Constants.PROJECT_DURATION_IN_YEARS;i++){
                evaluated.put(project,0);
            }
        }
        int numberOfPassed;
        int moneyPerPassed;
        if(passed.size()==1){
            numberOfPassed = passed.size();
        }else{      
        numberOfPassed = passed.size()/2;
        }
        if(passed.size()!=0){
        moneyPerPassed = budget/numberOfPassed;
        }else{
            moneyPerPassed=0;
        }
        for(ProjectInterface project : passed){
            if(numberOfPassed>0){
                evaluated.put(project,moneyPerPassed);
                remainingBudget-=moneyPerPassed;
                numberOfPassed--;
            }else{
                evaluated.put(project,0);
            }
        }
    }

    public void closeGrant(){
        this.state=GrantState.CLOSED;
        for(ProjectInterface project : evaluated.keySet()){
            int assigned = evaluated.get(project);
            int perYear = assigned/Constants.PROJECT_DURATION_IN_YEARS;
            if( assigned >0){
                project.getApplicant().registerProjectInOrganization(project);
            }
            for(int i=0;i<Constants.PROJECT_DURATION_IN_YEARS;i++){
                project.getApplicant().projectBudgetUpdateNotification(project,this.year+i, perYear);
            }
        }

    }
}
