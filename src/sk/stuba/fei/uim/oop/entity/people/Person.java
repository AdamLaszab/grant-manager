package sk.stuba.fei.uim.oop.entity.people;

import sk.stuba.fei.uim.oop.entity.organization.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
public class Person implements PersonInterface{
    private String name;
    private String address;
    private Set<OrganizationInterface> employers;

    public Person(){
        this.employers= new HashSet<OrganizationInterface>();
    }
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return Objects.equals(name, person.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
