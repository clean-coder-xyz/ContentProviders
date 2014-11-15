package com.cleancoder.contentproviders;

import android.content.Intent;
import android.test.AndroidTestCase;

import com.cleancoder.contentproviders.Student;

/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 26.10.2014.
 */
public class TestStudent extends AndroidTestCase {

    public void testEmptyConstructor() {
        Student student = new Student();
        assertNull(student.getName());
        assertNull(student.getAge());
        assertNull(student.getAcademicYear());
    }

    public void testParameterizedConstructor() {
        String testName = "John Average";
        Integer testAge = 18;
        Integer testAcademicYear = 1;
        Student student = new Student(testName, testAge, testAcademicYear);
        assertEquals(testName, student.getName());
        assertEquals(testAge, student.getAge());
        assertEquals(testAcademicYear, student.getAcademicYear());
    }

    public void testCopyConstructor() {
        Student original = new Student("Emmy", 19, 3);
        Student copy = new Student(original);
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getAge(), copy.getAge());
        assertEquals(original.getAcademicYear(), copy.getAcademicYear());
    }

    public void testEquals() {
        Student original = new Student("Jimmy", 19, 2);
        Student copy = new Student(original);
        assertEquals(original, copy);
        Student notCopy = new Student(
                "Not copy of " + original.getName(),
                original.getAge(),
                original.getAcademicYear());
        assertFalse(original.equals(notCopy));
    }

    public void testHashCode() {
        Student student1 = new Student("somebody", 20, 2);
        Student student2 = new Student(student1);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    public void testParcelable() {
        Student studentIn = new Student("Jack", 25, 6);
        // Parcel parcel = Parcel.obtain();
        // studentIn.writeToParcel(parcel, 0);
        // Student studentOut = Student.CREATOR.createFromParcel(parcel);
        Intent intent = new Intent();
        intent.putExtra("student", studentIn);
        Student studentOut = intent.getParcelableExtra("student");
        assertEquals(studentIn, studentOut);
    }

    public void testWithName() {
        Student student1 = new Student("student1", 20, 3);
        String testName = "student2";
        Student student2 = student1.withName(testName);
        assertNotNull(student2);
        assertNotSame(student2, student1);
        assertEquals(testName, student2.getName());
        assertEquals(student1.getAge(), student2.getAge());
        assertEquals(student1.getAcademicYear(), student2.getAcademicYear());
    }

    public void testWithAge() {
        Student student1 = new Student("Samuel", 17, 1);
        Integer testAge = student1.getAge() + 4;
        Student student2 = student1.withAge(testAge);
        assertNotNull(student2);
        assertNotSame(student2, student1);
        assertEquals(student1.getName(), student2.getName());
        assertEquals(testAge, student2.getAge());
        assertEquals(student1.getAcademicYear(), student2.getAcademicYear());
    }


    public void testWithAcademicYear() {
        Student student1 = new Student("Columbia", 19, 2);
        Integer testAcademicYear = student1.getAcademicYear() + 1;
        Student student2 = student1.withAcademicYear(testAcademicYear);
        assertNotNull(student2);
        assertNotSame(student2, student1);
        assertEquals(student1.getName(), student2.getName());
        assertEquals(student1.getAge(), student2.getAge());
        assertEquals(testAcademicYear, student2.getAcademicYear());
    }

}
