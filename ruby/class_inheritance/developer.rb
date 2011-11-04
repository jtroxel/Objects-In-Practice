require "./employee.rb"
class Developer < Employee

  attr_accessor :technical_skills

  def initialize(start)
    super(start)
    @technical_skills = []
  end

  def job_description
    ret = "Developer: "
    @technical_skills.each {|skill| ret << " ${skill"}
  end

end

#d = Developer.new(Date.today)
#d.technical_skills <<= "perl"
#puts d.job_description