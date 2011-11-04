require './employee.rb'

class Manager < Employee

  attr_accessor :reports # accessor
  def initialize(start)
    super(start)
    @reports = []
  end

  def job_description
    "Suit"
  end

end

#m = Manager.new(Date.today)
#puts m.start_date
#m.reports <<= "blah"
#puts m.reports
