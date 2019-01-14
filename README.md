# fullstack

Template for clojure/clojurescript/re-frame application with server/client part connected via websocket. Figwheel for debugging.

Tested with java 1.8 and leiningen 2.8.1, Chrome

Project based on a leiningen template containing server, css, debugging and testing
lein new re-frame fullstack +10x +handler +test

## Development Mode

s### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.
Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).


OR in a injected repl
```
start-figwheel!
```
or in this project execute the function ```fullstack.server/start-dev```


### Run tests:

Install karma and headless chrome

```
npm install -g karma-cli
npm install karma karma-cljs-test karma-chrome-launcher --save-dev
```

And then run your tests

```
lein clean
lein doo chrome-headless test once
```

Please note that [doo](https://github.com/bensu/doo) can be configured to run cljs.test in many JS environments (phantom, chrome, ie, safari, opera, slimer, node, rhino, or nashorn).

## Production Build

```
lein clean
lein with-profile prod uberjar
```
or just lein uberjar

That should compile the clojurescript code first, and then create the standalone jar.

When you run the jar you can set the port the ring server will use by setting the environment variable PORT.
If it's not set, it will run on port 3000 by default.


To deploy to heroku, first create your app:

```
heroku create
```

Then deploy the application:

```
git push heroku master
```

To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```

### Run production build

run the compiled uberjar with
jave -cp "./*" fullstack.server &

