package com.moon.util;

import com.moon.util.assertions.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author benshaoye
 */
class MapperUtilTestTest {

    static final Assertions assertions = Assertions.of();

    Object data;

    @Test
    void testToInstance() {
    }

    @Test
    void testToInstance1() {
    }

    @Test
    void testOverride() {

    }

    @Test
    void testOverride1() {
    }

    @Test
    void testForEachToMap() {
    }

    @Test
    void testForEachToInstance() {
    }

    @Test
    void testForEachToOtherInstance() {
    }

    public static class Employee {
        String name;
        int age;

        public Employee() {
        }

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Employee employee = (Employee) o;
            return age == employee.age &&
                Objects.equals(name, employee.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Employee{");
            sb.append("name='").append(name).append('\'');
            sb.append(", age=").append(age);
            sb.append('}');
            return sb.toString();
        }
    }

    @Test
    void testToMap() {
        Employee employee1 = new Employee("张三", 24);
        Map<String, Object> map = MapperUtil.toMap(employee1);
        assertions.assertTrue(map.containsKey("name"));
        assertions.assertEquals(map.get("name"), "张三");
        assertions.assertTrue(map.containsKey("age"));
        assertions.assertEquals(map.get("age"), 24);

        Employee e1 = MapperUtil.override(map, new Employee());
        assertions.assertNotSame(employee1, e1);
        assertions.assertEquals(employee1, e1);

        Console.out.println(System.identityHashCode(employee1));
        Console.out.println(System.identityHashCode(e1));

        Employee e2 = MapperUtil.toInstance(map, Employee.class);
        assertions.assertNotSame(employee1, e2);
        assertions.assertEquals(employee1, e1);
        assertions.assertEquals(e2, e1);

        Console.out.println(System.identityHashCode(employee1));
        Console.out.println(System.identityHashCode(e1));
        Console.out.println(System.identityHashCode(e2));
    }
}