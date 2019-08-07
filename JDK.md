# JDK源码笔记

## java.lang.Object

1. wait()

   

## java.lang.String

1. value[] 字符数组存储

2. hash 默认0

   ```java
    public int hashCode() {
           int h = hash;
           if (h == 0 && value.length > 0) {
               char val[] = value;
   
               for (int i = 0; i < value.length; i++) {
                   h = 31 * h + val[i];
               }
               hash = h;
           }
           return h;
       }
   
   ```

   

3. equals()

   ```java
    public boolean equals(Object anObject) {
           //先判断引用（地址）
           if (this == anObject) {
               return true;
           }
           if (anObject instanceof String) {
               String anotherString = (String)anObject;
               int n = value.length;
               //字符串长度判断
               if (n == anotherString.value.length) {
                   char v1[] = value;
                   char v2[] = anotherString.value;
                   int i = 0;
                   while (n-- != 0) {
                       if (v1[i] != v2[i])
                           return false;
                       i++;
                   }
                   return true;
               }
           }
           return false;
       }
   ```

   

4. startWith()

   ```java
   public boolean startsWith(String prefix, int toffset) {
           char ta[] = value;
           int to = toffset;
           char pa[] = prefix.value;
           int po = 0;
           int pc = prefix.value.length;
           // Note: toffset might be near -1>>>1.
       	//避免数组越界
           if ((toffset < 0) || (toffset > value.length - pc)) {
               return false;
           }
           while (--pc >= 0) {
               if (ta[to++] != pa[po++]) {
                   return false;
               }
           }
           return true;
       }
   
   ```

5. trim() 去除字符串首尾的空格

6. replace()

   ```java
   public String replace(char oldChar, char newChar) {
           if (oldChar != newChar) {
               int len = value.length;
               int i = -1;
               char[] val = value; /* avoid getfield opcode */
   
               while (++i < len) {
                   if (val[i] == oldChar) {
                       break;
                   }
               }
               if (i < len) {
                   char buf[] = new char[len];
                   for (int j = 0; j < i; j++) {
                       buf[j] = val[j];
                   }
                   while (i < len) {
                       char c = val[i];
                       buf[i] = (c == oldChar) ? newChar : c;
                       i++;
                   }
                   return new String(buf, true);
               }
           }
           return this;
       }
   
   ```

## java.lang.AbstractStringBuilder

1. value[]

2. count 字符串长度

3. 扩容,翻倍，尽量取最少

   ```java
     private int newCapacity(int minCapacity) {
           // overflow-conscious code
           int newCapacity = (value.length << 1) + 2;
           if (newCapacity - minCapacity < 0) {
               newCapacity = minCapacity;
           }
           return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0)
               ? hugeCapacity(minCapacity)
               : newCapacity;
       }
   ```

4. 每次增加字符串长度时，都会去ensureCapacityInternal(minimumCapacity)

## java.lang.StringBuffer

1. 继承AbstractStringBuilder

2. toStringCache

   ```java
       /**
        * A cache of the last value returned by toString. Cleared
        * whenever the StringBuffer is modified.
        */
       private transient char[] toStringCache;
   ```

3. synchronized方法保证线程安全

4. toString 

   ```java
   public synchronized String toString() {
           if (toStringCache == null) {
               toStringCache = Arrays.copyOfRange(value, 0, count);
           }
           return new String(toStringCache, true);
       }
   ```

## java.lang.StringBuilder

1. 继承AbstractStringBuilder

2. 线程不安全

3. toString

   ```java
   public String toString() {
           // Create a copy, don't share the array
           return new String(value, 0, count);
       }
   ```

   