package sk.stuba.fei.uim.oop.entity.grant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;
import sk.stuba.fei.uim.oop.utility.Constants;
public class Project implements ProjectInterface{
    private String projectName;
    private int startingYear;
    private OrganizationInterface applicant;
    private Set<PersonInterface> participants;
    private HashMap<Integer,Integer> yearToBudget;
    
    public Project(){
        this.participants=new HashSet<PersonInterface>();
        this.yearToBudget=new HashMap<Integer,Integer>();
    }

    public String getProjectName(){
        return this.projectName;
    }
    public void setProjectName(String name){
        this.projectName=name;
    }
    public int getStartingYear(){
        return this.startingYear;
    }
    public void setStartingYear(int year){
        if(year>=1000 && year<=9999){
        this.startingYear = year;
        }
    }
    public int getEndingYear(){
        return this.startingYear+(Constants.PROJECT_DURATION_IN_YEARS-1);
    }

    public void setApplicant(OrganizationInterface applicant){
        this.applicant=applicant;
    }
    public OrganizationInterface getApplicant(){
        return this.applicant;
    }

    public void addParticipant(PersonInterface participant){
        if (this.applicant!=null){
            if(participant.getEmployers().contains(applicant) && applicant.getEmployees().contains(participant)){
                participants.add(participant);
            }
        }        
    }
    public Set<PersonInterface> getAllParticipants(){
        return this.participants;
    }
    public void setBudgetForYear(int year,int budget){
            yearToBudget.put(year,budget);
            this.applicant.projectBudgetUpdateNotification(this, year, budget);
    }
    public int getBudgetForYear(int year){
        if(yearToBudget.get(year)==null){
            return 0;
        }
        return yearToBudget.get(year);
        
    }
    public int getTotalBudget(){
        int sum=0;
        for(int value : yearToBudget.values()){
            sum += value;
        }
        return sum;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Project project = (Project) obj;
        return Objects.equals(projectName, project.projectName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(projectName);
    }

}
