package sk.stuba.fei.uim.oop.entity.grant;
import java.util.Set;

import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
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
        HashSet<GrantInterface> runningGrants = new HashSet<>(); 
       runningGrants.addAll(this.agency.getAllGrants());
        
    }

    public void closeGrant(){

    }
}
