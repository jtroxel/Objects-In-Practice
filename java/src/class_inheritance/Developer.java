package class_inheritance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Developer: TODO
 *
 * @author <a href="mailto:jtroxel@yahoo.com">John Troxel</a>
 */
public class Developer extends Employee {

  public Developer(Date startDate) {
    super(startDate);
    this.technicalSkills = new ArrayList();
  }

  @Override
  public String jobDescription() {
    return "Developer: " + listTechnicalSkills(); // Default description, will be overriden by subclasses
  }

  private String listTechnicalSkills() {
    String skillStr = "";
    // TODO get skills from getTechnicalSkills and build string
    return skillStr;
  }

  private List technicalSkills;

  public List getTechnicalSkills() {
    return technicalSkills;
  }

  public void setTechnicalSkills(List technicalSkills) {
    this.technicalSkills = technicalSkills;
  }
}
