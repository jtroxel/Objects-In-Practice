package class_inheritance;

import java.util.Collection;
import java.util.Date;

/**
 * Manager: TODO
 *
 * @author <a href="mailto:jtroxel@yahoo.com">John Troxel</a>
 */
public class Manager extends Employee {
  Collection<Employee> reports;

  public Manager(Date startDate) {
    super(startDate);
  }

  @Override
  public String jobDescription() {
    return "Suit";
  }

  public Collection<Employee> getReports() {
    return reports;
  }
}
