                                 blog
                                 ====

Author: John <jtroxel@jtroxel-dell-linux.mygazoo.com>
Date: 2012-02-22 21:39:03 MST


Table of Contents
=================
1 OIP - Objects for Testing 
    1.1 Doubling the CUT with (anonymous) subclasses 
    1.2 Doubling collaborators with Stubs 
        1.2.1 Groovy 
        1.2.2 Ruby 
    1.3 Validating interactions with Mocks 
    1.4 More Convenient Doubling with "Cyborgs" 
        1.4.1 Ruby 
        1.4.2 Groovy with metaprogramming? 
    1.5 Isolating the CUT from collaborators - Stubs 
        1.5.1 stub collaborators 
    1.6 Verfifying collaboration - Mocks 
        1.6.1 mock libraries 


1 OIP - Objects for Testing 
~~~~~~~~~~~~~~~~~~~~~~~~~~~~
TDD is a different way of thinking, and learning to write good tests or specs is a journey.  Ultimately we want tests that properly islolate the topic, which often means special kinds of objects, just for testing.  Martin Fowler calls these special objects "[Doubles]," though quite often people refer to all doubles as mocks.  Let's look at some different kinds of doubles and how they help us isolate what we're testing.
Let's start with scenario where the Class Under Test (CUT) has internal logic that we want to isolate for testing.  When testing a method of the class under test, you often want to avoid/control other methods of that class that are called by the MUT.  For example, in the following Groovy class, we would like to test 
class Cut {
  def methodUnderTest() {
    def foo = internal()
    println("${foo}")
  }
  def internal() {
    def retVal;
    // Some complex or dependency-ridden code to set our retVal
    retVal
  }
}


[Doubles]: http://martinfowler.com/articles/mocksArentStubs.html

1.1 Doubling the CUT with (anonymous) subclasses 
=================================================
The easiest way, and perhaps most readable, to create a double of the CUT--with collaborating methods stubbed--is to use a concrete subclass.  In Groovy (and Java), you can do this in an anonymous form that reads pretty clearly.  
* Groovy 
  Cut cut = new Cut() {
    def internal() {
      "just return some string"
    }
  }
* Ruby 
  Ruby also has a nice syntax for anonymous singleton objects
  cut = Cut.new
  class << cut
    def internal
      "just return some string"
    end
  end
  Further on I will discuss how there might be even easier ways to create one-off doubles from real classes.

1.2 Doubling collaborators with Stubs 
======================================
class Cut {
  Collab someInjectedCollaborator

  def methodUnderTest() {
    // Do some stuff
    someInjectedCollaborator.doIt(a, b, c)
    // Do more stuff, return something
}
When you have a collaborator of your CUT, it often makes sense to create a double that just fulfills the interactions with the hunk of code you are testing.  These special objects are called Stubs.  

1.2.1 Groovy 
-------------
In Groovy, you can use the built in Mocking (stubs are tangled up with mocks in this case).  Or you can just use coercion to create your stub, since for typical "thin" stubs all we care about is the interactions, the stub can be completely fake otherwise.
  cut = new Cut()
  cut.someInjectedCollaborator = [doIt: { a, b, c -> return null }] as Collab

1.2.2 Ruby 
-----------
Ruby, being fully dynamic, has lots of options for creating doubles.  We use rspec with one of my clients, so I'll show examples of that.  The following is how you can create a "full" stub:  a completely different class than the collaborator with only provided behavior:
  cut.some_injected_collaborator = stub(doIt: nil)

1.3 Validating interactions with Mocks 
=======================================

1.4 More Convenient Doubling with "Cyborgs" 
============================================

1.4.1 Ruby 
-----------
The following is a minor variation on the full stub created above.  Here the rspec stub method (mixed in) only replaces doIt on an existing object.
  cut.some_injected_collaborator.stub(doIt: nil)

1.4.2 Groovy with metaprogramming? 
-----------------------------------
You can also redefine the methods using metaprogramming, language permitting
* Groovy 
  def cut = new CUT()
  cut.metaClass.cut = { println("ANON ${foo}") } // should work
  // however, "overriding" foo will not work ([http://jira.codehaus.org/browse/GROOVY-3942])
  Fat Stubs or Cyborgs?  Replacing just a price or 2.  Ruby "should."
  

1.5 Isolating the CUT from collaborators - Stubs 
=================================================

1.5.1 stub collaborators 
-------------------------
* stub libraries 
* metaprogramming 

1.6 Verfifying collaboration - Mocks 
=====================================

1.6.1 mock libraries 
---------------------
