Reakshon
====

When developing an Android application, have you dreamed about special tool that offers you a callbacks about methods being called in
production code?

Reakshon offers you a super simple approach to do so. The only thing you need in your production code is to annotate the methods you care
about with `@ReakshonTag`. Like this:

```java
@ReakshonTag
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
}
```

And you will receive callback if you add this part of code to your Application class:

```java
Rshn.withActivity(MainActivity.class, "onCreate")
    .subscribe(new Consumer<MainActivity>() {
        @Override
        public void accept(MainActivity mainActivity) throws Exception {
            Log.i("Reakshon", "MainActivity.onCreate() called on " + mainActivity);
        }
    });
```

Look at the sample app's code for example of using Reakshon. Look at the bindings in `AppDebug` and annotations in `MainActivity`.

Sample app is pure sample project from Android SDK.

License
--------

    Copyright 2017 Pavel Sliusar
    Inspired by Jake Wharton's Hugo https://github.com/JakeWharton/hugo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
