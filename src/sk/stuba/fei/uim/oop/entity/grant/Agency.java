package sk.stuba.fei.uim.oop.entity.grant;

import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
public class Agency implements AgencyInterface {
    private String name;
    private Set<GrantInterface> grants;

    public Agency(){
        this.grants = new HashSet<GrantInterface>();
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
    public void addGrant(GrantInterface gi, int year){
        if(year>=1000 && year<=9999){
        gi.setYear(year);
        grants.add(gi);
        }
    }
    public Set<GrantInterface> getAllGrants(){
        return this.grants;
    }
    public Set<GrantInterface> getGrantsIssuedInYear(int year){
        Set<GrantInterface> grantsIssuedInYear = new HashSet<>();

        for (GrantInterface grant : grants) {
            if (grant.getYear() == year) {
                grantsIssuedInYear.add(grant);
            }
        }

        return grantsIssuedInYear;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Agency agency = (Agency) obj;
        return Objects.equals(name, agency.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
}
