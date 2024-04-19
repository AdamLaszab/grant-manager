package sk.stuba.fei.uim.oop.entity.grant;

import java.util.Set;
public class Agency implements AgencyInterface {
    private String name;
    private Set<GrantInterface> grants;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name=name;
    }
}
