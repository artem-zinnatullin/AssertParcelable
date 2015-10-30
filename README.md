### AssertParcelable

Q: Need to verify `android.os.Parcelable` implementation in your tests?

A: It's easy as `assertObjectParcelable(someObject)`!

####Download

`com.artemzin.assert-parcelable:assert-parcelable:1.0.0`

---------------
Example of `build.gradle`:

```groovy
// Add for unit tests (Robolectric, etc)
testCompile 'com.artemzin.assert-parcelable:assert-parcelable:insert-latest-version-here'

// Or/and add for instrumentation tests (emulator, device, etc)
androidTestCompile 'com.artemzin.assert-parcelable:assert-parcelable:insert-latest-version-here'
```

----------------
Example of usage

```java
import static AssertParcelable.assertObjectParcelable;

@Test
public void verifyThatMyClassIsParcelable() {
  // Notice that your class must implement equals()!
  MyParcelableClass object = new MyParcelableClass("someValue");
  assertObjectParcelable(object);
}
```
