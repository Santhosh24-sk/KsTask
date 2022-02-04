package com.skstudio.task;

public class Project {

    public String ProjectName,ProjectDate,Cname,CatchPhrase,Location,Website;

    public Project(String projectName, String projectDate, String cname, String catchPhrase, String location, String website) {
        ProjectName = projectName;
        ProjectDate = projectDate;
        Cname = cname;
        CatchPhrase = catchPhrase;
        Location = location;
        Website = website;
    }

    public Project() {
    }
}
