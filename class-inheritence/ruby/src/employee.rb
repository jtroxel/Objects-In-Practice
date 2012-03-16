require 'date'

class Employee
  attr_accessor :start_date # method that creates accessors

  def initialize(start)
    @start_date = start
  end

  def months_on_job
    # Math is easy
    (Date.today.year*12 + Date.today.month) - (@start_date.year*12 + @start_date.month)
  end

  def job_description
    "New employee"
  end

end

#puts Employee.new(Date.today).start_date
#puts Employee.new(Date.today-35).months_on_job
