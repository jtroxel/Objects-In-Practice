Author: John <jtroxel@jtroxel-dell-linux.mygazoo.com>
Date: 2012-02-22 21:39:03 MST

# OIP - Objects for Testing 
Automated testing is a critical developing quality code.  And some argue (including me) that tests or TDD actually increase productivity overall, in addition to the other benefits: providing a safety net for refactoring, regressons testing, evaluating coverage.  I believe that, if you know what you're doing, most problems are easier to tackle using TDD.

Ultimately we want tests that properly isolate the topic, which often means utilizing special kinds of objects just for testing.  Martin Fowler calls these special objects "[Doubles]," though quite often people refer to all doubles generically as mocks.  Let's look at some different kinds of doubles and how they help us isolate what we're testing.
Let's start with scenario where the Class Under Test (CUT) has internal logic that we want to isolate for testing.  When testing a method of the class under test, you often want to avoid/control other methods of that class that are called by the MUT.  One might argue that doubling methods of the CUT is a code smell--replacing method logic when you should be factoring out collaborators--but often it is just more practical to isolate your logic this way.  Especially if you are adding tests to existing classes, maybe not your own.  Here is what doubling collaborating methods in the CUT looks like: in the following Groovy class, we would like to test methodUnderTest.

```groovy
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
```

[Doubles]: http://martinfowler.com/articles/mocksArentStubs.html

## Doubling the CUT with (anonymous) subclasses 
The easiest way, and perhaps most readable, to create a double of the CUT--with collaborating methods stubbed--is to use a concrete subclass.  In Groovy (and Java), you can do this in an anonymous form that reads pretty clearly.  

```groovy 
  Cut cut = new Cut() {
    def internal() {
      "just return some string"
    }
  }
``` 

  Ruby also has a nice syntax for anonymous singleton objects
```ruby
  cut = Cut.new
  class << cut
    def internal
      "just return some string"
    end
  end
```
Further on I will discuss how there might be even easier ways to create one-off doubles of real objects.

## Doubling collaborators with Stubs 
Check out the following scenario in Groovy

```groovy
class Cut {
  Collab someInjectedCollaborator

  def methodUnderTest() {
    // Do some stuff
    someInjectedCollaborator.doIt(a, b, c)
    // Do more stuff, return something
}
```

When you have a collaborator of your CUT, it often makes sense to create a double that just fulfills the interactions with the hunk of code you are testing.  These special objects are called Stubs.  Often, when using stub helpers provided by the language or libraries, we're talking about a complete imposter that only provides the interactions provided before the action of the test.

In Groovy, you can use the built in Mocking (stubs are tangled up with mocks in this case).  Or you can just use coercion to create your stub, since for typical "thin" stubs all we care about is the interactions, the stub can be completely fake otherwise.

```groovy
  cut = new Cut()
  cut.someInjectedCollaborator = [doIt: { a, b, c -> return null }] as Collab
```

Ruby, being fully dynamic, has lots of options for creating doubles.  We use rspec with one of my clients, so I'll show examples of that.  The following is how you can create a thin, imposter stub:  a completely different class than the collaborator with only provided behavior:

```ruby
  cut.some_injected_collaborator = stub(do_it: nil)
```

## Validating interactions with Mocks 
Mocks are a kind of double where the developer specifies what interactions are expected, and the testing framework verifies this.  There are lots of library options out there for creating these full mocks, basically complete imposters that throw errors if the expected interactions don't occur.  For Groovy take a look at the built-in mockFor or Spock mocks.  In Ruby, this kind of mock is usually not necessary:  developers can easily add mock behavior to real objects.
```ruby
  cut.some_collaborator.should_receive(:do_it) {nil}
```

## More Convenient Doubling with "Cyborgs" (Partial Mocks)

The following is a minor variation on the full stub created above.  Here the rspec stub method (mixed in) only replaces doIt on an existing object.

```ruby
  cut.some_injected_collaborator.stub(do_it: nil)
```  

In Groovy, within limits, we can create similar cyborg stubs with a little metaprogramming

```groovy
  def cut = new CUT()
  cut.metaClass.cut = { println("ANON ${foo}") } // should work
  // however, "overriding" foo will not work ([http://jira.codehaus.org/browse/GROOVY-3942])
```

