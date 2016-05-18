package tests.org.iovorobiev

import junit.framework.TestCase
import org.iovorobiev.hashing.UniversalHashMap

class HashMapTest : TestCase() {
    val LOST_ELEMENTS = 2;

    fun testHashMap() {
        val hashMap = UniversalHashMap()
        val keys = ShortArray(8128)
        for (i in 1..keys.size - 1 - LOST_ELEMENTS) {
            keys[i] = (Math.random()
                    * ((Short.MAX_VALUE.toDouble()- LOST_ELEMENTS) * 2.0 )
                    - (Short.MAX_VALUE - LOST_ELEMENTS)).toShort()
            val value = makeValue(keys[i]);
            hashMap.put(keys[i], value)
        }
        for (i in 1..keys.size - 1 - LOST_ELEMENTS) {
            assertEquals(makeValue(keys[i]), hashMap.get(keys[i]))
        }
        assertEquals(null, hashMap.get((Short.MAX_VALUE - 1).toShort()))
        assertEquals(null, hashMap.get(Short.MAX_VALUE))
    }

    fun testReplace() {
        val hashMap = UniversalHashMap()
        hashMap.put(12, "Twelve")
        hashMap.put(12, "Hello")
        assertEquals("Hello", hashMap.get(12))
    }

    private fun makeValue(key: Short) = key.toString()
}