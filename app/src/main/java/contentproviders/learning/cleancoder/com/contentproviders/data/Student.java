package contentproviders.learning.cleancoder.com.contentproviders.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Objects;

/**
 * Created by Leonid Semyonov (clean-coder-xyz) on 26.10.2014.
 */
public class Student implements Parcelable {

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel parcel) {
            return new Student(parcel);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    private final Integer academicYear;
    private final Integer age;
    private final String name;

    public Student() {
        this.name = null;
        this.age = null;
        this.academicYear = null;
    }


    public Student(String name, Integer age, Integer academicYear) {
        this.name = name;
        this.age = age;
        this.academicYear = academicYear;
    }

    public Student(Student origin) {
        this.name = origin.getName();
        this.age = origin.getAge();
        this.academicYear = origin.getAcademicYear();
    }

    public Student(Parcel in) {
        this.name = in.readString();
        this.age = (Integer) in.readValue(Integer.class.getClassLoader());
        this.academicYear = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(name);
        out.writeValue(age);
        out.writeValue(academicYear);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Student withName(String name) {
        return new Student(name, getAge(), getAcademicYear());
    }

    public Student withAge(int age) {
        return new Student(getName(), age, getAcademicYear());
    }

    public Student withAcademicYear(int academicYear) {
        return new Student(getName(), getAge(), academicYear);
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getAcademicYear() {
        return academicYear;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return (Objects.equal(getName(), other.getName()) &&
                Objects.equal(getAge(), other.getAge()) &&
                Objects.equal(getAcademicYear(), other.getAcademicYear()));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getAge(), getAcademicYear());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", getName())
                .add("age", getAge())
                .add("academicYear", getAcademicYear())
                .toString();
    }

}
