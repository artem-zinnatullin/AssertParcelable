### AssertParcelable

Q: Need to verify `android.os.Parcelable` implementation in your tests?

A: It's easy as `assertThatObjectParcelable(someObject)`!

####Download

`com.artemzin.assert-parcelable:assert-parcelable:1.0.1`

All versions on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22assert-parcelable%22).

---------------
Example of `build.gradle`:

```groovy
// Add for unit tests (Robolectric, etc)
testCompile 'com.artemzin.assert-parcelable:assert-parcelable:1.0.1'

// Or/and add for instrumentation tests (emulator, device, etc)
androidTestCompile 'com.artemzin.assert-parcelable:assert-parcelable:1.0.1'
```

----------------
Example of usage

```java
import static AssertParcelable.assertThatObjectParcelable;

@Test
public void verifyThatMyClassIsParcelable() {
  // Notice that your class must implement equals()!
  MyParcelableClass object = new MyParcelableClass("someValue");
  assertThatObjectParcelable(object);
}
```
