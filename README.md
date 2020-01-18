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
- A small demo to show some example usage.

There's also an annotation, but that's just there for some other ideas I had.

## Am I right in assuming that this is not production code, etc.?
You're absolutely right. This is just me having fun and seeing how things work.
