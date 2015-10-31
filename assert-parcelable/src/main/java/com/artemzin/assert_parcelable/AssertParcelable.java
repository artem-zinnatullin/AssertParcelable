package com.artemzin.assert_parcelable;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public final class AssertParcelable {

    private AssertParcelable() {
        throw new IllegalStateException("No instances please!");
    }

    /**
     * Asserts that passed object is {@link Parcelable}.
     * Notice: your object should implement {@link Object#equals(Object)} correctly in order to
     * compare objects before and after parcelization.
     *
     * @param object non-null object to check whether it has correctly
     *               implemented {@link Parcelable} or not.
     * @param <T>    type of the object.
     */
    public static <T extends Parcelable> void assertThatObjectParcelable(T object) {
        if (object == null) {
            throw new NullPointerException("object == null");
        }

        final Parcel parcel = Parcel.obtain();

        try {
            object.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);

            final Parcelable.Creator<T> CREATOR;

            try {
                final Field creatorField = object.getClass().getDeclaredField("CREATOR");

                if (!Modifier.isPublic(creatorField.getModifiers())) {
                    throw new AssertionError(object.getClass().getSimpleName() + ".CREATOR " +
                            "is not public");
                }

                creatorField.setAccessible(true);
                CREATOR = (Parcelable.Creator<T>) creatorField.get(null);
            } catch (NoSuchFieldException e) {
                throw new AssertionError(object.getClass().getSimpleName() + ".CREATOR " +
                        "public static field must be presented in the class");
            } catch (IllegalAccessException e) {
                throw new AssertionError(object.getClass().getSimpleName() + ".CREATOR " +
                        "is not accessible");
            } catch (ClassCastException e) {
                throw new AssertionError(object.getClass().getSimpleName() + ".CREATOR " +
                        "field must be of type android.os.Parcelable.Creator" +
                        "<" + object.getClass().getSimpleName() + ">");
            }

            final T objectFromParcelable = CREATOR.createFromParcel(parcel);

            if (!object.equals(objectFromParcelable)) {
                throw new AssertionError("Object before serialization is not equal to object " +
                        "after serialization!\nOne of the possible problems -> " +
                        "incorrect implementation of equals()." +
                        "\nobject before = " + object +
                        ", object after = " + objectFromParcelable);
            }
        } finally {
            parcel.recycle();
        }
    }
}
