# Watcher
How do you know if a bean has mutated? You watch it. But watching it yourself is boring, so you make a... WATCHER.

## What is this all about?
This is a very simple evening hack. It's all about checking if state has mutated in a bean.
Is there a simple way of doing this in Java? No. But you can get kinda close by using proxies.

## What about ${GOOD_IDEA}? Why not use that?
Because it's fun to learn new things for yourself, and I wanted to learn more AOP-techniques by myself. 
There are ways to deal with state changes in beans, JEE has stuff specifically made for that. But this isn't about these.
This is a small little thing, with the bare minimum of stuff to make it useful.

- A proxy factory, so you can easily proxy your objects, or set up your IoC container to proxy them for you.
- A functional interface, so it's nice and easy to set up what methods you want callbacks for.
- A simple (non Enterprise, unmanaged) bean that packages information up for you, so your callback isn't a mess.
- An annotation to put on methods when you want them watched.
- Two small demos to show some example usage.

## Am I right in assuming that this is not production code, etc.?
You're absolutely right. This is just me having fun and seeing how things work.

## Why are there no unit tests?
Strictly speaking, what else would you call the demos? Unit tests are not synonymous with JUnit. That being said, there are no real unit tests, because this isn't a real project. This is just exploratory programming, and the goal is to see if something can be done or not. Some people like test-first. I like test-while. However, I'm not religiously opposed to other people doing things in another order. There's also the question of what sort of things you should test, and how.

For example, does our built-in MethodPredicates work? They do, the demos show it, but how would we test this with a unit test? Wouldn't it just be subsets of hte code that has been written for the demos?

Now, if this was production code (which it is not!), I would agree. I would want a healthy battery of tests that tested things. That method calls went through. What happens when you try to use reflection to get at the fields of a proxied object? What about public fields? Etc.

Of course, the most important test would be to attempt to trigger exceptions being called, making sure that the stacktraces were helpful-ish, that useful information was logged, that there would only be a singular Exception-type there during creation, etc. That sort of really-helpful stuff makes your day so much better when things explode. A unit test that says that yes, you can call methods is useful. A proper error message that tells you exactly what went wrong, why, and by the way, here's the entire state so you can see for yourself what happened. (did autoboxing go south? Did some varargs not work as expected? did someone just call with an illegal argument? Did someone swap out the called method just to see what happens, and after we fixed a bug somewhere, this came back to bite us?)

Of course, if you want to add a unit test (JUnit 5 pls.), feel free to open a pull request. I would just assume you found it fun to do so.