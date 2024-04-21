package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.entity.grant.Agency;
import sk.stuba.fei.uim.oop.entity.grant.AgencyInterface;
import sk.stuba.fei.uim.oop.entity.grant.Grant;
import sk.stuba.fei.uim.oop.entity.grant.GrantInterface;
import sk.stuba.fei.uim.oop.entity.grant.Project;
import sk.stuba.fei.uim.oop.entity.grant.ProjectInterface;
import sk.stuba.fei.uim.oop.entity.organization.Company;
import sk.stuba.fei.uim.oop.entity.organization.OrganizationInterface;
import sk.stuba.fei.uim.oop.entity.organization.University;
import sk.stuba.fei.uim.oop.entity.people.Person;
import sk.stuba.fei.uim.oop.entity.people.PersonInterface;

public class Main {

    public static void main(String[] args) {
        OrganizationInterface STU = new University();
        OrganizationInterface UK = new University();
        OrganizationInterface ESET = new Company();

        STU.setName("STU");
        UK.setName("UK");
        ESET.setName("ESET");
        
        PersonInterface Peter = new Person();
        PersonInterface Jozef = new Person();
        PersonInterface Anna = new Person();
        PersonInterface Karol = new Person();

        Peter.setName("Peter");
        Jozef.setName("Jozef");
        Anna.setName("Anna");
        Karol.setName("Karol");
        STU.addEmployee(Peter, 2);
        Peter.addEmployer(STU);
        STU.addEmployee(Jozef, 3);
        Jozef.addEmployer(STU);
        UK.addEmployee(Anna, 3);
        UK.addEmployee(Karol, 1);
        Anna.addEmployer(UK);
        Karol.addEmployer(UK);
        ESET.addEmployee(Peter, 4);
        ESET.addEmployee(Anna, 2);
        ESET.addEmployee(Karol, 1);
        Peter.addEmployer(ESET);
        Anna.addEmployer(ESET);
        Karol.addEmployer(ESET);

        AgencyInterface APVV = new Agency();
        AgencyInterface VEGA = new Agency();
        GrantInterface grant1 = new Grant();
        GrantInterface grant2 = new Grant();
        ProjectInterface P1 = new Project();
        ProjectInterface P2 = new Project();

        grant1.setIdentifier("grant1");
        grant1.setBudget(100000);
        grant1.setAgency(APVV);
        grant2.setIdentifier("grant2");
        grant2.setBudget(8000);
        grant2.setAgency(VEGA);
        APVV.addGrant(grant1, 2022);
        VEGA.addGrant(grant2, 2023);

        P1.setApplicant(STU);
        P1.addParticipant(Peter);
        P1.addParticipant(Jozef);
        P1.setStartingYear(2022);
        P2.setApplicant(UK);
        P2.addParticipant(Karol);
        P2.addParticipant(Anna);
        P2.setStartingYear(2023);
        grant1.callForProjects();
        grant2.callForProjects();
        grant1.registerProject(P1);
        grant2.registerProject(P2);
        grant1.evaluateProjects();
        grant2.evaluateProjects();
        grant1.closeGrant();
        grant2.closeGrant();
        System.out.println(P1.getTotalBudget());
        System.out.println(P2.getTotalBudget());
        System.out.println(P1.getBudgetForYear(2022));
        System.out.println(P2.getBudgetForYear(2023));
        System.out.println(grant1.getBudgetForProject(P1));
        System.out.println(grant2.getBudgetForProject(P2));
        System.out.println("STU PROJECT: " + STU.getProjectBudget(P1));
        System.out.println("UK PROJECT: " + UK.getProjectBudget(P2));

        GrantInterface grant3 = new Grant();
        grant3.setAgency(APVV);
        grant3.setBudget(100000);
        grant3.setYear(2024);
        APVV.addGrant(grant3, 2024);
        ProjectInterface P3 = new Project();
        P3.setApplicant(ESET);
        P3.addParticipant(Peter);
        P3.setStartingYear(2024);
        grant3.callForProjects();
        System.out.println(grant3.registerProject(P3));
        grant3.evaluateProjects();
        grant3.closeGrant();
        System.out.println(P3.getTotalBudget());
        System.out.println(P3.getBudgetForYear(2024));
        System.out.println(grant3.getBudgetForProject(P3));
        System.out.println("ESET PROJECT: " + ESET.getProjectBudget(P3));

        GrantInterface grant4 = new Grant();
        grant4.setAgency(VEGA);
        grant4.setBudget(8000);
        grant4.setYear(2024);
        VEGA.addGrant(grant4, 2024);
        ProjectInterface P4 = new Project();
        P4.setApplicant(ESET);
        P4.addParticipant(Karol);
        P4.addParticipant(Anna);
        P4.setStartingYear(2024);
        ProjectInterface P5 = new Project();
        P5.setApplicant(STU);
        P5.addParticipant(Peter);
        P5.addParticipant(Jozef);
        P5.setStartingYear(2024);
        ProjectInterface P6 = new Project();
        P6.setApplicant(UK);
        P6.addParticipant(Anna);
        P6.setStartingYear(2024);
        ProjectInterface P7 = new Project();
        P7.setApplicant(UK);
        P7.addParticipant(Karol);
        P7.setStartingYear(2024);
        grant4.callForProjects();
        System.out.println(grant4.registerProject(P4));
        System.out.println(grant4.registerProject(P5));
        System.out.println(grant4.registerProject(P6));
        System.out.println(grant4.registerProject(P7));
        grant4.evaluateProjects();
        grant4.closeGrant();
        System.out.println(P4.getTotalBudget());
        System.out.println(P5.getTotalBudget());
        System.out.println(P6.getTotalBudget());
        System.out.println(P7.getTotalBudget());
        System.out.println(P4.getBudgetForYear(2024));
        System.out.println(grant4.getBudgetForProject(P4));
        System.out.println("ESET PROJECT: "+ ESET.getProjectBudget(P4));
        System.out.println(P5.getBudgetForYear(2024));
        System.out.println(grant4.getBudgetForProject(P5));
        System.out.println(P6.getBudgetForYear(2024));
        System.out.println(grant4.getBudgetForProject(P6));
        System.out.println(P7.getBudgetForYear(2024));
        System.out.println(grant4.getBudgetForProject(P7));

        System.out.println("ESET CELKOVO:" + ESET.getBudgetForAllProjects());
        System.out.println("STU CELKOVO:" + STU.getBudgetForAllProjects());
        System.out.println("UK CELKOVO:" + UK.getBudgetForAllProjects());
    }
}
