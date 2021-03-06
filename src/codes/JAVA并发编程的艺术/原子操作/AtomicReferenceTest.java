package codes.JAVA并发编程的艺术.原子操作;

import java.util.concurrent.atomic.AtomicReference;

/**
 * # @author  chilcyWind
 * # @Time   2020/7/22 22:52
 * # @version 1.0
 * # @File : AtomicReferenceTest.java
 * # @Software: IntelliJ IDEA
 */
public class AtomicReferenceTest {
    public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();

    public static void main(String[] args) {
        User user = new User("conan", 15);
        atomicUserRef.set(user);
        User updateUser = new User("Shinichi", 17);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
