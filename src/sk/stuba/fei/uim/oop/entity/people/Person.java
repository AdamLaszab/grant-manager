package sk.stuba.fei.uim.oop.entity.people;

import sk.stuba.fei.uim.oop.entity.organization.*;

import java.util.HashSet;
import java.util.Set;

public class Person implements PersonInterface{
    private String name;
    private String address;
    private Set<OrganizationInterface> employers= new HashSet<>();

    public String getName(){
        return this.name;
        }
    public void setName(String name){
        this.name=name;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public String getAddress(){
        return this.address;
    }

    public void addEmployer(OrganizationInterface organization){
        this.employers.add(organization);
    }

    public Set<OrganizationInterface> getEmployers(){
        return this.employers;
    }
}
