### AssertParcelable

Checks that class properly implements `android.os.Parcelable` interface forcing Framework to serialize and deserialize it and then checks that deserialized object is equal to original one.

Works in both Instrumentation and Robolectric environments.

#### Download

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

Usage example:

```java
import static AssertParcelable.assertThatObjectParcelable;

@Test
public void verifyThatMyClassIsParcelable() {
  // Class must properly implement equals()!
  MyParcelableClass object = new MyParcelableClass("someValue");
  assertThatObjectParcelable(object);
}
```
