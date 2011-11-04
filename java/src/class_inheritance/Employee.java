package class_inheritance;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Employee: TODO
 *
 * @author <a href="mailto:jtroxel@yahoo.com">John Troxel</a>
 */
public class Employee {
  private Date startDate;

  public Employee(Date startDate) {
    this.startDate = startDate;
  }

  public Integer monthsOnJob() {
    // Do the math, maybe use something like the joda lib
    return 0;
  }

  public String jobDescription() {
    return "New employee"; // Default description, will be overriden by subclasses
  }

  // Accessors, more code
}
