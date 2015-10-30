package com.artemzin.assert_parcelable;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AssertParcelableTest {

    static class ClassWithOkParcelableImpl implements Parcelable {

        private final String someString;

        ClassWithOkParcelableImpl(String someString) {
            this.someString = someString;
        }

        protected ClassWithOkParcelableImpl(Parcel in) {
            someString = in.readString();
        }

        public static final Creator<ClassWithOkParcelableImpl> CREATOR = new Creator<ClassWithOkParcelableImpl>() {
            @Override
            public ClassWithOkParcelableImpl createFromParcel(Parcel in) {
                return new ClassWithOkParcelableImpl(in);
            }

            @Override
            public ClassWithOkParcelableImpl[] newArray(int size) {
                return new ClassWithOkParcelableImpl[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(someString);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassWithOkParcelableImpl that = (ClassWithOkParcelableImpl) o;

            return !(someString != null ? !someString.equals(that.someString) : that.someString != null);
        }

        @Override
        public int hashCode() {
            return someString != null ? someString.hashCode() : 0;
        }
    }

    @Test
    public void assertThatObjectParcelable_positive() {
        ClassWithOkParcelableImpl object = new ClassWithOkParcelableImpl("yo");
        AssertParcelable.assertThatObjectParcelable(object); // should not throw any exceptions
    }

    @SuppressLint("ParcelCreator")
    static class ClassWithoutCREATOR implements Parcelable {

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    @Test
    public void assertThatObjectParcelable_noCREATORInClass() {
        ClassWithoutCREATOR object = new ClassWithoutCREATOR();

        try {
            AssertParcelable.assertThatObjectParcelable(object);
            failBecauseExceptionWasNotThrown(AssertionError.class);
        } catch (AssertionError expected) {
            assertThat(expected).hasMessage("ClassWithoutCREATOR.CREATOR public static field " +
                    "must be presented in the class");
        }
    }

    static class ClassWithPrivateCREATOR implements Parcelable {

        ClassWithPrivateCREATOR() {

        }

        protected ClassWithPrivateCREATOR(Parcel in) {
        }

        private static final Creator<ClassWithPrivateCREATOR> CREATOR = new Creator<ClassWithPrivateCREATOR>() {
            @Override
            public ClassWithPrivateCREATOR createFromParcel(Parcel in) {
                return new ClassWithPrivateCREATOR(in);
            }

            @Override
            public ClassWithPrivateCREATOR[] newArray(int size) {
                return new ClassWithPrivateCREATOR[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    @Test
    public void assertThatObjectParcelable_privateCREATOR() {
        ClassWithPrivateCREATOR object = new ClassWithPrivateCREATOR();

        try {
            AssertParcelable.assertThatObjectParcelable(object);
            failBecauseExceptionWasNotThrown(AssertionError.class);
        } catch (AssertionError expected) {
            assertThat(expected).hasMessage("ClassWithPrivateCREATOR.CREATOR is not public");
        }
    }

    static class ClassWithProtectedCREATOR implements Parcelable {

        ClassWithProtectedCREATOR() {

        }

        protected ClassWithProtectedCREATOR(Parcel in) {
        }

        protected static final Creator<ClassWithProtectedCREATOR> CREATOR = new Creator<ClassWithProtectedCREATOR>() {
            @Override
            public ClassWithProtectedCREATOR createFromParcel(Parcel in) {
                return new ClassWithProtectedCREATOR(in);
            }

            @Override
            public ClassWithProtectedCREATOR[] newArray(int size) {
                return new ClassWithProtectedCREATOR[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    @Test
    public void assertThatObjectParcelable_protectedCREATOR() {
        ClassWithProtectedCREATOR object = new ClassWithProtectedCREATOR();

        try {
            AssertParcelable.assertThatObjectParcelable(object);
            failBecauseExceptionWasNotThrown(AssertionError.class);
        } catch (AssertionError expected) {
            assertThat(expected).hasMessage("ClassWithProtectedCREATOR.CREATOR is not public");
        }
    }

    static class ClassWithPackagePrivateCREATOR implements Parcelable {

        ClassWithPackagePrivateCREATOR() {

        }

        protected ClassWithPackagePrivateCREATOR(Parcel in) {
        }

        static final Creator<ClassWithPackagePrivateCREATOR> CREATOR = new Creator<ClassWithPackagePrivateCREATOR>() {
            @Override
            public ClassWithPackagePrivateCREATOR createFromParcel(Parcel in) {
                return new ClassWithPackagePrivateCREATOR(in);
            }

            @Override
            public ClassWithPackagePrivateCREATOR[] newArray(int size) {
                return new ClassWithPackagePrivateCREATOR[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }

    @Test
    public void assertThatObjectParcelable_packagePrivateCREATOR() {
        ClassWithPackagePrivateCREATOR object = new ClassWithPackagePrivateCREATOR();

        try {
            AssertParcelable.assertThatObjectParcelable(object);
            failBecauseExceptionWasNotThrown(AssertionError.class);
        } catch (AssertionError expected) {
            assertThat(expected).hasMessage("ClassWithPackagePrivateCREATOR.CREATOR is not public");
        }
    }

    static class ClassWithIncorrectTypeOfCREATOR implements Parcelable {

        public static Object CREATOR = "ha!";

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }

    @Test
    public void assertThatObjectParcelable_incorrectTypeOfCREATOR() {
        ClassWithIncorrectTypeOfCREATOR object = new ClassWithIncorrectTypeOfCREATOR();

        try {
            AssertParcelable.assertThatObjectParcelable(object);
            failBecauseExceptionWasNotThrown(AssertionError.class);
        } catch (AssertionError expected) {
            assertThat(expected).hasMessage("ClassWithIncorrectTypeOfCREATOR.CREATOR field must " +
                    "be of type android.os.Parcelable.Creator<ClassWithIncorrectTypeOfCREATOR>");
        }
    }

    static class ClassWithOkParcelableImplButNoEquals implements Parcelable {

        private final String someText;

        ClassWithOkParcelableImplButNoEquals(String someText) {
            this.someText = someText;
        }

        protected ClassWithOkParcelableImplButNoEquals(Parcel in) {
            someText = in.readString();
        }

        public static final Creator<ClassWithOkParcelableImplButNoEquals> CREATOR = new Creator<ClassWithOkParcelableImplButNoEquals>() {
            @Override
            public ClassWithOkParcelableImplButNoEquals createFromParcel(Parcel in) {
                return new ClassWithOkParcelableImplButNoEquals(in);
            }

            @Override
            public ClassWithOkParcelableImplButNoEquals[] newArray(int size) {
                return new ClassWithOkParcelableImplButNoEquals[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(someText);
        }

        @Override
        public String toString() {
            return "ClassWithOkParcelableImplButNoEquals{" +
                    "someText='" + someText + '\'' +
                    '}';
        }
    }

    @Test
    public void assertThatObjectParcelable_parcelableIsOkButEqualsIsNot() {
        ClassWithOkParcelableImplButNoEquals object = new ClassWithOkParcelableImplButNoEquals("yo");

        try {
            AssertParcelable.assertThatObjectParcelable(object);
            failBecauseExceptionWasNotThrown(AssertionError.class);
        } catch (AssertionError expected) {
            assertThat(expected).hasMessage("Object before serialization is not equal to object " +
                    "after serialization!\nOne of the possible problems -> incorrect " +
                    "implementation of equals().\nobject before = " +
                    "ClassWithOkParcelableImplButNoEquals{someText='yo'}, " +
                    "object after = ClassWithOkParcelableImplButNoEquals{someText='yo'}");
        }
    }
}